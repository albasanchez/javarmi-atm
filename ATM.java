public class ATM {
 
   public static void main(String[] args) {
      Form forms = new Form();
      forms.getUserDocumentID();
      
      Menu menu = new Menu(forms);

      menu.openATMMenu();
   }
}