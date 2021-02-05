public class Client {
  private String documentID;
  private String name;
  private String username;
  private String password;

  // public Client(String documentID, String name, String username, String password){
  //   this.documentID = documentID;
  //   this.name = name;
  //   this.username = username;
  //   this.password = password;
  // }

  public void setDocumentID(String documentID){
    this.documentID = documentID;
  }

  public String getDocumentID(){
    return this.documentID;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

  public void setUsername(String username){
    this.username = username;
  }

  public String getUsername(){
    return this.username;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public String getPassword(){
    return this.password;
  }
}
