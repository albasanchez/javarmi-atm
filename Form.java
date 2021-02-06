import java.util.*;
public class Form {
  private Console console = new Console();
  private RMIStub stub = new RMIStub();
  private Client client = new Client();

  public void getUserDocumentID(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Documento de identidad del usuario");
    System.out.println();
    System.out.print("Introduzca su documento de identidad: ");

    String documentID = console.getInputString();
    client.setDocumentID(documentID);
  }

  private boolean checkDocumentIDForm(){
    String documentID = client.getDocumentID();
    boolean response = false;

    try {
      response = stub.getRMIStub().checkDocumentID(documentID);
    } catch (Exception e) {
      System.err.println("CheckDocumentIDForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    return response;
  }

  private boolean registerClientForm(){
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

  private boolean checkMaxAccountsForm(){
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

    String documentID = client.getDocumentID();
    boolean response = false;

    try {
      response = stub.getRMIStub().verifyUser(documentID, username, password);
    } catch (Exception e) {
      System.err.println("VerifyUserForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    return response;
  }

  private void intialDepositForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Depósito inicial para nueva cuenta");
    System.out.println();

    System.out.print("Introduzca su depósito inicial: ");
    double deposit = console.getInputDouble();
    String documentID = client.getDocumentID();

    Number response = 0;

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

  public void readUsersAccountsForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Cuentas del usuario");
    System.out.println();

    String documentID = client.getDocumentID();
    List<String> accounts = new ArrayList<String>();

    try {
      accounts = stub.getRMIStub().getUserAccounts(documentID);
    } catch (Exception e) {
      System.err.println("ReadUsersAccountsForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    for (int i = 1; i < accounts.size() + 1; i++) {
      System.out.println(i + ". Número de cuenta: " + accounts.get(i-1));
    }

    System.out.println();
    System.out.print("Introduzca el número de cuenta que desea consultar: ");
    int account = console.getInputInt();
    double balance = 0;
    List<Transaction> transactions = new ArrayList<Transaction>();

    try {
      transactions = stub.getRMIStub().getAccountLastTransactions(documentID, account);
      balance = stub.getRMIStub().getAccountBalance(documentID, account);
    } catch (Exception e) {
      System.err.println("ReadUsersAccountsForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Resumen de cuenta " + account);
    System.out.println();
    System.out.println("Balance actual: " + balance);
    System.out.println();

    System.out.println("Últimas 5 transacciones registradas:");

    for (int i = 1; i < transactions.size() + 1; i++) {
      System.out.println(i + ". ID(" + transactions.get(i-1).getTransactionID()+ ") Fecha: " + transactions.get(i-1).getTransactionDate() + " [Monto: " + transactions.get(i-1).getTransactionAmount() + "]");
      System.out.println("--- Tipo: " + transactions.get(i-1).getTransactionType());
      System.out.println("--- Cuenta origen: " + transactions.get(i-1).getTransactionSource());
      System.out.println("--- Cuenta destino: " + transactions.get(i-1).getTransactionDestination());
      System.out.println("--- Descripción: " + transactions.get(i-1).getTransactionDescription());
      System.out.println();
    }

    console.stopConsole();
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
        System.out.println();
        console.stopConsole();
      }
    }else{
      if(this.registerClientForm()){
        if(this.verifyUserForm()){
          this.intialDepositForm();
          console.stopConsole();
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
        System.out.println();
        console.stopConsole();
      }
    }
  }
}
