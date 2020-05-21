package edu.ufp.aed2.project.exceptions;

public class VertexNotFoundException extends Exception {
    String message;
    public VertexNotFoundException(String message){
        this.message = message;
    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
