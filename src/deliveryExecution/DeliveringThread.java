package deliveryExecution;

import com.adventnet.ds.query.Criteria;
import com.adventnet.persistence.*;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import java.util.ArrayList;
import java.util.Iterator;
import util.Printer;

public class DeliveringThread
  extends Thread
{
  private ArrayList<Order> orders;
  private Printer printer;
  private DeliveryMaker deliveryMaker;
  
  public DeliveringThread(Printer p)
  {
    this.orders = new ArrayList();
    this.printer = p;
    this.deliveryMaker = new DeliveryMaker(p);
  }
  
  public void run()
  {
    Iterator it;
    try
    {
      DataObject d = DataAccess.get("Order", (Criteria)null);
      it = d.getRows("Order");
      while (it.hasNext())
      {
        Row r = (Row)it.next();
        int orderId = ((Integer)r.get("ORDER_ID")).intValue();
        int custId = ((Integer)r.get("CUST_ID")).intValue();
        int restId = ((Integer)r.get("REST_ID")).intValue();
        Order o = new Order(orderId, custId, restId, this.printer, this.deliveryMaker);
        o.start();
        this.orders.add(o);
      }
    }
    catch (DataAccessException e)
    {
      this.printer.print(""+e);
    }
    catch (Exception e)
    {
      this.printer.print(""+e);
    }
    for (Order o : this.orders) {
      try
      {
        o.join();
      }
      catch (InterruptedException e)
      {
        this.printer.print(""+e);
      }
    }
  }
}
