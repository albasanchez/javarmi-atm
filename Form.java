import java.util.*;
public class Form {
  private Console console = new Console();
  private RMIStub stub = new RMIStub();
  private Client client = new Client();

  //[BANNER] Indicador de cuenta incorrecta
  private void accountNotMatch(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Número de cuenta incorrecto");
    System.out.println();
    System.out.println("El número de cuenta no concuerda con ninguna de las cuentas indicadas!");
    System.out.println();
  }

  //[BANNER] Listado de cuentas según el documento de identidad del usuario
  private void showAccounts(String documentID, boolean hasExtraOption, int lockAccount){
    List<String> accounts = new ArrayList<String>();
    
    try {
      accounts = stub.getRMIStub().getUserAccounts(documentID);
    } catch (Exception e) {
      System.err.println("ShowAccounts exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    for (int i = 1; i < accounts.size() + 1; i++) {
      if(lockAccount != Integer.parseInt(accounts.get(i-1))){
        System.out.println(i + ". Número de cuenta: " + accounts.get(i-1));
      }
    }

    if(hasExtraOption){
      System.out.println((accounts.size() + 1) + ". Cuenta de terceros: 1010");
    }
  }

  //[VERIFICATION] Verificar balance de cuenta
  private boolean verifyAccountBalance(String documentID, Number account){
    double checkBalance = 0;

    try {
      checkBalance = stub.getRMIStub().getAccountBalance(documentID, account);
    } catch (Exception e) {
      System.err.println("VerifyAccountBalance exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    if(checkBalance == -1){
      return false;
    }else{
      return true;
    }
  }

  //[VERIFICATION] Verificación de usuario registrado a través del documento de identidad
  private boolean checkDocumentID(){
    String documentID = client.getDocumentID();
    boolean response = false;

    try {
      response = stub.getRMIStub().checkDocumentID(documentID);
    } catch (Exception e) {
      System.err.println("CheckDocumentID exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    return response;
  }

  //[VERIFICATION] Verificación de cantidad de cuentas que posee el usuario
  private boolean checkMaxAccounts(){
    String documentID = client.getDocumentID();
    boolean response = false;

    try {
      response = stub.getRMIStub().checkMaxAccounts(documentID);
    } catch (Exception e) {
      System.err.println("checkMaxAccounts exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    return response;
  }

  //[FORM] Obtener el documento de identidad del usuario que ingresa al ATM
  public void getUserDocumentID(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Documento de identidad del usuario");
    System.out.println();
    System.out.print("Introduzca su documento de identidad: ");

    String documentID = console.getInputString();
    client.setDocumentID(documentID);
  }

  //[FORM] Registro de nuevo usuario a través del ATM
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

  //[FORM] Inicio de sesión para usuario que ingresó al ATM
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

  //[FORM] Depósito inicial en apertura de cuenta
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

  //[FORM] Consulta de cuentas de usuario
  public void readUsersAccountsForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Cuentas del usuario");
    System.out.println();

    String documentID = client.getDocumentID();

    this.showAccounts(documentID, false, 0);

    System.out.println();
    System.out.print("Introduzca el número de cuenta que desea consultar: ");
    int account = console.getInputInt();

    if(this.verifyAccountBalance(documentID, account)){
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
    }else{
      this.accountNotMatch();
    }
    
    console.stopConsole();
  }

  //[FORM] Retiro de cuentas a través del ATM
  public void withdrawalAccountsForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Cuentas del usuario");
    System.out.println();

    String documentID = client.getDocumentID();

    this.showAccounts(documentID, false, 0);

    System.out.println();
    System.out.print("Introduzca el número de cuenta en donde se hará el retiro: ");
    int account = console.getInputInt();
    
    double balance = 0;

    try {
      balance = stub.getRMIStub().getAccountBalance(documentID, account);
    } catch (Exception e) {
      System.err.println("WithdrawalAccountsForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    if(balance == -1) {
      this.accountNotMatch();
    } else {
      console.clearConsole();
      System.out.println("UCAB ATM (RPC/RMI) - Retiro de cuenta " + account);
      System.out.println();
      System.out.print("Introduzca la cantidad a retirar: ");
      double amount = console.getInputDouble();
  
      if (balance - amount > 0) {
        try {
          balance = stub.getRMIStub().withdrawal(documentID, account, amount);
        } catch (Exception e) {
          System.err.println("WithdrawalAccountsForm exception: " + e.toString()); 
          e.printStackTrace(); 
        }
  
        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Retiro de cuenta " + account);
        System.out.println();
        System.out.println("Balance actual: " + balance);
        System.out.println("Cantidad retirada: " + amount);
        System.out.println();
  
      } else {
        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Cantidad a retirar incorrecta");
        System.out.println();
        System.out.println("La cantidad a retirar no puede superar el balance de la cuenta!");
        System.out.println();
      }
    }
    console.stopConsole();
  }

  //[FORM] Depósito en cuenta a través del ATM
  public void depositAccountForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Cuentas del usuario");
    System.out.println();

    String documentID = client.getDocumentID();

    this.showAccounts(documentID, true, 0);

    System.out.println();
    System.out.print("Introduzca el número de cuenta en el que desea depositar: ");
    int account = console.getInputInt();

    if(account == 1010){
      console.clearConsole();
      System.out.println("UCAB ATM (RPC/RMI) - Depósito en cuenta de terceros");
      System.out.println();
      System.out.print("Número de cuenta destino: ");
      Number destinationAccount = console.getInputInt();
      System.out.print("Documento de identidad del titular de la cuenta de destino: ");
      String destinationDocumentID = console.getInputString();

      String destinationUserName = "";

      try {
        destinationUserName = stub.getRMIStub().getAccountUser(destinationDocumentID, destinationAccount);
      } catch (Exception e) {
        System.err.println("DepositAccountForm exception: " + e.toString()); 
        e.printStackTrace(); 
      }

      int check = 0;

      if(destinationUserName.length() == 0){
        return;
      }else{
        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Verificación de cuenta de terceros");
        System.out.println();
        System.out.println("Nombre del titular de la cuenta de destino: " + destinationUserName);
        System.out.println();
        System.out.print("Validar (1) / Negar(0): ");
        check = console.getInputInt();
      }

      if(check == 1){
        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Depósito en cuenta " + account);
        System.out.println();
        System.out.print("Cantidad que desea depositar: ");
        double amount = console.getInputDouble();
        System.out.print("Descripción: ");
        String description = console.getInputString();

        double balance = 0;

        try {
          balance = stub.getRMIStub().deposit(destinationDocumentID, destinationAccount, description, amount);
        } catch (Exception e) {
          System.err.println("DepositAccountForm exception: " + e.toString()); 
          e.printStackTrace(); 
        }

        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Resumen de depósito en cuenta de terceros");
        System.out.println();
        System.out.println("Cantidad depositada: " + amount);
        System.out.println("Balance actual: " + balance);
        System.out.println("Descripción: " + description);
        System.out.println("Cuenta de destino: " + destinationAccount);
        System.out.println("Titular de cuenta de destino: " + destinationUserName);
        System.out.println();
      }else{
        this.accountNotMatch();
      }

      console.stopConsole();
    }else{
      if(this.verifyAccountBalance(documentID, account)){
        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Depósito en cuenta " + account);
        System.out.println();
        System.out.print("Cantidad que desea depositar: ");
        double amount = console.getInputDouble();
        System.out.print("Descripción: ");
        String description = console.getInputString();

        double balance = 0;

        try {
          balance = stub.getRMIStub().deposit(documentID, account, description, amount);
        } catch (Exception e) {
          System.err.println("DepositAccountForm exception: " + e.toString()); 
          e.printStackTrace(); 
        }

        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Resumen de depósito en " + account);
        System.out.println();
        System.out.println("Cantidad depositada: " + amount);
        System.out.println("Balance actual: " + balance);
        System.out.println("Descripción: " + description);
        System.out.println();
      }else{
        this.accountNotMatch();
      }

      console.stopConsole();
    }
  }

  //[FORM] Transferencia entre cuentas a través del ATM
  public void transferenceAccountForm(){
    console.clearConsole();
    System.out.println("UCAB ATM (RPC/RMI) - Cuentas del usuario");
    System.out.println();

    String sourceDocumentID = client.getDocumentID();

    this.showAccounts(sourceDocumentID, false, 0);

    System.out.println();
    System.out.print("Introduzca el número de cuenta de origen: ");
    int sourceAccount = console.getInputInt();

    double balance = 0;

    try {
      balance = stub.getRMIStub().getAccountBalance(sourceDocumentID, sourceAccount);
    } catch (Exception e) {
      System.err.println("transferenceAccountForm exception: " + e.toString()); 
      e.printStackTrace(); 
    }

    if (balance != -1){
      console.clearConsole();
      System.out.println("UCAB ATM (RPC/RMI) - Cuentas del usuario");
      System.out.println();
  
      this.showAccounts(sourceDocumentID, true, sourceAccount);
  
      System.out.println();
      System.out.print("Introduzca el número de cuenta de destino: ");
      int destinationAccount = console.getInputInt();

      if(destinationAccount == 1010) {
        console.clearConsole();
        System.out.println("UCAB ATM (RPC/RMI) - Transferencia a cuenta de terceros");
        System.out.println();
        System.out.print("Número de cuenta destino: ");
        destinationAccount = console.getInputInt();
        System.out.print("Documento de identidad del titular de la cuenta de destino: ");
        String destinationDocumentID = console.getInputString();
  
        String destinationUserName = "";
  
        try {
          destinationUserName = stub.getRMIStub().getAccountUser(destinationDocumentID, destinationAccount);
        } catch (Exception e) {
          System.err.println("transferenceAccountForm exception: " + e.toString()); 
          e.printStackTrace(); 
        }
  
        int check = 0;
  
        if(destinationUserName.length() == 0){
          return;
        }else{
          console.clearConsole();
          System.out.println("UCAB ATM (RPC/RMI) - Verificación de cuenta de terceros");
          System.out.println();
          System.out.println("Nombre del titular de la cuenta de destino: " + destinationUserName);
          System.out.println();
          System.out.print("Validar (1) / Negar(0): ");
          check = console.getInputInt();
        }
  
        if(check == 1){
          console.clearConsole();
          System.out.println("UCAB ATM (RPC/RMI) - Transferencia en cuenta " + destinationAccount);
          System.out.println();
          System.out.print("Cantidad que desea transferir: ");
          double amount = console.getInputDouble();
          System.out.print("Descripción: ");
          String description = console.getInputString();
  
          if (balance - amount > 0) {
            try {
              balance = stub.getRMIStub().transference(sourceDocumentID, destinationDocumentID, sourceAccount, destinationAccount, description, amount);
            } catch (Exception e) {
              System.err.println("transferenceAccountForm exception: " + e.toString()); 
              e.printStackTrace(); 
            }
    
            console.clearConsole();
            System.out.println("UCAB ATM (RPC/RMI) - Resumen de transferencia en cuenta de terceros");
            System.out.println();
            System.out.println("Cantidad transferida: " + amount);
            System.out.println("Balance actual: " + balance);
            System.out.println("Descripción: " + description);
            System.out.println("Cuenta de destino: " + destinationAccount);
            System.out.println("Titular de cuenta de destino: " + destinationUserName);
            System.out.println();
          } else {
            console.clearConsole();
            System.out.println("UCAB ATM (RPC/RMI) - Cantidad a transferir incorrecta");
            System.out.println();
            System.out.println("La cantidad a transferir no puede superar el balance de la cuenta!");
            System.out.println();
          }
        }else{
          return;
        }
      } else {
        if(this.verifyAccountBalance(sourceDocumentID, destinationAccount)) {
          console.clearConsole();
          System.out.println("UCAB ATM (RPC/RMI) - Transferencia a cuenta " + destinationAccount);
          System.out.println();
          System.out.print("Cantidad que desea transferir: ");
          double amount = console.getInputDouble();
          System.out.print("Descripción: ");
          String description = console.getInputString();

          if (balance - amount > 0) {
            try {
              balance = stub.getRMIStub().transference(sourceDocumentID, sourceDocumentID, sourceAccount, destinationAccount, description, amount);
            } catch (Exception e) {
              System.err.println("transferenceAccountForm exception: " + e.toString()); 
              e.printStackTrace(); 
            }
    
            console.clearConsole();
            System.out.println("UCAB ATM (RPC/RMI) - Resumen de transferencia a " + destinationAccount);
            System.out.println();
            System.out.println("Cantidad transferida: " + amount);
            System.out.println("Balance actual: " + balance);
            System.out.println("Descripción: " + description);
            System.out.println();
          } else {
            console.clearConsole();
            System.out.println("UCAB ATM (RPC/RMI) - Cantidad a transferir incorrecta");
            System.out.println();
            System.out.println("La cantidad a transferir no puede superar el balance de la cuenta!");
            System.out.println();
          }
        } else {
          this.accountNotMatch();
        }
      }
    } else {
      this.accountNotMatch();
    }

    console.stopConsole();
  }

  //[FORM] Apertura de cuenta para usuarios registrados y no registrados
  public void openAccount(){
    if(this.checkDocumentID()){
      if(this.checkMaxAccounts()){
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
