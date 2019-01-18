package orderCreation;

import java.util.HashMap;

public class OrderSummary
{
  private int oid;
  private int custId;
  private int restId;
  private HashMap<Integer, Integer> order;
  
  public OrderSummary(int oid, int custId, int restId, HashMap<Integer, Integer> order)
  {
    this.oid = oid;
    this.custId = custId;
    this.restId = restId;
    this.order = order;
  }
  
  public int getOid()
  {
    return this.oid;
  }
  
  public void setOid(int oid)
  {
    this.oid = oid;
  }
  
  public int getCustId()
  {
    return this.custId;
  }
  
  public void setCustId(int custId)
  {
    this.custId = custId;
  }
  
  public int getRestId()
  {
    return this.restId;
  }
  
  public void setRestId(int restId)
  {
    this.restId = restId;
  }
  
  public HashMap<Integer, Integer> getOrder()
  {
    return this.order;
  }
  
  public void setOrder(HashMap<Integer, Integer> order)
  {
    this.order = order;
  }
}
