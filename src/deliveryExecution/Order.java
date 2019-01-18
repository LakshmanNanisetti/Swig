package deliveryExecution;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import java.util.ArrayList;
import util.DelDetails;
import util.Printer;
import util.Swiggy;

public class Order
  extends Thread
{
  private int orderId;
  private int custId;
  private int restId;
  private Printer printer;
  private DeliveryMaker deliveryMaker;
  
  public Order(int orderId, int custId, int restId, Printer p, DeliveryMaker dm)
  {
    this.orderId = orderId;
    this.custId = custId;
    this.restId = restId;
    this.printer = p;
    this.deliveryMaker = dm;
  }
  
  public void run()
  {
    Criteria cond = new Criteria(new Column("Restaurant", "REST_ID"), Integer.valueOf(this.restId), 0);
    try
    {
      DataObject d = DataAccess.get("Restaurant", cond);
      Row r = d.getFirstRow("Restaurant");
      int restAdd = ((Integer)r.get("REST_ADD")).intValue();
      
      cond = new Criteria(new Column("Customer", "CUST_ID"), Integer.valueOf(this.custId), 0);
      d = DataAccess.get("Customer", cond);
      r = d.getFirstRow("Customer");
      int custAdd = ((Integer)r.get("CUST_ADD")).intValue();
      DeliveryBoy db = this.deliveryMaker.getFreeDb(custAdd, restAdd);
      Swiggy.dels.add(new DelDetails(db.getId(), db.getAdd(), this.orderId, restAdd, custAdd));
      try
      {
        sleep(Math.abs(custAdd - restAdd) * 100);
        sleep(Math.abs(db.getAdd() - restAdd) * 100);
      }
      catch (InterruptedException e)
      {
        this.printer.print(""+e);
      }
      cond = new Criteria(new Column("DeliveryBoy", "DB_ID"), Integer.valueOf(db.getId()), 0);
      d = DataAccess.get("DeliveryBoy", cond);
      r = d.getFirstRow("DeliveryBoy");
      r.set("DB_BUSY", Boolean.valueOf(false));
      d.updateRow(r);
      DataAccess.update(d);
      this.deliveryMaker.wakeUp();
      cond = new Criteria(new Column("Order", "ORDER_ID"), Integer.valueOf(this.orderId), 0);
      d = DataAccess.get("Order", cond);
      r = d.getFirstRow("Order");
      r.set("DB_ID", Integer.valueOf(db.getId()));
      r.set("ORDER_DONE", Boolean.valueOf(true));
      d.updateRow(r);
      DataAccess.update(d);
    }
    catch (DataAccessException e)
    {
      this.printer.print("Order thread " + e);
    }
    catch (Exception e)
    {
      this.printer.print("order exception");
    }
  }
}
