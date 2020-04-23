package edu.ufp.aed2.project;

import java.time.DayOfWeek;
import java.time.LocalTime;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {
        //testInstantTime();
        //testPerson();
        //testUniversity();
        testJSONDecode();
    }

    private static void testJSONDecode() {
        File file = new File("data/example.json");
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String str = new String(data, "UTF-8");

            JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();

            String title = jsonObject.getAsJsonObject("glossary").get("title").getAsString();
            System.out.println(title);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static void testInstantTime(){
        System.out.println("[TEST] InstantTime.java");
        System.out.println("==================================================");
        InstantTime instantTime1 = new InstantTime(DayOfWeek.MONDAY,LocalTime.now());
        InstantTime instantTime2 = new InstantTime(DayOfWeek.TUESDAY,LocalTime.now());
        System.out.println("[DATA] Same hours , different days.");
        System.out.println("[CASE] instantTime2 > instantTime1");
        System.out.println("[ASSERT] 1");
        System.out.println(instantTime2.compareTo(instantTime1));
        System.out.println("[CASE] instantTime1 < instantTime2");
        System.out.println("[ASSERT] -1");
        System.out.println(instantTime1.compareTo(instantTime2));
        instantTime1 =  new InstantTime(DayOfWeek.MONDAY,LocalTime.of(15,0));
        instantTime2 =  new InstantTime(DayOfWeek.MONDAY,LocalTime.of(10,0));
        System.out.println("[DATA] Different hours , same day.");
        System.out.println("[CASE] instantTime2 < instantTime1");
        System.out.println("[ASSERT] -1");
        System.out.println(instantTime2.compareTo(instantTime1));
        System.out.println("[CASE] instantTime1 > instantTime2");
        System.out.println("[ASSERT] 1");
        System.out.println(instantTime1.compareTo(instantTime2));
    }

    private static void testPerson(){
        System.out.println("[TEST] Person.java");
        System.out.println("==================================================");
        Student student = new Student("37045","David Capela");
        Student student2 = new Student("37145","Joao Silva");
        Room r1 = new Room(101,"Sede",30,1,30);
        Subject s1 = new Subject("Base dados",6,"PL");
        Schedule schedule = new Schedule(
                new InstantTime(DayOfWeek.MONDAY,LocalTime.of(15,0)),
                new InstantTime(DayOfWeek.MONDAY,LocalTime.of(17,0)),
                r1);
        University university= new University("UFP");
        Class class1 = new Class("Eng Inf","PL","BGK",schedule,university,s1,null);
        try{
            class1.addStudent(student);
            class1.addStudent(student2);
        } catch (Exception e){
            System.out.println("ex");
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
        Student student1 = new Student("22222","Rogerio");
        student1.removeClass(class1);
    }

    private static void testUniversity(){
        University university = new University("UFP");
        Student student = new Student("37045","David Capela");
        Student student2 = new Student("36111","Jose Carlos");
        Professor professor = new Professor("jsobral","Joao Sobral","inf");
        Subject subject = new Subject("Base Dados",6,"BD");
        Schedule schedule = new Schedule(
                new InstantTime(DayOfWeek.MONDAY,LocalTime.of(15,0)),
                new InstantTime(DayOfWeek.MONDAY,LocalTime.of(17,0)),
                new Room(101,"Sede",30,1,15)
                );
        Class class1 = new Class("inf","PL","diurno",schedule,university,subject,professor);
        try{
            class1.addStudent(student);
            class1.addStudent(student2);
        } catch (PersonNotFoundException e){
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