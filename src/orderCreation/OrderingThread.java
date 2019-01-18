package orderCreation;

import com.adventnet.ds.query.Criteria;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import java.util.ArrayList;
import java.util.Iterator;
import util.Printer;

public class OrderingThread
  extends Thread
{
  int nr;
  OrderMaker om;
  private Printer printer;
  
  public OrderingThread(int nr, Printer p)
  {
    this.nr = nr;
    this.printer = p;
    this.om = new OrderMaker(p);
  }
  
  public void run()
  {
    ArrayList<Customer> al = new ArrayList();
    try
    {
      DataObject d = DataAccess.get("Customer", (Criteria)null);
      Iterator it = d.getRows("Customer");
      int id;
      while (it.hasNext())
      {
        Row r = (Row)it.next();
        id = ((Integer)r.get("CUST_ID")).intValue();
        int add = ((Integer)r.get("CUST_ADD")).intValue();
        Customer c = new Customer(id, add, this.nr, this.om, this.printer);
        c.start();
        al.add(c);
      }
      for (Customer c : al) {
        try
        {
          c.join();
        }
        catch (InterruptedException e)
        {
          this.printer.print("at join");
        }
      }
    }
    catch (DataAccessException e)
    {
      this.printer.print(e + "\n at thread making");
    }
    catch (Exception e)
    {
      this.printer.print("get excep");
    }
  }
}
