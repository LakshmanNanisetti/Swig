package deliveryExecution;

import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import util.Printer;

public class DeliveryMaker
{
  private Printer p;
  
  public DeliveryMaker(Printer p)
  {
    this.p = p;
  }
  
  public synchronized DeliveryBoy getFreeDb(int custAdd, int restAdd)
  {
    int ind = 0;
    HashMap<Integer, ArrayList<Integer>> freeDbs = new HashMap();
    for (;;)
    {
      freeDbs.putAll(getFreeDbs());
      int i = restAdd;int j = restAdd;
      while ((i >= 1) || (j <= 10))
      {
        if ((i >= 1) && (((ArrayList)freeDbs.get(Integer.valueOf(i))).size() != 0)) {
          ind = i;
        } else if ((j <= 10) && (((ArrayList)freeDbs.get(Integer.valueOf(j))).size() != 0)) {
          ind = j;
        }
        if (ind != 0) {
          break;
        }
        i--;
        j++;
      }
      if (ind != 0) {
        break;
      }
      try
      {
        wait();
      }
      catch (InterruptedException e)
      {
        this.p.print(""+e);
      }
    }
    int dbId = ((Integer)((ArrayList)freeDbs.get(Integer.valueOf(ind))).get(0)).intValue();
    int dbAdd = ind;
    Criteria cond = new Criteria(new Column("DeliveryBoy", "DB_ID"), Integer.valueOf(dbId), 0);
    try
    {
      DataObject d = DataAccess.get("DeliveryBoy", cond);
      Row r = d.getFirstRow("DeliveryBoy");
      r.set("DB_BUSY", Boolean.valueOf(true));
      r.set("DB_ADD", Integer.valueOf(custAdd));
      d.updateRow(r);
      DataAccess.update(d);
    }
    catch (DataAccessException e)
    {
      this.p.print(""+e);
    }
    catch (Exception e)
    {
      this.p.print("worst");
    }
    return new DeliveryBoy(dbId, dbAdd);
  }
  
  public HashMap<Integer, ArrayList<Integer>> getFreeDbs()
  {
    HashMap<Integer, ArrayList<Integer>> freeDbs = new HashMap();
    for (int i = 1; i <= 10; i++) {
      freeDbs.put(Integer.valueOf(i), new ArrayList());
    }
    Criteria cond = new Criteria(new Column("DeliveryBoy", "DB_BUSY"), Boolean.valueOf(false), 0);
    try
    {
      DataObject d = DataAccess.get("DeliveryBoy", cond);
      Iterator it = d.getRows("DeliveryBoy");
      while (it.hasNext())
      {
        Row r = (Row)it.next();
        int dbId = ((Integer)r.get("DB_ID")).intValue();
        int dbAdd = ((Integer)r.get("DB_ADD")).intValue();
        ((ArrayList)freeDbs.get(Integer.valueOf(dbAdd))).add(Integer.valueOf(dbId));
      }
    }
    catch (DataAccessException e)
    {
      this.p.print(""+e);
    }
    catch (Exception e)
    {
      this.p.print("worst");
    }
    return freeDbs;
  }
  
  public synchronized void wakeUp()
  {
    notify();
  }
}
