package util;

import java.io.IOException;
import java.io.Writer;

public class Printer
{
  private Writer out;
  
  public Printer(Writer out)
  {
    this.out = out;
  }
  
  public synchronized void print(String str)
  {
    try
    {
      this.out.write("<p>" + str + "</p>");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
