package edu.ufp.aed2.project;

import java.util.ArrayList;

/**
 * Main class for the whole system.
 * When you add something into the Class you add to the University STs also
 * So when you remove the class in the University method removeClass() , your deleting the entire class such
 * as the students referred to it.
 */
public class Class {
    private final String course;
    private final String type;
    private final String initials;
    private final University university;
    private final Subject subject;
    private Schedule schedule;
    private final Professor professor;
    private final ArrayList<Student> students;

    public Class(String course, String type, String initials, Schedule schedule, University university, Subject subject, Professor professor) {
        this.course = course;
        this.type = type;
        this.initials = initials;
        this.schedule = schedule;
        this.students = new ArrayList<>();
        this.university = university;
        this.subject = subject;
        this.professor = professor;
        professor.addClass(this);
        this.university.addProfessor(professor, this);
    }

    /**
     * @param student being added to students ArrayList
     * @throws PersonNotFoundException if the person doesn't exist.
     */
    public void addStudent(Student student) throws PersonNotFoundException {
        if (!this.students.contains(student)) {
            // doesn't contains this student in students ArrayList
            this.students.add(student);     // adds this students to arraylist
            student.addClass(this);     // adds this class to the student's class
            return;
        }
        System.out.println("[WARNING] Class.java - addStudent():");
        System.out.println("[WARNING] Student already added to students.");
        throw new PersonNotFoundException(student.getName());
    }

    /**
     * @param student to be removed from the student's list.
     * @throws PersonNotFoundException if the person doesn't exist.
     */
    public void removeStudent(Student student) throws PersonNotFoundException {
        if (this.students.contains(student)) {
            this.students.remove(student);
            student.removeClass(this);
            return;
        }
        System.out.println("[WARNING] Class.java - removeStudent():");
        System.out.println("[WARNING] Student not found in this class.");
        throw new PersonNotFoundException(student.getName());
    }


    public ArrayList<Student> getStudents() {
        return students;
    }

    public String getCourse() {
        return course;
    }

    public String getType() {
        return type;
    }

    public String getSigle() {
        return initials;
    }

    public Subject getSubject() {
        return subject;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public University getUniversity() {
        return university;
    }

    @Override
    public String toString() {
        return "Type: " + type + '\n' +
                "Sigle: " + initials + '\n' +
                "Subject: " + subject.getName() + '\n' +
                "Professor: " + professor.getName() + '\n' +
                "Schedule: " + schedule.toString();
    }

    public void printStudents() {
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }
}
