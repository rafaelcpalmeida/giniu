package edu.ufp.aed2.project.exceptions;

public class FloorNotFoundException extends Exception {
    private final int floor;

    public FloorNotFoundException(int floor) {
        this.floor = floor;
    }

    @Override
    public String getMessage() {
        return "Floor nr. " +this.floor + " not found.";
    }
}
