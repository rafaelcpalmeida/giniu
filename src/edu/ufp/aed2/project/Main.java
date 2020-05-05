package edu.ufp.aed2.project;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //testInstantTime();
        //testPerson();
        //testUniversity();
        //testFileManager();
        testManager();
    }

    private static void testManager() {
        Manager manager = Manager.getInstance();
        FileManager fileManager = FileManager.getInstance();
        System.out.println("Testing manager");
        System.out.println("populate sts from file");
        manager.populateSTsFromFile();
        //University university = manager.getUniversity("UFP");
        //university.printProfClass();
        //university.printRoomSubject();
        //university.printCourseClass();
        //university.printSubjProf();
        //testPerson();
        Room r1 = new Room(301, "Sede", 10, 3, 2);
        University university = manager.getUniversity("UFP");
        Subject s1 = new Subject("Hacking SI", 6, "PL");
        Schedule schedule = new Schedule(
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(15, 0)),
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(17, 0)),
                r1);
        Professor professor = new Professor("sal", "Tio Sal", "InfoSec");
        Class class1 = new Class("Eng Inf", "PL", "BGK", schedule, university, s1, professor, new ArrayList<>());
        Student student = new Student("21561", "Tio Sal");
        try {
            class1.addStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }

        manager.getClasses().put(class1, class1.getStudents());

        fileManager.saveSTsToFile(manager.getClasses());
    }

    private static void testFileManager() {
        FileManager fileManager = FileManager.getInstance();
    }


    private static void testInstantTime() {
        System.out.println("[TEST] InstantTime.java");
        System.out.println("==================================================");
        InstantTime instantTime1 = new InstantTime(DayOfWeek.MONDAY, LocalTime.now());
        InstantTime instantTime2 = new InstantTime(DayOfWeek.TUESDAY, LocalTime.now());
        System.out.println("[DATA] Same hours , different days.");
        System.out.println("[CASE] instantTime2 > instantTime1");
        System.out.println("[ASSERT] 1");
        System.out.println(instantTime2.compareTo(instantTime1));
        System.out.println("[CASE] instantTime1 < instantTime2");
        System.out.println("[ASSERT] -1");
        System.out.println(instantTime1.compareTo(instantTime2));
        instantTime1 = new InstantTime(DayOfWeek.MONDAY, LocalTime.of(15, 0));
        instantTime2 = new InstantTime(DayOfWeek.MONDAY, LocalTime.of(10, 0));
        System.out.println("[DATA] Different hours , same day.");
        System.out.println("[CASE] instantTime2 < instantTime1");
        System.out.println("[ASSERT] -1");
        System.out.println(instantTime2.compareTo(instantTime1));
        System.out.println("[CASE] instantTime1 > instantTime2");
        System.out.println("[ASSERT] 1");
        System.out.println(instantTime1.compareTo(instantTime2));
    }

    private static void testPerson() {
        System.out.println("[TEST] Person.java");
        System.out.println("==================================================");
        Student student = new Student("37045", "David Capela");
        Student student2 = new Student("37145", "Joao Silva");
        Student student3 = new Student("21561", "Tio Sal");
        Room r1 = new Room(101, "Sede", 30, 1, 30);
        Subject s1 = new Subject("Base dados", 6, "PL");
        Schedule schedule = new Schedule(
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(15, 0)),
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(17, 0)),
                r1);
        University university = new University("UFP");
        Professor professor = new Professor("a", "joao", "eng ing");
        Class class1 = new Class("Eng Inf", "PL", "BGK", schedule, university, s1, professor, new ArrayList<>());
        try {
            class1.addStudent(student);
            class1.addStudent(student2);
            class1.addStudent(student3);
        } catch (Exception e) {
            System.out.println("ex");
            System.out.println(e.getMessage());
        }
        System.out.println("[DATA] Different students , one class.");
        System.out.println("[CASE] Print student's schedule.");
        student.printClassSchedule();
        student2.printClassSchedule();
        System.out.println("[CASE] Print student's schedule when removed class.");
        try {
            class1.removeStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
        student.printClassSchedule();
        class1.printStudents();
        System.out.println("[CASE] Remove a class when a person doesn't have any.");
        Student student1 = new Student("22222", "Rogerio");
        student1.removeClass(class1);
    }

    private static void testUniversity() {
        University university = new University("UFP");
        Student student = new Student("37045", "David Capela");
        Student student2 = new Student("36111", "Jose Carlos");
        Professor professor = new Professor("jsobral", "Joao Sobral", "inf");
        Subject subject = new Subject("Base Dados", 6, "BD");
        Schedule schedule = new Schedule(
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(15, 0)),
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(17, 0)),
                new Room(101, "Sede", 30, 1, 15)
        );
        Class class1 = new Class("inf", "PL", "diurno", schedule, university, subject, professor, new ArrayList<>());
        try {
            class1.addStudent(student);
            class1.addStudent(student2);
        } catch (PersonNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //university.printSubjProf();
        //university.printProfClass();
        //university.printCourseClass();
        //university.printRoomSubject();
        student.printClassSchedule();
        System.out.println("removing class");
        //System.out.println(class1.getStudents().size());
        university.removeClass(class1);
        student.printClassSchedule();
    }
}