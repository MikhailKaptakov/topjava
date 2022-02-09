<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="ru.javawebinar.topjava.model.MealTo"%>
<%@ page import="java.util.List" %>

<html lang="ru">
<head>
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
        <th style="background-color:LightCyan">Date</th>
        <th style="background-color:LightCyan">Time</th>
        <th style="background-color:LightCyan">Description</th>
        <th style="background-color:LightCyan">Calories</th>
        <th style="background-color:LightCyan">ID</th>
    </tr> <!--ряд с ячейками заголовков-->

    <% List<MealTo> meals = (List<MealTo>)request.getAttribute("meals");
        int index = 0;
        for (MealTo meal : meals) {
            index++;
            String color;
            if (meal.isExcess()) {
                color = "tomato";
            } else {
                color = "palegreen";
            }
            String date = meal.getDate().toString();
            String time = meal.getTime().toString();
            String description = meal.getDescription();
            int calories = meal.getCalories();
            Integer id = meal.getId();%>
        <tr style="background-color:<%= color%>" >
    <td>
        <%= index%>
    </td>
    <td>
        <%= date%>
    </td>
    <td>
        <%= time%>
    </td>
    <td>
        <%= description%>
    </td>
    <td>
        <%= calories%>
    </td>
    <td>
        <%= id%>
    </td>
    </tr>
    <%}%>

</table>
</body>
</html>