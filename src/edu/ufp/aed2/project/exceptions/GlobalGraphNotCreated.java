package edu.ufp.aed2.project.exceptions;

public class GlobalGraphNotCreated extends Exception {
    public GlobalGraphNotCreated(){}
    @Override
    public String getMessage() {
        return "The Global Graph from this university was not created yet! you must call the method createGlobalGraph().";
    }
}
