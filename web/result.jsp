<%--
  Created by IntelliJ IDEA.
  User: ruisun
  Date: 9/23/20
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%= request.getAttribute("doctype") %>
<html>
<head>
    <title>Project 1 Task 2</title>
</head>
<body>
<h1>State: <%= request.getAttribute("state") %></h1>
<img src="<%= request.getAttribute("flag") %>">
<p>Credit: https://en.wikipedia.org</p>
<h2>Statistic: <%= request.getAttribute("category") %></h2>
<h2>From: <%= request.getAttribute("from") %></h2>
<h2>To: <%= request.getAttribute("to") %></h2>
<h2>Change: <%= request.getAttribute("change") %></h2>
<a href="project1Task2">
    <button>Continue</button>
</a>
</body>
</html>
