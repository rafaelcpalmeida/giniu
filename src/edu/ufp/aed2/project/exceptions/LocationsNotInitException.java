package edu.ufp.aed2.project.exceptions;

public class LocationsNotInitException extends Exception {

    public LocationsNotInitException(){}
    @Override
    public String getMessage() {
        return "The array locations is empty . You need to add locations to create a sub-graph and populate the locations array";
    }
}
