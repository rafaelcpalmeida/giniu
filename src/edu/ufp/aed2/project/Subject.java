package edu.ufp.aed2.project;

import java.util.ArrayList;

public class Subject {
    private String name;
    private int ects;
    private String initials;
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

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public String getInitials() {
        return initials;
    }

    public void setinitials(String initials) {
        this.initials = initials;
    }

    @Override
    public String toString() {
        return "Subject" +
                "\nname= '" + name + '\'' +
                "\nects=" + ects;
    }
}
