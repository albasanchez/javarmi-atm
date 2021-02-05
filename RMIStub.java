import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;  

public class RMIStub {  
   public RemoteInterface getRMIStub() { 
    Registry registry;
    RemoteInterface stub = null;

    try {  
      // Getting the registry 
      registry = LocateRegistry.getRegistry(null); 

      // Looking up the registry for the remote object 
      stub = (RemoteInterface) registry.lookup("RMI-INTERFACE"); 
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString()); 
      e.printStackTrace(); 
    } 

    return stub;
  } 
}