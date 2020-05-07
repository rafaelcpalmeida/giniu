package edu.ufp.aed2.project;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Singleton for a File Manager Class
 * All the operation for the file related stuff are done here
 */
public class FileManager {
    private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());

    private static FileManager obj;

    private final File file;

    private FileManager() {
        this.file = new File("/app/data/bd.json");
    }

    public static FileManager getInstance() {
        if (obj == null) obj = new FileManager();
        return obj;
    }

    /**
     * Read line by line the file and returns all of it.
     *
     * @return All lines from the file.
     * @throws IOException reading the file.
     */
    private String readAllFromFile(File file) throws IOException {
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
     *
     * @return ArrayList of Class
     * @throws IOException reading the file
     */
    public SeparateChainingHashST<Class, ArrayList<Student>> getClasses() throws IOException {
        SeparateChainingHashST<Class, ArrayList<Student>> classes = new SeparateChainingHashST<>();
        JsonObject jsonObject = JsonParser.parseString(this.readAllFromFile(this.file)).getAsJsonObject();
        JsonArray jsonArray = JsonParser.parseString(jsonObject.get("classes").toString()).getAsJsonArray();    // Gets the "classes" array
        for (JsonElement element : jsonArray) {
            // Iterating every class
            JsonObject uniClass = element.getAsJsonObject();    // class
            String universityName = uniClass.get("university").getAsString();
            University university = Manager.getInstance().getUniversity(universityName); //university
            String course = uniClass.get("course").getAsString();   // course
            String type = uniClass.get("type").getAsString();   // type
            String initials = uniClass.get("initials").getAsString();   // initials
            Schedule schedule = getScheduleFromJson(uniClass.getAsJsonObject("schedule")); //schedule
            Subject subject = getSubjectFromJson(uniClass.getAsJsonObject("subject"));  //subject
            Professor professor = getProfessorFromJson(university, uniClass.getAsJsonObject("professor"));  //professor
            ArrayList<Student> students = getStudentsFromJson(uniClass.getAsJsonArray("students"));  //students
            Class newClass = new Class(course, type, initials, schedule, university, subject, professor, students); // creating class
            classes.put(newClass, students);
        }
        return classes;
    }

    public void saveSTsToFile(SeparateChainingHashST<Class, ArrayList<Student>> classes) {
        try {
            JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter(this.file, false)));
            writer.setIndent("  ");
            writer.beginObject();
            writer.name("classes");
            writer.beginArray();
            writeClassesToJSON(writer, classes);
            writer.endArray();
            writer.endObject();
            writer.close();
            LOGGER.info("Data write to a file successfully");
        } catch (IOException e) {
            LOGGER.info("[WARNING] Exception made in saveSTsToFile(): " + e.getMessage());
        }
    }

    private void writeClassesToJSON(JsonWriter writer, SeparateChainingHashST<Class, ArrayList<Student>> classes) throws IOException {
        for (Class classAux : classes.keys()) {
            writer.beginObject();
            writer.name("course").value(classAux.getCourse());
            writer.name("type").value(classAux.getType());
            writer.name("initials").value(classAux.getInitials());
            writer.name("university").value(classAux.getUniversity().getName());
            writer.name("subject");
            writeSubjectToJSON(writer, classAux.getSubject());
            writer.name("schedule");
            writeScheduleToJSON(writer, classAux.getSchedule());
            writer.name("professor");
            writeProfessorToJSON(writer, classAux.getProfessor());
            writer.name("students");
            writeStudentsToJSON(writer, classAux.getStudents());
            writer.endObject();
        }
    }

    private void writeSubjectToJSON(JsonWriter writer, Subject subject) throws IOException {
        writer.beginObject();
        writer.name("name").value(subject.getName());
        writer.name("ects").value(subject.getEcts());
        writer.name("initials").value(subject.getInitials());
        writer.endObject();
    }

    private void writeScheduleToJSON(JsonWriter writer, Schedule schedule) throws IOException {
        // start schedule object
        writer.beginObject();
        writer.name("start");
        // start start object
        writer.beginObject();
        writer.name("dayOfWeek").value(schedule.getStart().getDayOfWeek().toString());
        writer.name("time").value(schedule.getStart().getTime().toString());
        // end start object
        writer.endObject();
        writer.name("end");
        // start end object
        writer.beginObject();
        writer.name("dayOfWeek").value(schedule.getEnd().getDayOfWeek().toString());
        writer.name("time").value(schedule.getEnd().getTime().toString());
        // end end object
        writer.endObject();
        writer.name("room");
        // start room object
        writer.beginObject();
        writer.name("building").value(schedule.getRoom().getBuilding());
        writer.name("number").value(schedule.getRoom().getNumber());
        writer.name("maxSize").value(schedule.getRoom().getMaxSize());
        writer.name("floor").value(schedule.getRoom().getFloor());
        writer.name("plugNumber").value(schedule.getRoom().getPlugNumber());
        // end room object
        writer.endObject();
        // end schedule object
        writer.endObject();
    }

    private void writeProfessorToJSON(JsonWriter writer, Professor professor) throws IOException {
        writer.beginObject();
        writer.name("id").value(professor.getId());
        writer.name("name").value(professor.getName());
        writer.name("course").value(professor.getCourse());
        writer.endObject();
    }

    private void writeStudentsToJSON(JsonWriter writer, ArrayList<Student> students) throws IOException {
        writer.beginArray();
        for (Student student : students) {
            writer.beginObject();
            writer.name("id").value(student.getId());
            writer.name("name").value(student.getName());
            writer.endObject();
        }
        writer.endArray();
    }

    /**
     * @param professorJsonObject Professor Json Object
     * @return Professor from json
     */
    private Professor getProfessorFromJson(University university, JsonObject professorJsonObject) {
        String profId = professorJsonObject.get("id").getAsString();
        String profName = professorJsonObject.get("name").getAsString();
        String profCourse = professorJsonObject.get("course").getAsString();

        Professor professor = university.getProfessor(profId);

        if (professor != null) {
            return professor;
        }

        return new Professor(profId, profName, profCourse);
    }

    /**
     * @param subjectJsonObject Subject Json Object
     * @return Subject from json
     */
    private Subject getSubjectFromJson(JsonObject subjectJsonObject) {
        String subjectName = subjectJsonObject.get("name").getAsString();
        int ects = subjectJsonObject.get("ects").getAsInt();
        String initials = subjectJsonObject.get("initials").getAsString();
        return new Subject(subjectName, ects, initials);
    }

    /**
     * @param studentsJsonObject JsonObject from the json file
     * @return ArrayList<Student> from the json
     */
    private ArrayList<Student> getStudentsFromJson(JsonArray studentsJsonObject) {
        ArrayList<Student> students = new ArrayList<>();
        for (JsonElement student : studentsJsonObject) {
            // iterate each student
            JsonObject studentObject = student.getAsJsonObject();
            String id = studentObject.get("id").getAsString();
            String name = studentObject.get("name").getAsString();
            students.add(new Student(id, name));
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
        return new Schedule(start, end, room);
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
        LocalTime localTime = LocalTime.of(Integer.parseInt(time.substring(0, 2)), Integer.parseInt(time.substring(3, 5)));
        return new InstantTime(startDayOfWeek, localTime);
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
        return new Room(number, building, maxSize, floor, plugNumber);
    }
}
