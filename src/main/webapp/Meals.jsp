<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html lang="ru">
<head>
    <title>Show Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
    <table border=1>
        <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${mealTo}" var="meal">
                <tr>
                    <td>${meal.dateTime}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td>href </td>
                    <td>href </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>