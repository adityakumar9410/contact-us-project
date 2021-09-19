<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.adityakumar.model.Request" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<html>
<head>
    <title>Requests</title>
</head>
<body>

<%
    List<Request>  requests = (ArrayList<Request>) session.getAttribute("requests");

    List<Request> activeRequests = new ArrayList<>();
    List<Request> archivedRequests = new ArrayList<>();

    for(Request currRequest : requests){
        if(currRequest.isActive()){
            activeRequests.add(currRequest);
        }else {
            archivedRequests.add(currRequest);
        }
    }
%>

<h1 style="text-align: center">Archived</h1>
<table style="width:100%; margin: 20px">
    <tr>
        <th>Full Name</th>
        <th>Email</th>
        <th>Message</th>
        <th>Contact Date</th>
        <th>Action</th>
    </tr>
    <%
        Iterator<Request> iterator = archivedRequests.iterator();
        while (iterator.hasNext()){
            Request req = iterator.next();
            if(!(req.isActive())){
    %>
    <tr>
        <td><%=  req.getFullName()%></td>
        <td><%=  req.getEmail()%></td>
        <td><%=  req.getMessage()%></td>
        <td><%=  req.getRequestDate()%></td>
        <td>
            <form action="requests" method="post">
                <input type="hidden" name="date" value="<%= req.getRequestDate()%>">
                <input type="hidden" name="isActive" value="<%=req.isActive()%>">
                <input type="submit" value="ACTIVATE">
            </form>
        </td>

    </tr>
    <%}
    }
    %>
</table>
<h1 style="text-align: center">Active</h1>
<table style="width:100%; margin: 20px">
    <tr>
        <th>Full Name</th>
        <th>Email</th>
        <th>Message</th>
        <th>Contact Date</th>
        <th>Action</th>
    </tr>
    <%
        Iterator<Request> iterator1 = activeRequests.iterator();
        while (iterator1.hasNext()){
            Request req = iterator1.next();
            if((req.isActive())){
    %>
    <tr>
        <td><%=  req.getFullName()%></td>
        <td><%=  req.getEmail()%></td>
        <td><%=  req.getMessage()%></td>
        <td><%=  req.getRequestDate()%></td>
        <td>
            <form action="requests" method="post">
                <input type="hidden" name="date" value="<%= req.getRequestDate()%>">
                <input type="hidden" name="isActive" value="<%=req.isActive()%>">
                <input type="submit" value="PROCESS">
            </form>
        </td>

    </tr>
    <%}
    }
    %>

</table>
</body>
</html>

