package edu.ufp.aed2.project;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Singleton for a File Manager Class
 * All the operation for the file related stuff are done here
 */
public class FileManager {
    private static FileManager obj;

    private final File file;

    private FileManager(){
        this.file = new File("data/bd.json");
    }

    public static FileManager getInstance(){
        if (obj==null) obj = new FileManager();
        return obj;
    }

    /**
     * Read line by line the file and returns all of it.
     * @return All lines from the file.
     * @throws IOException reading the file.
     */
    private String readAll() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        return content.toString();
    }


    /**
     * Returns all the classes of a university from the bd.json and
     * the corresponding ArrayList of Students
     * @return ArrayList of Class
     * @throws IOException reading the file
     */
    public SeparateChainingHashST<Class,ArrayList<Student>> getClasses() throws IOException{
        SeparateChainingHashST<Class,ArrayList<Student>> classes = new SeparateChainingHashST<>();
        JsonObject jsonObject = new JsonParser().parse(this.readAll()).getAsJsonObject();
        JsonArray jsonArray = new JsonParser().parse(jsonObject.get("classes").toString()).getAsJsonArray();    // Gets the "classes" array
        for (JsonElement element : jsonArray) {
            // Iterating every class
            JsonObject uniClass = element.getAsJsonObject();    // class
            String universityName = uniClass.get("university").getAsString();
            University university = Manager.getInstance().getUniversity(universityName); //university
            String course = uniClass.get("course").getAsString();   // course
            String type = uniClass.get("type").getAsString();   // type
            String initials = uniClass.get("initials").getAsString();   // initials
            JsonObject scheduleJsonObject = uniClass.getAsJsonObject("schedule");   // schedule
            Schedule schedule =  getScheduleFromJson(scheduleJsonObject);
            JsonObject subjectJsonObject = uniClass.getAsJsonObject("subject");
            String subjectName = subjectJsonObject.get("name").getAsString();
            int ects = subjectJsonObject.get("ects").getAsInt();
            String sigle = subjectJsonObject.get("sigle").getAsString();
            Subject subject = new Subject(subjectName,ects,sigle);
            JsonObject professorJsonObject = uniClass.getAsJsonObject("professor"); // professor
            String profId = professorJsonObject.get("id").getAsString();
            String profName = professorJsonObject.get("name").getAsString();
            String profCourse = professorJsonObject.get("course").getAsString();
            Professor professor = new Professor(profId,profName,profCourse);
            JsonArray studentsJsonObject = uniClass.getAsJsonArray("students");
            ArrayList<Student> students = getStudentsFromJson(studentsJsonObject);  //students
            Class newClass = new Class(course,type,initials,schedule,university,subject,professor);
            classes.put(newClass,students);
        }
        return classes;
    }

    /**
     * @param studentsJsonObject JsonObject from the json file
     * @return ArrayList<Student> from the json
     */
    private ArrayList<Student> getStudentsFromJson(JsonArray studentsJsonObject) {
        ArrayList<Student> students = new ArrayList<>();
        for(JsonElement student : studentsJsonObject){
            // iterate each student
            JsonObject studentObject = student.getAsJsonObject();
            String id = studentObject.get("id").toString();
            String name = studentObject.get("name").toString();
            students.add(new Student(id,name));
        }
        return students;
    }

    /**
     * @param schedule JsonObject from the json file
     * @return Schedule
     */
    private Schedule getScheduleFromJson(JsonObject schedule){
        // getting the start InstantTime
        JsonObject startInstanttime = schedule.getAsJsonObject("start");
        String startDayOfWeekString = startInstanttime.get("dayOfWeek").getAsString();
        DayOfWeek startDayOfWeek = DayOfWeek.valueOf(startDayOfWeekString);
        String time = startInstanttime.get("time").getAsString();
        // parse 15h30 to LocalTime(15,30)
        LocalTime localTime = LocalTime.of(Integer.parseInt(time.substring(0,2)),Integer.parseInt(time.substring(3,5)));
        InstantTime start = new InstantTime(startDayOfWeek,localTime);
        // getting the end InstantTime
        JsonObject endInstanttime = schedule.getAsJsonObject("end");
        String endDayOfWeekString = endInstanttime.get("dayOfWeek").getAsString();
        DayOfWeek endDayOfWeek = DayOfWeek.valueOf(endDayOfWeekString);
        String endTime = startInstanttime.get("time").getAsString();
        // parse 15h30 to LocalTime(15,30)
        LocalTime endLocalTime = LocalTime.of(Integer.parseInt(time.substring(0,2)),Integer.parseInt(time.substring(3,5)));
        InstantTime end = new InstantTime(endDayOfWeek,endLocalTime);
        // getting the room
        JsonObject room = schedule.getAsJsonObject("room");
        String building = room.get("building").getAsString();
        int number = room.get("number").getAsInt();
        int maxSize = room.get("maxSize").getAsInt();
        int floor = room.get("floor").getAsInt();
        int plugNumber = room.get("plugNumber").getAsInt();
        Room newRoom = new Room(number,building,maxSize,floor,plugNumber);
        return new Schedule(start,end,newRoom);
    }




}
