package edu.ufp.aed2.project;

import edu.ufp.aed2.project.exceptions.PersonNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Main class for the whole system.
 * When you add something into the Class you add to the University STs also
 * So when you remove the class in the University method removeClass() , your deleting the entire class such
 * as the students referred to it.
 */
public class Class {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private final String course;
    private final String type;
    private final String initials;
    private final University university;
    private final Subject subject;
    private final Professor professor;
    private Schedule schedule;
    private ArrayList<Student> students;

    public Class(String course, String type, String initials, Schedule schedule, University university,
                 Subject subject, Professor professor, ArrayList<Student> students) {
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
        this.students = students;
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
        LOGGER.info("[WARNING] Class.java - addStudent():");
        LOGGER.info("[WARNING] Student already added to students.");
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
        LOGGER.info("[WARNING] Class.java - removeStudent():");
        LOGGER.info("[WARNING] Student not found in this class.");
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

    public String getInitials() {
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
        return "\n\tType: " + type +
                "\n\tInitials: " + initials +
                "\n\tSubject: " + subject.getName() +
                "\n\tProfessor: " + professor.getName() +
                "\n\tSchedule: " + schedule.toString();
    }

    public void printStudents() {
        for (Student student : students) {
            LOGGER.info(student.toString());
        }
    }

    public boolean isHavingClassesIn(InstantTime instantTime) {
        return this.schedule.interceptThisInstantTime(instantTime);
    }
}
