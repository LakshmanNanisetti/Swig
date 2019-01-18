package servlets;

import deliveryExecution.DeliveringThread;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Printer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeliveryServlet
 */
@WebServlet("/DeliveryServlet")
public class DeliveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeliveryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    PrintWriter out = null;
	    try
	    {
	      out = response.getWriter();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    Printer p = new Printer(out);
	    DeliveringThread dt = new DeliveringThread(p);
	    dt.start();
	    try
	    {
	      dt.join();
	    }
	    catch (InterruptedException e)
	    {
	      e.printStackTrace();
	    }
	    RequestDispatcher rd = request.getRequestDispatcher("dJsp.jsp");
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
