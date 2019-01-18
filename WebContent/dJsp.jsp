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
<div class="limiter">
<div class="container-table100">
<div class="wrap-table100">
<div class="table100">			
<h2>Delivery Log</h2>
<table>
<thead>
	<tr class="table100-head">
		<th>DeliveryBoy</th>
		<th>DeliveryBoy Address</th>
		<th>Order Id</th>
		<th>Rest Address</th>
		<th>Customer Address</th>
	</tr>
</thead>
<c:forEach items="${Swiggy.dels}" var="ds">
<tr>
<td>db<c:out value="${ds.getDbId()}"/></td>
<td>order<c:out value="${ds.getOrderId()}"/></td>
<td>area<c:out value="${ds.getDbAdd()}"/></td>
<td>area<c:out value="${ds.getRestAdd()}"/></td>
<td>area<c:out value="${ds.getCustAdd()}"/></td>
</tr>
</c:forEach>
</table>
</div>
</div>
</div>
</div>
	</body>
</html>