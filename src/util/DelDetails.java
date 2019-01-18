package util;

public class DelDetails
{
  private int dbId;
  private int dbAdd;
  private int orderId;
  private int restAdd;
  private int custAdd;
  
  public DelDetails(int dbId, int dbAdd, int orderId, int restAdd, int custAdd)
  {
    this.dbId = dbId;
    this.dbAdd = dbAdd;
    this.orderId = orderId;
    this.restAdd = restAdd;
    this.custAdd = custAdd;
  }
  
  public int getDbId()
  {
    return this.dbId;
  }
  
  public int getOrderId()
  {
    return this.orderId;
  }
  
  public int getDbAdd()
  {
    return this.dbAdd;
  }
  
  public int getRestAdd()
  {
    return this.restAdd;
  }
  
  public int getCustAdd()
  {
    return this.custAdd;
  }
}
