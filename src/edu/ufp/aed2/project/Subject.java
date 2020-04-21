package edu.ufp.aed2.project;

import java.util.ArrayList;

public class Subject {
    private String name;
    private int ects;
    private String sigle;
    private ArrayList<Class> classes;

    public Subject(String name, int ects, String sigle) {
        this.name = name;
        this.ects = ects;
        this.sigle = sigle;
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

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    @Override
    public String toString() {
        return "Subject" +
                "\nname= '" + name + '\'' +
                "\nects=" + ects;
    }
}
