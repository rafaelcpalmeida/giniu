package edu.ufp.aed2.project;

import java.io.Serializable;

public class Schedule implements Comparable<Schedule>, Serializable {
    private InstantTime start;
    private InstantTime end;
    private Room room = null;

    public Schedule(InstantTime start, InstantTime end, Room room) {
        this.start = start;
        this.end = end;
        this.setRoom(room);
    }

    public InstantTime getStart() {
        return start;
    }

    public void setStart(InstantTime start) {
        this.start = start;
    }

    public InstantTime getEnd() {
        return end;
    }

    public void setEnd(InstantTime end) {
        this.end = end;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
        room.addSchedule(this);
    }

    @Override
    public String toString() {
        return "\n\t\tStart: " + start.toString() +
                "\n\t\tEnd: " + end.toString() +
                "\n\t\tRoom: " + ((room != null) ? room.toString() : "");
    }

    /**
     * @param schedule to compare to.
     * @return 1 if start time is greater , 0 if equal and -1 if less.
     */
    @Override
    public int compareTo(Schedule schedule) {
        return this.start.compareTo(schedule.start);
    }

    /**
     * Checks if this Schedule is between other Schedule,
     * ex:
     * schedule( Monday , 15h00 - 16h00).isBetween(Monday, 14h00 - 17h00 )
     * true
     * @param other schedule
     * @return boolean
     */
    public boolean isBetween(Schedule other){
        return this.compareTo(other) >= 0 && this.end.compareTo(other.end) <= 0;
    }

    public boolean interceptThisInstantTime(InstantTime instantTime){
        return this.start.compareTo(instantTime)<=0 && this.end.compareTo(instantTime) >=0;
    }
}
