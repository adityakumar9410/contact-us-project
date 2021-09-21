<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact Us</title>
    <link href="css/contacts.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <div >
        <div class="header">
            <h1 class="heading">Contact Us</h1>
            <p class="contact-msg">Please fill this form in decent manner</p>
        </div>
    </div>

    <div class="form-head">
        <form action="contactus"  method="post">
            <p>
                <label>Full Name<span class="req">*</span></label><br>
                <input type="text" name="name"  required/>
            </p>
            <p class="email-class">
                <label>E-Mail <span class="req">*</span></label><br>
                <input type="email" name="email" required />
            </p>
            <p class="email-hint">example@gmail.com</p>
            <label>Message<span class="req">*</span></label><br>
            <textarea cols="40" rows="3" name="message" required></textarea>
            <input class="btn " type="submit" value="SUBMIT" />
        </form>
    </div>
    <h4>
        <% if(request.getAttribute("insertMsg")!=null){%>
        <%= request.getAttribute("insertMsg")%>
        <%}%>
    </h4>
</div>
</body>
</html>

