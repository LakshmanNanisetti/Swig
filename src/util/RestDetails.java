package util;

import java.util.HashMap;

public class RestDetails
{
  private int restId;
  private int restAdd;
  private HashMap<Integer, Integer> restItems;
  
  public RestDetails(int restId, int restAdd, HashMap<Integer, Integer> restItems)
  {
    this.restId = restId;
    this.restAdd = restAdd;
    this.restItems = restItems;
  }
  
  public int getRestId()
  {
    return this.restId;
  }
  
  public int getRestAdd()
  {
    return this.restAdd;
  }
  
  public HashMap<Integer, Integer> getRestItems()
  {
    return this.restItems;
  }
}
