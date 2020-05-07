package edu.ufp.aed2.project;

public class Schedule implements Comparable<Schedule> {
    private InstantTime start;
    private InstantTime end;
    private Room room;

    public Schedule(InstantTime start, InstantTime end, Room room) {
        this.start = start;
        this.end = end;
        this.room = room;
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
    }

    @Override
    public String toString() {
        return "\n\t\tStart: " + start.toString() +
                "\n\t\tEnd: " + end.toString() +
                "\n\tRoom: " + room.toString();
    }

    /**
     * @param schedule to compare to.
     * @return 1 if start time is greater , 0 if equal and -1 if less.
     */
    @Override
    public int compareTo(Schedule schedule) {
        return this.start.compareTo(schedule.start);
    }
}
