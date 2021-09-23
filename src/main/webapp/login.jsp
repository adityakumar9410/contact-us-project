<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <link href="css/logins.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <div class="login-form">
        <h2>Admin Login</h2>
        <form action="login" method="post">
            <label>UserName</label><br>
            <input type="text" name="username"><br>
            <label>Password</label><br>
            <input type="password" name="password"><br>
            <input class="btn" type="submit" value="Login">
        </form>
        <div>
            <h4>
                <% if (request.getAttribute("error") != null) {%>
                <%= request.getAttribute("error")%>
                <%}%>
            </h4>

            <h4>
                <%
                    String logoutMessage = (String) session.getAttribute("logoutMsg");
                    if (logoutMessage != null) {%>
                <%= logoutMessage%>
                <%
                    }
                %>
            </h4>
        </div>
    </div>
</div>
</body>
</html>
