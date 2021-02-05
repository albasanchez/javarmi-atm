import java.io.IOException;
import java.util.Scanner;

public class Console {
  private Scanner clientInputNumbers = new Scanner(System.in);
  private Scanner clientInputStrings = new Scanner(System.in);
  private Scanner keyboard = new Scanner(System.in);

  public int getInputInt() {
    int option = 0;

    try {
      return option = this.clientInputNumbers.nextInt();
    } catch (Exception e) {
      this.clientInputNumbers.next();
      return option;
    }
  }

  public double getInputDouble() {
    double value = 0;
    
    try {
      return value = this.clientInputNumbers.nextDouble();
    } catch (Exception e) {
      this.clientInputNumbers.next();
      return value;
    }
  }

  public String getInputString() {
    String value = "";

    try {
      this.clientInputStrings.useDelimiter("\n");
      value = this.clientInputStrings.next();
      value = value.replaceAll("\\r|\\n", "");
      return value;
    } catch (Exception e) {
      this.clientInputStrings.next();
      return value;
    }
  }

  public void stopConsole() {
    System.out.println("Presione cualquier tecla para continuar...");
    try {
      this.keyboard.nextLine();
    } catch(Exception e) {
      System.err.println("Stop console exception: " + e.toString()); 
    }
  }

  public void clearConsole() {
    try {
      this.commandClear();
    } catch (Exception e) {
      System.err.println("Clear console exception: " + e.toString()); 
      e.printStackTrace();
    }
  }

  private void commandClear() throws IOException, InterruptedException {
    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
  }
}
