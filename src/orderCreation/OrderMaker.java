package orderCreation;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import util.Printer;
import util.Swiggy;

public class OrderMaker
{
  private int id;
  private Printer p;
  
  public OrderMaker(Printer p)
  {
    this.id = 1;
    this.p = p;
  }
  
  private int getId()
  {
    return this.id++;
  }
  
  public synchronized boolean makeOrder(int custId, int restId, HashMap<Integer, Integer> order)
  {
    boolean done = true;
    Criteria cond = new Criteria(new Column("Item", "REST_ID"), Integer.valueOf(restId), 0);
    try
    {
      DataObject d = DataAccess.get("Item", cond);
      int q;
      for (Map.Entry<Integer, Integer> me : order.entrySet())
      {
        int id = ((Integer)me.getKey()).intValue();
        q = ((Integer)me.getValue()).intValue();
        Criteria c = new Criteria(new Column("Item", "ITEM_ID"), Integer.valueOf(id), 0);
        Row r = d.getRow("Item", c);
        int tq = ((Integer)r.get("ITEM_QUANTITY")).intValue();
        if (q < tq)
        {
          r.set("ITEM_QUANTITY", Integer.valueOf(tq - q));
          d.updateRow(r);
        }
        else
        {
          done = false;
          break;
        }
      }
      if (done)
      {
        DataAccess.update(d);
        d = new WritableDataObject();
        Row r = new Row("Order");
        int oid = getId();
        r.set("ORDER_ID", Integer.valueOf(oid));
        r.set("CUST_ID", Integer.valueOf(custId));
        r.set("REST_ID", Integer.valueOf(restId));
        r.set("ORDER_DONE", Boolean.valueOf(false));
        
        d.addRow(r);
        DataAccess.update(d);
        d = new WritableDataObject();
        for (Map.Entry<Integer, Integer> me : order.entrySet())
        {
          int id = ((Integer)me.getKey()).intValue();
          int quant = ((Integer)me.getValue()).intValue();
          r = new Row("OrderedItem");
          r.set("OITEM_ID", Integer.valueOf(id));
          r.set("ORDER_ID", Integer.valueOf(oid));
          r.set("ORDER_QUANTITY", Integer.valueOf(quant));
          d.addRow(r);
        }
        DataAccess.update(d);
        OrderSummary osum = new OrderSummary(oid, custId, restId, order);
        Swiggy.os.add(osum);
      }
    }
    catch (DataAccessException e)
    {
      this.p.print(e + "\n at order making");
    }
    catch (Exception e)
    {
      this.p.print("order making exception " + e);
    }
    return done;
  }
}
