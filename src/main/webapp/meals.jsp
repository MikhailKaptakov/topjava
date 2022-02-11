<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
    <table>
        <caption> Meals_table</caption>
        <tr>
            <th style="background-color:LightCyan">№</th>
            <th style="background-color:LightCyan">Date&Time</th>
            <th style="background-color:LightCyan">Description</th>
            <th style="background-color:LightCyan">Calories</th>
            <th style="background-color:LightCyan">ID</th>
            <th colspan=2>Action</th>
         </tr>
        <c:set var="index" value="${0}"/>
        <c:forEach items="${requestScope.meals}" var="mealTo">
            <c:set var="index" value="${index=index+1}"/>
            <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
            <c:if test="${mealTo.excess}">
                 <c:set var="color" value="tomato"/>
            </c:if>
            <c:if test="${!mealTo.excess}">
                <c:set var="color" value="palegreen"/>
            </c:if>
            <tr style="background-color:${color}">

                <td>${index}</td>
                <td>
                     <fmt:parseDate value="${mealTo.dateTime}" pattern="y-M-dd'T'H:m" var="parseDate"/>
                     <fmt:formatDate value="${parseDate}" pattern="yyyy.MM.dd HH:mm"/>
                </td>
                <td>${mealTo.description}</td>
                <td>${mealTo.calories}</td>
                <td>${mealTo.id}</td>
                <td><a href="mealController?action=edit&mealId=<c:out value="${mealTo.id}"/>">Update</a></td>
                <td><a href="mealController?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="mealController?action=insert">Add</a>
</body>
</html>