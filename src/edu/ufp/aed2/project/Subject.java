package edu.ufp.aed2.project;

import java.io.Serializable;
import java.util.ArrayList;

public class Subject implements Serializable {
    private String name;
    private final int ects;
    private final String initials;
    private ArrayList<Class> classes;

    public Subject(String name, int ects, String initials) {
        this.name = name;
        this.ects = ects;
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEcts() {
        return ects;
    }

    public String getInitials() {
        return initials;
    }

    @Override
    public String toString() {
        return "Subject" +
                "\nname= '" + name + '\'' +
                "\nects=" + ects;
    }
}
