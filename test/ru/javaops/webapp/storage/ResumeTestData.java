package ru.javaops.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.util.DateUtil;

import java.time.Month;

import static org.junit.Assert.assertEquals;

public class ResumeTestData {
    private Resume resume;
    private final Organization Enkata = new Organization(new Link("Enkata", "http://enkata.com/"),
            new Organization.Position(DateUtil.of(2007, Month.of(3)),
                    DateUtil.of(2006, Month.of(6)),
                    " \tРазработчик ПО",
                    "Реализация клиентской (Eclipse RCP) и " +
                            "серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                            "частей кластерного J2EE приложения (OLAP, Data mining)."));
    private final Organization Wrike = new Organization(new Link("Wrike", "https://www.wrike.com/"),
            new Organization.Position(DateUtil.of(2014, Month.of(10)),
                    DateUtil.of(2016, Month.of(1)),
                    "Старший разработчик (backend)",
                    "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                            "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                            "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
    private final Organization Coursera = new Organization(new Link("Coursera", "https://www.coursera.org/course/progfun"),
            new Organization.Position(DateUtil.of(2013, Month.of(3)),
                    DateUtil.of(2013, Month.of(5)),
                    "Student",
                    "\"Functional Programming Principles in Scala\" by Martin Odersky"));
    private final Organization Luxoft = new Organization(new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
            new Organization.Position(DateUtil.of(2011, Month.of(3)),
                    DateUtil.of(2011, Month.of(4)),
                    "Student",
                    "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));


    public void setResumeFull(Resume resume) {
        setResumeWithContacts(resume);
        setResumeWithSections(resume);
    }

    public void setResumeWithContacts(Resume resume) {
        resume.addContact(Contact.PHONE, "11111");
        resume.addContact(Contact.MOBILE, "22222");
        resume.addContact(Contact.HOME_PHONE, "33333");
        resume.addContact(Contact.SKYPE, "skype");
        resume.addContact(Contact.MAIL, "mail1@ya.ru");
        resume.addContact(Contact.LINKEDIN, "linkedin");
        resume.addContact(Contact.GITHUB, "github");
        resume.addContact(Contact.STACKOVERFLOW, "stackoverflow");
        resume.addContact(Contact.HOME_PAGE, "home_page.html");
    }

    public void setResumeWithSections(Resume resume) {
        resume.addSection(Section.OBJECTIVE, new Text("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(Section.PERSONAL, new Text("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.addSection(Section.ACHIEVEMENT, new ListOfTexts("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."));
        resume.addSection(Section.QUALIFICATIONS, new ListOfTexts("MySQL, SQLite, MS SQL, HSQLDB",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements). "));
        resume.addSection(Section.EXPERIENCE, new ListOfOrganizations(Enkata, Wrike));
        resume.addSection(Section.EDUCATION, new ListOfOrganizations(Coursera, Luxoft));
    }

    @Before
    public void init() {
        resume = new Resume("uuid1", "Name1");
    }

    @Test
    public void testContact() {
        resume.addContact(Contact.MAIL, "mail1@ya.ru");
        resume.addContact(Contact.PHONE, "11111");
        assertEquals("mail1@ya.ru", resume.getContact(Contact.MAIL));
        assertEquals("11111", resume.getContact(Contact.PHONE));
    }

    @Test
    public void testSectionObjective() {
        Text text = new Text("Objective1");
        resume.addSection(Section.OBJECTIVE, text);
        assertEquals(text, resume.getSection(Section.OBJECTIVE));
    }

    @Test
    public void testSectionAchievement() {
        ListOfTexts list = new ListOfTexts("Achievement11", "Achievement12", "Achievement13");
        resume.addSection(Section.ACHIEVEMENT, list);
        assertEquals(list, resume.getSection(Section.ACHIEVEMENT));
    }

    @Test
    public void testSectionExperience() {
        ListOfOrganizations list = new ListOfOrganizations(Enkata, Wrike);
        resume.addSection(Section.EXPERIENCE, list);
        assertEquals(list, resume.getSection(Section.EXPERIENCE));
    }
}
