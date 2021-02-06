public class Menu {
   private Console console = new Console();
   private Form forms;

   public Menu(Form forms){
      this.forms = forms;
   }

   private void openTransactionMenu() {
      boolean outATMTransactions = false;
      int option;

      if(forms.verifyUserForm()){
         while (!outATMTransactions) {
            console.clearConsole();
   
           System.out.println("UCAB ATM (RPC/RMI) - Transacciones");
           System.out.println("1. Consulta de cuenta");
           System.out.println("2. Depósito a cuenta");
           System.out.println("3. Retiro de cuenta");
           System.out.println("4. Transferencia entre cuentas");
           System.out.println("5. Salir al menú principal");
   
           System.out.println();
           System.out.print("(ATM) Indique una de las opciones: ");
           option = console.getInputInt();
   
            switch (option) {
               case 0:
                  System.out.println("No se admiten letras al momento de indicar una opción, solo números!");
                  System.out.println();
                  console.stopConsole();
                  break;
               case 1:
                  forms.readUsersAccountsForm();
                  break;
               case 2:
                  System.out.println("Has seleccionado la opcion 2");
                  System.out.println();
                  console.stopConsole();
                  break;
               case 3:
                  forms.withdrawalAccountsForm();
                  break;
               case 4:
                  System.out.println("Has seleccionado la opcion 4");
                  System.out.println();
                  console.stopConsole();
                  break;
               case 5:
                  System.out.println();
                  outATMTransactions = true;
                  break;
               default:
                  System.out.println("Esta opción no está disponible, indique una opción válida!");
                  System.out.println();
                  console.stopConsole();
            }
         } 
      }
   }

   public void openATMMenu() {
      boolean outATM = false;
      int option;

      while (!outATM) {
         console.clearConsole();

         System.out.println("UCAB ATM (RPC/RMI)");
         System.out.println("1. Apertura de cuenta");
         System.out.println("2. Realizar transacción");
         System.out.println("3. Salir del ATM");

         System.out.println();
         System.out.print("(ATM) Indique una de las opciones: ");
         option = console.getInputInt();

         switch (option) {
            case 0:
               System.out.println("No se admiten letras al momento de indicar una opción, solo números!");
               System.out.println();
               console.stopConsole();
               break;
            case 1:
               forms.openAccount();
               break;
            case 2:
               this.openTransactionMenu();
               break;
            case 3:
               System.out.println();
               System.out.println("Gracias por usar UCAB ATM!");
               outATM = true;
               break;
            default:
               System.out.println();
               System.out.println("Esta opción no está disponible, indique una opción válida!");
               System.out.println();
               console.stopConsole();
         }
      } 
   }
}