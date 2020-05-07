package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.util.ArrayList;

/**
 * Main class.
 * Has all information about the university.
 * All the information is held here.
 */
public class University {
    private static final Logger LOGGER = Logger.getLogger(University.class.getName());

    private final String name;
    private final SeparateChainingHashST<Subject, ArrayList<Professor>> subjProf;
    private final SeparateChainingHashST<Professor, ArrayList<Class>> profClass;
    private final SeparateChainingHashST<String, ArrayList<Class>> courseClass;
    private final RedBlackBST<Room, ArrayList<Subject>> roomSubject;

    public University(String name) {
        this.name = name;
        subjProf = new SeparateChainingHashST<>();
        profClass = new SeparateChainingHashST<>();
        courseClass = new SeparateChainingHashST<>();
        roomSubject = new RedBlackBST<>();
    }

    /**
     * @return university name
     */
    public String getName() {
        return name;
    }

    /**
     * @return rooms being used now.
     */
    private ArrayList<Room> getRoomsUsedNow() {
        return null;
    }

    /**
     * @return subjects taught now.
     */
    private ArrayList<Subject> getSubjectsTaughtNow() {
        return null;
    }

    /**
     * @return available profs now.
     */
    private ArrayList<Professor> getAvailableProfsNow() {
        return null;
    }

    /**
     * @return busy profs now.
     */
    private ArrayList<Professor> getBusyProfsNow() {
        return null;
    }


    /**
     * Prints the current:
     * - Rooms being used now.
     * - Subjects being taught now.
     * - Available profs now.
     * - Busy profs now.
     */
    public void now() {

    }

    /**
     * Adds a professor to profClass and subjProf.
     * Adds a course to courseClass.
     * Adds a room to roomSubject
     *
     * @param professor of the class.
     * @param pclass    class of the professor.
     */
    public void addProfessor(Professor professor, Class pclass) {
        Subject subject = pclass.getSubject();
        addProfessorToSubject(professor, subject);       // Adds Professor to subjProf
        addClassToCourse(professor.getCourse(), pclass);
        addSubjectToRoom(pclass.getSchedule().getRoom(), subject);
        // Adding professor to profClass
        if (!this.profClass.contains(professor)) {
            // Professor not registered yet, need to create an ArrayList of classes!
            ArrayList<Class> classes = new ArrayList<>();
            classes.add(pclass);
            this.profClass.put(professor, classes);
            return;
        }
        // Already contains the professor, just add the class to ArrayList
        ArrayList<Class> classes = this.profClass.get(professor);
        if (!classes.contains(pclass)) {
            classes.add(pclass);
            return;
        }
        LOGGER.info("[WARNING] University.java - addProfessor():");
        LOGGER.info("[WARNING] Class already added to this professor.");
    }

    /**
     * @param course of the classes.
     * @param aclass added to the course.
     */
    private void addClassToCourse(String course, Class aclass) {
        // Adding class to courseClass
        if (!this.courseClass.contains(course)) {
            // Course not registered yet, need to create an ArrayList of professors!
            ArrayList<Class> classes = new ArrayList<>();
            classes.add(aclass);
            this.courseClass.put(course, classes);
            return;
        }
        // Already contains the course, just add the class to ArrayList
        ArrayList<Class> classes = this.courseClass.get(course);
        if (!classes.contains(aclass)) {
            classes.add(aclass);
            return;
        }
        LOGGER.info("[WARNING] University.java - addCourseToClass():");
        LOGGER.info("[WARNING] Class already added to this course.");
    }

    /**
     * Adds a subject to a room.
     *
     * @param room    key.
     * @param subject value.
     */
    private void addSubjectToRoom(Room room, Subject subject) {
        // Adding subject to Room
        if (!this.roomSubject.contains(room)) {
            // Room not registered yet, need to create an ArrayList of subjects!
            ArrayList<Subject> subjects = new ArrayList<>();
            subjects.add(subject);
            this.roomSubject.put(room, subjects);
            return;
        }
        // Already contains the room, just add the subject to ArrayList
        ArrayList<Subject> subjects = this.roomSubject.get(room);
        if (!subjects.contains(subject)) {
            subjects.add(subject);
            return;
        }
        LOGGER.info("[WARNING] University.java - addSubjectToRoom():");
        LOGGER.info("[WARNING] Subject already added to this room.");

    }


    /**
     * @param professor that teaches the subject.
     * @param subject   being taught.
     */
    private void addProfessorToSubject(Professor professor, Subject subject) {
        // Adding subject to profClass
        if (!this.subjProf.contains(subject)) {
            // Subject not registered yet, need to create an ArrayList of professors!
            ArrayList<Professor> professors = new ArrayList<>();
            professors.add(professor);
            this.subjProf.put(subject, professors);
            return;
        }
        // Already contains the subject, just add the professor to ArrayList
        ArrayList<Professor> professors = this.subjProf.get(subject);
        if (!professors.contains(professor)) {
            professors.add(professor);
            return;
        }
        LOGGER.info("[WARNING] University.java - addProfessorToSubject():");
        LOGGER.info("[WARNING] Professor already added to this subject.");
    }

    /**
     * Removes a class and its students, rooms, professors from the STs
     *
     * @param aclass being removed from the university.
     */
    public void removeClass(Class aclass) {
        // Get all the data from the class
        ArrayList<Student> students = aclass.getStudents();
        Room room = aclass.getSchedule().getRoom();
        Professor professor = aclass.getProfessor();
        Subject subject = aclass.getSubject();
        // Check if the class is in our STs and remove it
        ArrayList<Class> classes = this.profClass.get(professor);
        if (classes.contains(aclass)) {       // Removes all the data from the STs
            classes.remove(aclass);         // Remove class from profClass
            if (classes.isEmpty())
                profClass.delete(professor);      // if this professor has no more classes than he's removed
            ArrayList<Professor> professors = subjProf.get(subject);    // removing professor from the subject taught in this class
            professors.remove(professor);
            if (professors.isEmpty())
                this.subjProf.delete(subject); // if this subject has no more professor than it's removed
            ArrayList<Class> classes2 = this.courseClass.get(aclass.getCourse());   // removing class from the the course
            classes2.remove(aclass);
            if (classes2.isEmpty()) this.courseClass.delete(aclass.getCourse());
            ArrayList<Subject> subjects = this.roomSubject.get(room);   // removing subject from the rooms ST
            subjects.remove(subject);
            if (subjects.isEmpty()) this.roomSubject.delete(room);       // if this room has no more subjects, delete it
            for (Student student : students) {
                // removing class for students
                student.removeClass(aclass);
            }
            // removing class for professor
            professor.removeClass(aclass);
            return;
        }
        LOGGER.info("[WARNING] University.java - removeClass():");
        LOGGER.info("[WARNING] Class not found.");
    }

    /**
     * prints subjProf
     */
    public void printSubjProf() {
        for (Subject subject : subjProf.keys()) {
            LOGGER.info(subject.toString());
            for (Professor professor : subjProf.get(subject)) {
                LOGGER.info("\t");
                LOGGER.info(professor.toString());
            }
        }
    }

    /**
     * prints profClass
     */
    public void printProfClass() {
        for (Professor professor : profClass.keys()) {
            LOGGER.info(professor.toString());
            for (Class aclass : profClass.get(professor)) {
                LOGGER.info("\t");
                LOGGER.info(aclass.toString());
            }
        }
    }

    /**
     * prints courseClass
     */
    public void printCourseClass() {
        for (String course : courseClass.keys()) {
            System.out.println(course);
            for (Class aclass : courseClass.get(course)) {
                System.out.println("\t");
                System.out.println(aclass.toString());
            }
        }
    }

    public void printRoomSubject() {
        if (roomSubject.isEmpty()) System.out.println("roomSubject() empty");
        for (Room room : roomSubject.keys()) {
            System.out.println(room.toString());
            for (Subject subject : roomSubject.get(room)) {
                System.out.println("\t");
                System.out.println(subject.toString());
            }
        }
    }


}
