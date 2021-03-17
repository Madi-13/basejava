<%@ page import="ru.javaops.webapp.model.*" %>
<%@ page import="ru.javaops.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script>
        $(document).ready(function (e) {
            $('#addAch').click(function (e) {
                addText('ACHIEVEMENT', 'ListOfAch', 'delAch');
            });

            $('#addQua').click(function (e) {
                addText('QUALIFICATIONS', 'ListOfQua', 'delQua');
            });

            function addText(name, idList, idDel) {
                $('#' + idList).append("<div>\n" +
                    "<textarea name=\"" + name + "\" rows=\"2\" cols=\"200\" class=\"text\"></textarea>\n" +
                    "<a href=\"##\" id=\""+ idDel +"\"><img src=\"img/delete.png\"></a>\n" +
                    "</div>");
            }
            $('#ListOfAch').on('click','#delAch', function (e) {
                $(this).parent('div').remove()
            });
            $('#ListOfQua').on('click','#delQua', function (e) {
                $(this).parent('div').remove()
            });

            $('#addExp').click(function (e) {
                addOrg('EXPERIENCE', 'ListOfExp', 'addExp', 'delExp');
            });

            $('#addEdu').click(function (e) {
                addOrg('EDUCATION', 'ListOfEdu', 'addEdu', 'delEdu');
            });

            $('#ListOfExp').on('click','#delExp', function (e) {
                $(this).parent('div').remove()
            });
            $('#ListOfEdu').on('click','#delEdu', function (e) {
                $(this).parent('div').remove()
            });

            function addOrg(name, idList, idAddPos, idDelOrg) {
                $('#' + idList).
                append("<div>\n" +
                    "<dd>\n" +
                    "<p>\n" +
                    "<dt>Организация</dt>\n" +
                    "<dh><input type=\"text\" name=\"" + name + "\"></dh>\n" +
                    "<a href=\"##\" id=\"" + idDelOrg + "\"><img src=\"img/delete.png\"></a>\n" +
                    "</p>\n" +
                    "<p>\n" +
                    "<dt>Сайт Организации</dt>\n" +
                    "<dh><input type=\"text\" name=\"" + name + "orgLink\"></dh>\n" +
                    "</p>\n" +
                    "<p>\n" +
                    "<dt>Должность</dt>\n" +
                    "<dh><input type=\"text\" name=\"" + name + "posTitle\"></dh>\n" +
                    "</p>\n" +
                    "<p>\n" +
                    "<dt>Дата приема</dt>\n" +
                    "<dh>Месяц: <input type=\"number\" name=\"" + name + "posStartDateMonth\" min=\"1\" max=\"12\">\n" +
                    "Год: <input type=\"number\" name=\"" + name + "posStartDateYear\"  min=\"1970\" max=\"2021\">\n" +
                    "</dh>\n" +
                    "</p>\n" +
                    "<p>\n" +
                    "<dt>Дата увольнения</dt>\n" +
                    "<dh>Месяц: <input type=\"number\" name=\"" + name + "posEndDateMonth\"  min=\"1\" max=\"12\">\n" +
                    "Год: <input type=\"number\" name=\"" + name + "posEndDateYear\"  min=\"1970\" max=\"2021\">\n" +
                    "</dh>\n" +
                    "</p>\n" +
                    "<p>\n" +
                    "<dt>Информация</dt>\n" +
                    "<dl><textarea rows=\"2\" cols=\"200\" name=\"" + name + "posInfo\"></textarea></dl>\n" +
                    "</p>\n" +
                    "</div>"
                    )
            }
        });

    </script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=Contact.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="section" items="<%=Section.values()%>">
            <jsp:useBean id="section" type="ru.javaops.webapp.model.Section"/>
            <dl>
                <dt>${section.title}</dt>
                <c:choose>
                    <c:when test="${section == Section.OBJECTIVE || section == Section.PERSONAL}">
                        <dd>
                            <div>
                            <textarea name="${section.name()}" rows="2" cols="200" class="text"><%= resume.getSection(section) == null? "" : (((Text) resume.getSection(section)).getText())%></textarea>
                            </div>
                        </dd>
                    </c:when>
                    <c:when test="${section == Section.ACHIEVEMENT || section == Section.QUALIFICATIONS}">
                        <dd id="${section == Section.ACHIEVEMENT? "ListOfAch" : "ListOfQua"}">
                        <c:forEach var="text" items="<%=resume.getSection(section) == null? null : ((ListOfTexts)resume.getSection(section)).getTexts()%>">
                            <div>
                                <textarea name="${section.name()}" rows="2" cols="200" class="text">${text.text}</textarea>
                                <a href="##" id="${section == Section.ACHIEVEMENT? "delAch" : "delQua"}"><img src="img/delete.png"></a>
                            </div>
                        </c:forEach>
                        </dd>
                        <a href="##" id="${section == Section.ACHIEVEMENT? "addAch" : "addQua"}">Добавить</a>
                    </c:when>
                    <c:when test="${section == Section.EXPERIENCE || section == Section.EDUCATION}">
                        <dd id="${section == Section.EXPERIENCE? "ListOfExp" : "ListOfEdu"}">
                        <dl>
                            <c:forEach var="org" items="<%=resume.getSection(section) == null? null: ((ListOfOrganizations)resume.getSection(section)).getOrganizations()%>">
                                <div>
                                <c:forEach var="pos" items="${org.positions}">
                                <dd>
                                    <p>
                                        <dt>Организация</dt>
                                        <dh><input type="text" name="${section.name()}" value="${org.link.title}"></dh>
                                        <a href="##" id="${section == Section.EXPERIENCE? "delExp" : "delEdu"}"><img src="img/delete.png"></a>
                                    </p>
                                    <p>
                                        <dt>Сайт Организации</dt>
                                        <dh><input type="text" name="${section.name()}orgLink" value="${org.link.url}"></dh>
                                    </p>
                                    <p>
                                        <dt>Должность</dt>
                                        <dh><input type="text" name="${section.name()}posTitle" value="${pos.title}"></dh>
                                    </p>
                                    <p>
                                        <dt>Дата приема</dt>
                                        <dh>Месяц: <input type="number" name="${section.name()}posStartDateMonth" value="${pos.startDate.monthValue}" min="1" max="12">
                                            Год: <input type="number" name="${section.name()}posStartDateYear" value="${pos.startDate.year}" min="1970" max="2021">
                                        </dh>
                                    </p>
                                    <p>
                                        <dt>Дата увольнения</dt>
                                        <dh>Месяц: <input type="number" name="${section.name()}posEndDateMonth" value="${pos.endDate == DateUtil.NOW? '' : pos.endDate.monthValue}" min="1" max="12">
                                            Год: <input type="number" name="${section.name()}posEndDateYear" value="${pos.endDate == DateUtil.NOW? '' : pos.endDate.year}" min="1970" max="2021">
                                        </dh>
                                    </p>
                                    <p>
                                        <dt>Информация</dt>
                                        <dl><textarea rows="2" cols="200" name="${section.name()}posInfo">${pos.info}</textarea></dl>
                                    </p>
                                </c:forEach>
                                </div>
                            </c:forEach>
                            </dl>
                        </dd>
                        <a href="##" id="${section == Section.EXPERIENCE? "addExp" : "addEdu"}">Добавить</a>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit" onclick="location.href='/WEB-INF/jsp/view.jsp'">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>