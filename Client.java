public class Client {
 
   public static void main(String[] args) {
   
      TestRMI test = new TestRMI();
      Console console = new Console();
      boolean outATM = false;
      int option; //Guardaremos la option del usuario

      while (!outATM) {
         console.clearConsole();

         System.out.println("UCAB ATM (RPC/RMI)");
         System.out.println("1. Apertura de cuenta");
         System.out.println("2. Realizar transacción");
         System.out.println("3. Salir del ATM");

         System.out.print("(ATM) Indique una de las opciones: ");
         option = console.getInputValue();

         switch (option) {
            case 0:
               System.out.println("Introduzca una opción válida!");
               System.out.println();
               console.stopConsole();
               break;
            case 1:
               System.out.println("Has seleccionado la opcion 1");
               test.runTest();
               System.out.println();
               console.stopConsole();
               break;
            case 2:
               System.out.println("Has seleccionado la opcion 2");
               System.out.println();
               console.stopConsole();
               break;
            case 3:
               System.out.println();
               System.out.println("Gracias por usar UCAB ATM!");
               outATM = true;
               break;
            default:
               System.out.println("Solo números entre 1 y 3");
               console.stopConsole();
         }
   
      } 
   }
}