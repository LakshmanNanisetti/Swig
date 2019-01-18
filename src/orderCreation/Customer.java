package orderCreation;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import util.Printer;

public class Customer
  extends Thread
{
  private int id;
  private int address;
  private int noOfItemsInOrder;
  private int nr;
  private int restId;
  private ArrayList<Integer> restItems;
  private HashMap<Integer, Integer> order;
  private OrderMaker om;
  private boolean canOrder;
  private Printer p;
  Random rand;
  
  public Customer(int name, int address, int nr, OrderMaker om, Printer p)
  {
    this.id = name;
    this.address = address;
    this.nr = nr;
    this.om = om;
    this.p = p;
    this.rand = new Random();
  }
  
  public int getname()
  {
    return this.id;
  }
  
  public void setname(int name)
  {
    this.id = name;
  }
  
  public int getAddress()
  {
    return this.address;
  }
  
  public void setAddress(int address)
  {
    this.address = address;
  }
  
  public void run()
  {
    int tolerance = 10;
    while (tolerance > 0)
    {
      this.noOfItemsInOrder = (this.rand.nextInt(2) + 1);
      this.restId = (this.rand.nextInt(this.nr) + 1);
      this.restItems = getRestaurantItems();
      this.order = initOrder(this.restItems);
      if (this.om.makeOrder(this.id, this.restId, this.order)) {
        break;
      }
      tolerance--;
    }
  }
  
  public HashMap<Integer, Integer> initOrder(ArrayList<Integer> restItems)
  {
    HashMap<Integer, Integer> order = new HashMap();
    for (int j = 0; j < this.noOfItemsInOrder; j++)
    {
      int ind = this.rand.nextInt(restItems.size() - j);
      int itemno = ((Integer)restItems.remove(ind)).intValue();
      int q = this.rand.nextInt(2) + 1;
      order.put(Integer.valueOf(itemno), Integer.valueOf(q));
    }
    return order;
  }
  
  private ArrayList<Integer> getRestaurantItems()
  {
    Criteria cond = new Criteria(new Column("Item", "REST_ID"), Integer.valueOf(this.restId), 0);
    ArrayList<Integer> items = new ArrayList();
    try
    {
      DataObject d = DataAccess.get("Item", cond);
      Iterator it = d.getRows("Item");
      while (it.hasNext())
      {
        Row r = (Row)it.next();
        int id = ((Integer)r.get("ITEM_ID")).intValue();
        items.add(Integer.valueOf(id));
      }
    }
    catch (DataAccessException e)
    {
      this.p.print("get restitems :" + e);
    }
    catch (Exception e)
    {
      this.p.print("normal excep at getRestaurantItems" + e);
    }
    return items;
  }
}
