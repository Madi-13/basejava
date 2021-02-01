package ru.javaops.webapp.storage;

import org.junit.Before;
import ru.javaops.webapp.model.*;

import java.time.LocalDate;

public class ResumeTestData {
    private Resume resume;
    private final Organization Enkata = new Organization(new Link("Enkata", "http://enkata.com/"),
                                                            LocalDate.of(2007, 3, 1),
                                                            LocalDate.of(2006, 6, 1),
                                                            " \tРазработчик ПО\n" +
                                                                    "Реализация клиентской (Eclipse RCP) и " +
                                                                    "серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                                                                    "частей кластерного J2EE приложения (OLAP, Data mining).");
    private final Organization Wrike = new Organization(new Link("Wrike", "https://www.wrike.com/"),
                                                    LocalDate.of(2014, 10, 1),
                                                    LocalDate.of(2016, 1, 1),
                                                "Старший разработчик (backend)\n" +
                                                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                                        "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
    private final Organization Coursera = new Organization(new Link("Coursera", "https://www.coursera.org/course/progfun"),
                                                            LocalDate.of(2013, 3, 1),
                                                            LocalDate.of(2013, 5, 1),
                                                            "\"Functional Programming Principles in Scala\" by Martin Odersky");
    private final Organization Luxoft = new Organization(new Link("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                                                            LocalDate.of(2011, 3, 1),
                                                            LocalDate.of(2011, 4, 1),
                                                            "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");



    @Before
    public void init() {
        resume = new Resume("uuid1", "Name1");
        resume.addContact(Contact.MAIL, "mail1@ya.ru");
        resume.addContact(Contact.PHONE, "11111");
        resume.addSection(Section.OBJECTIVE, new Text("Objective1"));
        resume.addSection(Section.PERSONAL, new Text("Personal data"));
        resume.addSection(Section.ACHIEVEMENT, new ListOfTexts("Achievement11", "Achievement12", "Achievement13"));
        resume.addSection(Section.QUALIFICATIONS, new ListOfTexts("Java", "SQL", "JavaScript"));
        resume.addSection(Section.EXPERIENCE, new ListOfOrganizations(Enkata, Wrike));
        resume.addSection(Section.EDUCATION, new ListOfOrganizations(Coursera, Luxoft));
    }

}
