package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("123");

        Field field = resume.getClass().getDeclaredFields()[0];
        System.out.println(field.getName());
        field.setAccessible(true);
        System.out.println(field.get(resume));
        field.set(resume, "23");
        System.out.println(resume);

        field.set(resume, "uuid100");
        Method method = resume.getClass().getMethod("toString");
        String toStr = (String) method.invoke(resume);
        System.out.println(toStr);

        method = Resume.class.getMethod("compareTo", Resume.class);

        int result = (int) method.invoke(resume, new Resume("uuid099"));
        System.out.println(result);
    }
}
