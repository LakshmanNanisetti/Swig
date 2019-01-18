package util;

import com.adventnet.ds.query.Criteria;
import com.adventnet.persistence.DataAccess;
import com.adventnet.persistence.DataAccessException;
import com.adventnet.persistence.DataObject;
import com.adventnet.persistence.Row;
import com.adventnet.persistence.WritableDataObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.Random;
import orderCreation.OrderSummary;

public class Swiggy
{
  public static ArrayList<OrderSummary> os = new ArrayList();
  public static ArrayList<UserDetails> custs = new ArrayList();
  public static ArrayList<RestDetails> rests = new ArrayList();
  public static ArrayList<UserDetails> dboys = new ArrayList();
  public static ArrayList<DelDetails> dels = new ArrayList();
  
  public static void work(Printer p, int nc, int nr, int ndb)
  {
    deleteWholeData(p);
    initCustomers(p, nc);
    initRestaurants(p, nr);
    initDeliveryBoys(p, ndb);
  }
  
  private static void initDeliveryBoys(Printer p, int ndb)
  {
    DataObject dobj = null;
    Random rand = new Random();
    try
    {
      dobj = new WritableDataObject();
      for (int i = 1; i <= ndb; i++)
      {
        int areaNo = rand.nextInt(10) + 1;
        Row r = new Row("DeliveryBoy");
        r.set("DB_ID", Integer.valueOf(i));
        r.set("DB_ADD", Integer.valueOf(areaNo));
        r.set("DB_BUSY", Boolean.valueOf(false));
        dobj.addRow(r);
        dboys.add(new UserDetails(i, areaNo));
      }
      DataAccess.update(dobj);
    }
    catch (DataAccessException ex)
    {
      p.print(ex + "\t at init Restaurants\n");
    }
  }
  
  private static void initRestaurants(Printer p, int nr)
  {
    DataObject dobj = null;
    
    Random rand = new Random();
    try
    {
      dobj = new WritableDataObject();
      for (int i = 1; i <= nr; i++)
      {
        int areaNo = rand.nextInt(10) + 1;
        Row r = new Row("Restaurant");
        r.set("REST_ID", Integer.valueOf(i));
        r.set("REST_ADD", Integer.valueOf(areaNo));
        dobj.addRow(r);
        
        ArrayList<Integer> itemnos = new ArrayList();
        for (int j = 1; j <= 5; j++) {
          itemnos.add(Integer.valueOf(j));
        }
        HashMap<Integer, Integer> restItems = new HashMap();
        for (int j = 0; j < 3; j++)
        {
          int ind = rand.nextInt(5 - j);
          int itemno = ((Integer)itemnos.remove(ind)).intValue();
          int q = rand.nextInt(2) + 2;
          r = new Row("Item");
          r.set("ITEM_ID", Integer.valueOf(itemno));
          r.set("REST_ID", Integer.valueOf(i));
          r.set("ITEM_QUANTITY", Integer.valueOf(q));
          dobj.addRow(r);
          restItems.put(Integer.valueOf(itemno), Integer.valueOf(q));
        }
        rests.add(new RestDetails(i, areaNo, restItems));
      }
      DataAccess.update(dobj);
    }
    catch (DataAccessException ex)
    {
      p.print(ex + "\t at init Restaurants\n");
    }
  }
  
  public static void deleteWholeData(Printer p)
  {
    DataObject dobj = null;
    os = new ArrayList();
    custs = new ArrayList();
    rests = new ArrayList();
    dboys = new ArrayList();
    dels = new ArrayList();
    try
    {
      dobj = DataAccess.get("Customer", (Criteria)null);
      dobj.deleteRows("Customer", (Criteria)null);
      DataAccess.update(dobj);
      
      dobj = DataAccess.get("Restaurant", (Criteria)null);
      dobj.deleteRows("Restaurant", (Criteria)null);
      DataAccess.update(dobj);
      
      dobj = DataAccess.get("DeliveryBoy", (Criteria)null);
      dobj.deleteRows("DeliveryBoy", (Criteria)null);
      DataAccess.update(dobj);
      
      dobj = DataAccess.get("Order", (Criteria)null);
      dobj.deleteRows("Order", (Criteria)null);
      DataAccess.update(dobj);
      
      dobj = DataAccess.get("Item", (Criteria)null);
      dobj.deleteRows("Item", (Criteria)null);
      DataAccess.update(dobj);
      
      dobj = DataAccess.get("OrderedItem", (Criteria)null);
      dobj.deleteRows("OrderedItem", (Criteria)null);
      DataAccess.update(dobj);
    }
    catch (DataAccessException ex)
    {
      p.print(ex + "\t at deletion\n");
    }
  }
  
  public static void initCustomers(Printer p, int nc)
  {
    DataObject dobj = null;
    
    Random rand = new Random();
    try
    {
      dobj = new WritableDataObject();
      for (int i = 1; i <= nc; i++)
      {
        int areaNo = rand.nextInt(10) + 1;
        Row r = new Row("Customer");
        r.set("CUST_ID", Integer.valueOf(i));
        r.set("CUST_ADD", Integer.valueOf(areaNo));
        dobj.addRow(r);
        custs.add(new UserDetails(i, areaNo));
      }
      DataAccess.update(dobj);
    }
    catch (DataAccessException ex)
    {
      p.print(ex + "\t at init customers\n");
    }
  }
  
  public static String hashMapToItemsString(HashMap<Integer, Integer> hm)
  {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<Integer, Integer> me : hm.entrySet()) {
      sb.append("item" + me.getKey() + ":" + me.getValue() + "\n");
    }
    String temp = sb.toString();
    return temp;
  }
}
