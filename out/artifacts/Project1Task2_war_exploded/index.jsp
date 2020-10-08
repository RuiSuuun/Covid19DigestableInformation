<%--
  Created by IntelliJ IDEA.
  User: ruisun
  Date: 9/22/20
  Time: 22:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%= request.getAttribute("doctype") %>
<html>
  <head>
    <title>Project 1 Task 2</title>
  </head>
  <body>
  <h1>Covid Trends by State</h1>
  <h4>Created by Rui Sun</h4>
  <form action="project1Task2" method="post">
    <label for="state">Choose the name of a state:</label><br>
    <select id="state" name="state">
      <% String[][] abbrStates = (String[][]) request.getAttribute("abbrStates"); for (String[] state: abbrStates ) { %>
      <option value="<%= state[0] %>"><%= state[1] %></option>
      <% } %>
    </select><br><br>
    <label for="category">Enter the name of a statistic:</label><br>
    <select name="category" id="category">
      <option value="positive">Positive Cases</option>
      <option value="negative">Negative Cases</option>
      <option value="hospitalizedCurrently">Currently Hospitalized</option>
      <option value="hospitalizedCumulative">Cumulative Hospitalized</option>
    </select><br><br>
    <label for="startDate">Enter the start date: </label><br>
    <input type="date" id="startDate" name="startDate" min="2020-03-01"><br><br>
    <label for="endDate">Enter the end date: </label><br>
    <input type="date" id="endDate" name="endDate" min="2020-03-01"><br><br>
    <input type="submit" value="Submit">

  </form>
  </body>
</html>
