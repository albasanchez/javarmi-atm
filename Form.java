public class Form {
  private Console console = new Console();
  private RMIStub stub = new RMIStub();
  private Client client = new Client();

  public boolean checkDocumentIDForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Verificación de usuario");
    System.out.println();
    System.out.print("Introduzca su documento de identidad: ");

    String documentID = console.getInputString();
    client.setDocumentID(documentID);
    boolean response = false;

    try {
      response = stub.getRMIStub().checkDocumentID(documentID);
    } catch (Exception e) {
      System.err.println("CheckDocumentIDForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    return response;
  }

  public boolean registerClientForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Registro de nuevo usuario");
    System.out.println();

    String documentID = client.getDocumentID();

    System.out.print("Introduzca su nombre completo: ");
    String name = console.getInputString();

    System.out.print("Introduzca su nuevo username: ");
    String username = console.getInputString();

    System.out.print("Introduzca su nuevo password: ");
    String password = console.getInputString();

    boolean response = false;
    try {
      response = stub.getRMIStub().registerClient(documentID, name, username, password);
    } catch (Exception e) {
      System.err.println("RegisterClientForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    return response;
  }

  public boolean checkMaxAccountsForm(){
    String documentID = client.getDocumentID();
    boolean response = false;

    try {
      response = stub.getRMIStub().checkMaxAccounts(documentID);
    } catch (Exception e) {
      System.err.println("CheckMaxAccountsForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    return response;
  }

  public boolean verifyUserForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Login");
    System.out.println();

    System.out.print("Introduzca su username: ");
    String username = console.getInputString();

    System.out.print("Introduzca su password: ");
    String password = console.getInputString();

    boolean response = false;

    try {
      response = stub.getRMIStub().verifyUser(username, password);
    } catch (Exception e) {
      System.err.println("VerifyUserForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    return response;
  }

  public void intialDepositForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Depósito inicial para nueva cuenta");
    System.out.println();

    System.out.print("Introduzca su depósito inicial: ");
    double deposit = console.getInputDouble();
    String documentID = client.getDocumentID();

    String response = "";

    try {
      response = stub.getRMIStub().intialDeposit(documentID, deposit);
    } catch (Exception e) {
      System.err.println("IntialDepositForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Resumen de apertura de cuenta");
    System.out.println();
    System.out.println("Número de cuenta: " + response);
    System.out.println("Balance actual: " + deposit);
    System.out.println();
  }

  public void openAccount(){
    if(this.checkDocumentIDForm()){
      if(this.checkMaxAccountsForm()){
        if(this.verifyUserForm()){
          this.intialDepositForm();
          console.stopConsole();
        }else{
          console.clearConsole();
          System.out.println("UCAB ATM (RPC/RMI) - Error de login");
          System.out.println();
          console.stopConsole();
        }
      } else {
        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Máximo número de cuentas por usuario");
        System.out.println();
        System.out.println("No se puede agregar una nueva cuenta ya que alcanzó el límite establecido por usuario!");
        console.stopConsole();
      }
    }else{
      if(this.registerClientForm()){
        if(this.verifyUserForm()){
          this.intialDepositForm();
        }else{
          console.clearConsole();
          System.out.println("UCAB ATM (RPC/RMI) - Error de login");
          System.out.println();
          console.stopConsole();
        }
      }else{
        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Error de registro");
        System.out.println();
        System.out.println("Error al registrar un nuevo usuario, intente nuevamente!");
        console.stopConsole();
      }
    }
  }
}
