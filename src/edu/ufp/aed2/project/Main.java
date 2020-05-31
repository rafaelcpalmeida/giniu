package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.ufp.aed2.project.exceptions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "\033[32m%1$tF %1$tT\033[39m \u001b[33m[%4$-7s]\u001b[0m %5$s %n");
        LOGGER = Logger.getLogger(Main.class.getName());
    }

    public static void main(String[] args) {
        //testInstantTime();
        //testPerson();
        //testUniversity();
        //testFileManager();
        //testManager();
        //testLocation();
        testShortestPath();
    }

    private static void testShortestPath() {
        Room room0 = new Room(101,"sede",12,1,123,"UFP",10,10);
        Room room1 = new Room(102,"sede",12,1,123,"UFP",20,20);
        Room room2 = new Room(103,"sede",12,1,123,"UFP",30,30);
        Room room3 = new Room(104,"sede",12,1,123,"UFP",25,25);
        Room room4 = new Room(105,"sede",12,1,123,"UFP",20,15);
        //Room roomExcluded = new Room(106,"sede",12,1,123,"UFP",23,15);
        //Room roomExcluded2 = new Room(107,"sede",12,1,123,"UFP",28,15);
        //Room room3 = new Room(201,"sede",12,2,123,"UFP",10,10);
        //Room room4 = new Room(202,"sede",12,2,123,"UFP",5,20);
        //Location location = new Location(1,35,5,"UFP",TypeOfSpace.pp);
        Location exitNear = new Location(1,40,40,"UFP",TypeOfSpace.exit);
        Location exitFar = new Location(1,100,100,"UFP",TypeOfSpace.exit);
        Student student = new Student("37045","David Capela");
        student.setFloor(1);
        student.setX(29);
        student.setY(29);

        LocationManager locationManager = Manager.getInstance().getLocationManager("UFP");

        try {
            locationManager.createGlobalGraph();
            // floor 1
            locationManager.createEdge(room0.getVertexId(),room1.getVertexId(),locationManager.calculateWeight(room0,room1),10);
            locationManager.createEdge(room1.getVertexId(),room0.getVertexId(),locationManager.calculateWeight(room1,room0),10);
            locationManager.createEdge(room1.getVertexId(),room2.getVertexId(),locationManager.calculateWeight(room1,room2),20);
            locationManager.createEdge(room2.getVertexId(),room1.getVertexId(),locationManager.calculateWeight(room2,room1),20);
            locationManager.createEdge(room2.getVertexId(),room3.getVertexId(),locationManager.calculateWeight(room2,room3),10);
            locationManager.createEdge(room2.getVertexId(),room4.getVertexId(),500,5);
            locationManager.createEdge(room3.getVertexId(),room2.getVertexId(),locationManager.calculateWeight(room3,room2),20);
            locationManager.createEdge(room3.getVertexId(),room4.getVertexId(),locationManager.calculateWeight(room3,room4),20);
            locationManager.createEdge(room4.getVertexId(),room3.getVertexId(),locationManager.calculateWeight(room4,room3),20);
            locationManager.createEdge(room4.getVertexId(),exitNear.getVertexId(),locationManager.calculateWeight(room4,exitNear),10);
            locationManager.createEdge(room3.getVertexId(),exitFar.getVertexId(),locationManager.calculateWeight(room3,exitFar),5);

            LOGGER.info("Printing global graph ...");
            LOGGER.info(locationManager.getGlobalGraph().toString());
            locationManager.shortestPathToExit(student,CostEnum.DISTANCE);
            locationManager.shortestPathToExit(student,CostEnum.TIME);
            locationManager.isConnected(locationManager.getGlobalGraph());
            /*
            LOGGER.info("Creating the sub-graph locations ...");
            ArrayList<Location> subGraphLocations = new ArrayList<>();
            subGraphLocations.add(room2);
            subGraphLocations.add(room3);
            subGraphLocations.add(room4);
            subGraphLocations.add(roomExcluded);
            subGraphLocations.add(roomExcluded);
            ArrayList<Location> roomsExcluded = new ArrayList<>();
            roomsExcluded.add(roomExcluded);
            roomsExcluded.add(roomExcluded2);

            LOGGER.info("Subtracting the unwanted locations");
            locationManager.subtractLocations(subGraphLocations,roomsExcluded);

            LOGGER.info("Creating sub graph ...");
            Subgraph subgraph1 = locationManager.getSubGraphFromVertices(subGraphLocations);

            LOGGER.info("printing shortest path now ...");
            locationManager.shortestPathBetween(room2.getVertexId(),room4.getVertexId(),subgraph1.getGraph(),subgraph1.getOffset(),CostEnum.DISTANCE);

            LOGGER.info("printing shortest time path now ...");
            locationManager.shortestPathBetween(room2.getVertexId(),room4.getVertexId(),subgraph1.getGraph(),subgraph1.getOffset(),CostEnum.TIME);
        */
        } catch (LocationsNotInitException | VertexNotFoundException | GlobalGraphNotCreated | FloorNotFoundException e) {

            LOGGER.warning(e.getMessage());
        }
    }

    private static void testLocation() {
        Room room1 = new Room(101,"sede",12,1,123,"UFP",10,10);
        Room room2 = new Room(102,"sede",12,1,123,"UFP",20,20);
        Room room3 = new Room(201,"sede",12,2,123,"UFP",10,10);
        Room room4 = new Room(202,"sede",12,2,123,"UFP",5,20);
        Location location = new Location(2,35,5,"UFP",TypeOfSpace.pp);
        LocationManager locationManager = Manager.getInstance().getLocationManager("UFP");

        try {
            locationManager.createGlobalGraph();
            // floor 1
            locationManager.createEdge(room1.getVertexId(),room2.getVertexId(),locationManager.calculateWeight(room1,room2),30);
            locationManager.createEdge(room2.getVertexId(),room1.getVertexId(),locationManager.calculateWeight(room2,room1),20);
            // floor 2
            locationManager.createEdge(room3.getVertexId(),room4.getVertexId(),locationManager.calculateWeight(room3,room4),20);
            locationManager.createEdge(room4.getVertexId(),room3.getVertexId(),locationManager.calculateWeight(room4,room3),20);
            locationManager.createEdge(location.getVertexId(),room3.getVertexId(),locationManager.calculateWeight(location,room3),20);

            EdgeWeightedDigraph graph = locationManager.getGlobalGraph();
            LOGGER.info(graph.toString());
            LOGGER.info("Creating sub-graph from floor 1");
            LOGGER.info(locationManager.getSubGraphFromFloor(1).toString());
            LOGGER.info("Creating sub-graph from floor 2");
            LOGGER.info(locationManager.getSubGraphFromFloor(2).toString());
            LOGGER.info("Creating sub-graph from floor 3");
            LOGGER.info(locationManager.getSubGraphFromFloor(3).toString());
        } catch (LocationsNotInitException | VertexNotFoundException | GlobalGraphNotCreated | FloorNotFoundException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    private static void testManager() {
        Manager manager = Manager.getInstance();
        FileManager fileManager = FileManager.getInstance();
        LOGGER.info("Testing manager");
        LOGGER.info("Populate STs from file");
        manager.populateSTsFromFile();
        //University university = manager.getUniversity("UFP");
        //university.printProfClass();
        //university.printRoomClass();
        //university.printCourseClass();
        //university.printSubjProf();
        //testPerson();
        //Room r1 = new Room(301, "Sede", 10, 3, 2);
        LOGGER.info("Using UFP as university");
        University university = manager.getUniversity("UFP");
        LOGGER.info("Listing classes for sal");
        Professor professor = university.getProfessor("sal");
        university.getProfessorClasses(professor);
        LOGGER.info("");
        LOGGER.info("Listing classes for inexisting");
        professor = university.getProfessor("inexisting");
        university.getProfessorClasses(professor);
        LOGGER.info("");
        LOGGER.info("Listing classes for jsobral");
        professor = university.getProfessor("jsobral");
        university.getProfessorClasses(professor);
        LOGGER.info("");
        LOGGER.info("Listing professors for Hacking SI");
        Subject subject = university.getSubject("Hacking SI");
        university.getProfessorSubjects(subject);
        LOGGER.info("");
        LOGGER.info("Listing free rooms for Monday 10:00 until 12:00");
        university.getUnusedRoomsBetweenTimes( new InstantTime(DayOfWeek.MONDAY, LocalTime.of(10, 0)),
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(12, 0)));
        LOGGER.info("");
        LOGGER.info("Listing free rooms for Monday 13:00 until 14:00");
        university.getUnusedRoomsBetweenTimes( new InstantTime(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(14, 0)));
        LOGGER.info("");
        LOGGER.info("Listing room 303 occupancy for Monday 13:00 until 14:00");
        Room room = university.getRoom("303");
        university.getRoomsOccupancyBetweenTimes(room,  new InstantTime(DayOfWeek.MONDAY, LocalTime.of(13, 0)),
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(14, 0)));
        LOGGER.info("");
        LOGGER.info("Listing room 303 occupancy for Monday 13:00 until 14:00");
        university.getRoomsOccupancyBetweenTimes(room,  new InstantTime(DayOfWeek.TUESDAY, LocalTime.of(15, 0)),
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(17, 0)));
        LOGGER.info("");
        LOGGER.info("Showing attendance time availability for jsobral and capela");
        Student student = university.getStudent("37045");
        university.getAttendanceTimeAvailability(professor, student);
        LOGGER.info("");
        LOGGER.info("Showing attendance time availability for sal and capela");
        professor = university.getProfessor("sal");
        university.getAttendanceTimeAvailability(professor, student);
        LOGGER.info("");
        /*Subject s1 = new Subject("Hacking SI", 6, "PL");
        Schedule schedule = new Schedule(
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(15, 0)),
                new InstantTime(DayOfWeek.MONDAY, LocalTime.of(17, 0)),
                r1);
        Class class1 = new Class("Eng Inf", "PL", "BGK", schedule, university, s1, professor, new ArrayList<>());
        Student student = new Student("21561", "Leandro Pires");
        try {
            class1.addStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }

        manager.getClasses().put(class1, class1.getStudents());*/

        fileManager.saveSTsToFile(manager.getClasses());
    }

    private static void testFileManager() {
        FileManager fileManager = FileManager.getInstance();
    }

    private static void testInstantTime() {
        LOGGER.info("[TEST] InstantTime.java");
        LOGGER.info("==================================================");
        InstantTime instantTime1 = new InstantTime(DayOfWeek.MONDAY, LocalTime.now());
        InstantTime instantTime2 = new InstantTime(DayOfWeek.TUESDAY, LocalTime.now());
        LOGGER.info("[DATA] Same hours , different days.");
        LOGGER.info("[CASE] instantTime2 > instantTime1");
        LOGGER.info("[ASSERT] 1");
        LOGGER.info(String.valueOf(instantTime2.compareTo(instantTime1)));
        LOGGER.info("[CASE] instantTime1 < instantTime2");
        LOGGER.info("[ASSERT] -1");
        LOGGER.info(String.valueOf(instantTime1.compareTo(instantTime2)));
        instantTime1 = new InstantTime(DayOfWeek.MONDAY, LocalTime.of(15, 0));
        instantTime2 = new InstantTime(DayOfWeek.MONDAY, LocalTime.of(10, 0));
        LOGGER.info("[DATA] Different hours , same day.");
        LOGGER.info("[CASE] instantTime2 < instantTime1");
        LOGGER.info("[ASSERT] -1");
        LOGGER.info(String.valueOf(instantTime2.compareTo(instantTime1)));
        LOGGER.info("[CASE] instantTime1 > instantTime2");
        LOGGER.info("[ASSERT] 1");
        LOGGER.info(String.valueOf(instantTime1.compareTo(instantTime2)));
    }

    private static void testPerson() {
        LOGGER.info("[TEST] Person.java");
        LOGGER.info("==================================================");
        Student student = new Student("37045", "David Capela");
        Student student2 = new Student("37145", "Joao Silva");
        Student student3 = new Student("21561", "Tio Sal");
        Room r1 = new Room(101, "Sede", 30, 1, 30,"UFP",12,2);
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
            LOGGER.info("ex");
            LOGGER.info(e.getMessage());
        }
        LOGGER.info("[DATA] Different students , one class.");
        LOGGER.info("[CASE] Print student's schedule.");
        student.printClassSchedule();
        student2.printClassSchedule();
        LOGGER.info("[CASE] Print student's schedule when removed class.");
        try {
            class1.removeStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
        student.printClassSchedule();
        class1.printStudents();
        LOGGER.info("[CASE] Remove a class when a person doesn't have any.");
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
                new Room(101, "Sede", 30, 1, 15,"UFP",1,2)
        );
        Class class1 = new Class("inf", "PL", "diurno", schedule, university, subject, professor, new ArrayList<>());
        try {
            class1.addStudent(student);
            class1.addStudent(student2);
        } catch (PersonNotFoundException e) {
            LOGGER.info(e.getMessage());
        }

        //university.printSubjProf();
        //university.printProfClass();
        //university.printCourseClass();
        //university.printRoomClass();
        student.printClassSchedule();
        LOGGER.info("removing class");
        //LOGGER.info(class1.getStudents().size());
        university.removeClass(class1);
        student.printClassSchedule();
    }
}