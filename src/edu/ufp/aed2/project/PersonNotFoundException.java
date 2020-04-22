package edu.ufp.aed2.project;

/**
 * Exception for ST's where the Person was not found.
 */
public class PersonNotFoundException extends Exception{
    private final String name;

    public PersonNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return this.name + " was not found.";
    }
}
