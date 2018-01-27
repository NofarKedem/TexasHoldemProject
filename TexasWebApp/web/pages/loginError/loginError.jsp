<%--
  Created by IntelliJ IDEA.
  User: nofarkedemzada
  Date: 1/26/18
  Time: 2:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <%@page import="utils.*" %>
    <%@ page import="utils.SessionUtils" %>

    <head>
    <title>Texas Holdem</title>
</head>
<body>
    <div>
        <% String usernameFromSession = SessionUtils.getUsername(request);%>
        <% String usernameFromParameter = request.getParameter("username") != null ? request.getParameter("username") : "";%>
        <% if (usernameFromSession == null) {%>
        <h1>Welcome to Texas Holdem Game</h1>
        <br/>
        <h2>Please enter a unique user name:</h2>
        <form method="GET" action="login">
            <input type="text" name="<%="username"%>" value="<%=usernameFromParameter%>"/>
            <br/>
            <input type="radio" name="userType" value="Human Player" checked> Human Player <br>
            <input type="radio" name="userType" value="Computer Player"> Computer Player <br>
            <input type="submit" value="Login"/>
        </form>
        <% Object errorMessage = request.getAttribute("username_error");%>
        <% if (errorMessage != null) {%>
        <span class="bg-danger" style="color:red;"><%=errorMessage%></span>
        <% } %>
        <% } else {%>
        <h1>Welcome back, <%=usernameFromSession%></h1>
        <a href="/pages/gamesManager/gamesManager.html">Click here to enter the Texas Holdem</a>
        <br/>
        <a href="login?logout=true" id="logout">logout</a>
        <% }%>
    </div>
</body>
</html>
