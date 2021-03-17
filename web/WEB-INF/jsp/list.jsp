<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javaops.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javaops.webapp.model.Contact" %>
<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<section>
    <jsp:include page="fragments/header.jsp"/>
    <table border="5" border-color="blue" cellpadding="8" cellspacing="0">
        <tr>
            <th>Полное Имя</th>
            <th>View</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
        <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume"/>
        <tr>
            <td>${resume.fullName}</td>
            <td><a href="resume?uuid=${resume.uuid}&action=view"><img src="img/view.png"></a></td>
            <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
            <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
        </tr>
        </c:forEach>
    </table>
    <p>
        <a href="resume?action=add"><img src="img/add.png"></a>
    </p>
    <jsp:include page="fragments/footer.jsp"/>
</section>
</body>
</html>
