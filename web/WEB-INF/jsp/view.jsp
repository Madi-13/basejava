<%@ page import="ru.javaops.webapp.model.Section" %>
<%@ page import="ru.javaops.webapp.model.Text" %>
<%@ page import="ru.javaops.webapp.model.ListOfTexts" %>
<%@ page import="ru.javaops.webapp.model.ListOfOrganizations" %>
<%@ page import="ru.javaops.webapp.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
    <jsp:include page="fragments/header.jsp"/>
    <section>
        <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a> </h2>
        <p>
            <c:forEach items="${resume.contacts}" var="contactEntry">
                <jsp:useBean id="contactEntry" type="java.util.Map.Entry<ru.javaops.webapp.model.Contact, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br>
            </c:forEach>
        </p>
        <hr>
        <table cellpadding="5">
            <tbody>
                <c:forEach items="${resume.sections}" var="sectionEntry">
                    <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<ru.javaops.webapp.model.Section, ru.javaops.webapp.model.SectionInfo>"/>
                    <tr>
                        <td colspan="20"><h2><%=sectionEntry.getKey().getTitle()%></h2></td>
                    </tr>
                    <c:choose>
                        <c:when test="<%=sectionEntry.getKey() == Section.PERSONAL || sectionEntry.getKey() == Section.OBJECTIVE%>">
                            <tr>
                                <td colspan="20"><%=((Text)sectionEntry.getValue()).getText()%></td>
                            </tr>
                        </c:when>
                        <c:when test="<%=sectionEntry.getKey() == Section.ACHIEVEMENT || sectionEntry.getKey() == Section.QUALIFICATIONS%>">
                            <tr>
                                <td colspan="20">
                                    <ul>
                                        <c:forEach items="<%=((ListOfTexts)sectionEntry.getValue()).getTexts()%>" var="text">
                                            <li>${text}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="<%=sectionEntry.getKey() == Section.EXPERIENCE || sectionEntry.getKey() == Section.EDUCATION%>">
                                <c:forEach items="<%=((ListOfOrganizations)sectionEntry.getValue()).getOrganizations()%>" var="org">
                                    <tr>
                                    <td style="vertical-align: top" width="15%">
                                        <h3><a href=${org.link.url}>${org.link.title}</a></h3>
                                    </td>
                                    </tr>
                                    <c:forEach items="${org.positions}" var="position">
                                    <tr>
                                        <td style="vertical-align: top" width="15%">${DateUtil.toString(position.startDate)}-${DateUtil.toString(position.endDate)}</td>
                                        <td><b>${position.title}</b>.<br>${position.info}</td>
                                    </tr>
                                    </c:forEach>
                                </c:forEach>
                        </c:when>
                        </c:choose>
                    </c:forEach>
            </tbody>
        </table>
    </section>
    <button onclick="window.history.back()">ОК</button>
    <jsp:include page="fragments/footer.jsp"/>
</body>
</html>
