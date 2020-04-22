package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.RedBlackBST;

import java.util.ArrayList;

public class Professor extends Person{
    private String course;
    private ArrayList<Schedule> attendanceSchedule;
    public Professor(String id, String name, String course) {
        super(id, name);
        this.course = course;
        attendanceSchedule = new ArrayList<>();
    }

    /**
     * @param s schedule being added to attendanceSchedule.
     */
    public void addAttendanceSchedule(Schedule s){
        this.attendanceSchedule.add(s);
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Professor" +
                "\ncourse='" + course + '\'' +
                "\nname='" + this.getName() + '\'' +
                "\nid='" + this.getId() + '\'';
    }
}
