package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;


import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/**
 * Main class.
 * Has all information about the university.
 * All the information is held here.
 */

public class University implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(University.class.getName());

    private final String name;
    private final HashMap<String, Professor> professors;
    private final HashMap<String, Subject> subjects;
    private final HashMap<String, Room> rooms;
    private final HashMap<String, Student> students;
    private final SeparateChainingHashST<Subject, ArrayList<Professor>> subjProf;
    private final SeparateChainingHashST<Professor, ArrayList<Class>> profClass;
    private final SeparateChainingHashST<String, ArrayList<Class>> courseClass;
    private final RedBlackBST<Room, ArrayList<Class>> roomClass;

    public University(String name) {
        this.name = name;
        professors = new HashMap<>();
        subjects = new HashMap<>();
        rooms = new HashMap<>();
        students = new HashMap<>();
        subjProf = new SeparateChainingHashST<>();
        profClass = new SeparateChainingHashST<>();
        courseClass = new SeparateChainingHashST<>();
        roomClass = new RedBlackBST<>();
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
    public ArrayList<Room> getRoomsUsedNow() {
        ArrayList<Room> roomsBeeingUsed = new ArrayList<>();
        this.rooms.forEach(((key, room) -> {
            if(room.isAvailable(this.getInstantTimeNow())){
                roomsBeeingUsed.add(room);
            }
        }));
        return roomsBeeingUsed;
    }

    private InstantTime getInstantTimeNow(){
        LocalTime localTime = LocalTime.now();              // not summer time
        DayOfWeek dayOfWeek = this.getTodayDate().getDayOfWeek();
        return new InstantTime(dayOfWeek,localTime);
    }

    private LocalDate getTodayDate(){
        return  LocalDate.now();
    }

    /**
     * @return subjects taught now.
     */
    private ArrayList<Subject> getSubjectsTaughtNow() {
        ArrayList<Subject> subjectsTaughtNow = new ArrayList<>();
        for(String course : this.courseClass.keys()){
            ArrayList<Class> courseClasses = this.courseClass.get(course);
            courseClasses.forEach(class1 ->{
                if(class1.isHavingClassesIn(this.getInstantTimeNow())) subjectsTaughtNow.add(class1.getSubject());
            });
        }
        return subjectsTaughtNow;
    }

    /**
     * @return available profs now.
     */
    private ArrayList<Professor> getAvailableProfsNow() {
        ArrayList<Professor> professors = new ArrayList<>();
        for(Professor professor : this.profClass.keys()){
            LOGGER.info(professor.getName());
            ArrayList<Class> profClasses = this.profClass.get(professor);
            profClasses.forEach(class2 -> {
                if(!class2.isHavingClassesIn(this.getInstantTimeNow())) professors.add(professor);
            });
        }
        return professors;
    }

    /**
     * @return busy profs now.
     */
    private ArrayList<Professor> getBusyProfsNow() {
        ArrayList<Professor> professors = new ArrayList<>();
        for(Professor professor : this.profClass.keys()){
            ArrayList<Class> profClasses = this.profClass.get(professor);
            profClasses.forEach(class2 -> {
                if(class2.isHavingClassesIn(this.getInstantTimeNow())) professors.add(professor);
            });
        }
        return professors;
    }


    /**
     * Prints the current:
     * - Rooms being used now.
     * - Subjects being taught now.
     * - Available profs now.
     * - Busy profs now.
     */
    public void now() {
        LOGGER.info("Printing current university status ...");
        LOGGER.info("printing rooms being used now...");
        for(Room room :this.getRoomsUsedNow()) {
            System.out.println(room);
        }

        LOGGER.info("printing subjects being taught now...");
        for(Subject subject1 :this.getSubjectsTaughtNow()) {
            System.out.println(subject1);
        }

        LOGGER.info("printing professor available now...");
        for(Professor professor1 : this.getAvailableProfsNow()){
            System.out.println(professor1);
        }

        LOGGER.info("printing professor busy now...");
        for(Professor professor1 : this.getBusyProfsNow()){
            System.out.println(professor1);
        }
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
        addClassToRoom(pclass.getSchedule().getRoom(), pclass);
        professors.putIfAbsent(professor.getId(), professor);
        subjects.putIfAbsent(subject.getName(), subject);
        rooms.putIfAbsent(String.valueOf(pclass.getSchedule().getRoom().getNumber()), pclass.getSchedule().getRoom());
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
     * Adds a classAux to a room.
     *
     * @param room     key.
     * @param classAux value.
     */
    private void addClassToRoom(Room room, Class classAux) {
        // Adding classAux to Room
        if (!this.roomClass.contains(room)) {
            // Room not registered yet, need to create an ArrayList of classes!
            ArrayList<Class> classes = new ArrayList<>();
            classes.add(classAux);
            this.roomClass.put(room, classes);
            return;
        }
        // Already contains the room, just add the classAux to ArrayList
        ArrayList<Class> classes = this.roomClass.get(room);
        if (!classes.contains(classAux)) {
            classes.add(classAux);
            return;
        }
        LOGGER.warning("University.java - addClassToRoom():");
        LOGGER.warning("Class already added to this room.");

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
            ArrayList<Class> classesAux = this.roomClass.get(room);   // removing subject from the rooms ST
            classesAux.remove(aclass);
            if (classesAux.isEmpty()) this.roomClass.delete(room);       // if this room has no more classes, delete it
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
            LOGGER.info(course);
            for (Class aclass : courseClass.get(course)) {
                LOGGER.info("\t");
                LOGGER.info(aclass.toString());
            }
        }
    }

    public void printRoomClass() {
        if (roomClass.isEmpty()) LOGGER.info("roomClass() empty");
        for (Room room : roomClass.keys()) {
            LOGGER.info(room.toString());
            for (Class classAux : roomClass.get(room)) {
                LOGGER.info("\t");
                LOGGER.info(classAux.toString());
            }
        }
    }

    public Professor getProfessor(String username) {
        return professors.get(username);
    }

    public HashMap<String, Student> getStudents() {
        return students;
    }

    public Student getStudent(String username) {
        return students.get(username);
    }

    public Subject getSubject(String name) {
        return subjects.get(name);
    }

    public Room getRoom(String id) {
        return rooms.get(id);
    }

    public void addStudent(Student student) {
        students.putIfAbsent(student.getId(), student);
    }

    public void getProfessorClasses(Professor professor) {
        if (professor == null) {
            LOGGER.warning("invalid professor");
            return;
        }
        ArrayList<Class> profClasses = profClass.get(professor);

        if (profClasses != null) {
            for (Class aclass : profClasses) {
                LOGGER.info(aclass.getInitials());
            }

            return;
        }

        LOGGER.info("No classes found for given professor");
    }

    public void getProfessorSubjects(Subject subject) {
        if (subject == null) {
            LOGGER.warning("invalid subject");
            return;
        }

        ArrayList<Professor> subjectProfs = subjProf.get(subject);

        if (subjectProfs != null) {
            for (Professor professor : subjectProfs) {
                LOGGER.info(professor.getId() + " - " + professor.getName());
            }

            return;
        }

        LOGGER.info("No classes found for given professor");
    }

    public void getUnusedRoomsBetweenTimes(InstantTime begin, InstantTime end) {
        for (Room room : roomClass.keys()) {
            roomClass.get(room).forEach(classAux -> {
                if (classAux.getSchedule().getStart().getDayOfWeek().compareTo(classAux.getSchedule().getEnd().getDayOfWeek()) == 0) {
                    if ((classAux.getSchedule().getStart().getTime().isAfter(begin.getTime()) ||
                            classAux.getSchedule().getStart().getTime().compareTo(begin.getTime()) == 0) &&
                            classAux.getSchedule().getStart().getTime().isBefore(end.getTime())) {
                        return;
                    }
                    LOGGER.info(String.valueOf(room.getNumber()));
                }
            });
        }
    }

    public void getRoomsOccupancyBetweenTimes(Room room, InstantTime begin, InstantTime end) {
        if (room == null) {
            LOGGER.warning("invalid room");
            return;
        }

        roomClass.get(room).forEach(classAux -> {
            if (classAux.getSchedule().getStart().getDayOfWeek().compareTo(classAux.getSchedule().getEnd().getDayOfWeek()) == 0) {
                if ((classAux.getSchedule().getStart().getTime().isAfter(begin.getTime()) ||
                        classAux.getSchedule().getStart().getTime().compareTo(begin.getTime()) == 0) &&
                        classAux.getSchedule().getStart().getTime().isBefore(end.getTime())) {
                    LOGGER.info(String.valueOf(classAux.getStudents().size()));
                    return;
                }
                LOGGER.info("0");
            }
        });
    }

    public void getAttendanceTimeAvailability(Professor professor, Student student) {
        // TODO check student free schedule and check if it matches with professor free schedule
        RedBlackBST<InstantTime, Class> studentClasses = student.getInstantTimeClass();
        professor.getAttendanceSchedule().forEach(schedule -> {
            AtomicBoolean available = new AtomicBoolean(true);
            studentClasses.keys().forEach(instantTime -> {
                if (studentClasses.get(instantTime).getSchedule().compareTo(schedule) == 0) {
                    available.set(false);
                }
            });

            if (available.get()) {
                LOGGER.info(schedule.getStart().getDayOfWeek() + ": " + schedule.getStart().getTime() + " - " + schedule.getEnd().getTime());
            }
        });
    }
}
