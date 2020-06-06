package edu.ufp.aed2.project;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Professor extends Person {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private String course;

    private final ArrayList<Schedule> attendanceSchedule;

    public Professor(String id, String name, String course) {
        super(id, name);
        this.course = course;
        attendanceSchedule = new ArrayList<>();
    }

    public ArrayList<Schedule> getAttendanceSchedule() {
        return attendanceSchedule;
    }

    /**
     * @param s schedule being added to attendanceSchedule.
     */
    public void addAttendanceSchedule(Schedule s) {
        if (this.attendanceSchedule.size() == 0) {
            this.attendanceSchedule.add(s);
            return;
        }

        // Using for loop to avoid java.util.ConcurrentModificationException **facepalm**
        for (Schedule schedule : this.attendanceSchedule) {
            if (schedule.compareTo(s) == 0) {
                return;
            }
        }
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
