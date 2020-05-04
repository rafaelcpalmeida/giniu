package edu.ufp.aed2.project;

import java.util.ArrayList;

public class Student extends Person {
    public Student(String id, String name) {
        super(id, name);
    }

    /**
     * Intersects this student schedule with the attendanceSchedule from the
     * professor and returns a valid schedule that can be valid for both.
     *
     * @param professor that we need info
     * @return with the valid schedules.
     */
    public ArrayList<Schedule> getScheForProf(Professor professor) {
        return null;
    }
}
