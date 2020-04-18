package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.RedBlackBST;

public class Professor extends Person{
    private String course;
    public Professor(String id, String name, String course) {
        super(id, name);
        this.course = course;
    }


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
