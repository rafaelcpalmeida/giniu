package edu.ufp.aed2.project;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;

/**
 * Information about a room.
 */
public class Room extends Location implements Comparable<Room>, Serializable {
    private final int number;         // room's number
    private final String building;    // room's building name ex: "Sede", "Fisio"
    private final int maxSize;        // room's maximum size
    private final int floor;          // room's floor number
    private final int plugNumber;     // room's number os electric plugs , if none than 0
    private String universityName;    // room's university
    private ArrayList<Schedule> schedules;

    /**
     * Constructor to add this Room to graph
     */
    public Room(int number, String building, int maxSize, int floor, int plugNumber, String universityName, int x , int y) {
        super(floor,x,y,universityName,TypeOfSpace.room);               // create the location
        this.number = number;
        this.building = building;
        this.maxSize = maxSize;
        this.floor = floor;
        this.plugNumber = plugNumber;
        this.universityName = universityName;
        this.schedules = new ArrayList<>();
    }

    /**
     * Constructor to not add this Room to graph
     */
    public Room(int number, String building, int maxSize, int floor, int plugNumber) {
        this.number = number;
        this.building = building;
        this.maxSize = maxSize;
        this.floor = floor;
        this.plugNumber = plugNumber;
    }

    public int getNumber() {
        return number;
    }

    public String getBuilding() {
        return building;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getFloor() {
        return floor;
    }

    public int getPlugNumber() {
        return plugNumber;
    }

    /**
     * Given a domain of time (start and end) of the week , returns a list of schedules,
     * which the room is available.
     *
     * @param start starting day of the week.
     * @param end   ending day of the week.
     * @return ArrayList of Schedules which the room is available.
     */
    public ArrayList<Schedule> availabilityBetween(DayOfWeek start, DayOfWeek end) {
        return null;
    }

    /**
     * @param instantTime passed to check availability.
     * @return room's availability.
     */
    public boolean isAvailable(InstantTime instantTime) {
        for(Schedule schedule : this.schedules){
            if(schedule.interceptThisInstantTime(instantTime)) return true;
        }
        return false;
    }

    /**
     * @param room other room to compare.
     * @return 0 if equal , 1 if greater , -1 if lesser.
     */
    @Override
    public int compareTo(Room room) {
        if (this.number > room.number) return 1;
        return this.number == room.number ? 0 : -1;
    }

    @Override
    public String toString() {
        return "\n\tNumber: " + number +
                "\n\tBuilding: " + building;
    }

    public void addSchedule(Schedule schedule){
        if(this.schedules.contains(schedule)){
            System.out.println("Schedule already exists !");
            return;
        }
        this.schedules.add(schedule);
    }
}
