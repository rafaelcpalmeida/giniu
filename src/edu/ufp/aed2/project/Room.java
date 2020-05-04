package edu.ufp.aed2.project;

import java.time.DayOfWeek;
import java.util.ArrayList;

/**
 * Information about a room.
 */
public class Room implements Comparable<Room> {
    private final int number;         // room's number
    private final String building;    // room's building name ex: "Sede", "Fisio"
    private final int maxSize;        // room's maximum size
    private final int floor;          // room's floor number
    private final int plugNumber;     // room's number os electric plugs , if none than 0

    public Room(int number, String building, int maxSize, int floor, int plugNumber) {
        this.number = number;
        this.building = building;
        this.maxSize = maxSize;
        this.floor = floor;
        this.plugNumber = plugNumber;
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
     * @param schedule passed to check availability.
     * @return room's availability.
     */
    public boolean isAvailable(Schedule schedule) {
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
}
