<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.adityakumar.model.Request" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<html>
<head>
    <title>Requests</title>
    <link href="css/requests.css" rel="stylesheet" type="text/css">
</head>
<body>
       <div style="float: right">
             <a href="logout"><H2>Logout</H2></a>
       </div>
<%
    List<Request> requests;
    if ((ArrayList<Request>) session.getAttribute("requests") != null) {
        requests = (ArrayList<Request>) session.getAttribute("requests");
    } else {
        requests = new ArrayList<>();
    }
    List<Request> activeRequests = new ArrayList<>();
    List<Request> archivedRequests = new ArrayList<>();

    for (Request contactRequest : requests) {
        if (contactRequest.isActive()) {
            activeRequests.add(contactRequest);
        } else {
            archivedRequests.add(contactRequest);
        }
    }
%>
<div class="container">
    <h1 style="text-align: center">Archived</h1>
    <table>
        <tr>
            <th>Full Name</th>
            <th>Email</th>
            <th>Message</th>
            <th>Contact Date</th>
            <th>Action</th>
        </tr>
        <%
            Iterator<Request> archivedRequestIterator = archivedRequests.iterator();
            while (archivedRequestIterator.hasNext()) {
                Request archivedRequest = archivedRequestIterator.next();
        %>
        <tr>
            <td><%=  archivedRequest.getFullName()%>
            </td>
            <td><%=  archivedRequest.getEmail()%>
            </td>
            <td><%=  archivedRequest.getMessage()%>
            </td>
            <td><%=  archivedRequest.getRequestDate()%>
            </td>
            <td>
                <form action="requests" method="post">
                    <input type="hidden" name="requestId" value="<%= archivedRequest.getRequestId()%>">
                    <input type="hidden" name="isActive" value="<%=archivedRequest.isActive()%>">
                    <input type="submit" value="ACTIVATE">
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <h1 style="text-align: center">Active</h1>
    <table>
        <tr>
            <th>Full Name</th>
            <th>Email</th>
            <th>Message</th>
            <th>Contact Date</th>
            <th>Action</th>
        </tr>
        <%
            Iterator<Request> activeRequestIterator = activeRequests.iterator();
            while (activeRequestIterator.hasNext()) {
                Request activeRequest = activeRequestIterator.next();
        %>
        <tr>
            <td><%=  activeRequest.getFullName()%>
            </td>
            <td><%=  activeRequest.getEmail()%>
            </td>
            <td><%=  activeRequest.getMessage()%>
            </td>
            <td><%=  activeRequest.getRequestDate()%>
            </td>
            <td>
                <form action="requests" method="post">
                    <input type="hidden" name="requestId" value="<%= activeRequest.getRequestId()%>">
                    <input type="hidden" name="isActive" value="<%=activeRequest.isActive()%>">
                    <input type="submit" value="PROCESS">
                </form>
            </td>

        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>

