import java.io.IOException;
import java.util.Scanner;

public class Console {
  private Scanner clientInput = new Scanner(System.in);
  private Scanner keyboard = new Scanner(System.in);

  public int getInputValue() {
    int option = 0;

    try {
      return option = this.clientInput.nextInt();
    } catch (Exception e) {
      this.clientInput.next();
      return option;
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
