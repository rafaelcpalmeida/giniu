package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.util.ArrayList;

public class University {
    private final String name;
    private SeparateChainingHashST<Subject, ArrayList<Professor>> subjProf;
    private SeparateChainingHashST<Professor,ArrayList<Class>> profClass;
    private SeparateChainingHashST<String,ArrayList<Class>> courseClass;
    private RedBlackBST<Room,ArrayList<Subject>> roomSubject;

    public University(String name){
        this.name = name;
        subjProf = new SeparateChainingHashST<>();
        profClass = new SeparateChainingHashST<>();
        courseClass = new SeparateChainingHashST<>();
        roomSubject = new RedBlackBST<>();
    }

    /**
     * @return rooms being used now.
     */
    private ArrayList<Room> getRoomsUsedNow(){
        return null;
    }

    /**
     * @return subjects taught now.
     */
    private ArrayList<Subject> getSubjectsTaughtNow(){
        return null;
    }

    /**
     * @return available profs now.
     */
    private ArrayList<Professor> getAvailableProfsNow(){
        return null;
    }

    /**
     * @return busy profs now.
     */
    private ArrayList<Professor> getBusyProfsNow(){
        return null;
    }


    /**
     * Prints the current:
     * - Rooms being used now.
     * - Subjects being taught now.
     * - Available profs now.
     * - Busy profs now.
     */
    public void now(){

    }

    /**
     * Adds a professor to profClass and subjProf.
     * Adds a course to courseClass.
     * Adds a room to roomSubject
     * @param professor of the class.
     * @param pclass class of the professor.
     */
    public void addProfessor(Professor professor, Class pclass){
        Subject subject = pclass.getSubject();
        addProfessorToSubject(professor,subject);       // Adds Professor to subjProf
        addClassToCourse(professor.getCourse(),pclass);
        // Adding professor to profClass
        if (!this.profClass.contains(professor)) {
            // Professor not registered yet , need to create an ArrayList of classes!
            ArrayList<Class> classes = new ArrayList<>();
            classes.add(pclass);
            this.profClass.put(professor, classes);
        } else {
            // Already contains the professor , just add the class to ArrayList
            ArrayList<Class> classes = this.profClass.get(professor);
            if (!classes.contains(pclass)) {
                classes.add(pclass);
            } else {
                System.out.println("[WARNING] University.java - addProfessor():");
                System.out.println("[WARNING] Class already added to this professor.");
            }
        }

    }


    /**
     * @param course of the classes.
     * @param aclass added to the course.
     */
    private void addClassToCourse(String course, Class aclass){
        // Adding class to courseClass
        if (!this.courseClass.contains(course)) {
            // Course not registered yet , need to create an ArrayList of professors!
            ArrayList<Class> classes = new ArrayList<>();
            classes.add(aclass);
            this.courseClass.put(course, classes);
        } else {
            // Already contains the course , just add the class to ArrayList
            ArrayList<Class> classes = this.courseClass.get(course);
            if (!classes.contains(aclass)) {
                classes.add(aclass);
            } else {
                System.out.println("[WARNING] University.java - addCourseToClass():");
                System.out.println("[WARNING] Class already added to this course.");
            }
        }
    }


    /**
     * Adds a subject to a room.
     * @param room key.
     * @param subject value.
     */
    private void addSubjectToRoom(Room room , Subject subject){
        // Adding subject to Room
        if (!this.roomSubject.contains(room)) {
            // Room not registered yet , need to create an ArrayList of subjects!
            ArrayList<Subject> subjects = new ArrayList<>();
            subjects.add(subject);
            this.roomSubject.put(room, subjects);
        } else {
            // Already contains the room , just add the subject to ArrayList
            ArrayList<Subject> subjects = this.roomSubject.get(room);
            if (!subjects.contains(subject)) {
                subjects.add(subject);
            } else {
                System.out.println("[WARNING] University.java - addSubjectToRoom():");
                System.out.println("[WARNING] Subject already added to this room.");
            }
        }

    }




    /**
     * @param professor that teaches the subject.
     * @param subject being taught.
     */
    private void addProfessorToSubject(Professor professor , Subject subject){
        // Adding subject to profClass
        if (!this.subjProf.contains(subject)) {
            // Subject not registered yet , need to create an ArrayList of professors!
            ArrayList<Professor> professors = new ArrayList<>();
            professors.add(professor);
            this.subjProf.put(subject, professors);
        } else {
            // Already contains the subject , just add the professor to ArrayList
            ArrayList<Professor> professors = this.subjProf.get(subject);
            if (!professors.contains(professor)) {
                professors.add(professor);
            } else {
                System.out.println("[WARNING] University.java - addProfessorToSubject():");
                System.out.println("[WARNING] Professor already added to this subject.");
            }
        }

    }


    public void removeClass(Class aclass){

    }


}
