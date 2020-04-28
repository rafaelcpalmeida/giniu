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

/**
 * Singleton for a File Manager Class
 * All the operation for the file related stuff are done here
 */
public class FileManager {
    private static FileManager obj;

    private final File file;

    private FileManager() {
        this.file = new File("data/bd.json");
    }

    public static FileManager getInstance() {
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
    public SeparateChainingHashST<Class,ArrayList<Student>> getClasses() throws IOException {
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
            Schedule schedule =  getScheduleFromJson(uniClass.getAsJsonObject("schedule")); //schedule
            Subject subject = getSubjectFromJson(uniClass.getAsJsonObject("subject"));  //subject
            Professor professor = getProfessorFromJson(uniClass.getAsJsonObject("professor"));  //professor
            ArrayList<Student> students = getStudentsFromJson(uniClass.getAsJsonArray("students"));  //students
            Class newClass = new Class(course,type,initials,schedule,university,subject,professor); // creating class
            classes.put(newClass,students);
        }
        return classes;
    }

    /**
     * @param professorJsonObject Professor Json Object
     * @return Professor from json
     */
    private Professor getProfessorFromJson(JsonObject professorJsonObject) {
        String profId = professorJsonObject.get("id").getAsString();
        String profName = professorJsonObject.get("name").getAsString();
        String profCourse = professorJsonObject.get("course").getAsString();
        return new Professor(profId,profName,profCourse);
    }

    /**
     * @param subjectJsonObject Subject Json Object
     * @return Subject from json
     */
    private Subject getSubjectFromJson(JsonObject subjectJsonObject) {
        String subjectName = subjectJsonObject.get("name").getAsString();
        int ects = subjectJsonObject.get("ects").getAsInt();
        String sigle = subjectJsonObject.get("sigle").getAsString();
        return new Subject(subjectName,ects,sigle);
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
    private Schedule getScheduleFromJson(JsonObject schedule) {
        // getting the start InstantTime
        InstantTime start = getInstantTimeFromJson(schedule.getAsJsonObject("start"));
        // getting the end InstantTime
        InstantTime end = getInstantTimeFromJson(schedule.getAsJsonObject("end"));
        // getting the room
        Room room = getRoomFromJson(schedule.getAsJsonObject("room"));
        return new Schedule(start,end,room);
    }

    /**
     * @param jsonObject JsonObject of InstantTime
     * @return InstantTime
     */
    private InstantTime getInstantTimeFromJson(JsonObject jsonObject) {
        String dayOfWeekString = jsonObject.get("dayOfWeek").getAsString();
        DayOfWeek startDayOfWeek = DayOfWeek.valueOf(dayOfWeekString);
        String time = jsonObject.get("time").getAsString();
        // parse ex. 15h30 to ex. LocalTime(15,30)
        LocalTime localTime = LocalTime.of(Integer.parseInt(time.substring(0,2)),Integer.parseInt(time.substring(3,5)));
        return new InstantTime(startDayOfWeek,localTime);
    }

    /**
     * @param room JsonObject of Room
     * @return Room
     */
    private Room getRoomFromJson(JsonObject room) {
        String building = room.get("building").getAsString();
        int number = room.get("number").getAsInt();
        int maxSize = room.get("maxSize").getAsInt();
        int floor = room.get("floor").getAsInt();
        int plugNumber = room.get("plugNumber").getAsInt();
        return new Room(number,building,maxSize,floor,plugNumber);
    }
}
