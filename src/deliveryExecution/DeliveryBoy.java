package deliveryExecution;

public class DeliveryBoy
{
  private int dbId;
  private int dbAdd;
  
  public DeliveryBoy(int dbId, int dbAdd)
  {
    this.dbId = dbId;
    this.dbAdd = dbAdd;
  }
  
  public int getAdd()
  {
    return this.dbAdd;
  }
  
  public int getId()
  {
    return this.dbId;
  }
}
