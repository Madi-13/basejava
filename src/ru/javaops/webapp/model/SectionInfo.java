package ru.javaops.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD) // to work with fields without setters for XmlParser
public abstract class SectionInfo implements Serializable {
}
