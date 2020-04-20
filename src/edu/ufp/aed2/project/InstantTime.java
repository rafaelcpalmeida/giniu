package edu.ufp.aed2.project;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class InstantTime implements Comparable<InstantTime> {
    private DayOfWeek dayOfWeek;
    private LocalTime time;

    public InstantTime(DayOfWeek dayOfWeek, LocalTime time) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
    }

    /**
     * @param instantTime comparing to.
     * @return 0 if equal , 1 if greater and -1 if less.
     */
    @Override
    public int compareTo(InstantTime instantTime) {
        int index = this.dayOfWeek.compareTo(instantTime.dayOfWeek);
        if(index > 0){
            // this dayOfWeek is after instantTime
            return 1;
        }
        else if(index == 0){
            // instantTime is in the same week day of this dayOfWeek
            if(this.time.isAfter(instantTime.time)) return 1;
            else if(this.time.isBefore(instantTime.time)) return -1;
            return 0;
        }
        // this dayOfWeek is before instantTime
        return -1;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "\n\t\tDay of the week: " + this.dayOfWeek +
                "\n\t\tTime: " + this.time;
    }


}
