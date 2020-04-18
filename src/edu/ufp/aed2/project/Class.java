package edu.ufp.aed2.project;

import java.util.ArrayList;

public class Class {
    private final String course;
    private final String type;
    private final String sigle;
    private final University university;
    private Subject subject;
    private Schedule schedule;
    private Professor professor;
    private ArrayList<Student> students;

    public Class(String course, String type, String sigle, Schedule schedule,University university, Subject subject, Professor professor) {
        this.course = course;
        this.type = type;
        this.sigle = sigle;
        this.schedule = schedule;
        this.students = new ArrayList<>();
        this.university = university;
        this.subject = subject;
        this.professor = professor;
        this.university.addProfessor(professor,this);
    }

    /**
     * @param student being added to students ArrayList
     * @throws PersonNotFoundException if the person doesn't exist.
     */
    public void addStudent(Student student) throws Exception{
        if(!this.students.contains(student)){
            // doesn't contains this student in students ArrayList
            this.students.add(student);     // adds this students to arraylist
            student.addClass(this);     // adds this class to the student's class
        } else{
            System.out.println("[WARNING] Class.java - addStudent():");
            System.out.println("[WARNING] Student already added to students.");
            throw new PersonNotFoundException(student.getName());
        }
    }

    /**
     * @param student to be removed from the student's list.
     * @throws PersonNotFoundException if the person doesn't exist.
     */
    public void removeStudent(Student student) throws Exception{
        if(this.students.contains(student)){
            this.students.remove(student);
            student.removeClass(this);
        } else{
            System.out.println("[WARNING] Class.java - removeStudent():");
            System.out.println("[WARNING] Student not found in this class.");
            throw new PersonNotFoundException(student.getName());
        }
    }


    public String getCourse() {
        return course;
    }

    public String getType() {
        return type;
    }

    public String getSigle() {
        return sigle;
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
                "Sigle: " + sigle + '\n' +
                "Subject: " + subject.getName() + '\n' +
                "Professor: " + professor.getName() + '\n' +
                "Schedule: " + schedule.toString();
    }

    public void printStudents(){
        for(Student student : students){
            System.out.println(student.toString());
        }
    }
}
