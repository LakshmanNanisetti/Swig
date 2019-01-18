package util;

public class UserDetails
{
  private int custId;
  private int custAdd;
  
  public UserDetails(int custId, int custAdd)
  {
    this.custId = custId;
    this.custAdd = custAdd;
  }
  
  public int getUserId()
  {
    return this.custId;
  }
  
  public int getUserAdd()
  {
    return this.custAdd;
  }
}
