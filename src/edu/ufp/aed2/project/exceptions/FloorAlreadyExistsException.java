package edu.ufp.aed2.project.exceptions;

public class FloorAlreadyExistsException extends Exception {
    private final int floor;

    public FloorAlreadyExistsException(int floor) {
        this.floor = floor;
    }

    @Override
    public String getMessage() {
        return "Floor nr. " +this.floor + " already exists.";
    }
}
