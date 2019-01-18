<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page import = "java.io.*,
    	java.util.*,
    	util.*,
    	orderCreation.*,
     	com.adventnet.ds.query.*,
     	com.adventnet.persistence.*" %>
     
    <%!
    	int nc = 0, nr = 0, ndb = 0;
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Summary</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/perfect-scrollbar/perfect-scrollbar.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<%
	try{
	    nc = Integer.parseInt(request.getParameter("noOfCustomers"));
	    nr = Integer.parseInt(request.getParameter("noOfRestaurants"));
	    ndb = Integer.parseInt(request.getParameter("noOfDeliveryBoys"));
	}
	catch(NumberFormatException nfe){
	    nfe.printStackTrace();
	    try{
	        response.sendRedirect("index.html");
	    }catch(IOException ioe){
	        ioe.printStackTrace();
	    }
	}
%>
<p>The given parameters are:</p>
<div class="limiter">
		<div class="container-table100">
			<div class="wrap-table100">
				<div class="table100">
					<table>
					<thead>
						<tr class="table100-head">
							<th class="column1">Type</th>
							<th class="column2">Count</th>
						</tr>
						</thead>
					<tr>
					<td class="column1">No of Customers:</td>
					<td class="column2"><c:out value="${nc}"/></td>
					</tr>
					<tr>
					<td class="column1">No of Restaurants:</td>
					<td class="column2"><c:out value="${nr}"/></td>
					</tr>
					<tr>
					<td class="column1">No of Delivery Boys:</td>
					<td class="column2"><c:out value="${ndb}"/></td>
					</tr>
					</table>
				
<%--
Printer p = new Printer( (Writer)out);
Swiggy.work(p, nc, nr, ndb);
OrderingThread ot = new OrderingThread(nr, p);
ot.start();
try {
	ot.join();
} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	p.print ("exception at join");
}
--%>
<table>
<thead>
	<tr class="table100-head">
		<th class="column1">Customer Name</th>
		<th class="column2">Customer Address</th>
	</tr>
</thead>
<c:forEach items="${Swiggy.custs}" var="ud">
<tr>
<td>cust<c:out value="${ud.getUserId()}"/></td>
<td>area<c:out value="${ud.getUserAdd()}"/></td>
</tr>
</c:forEach>

</table>
<table>
<thead>
	<tr class="table100-head">
		<th class="column1">Delivery Boy Name</th>
		<th class="column2">Delivery Boy Address</th>
	</tr>
</thead>
<c:forEach items="${Swiggy.dboys}" var="db">
<tr>
<td>db<c:out value="${db.getUserId()}"/></td>
<td>area<c:out value="${db.getUserAdd()}"/></td>
</tr>
</c:forEach>
</table>
<table>
<thead>
	<tr class="table100-head">
		<th class="column1">Restaurant Name</th>
		<th class="column2">Restaurant Address</th>
		<th class="column3">Restaurant Items</th>
	</tr>
</thead>
<<c:forEach items="${Swiggy.rests}" var="rd">
<tr>
<td>rest<c:out value="${rd.getRestId()}"/></td>
<td>area<c:out value="${rd.getRestAdd()}"/></td>
<td><c:out value="${Swiggy.hashMapToItemsString(rd.getRestItems())}"/></td>
</tr>
</c:forEach>
</table>
<h2>Orders Made are:</h2>
<table>
<thead>
	<tr class="table100-head">
		<th class="column1">OrderId</th>
		<th class="column2">Customer</th>
		<th class="column3">Restaurant</th>
		<th class="column4">Items</th>
	</tr>
</thead>
<<c:forEach items="${Swiggy.os}" var="osum">
<tr>
<td>order<c:out value="${osum.getOid()}"/></td>
<td>cust<c:out value="${osum.getCustId()}"/></td>
<td>rest<c:out value="${osum.getRestId()}"/></td>
<td><c:out value="${Swiggy.hashMapToItemsString(osum.getOrder())}"/></td>
</tr>
</c:forEach>
</table>
<form method = "GET" action="DeliveryServlet">
<input type="submit" value="make deliveries"/>
</form>
				</div>
			</div>
		</div>
</div>
	</body>
</html>