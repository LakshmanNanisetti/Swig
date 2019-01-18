package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import orderCreation.OrderingThread;
import util.Printer;
import util.Swiggy;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    response.setContentType("text/html;charset=UTF-8");
	    int nc = 0;int ndb = 0;int nr = 0;
	    PrintWriter out = null;
	    try
	    {
	      nc = Integer.parseInt(request.getParameter("noOfCustomers"));
	      nr = Integer.parseInt(request.getParameter("noOfRestaurants"));
	      ndb = Integer.parseInt(request.getParameter("noOfDeliveryBoys"));
	    }
	    catch (NumberFormatException nfe)
	    {
	      nfe.printStackTrace();
	      try
	      {
	        response.sendRedirect("index.html");
	      }
	      catch (IOException ioe)
	      {
	        ioe.printStackTrace();
	      }
	    }
	    try
	    {
	      out = response.getWriter();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    Printer p = new Printer(out);
	    Swiggy.work(p, nc, nr, ndb);
	    
	    OrderingThread ot = new OrderingThread(nr, p);
	    ot.start();
	    try
	    {
	      ot.join();
	    }
	    catch (InterruptedException e)
	    {
	      e.printStackTrace();
	    }
	    RequestDispatcher rd = request.getRequestDispatcher("MyJsp.jsp");
	    request.setAttribute("nc", Integer.valueOf(nc));
	    request.setAttribute("nr", Integer.valueOf(nr));
	    request.setAttribute("ndb", Integer.valueOf(ndb));
	    request.setAttribute("custs", Swiggy.custs);
	    request.setAttribute("rests", Swiggy.rests);
	    request.setAttribute("dboys", Swiggy.dboys);
	    request.setAttribute("orderSummaries", Swiggy.os);
	    rd.include(request, response);
	  }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
