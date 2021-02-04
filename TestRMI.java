import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;  

public class TestRMI {  
   public void runTest() {  
    try {  
      // Getting the registry 
      Registry registry = LocateRegistry.getRegistry(null); 

      // Looking up the registry for the remote object 
      RemoteInterface stub = (RemoteInterface) registry.lookup("TEST/RMI"); 

      // Calling the remote method using the obtained object 
      stub.printMsg(); 
      
      // System.out.println("Remote method invoked"); 
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString()); 
      e.printStackTrace(); 
    } 
  } 
}