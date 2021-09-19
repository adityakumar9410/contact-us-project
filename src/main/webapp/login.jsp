<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <h2>Admin Login</h2>
    <form action="login" method="post">
        <label>UserName</label><br>
        <input type="text" name="username"><br>
        <label>Password</label><br>
        <input type="password" name="password"><br>
        <input type="submit" value="Login">

    </form>
</div>


<% if(request.getAttribute("error")!=null){%>
<%= request.getAttribute("error")%><br>
<%}%>
</body>
</html>
