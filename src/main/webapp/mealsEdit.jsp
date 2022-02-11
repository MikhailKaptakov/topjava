<%@ page contentType="text/html; charset=UTF-8" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Add or edit</title>
</head>
<body>
    <h3>Edit form</h3>
    <hr>
    <c:set var="meal" value="${requestScope.meal}"/>
    <c:if test="${meal==null}">
        <c:set var="typeIdField" value="hidden"/>
        <c:set var="mealId" value=""/>
        <c:set var="dateTime" value="2000.01.01 00:00"/>
        <c:set var="description" value=""/>
        <c:set var="calories" value="0"/>
    </c:if>
    <c:if test="${meal!=null}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal"/>
        <c:set var="typeIdField" value="text"/>
        <c:set var="mealId" value="${meal.id}"/>
        <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parseDate"/>
        <fmt:formatDate pattern="yyyy.MM.dd HH:mm" value="${parseDate}" var="dateTime"/>
        <c:set var="description" value="${meal.description}"/>
        <c:set var="calories" value="${meal.calories}"/>
    </c:if>
    <form method="POST" action='mealController'>
        ID :
        <label>
            <input type="${typeIdField}" readonly="readonly" name="mealId" value="<c:out value="${mealId}"/>"/>
        </label>
        <br/>
        Date&Time :
        <label>
            <input type="text" name="dateTime"
                       value="${dateTime}"/>"/>
        </label>
        <br/>
        Description :
        <label>
            <input type="text" name="description" value="<c:out value="${description}" />"/>
        </label>
        <br/>
        Calories :
        <label>
            <input type="text" name="calories" value="<c:out value="${calories}"/>"/>
        </label>
        <br/>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>