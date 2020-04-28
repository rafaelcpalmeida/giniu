package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Singleton that manages all the universities
 */
public class Manager {
    private SeparateChainingHashST<String,University> universities;

    private static Manager manager;

    private Manager() {
        this.universities = new SeparateChainingHashST<>();
    }

    public static Manager getInstance(){
        if(manager==null) manager = new Manager();
        return manager;
    }

    /**
     * Returns a university stored in universities
     * @param name wanted.
     * @return The university called by the name.
     */
    public University getUniversity(String name){
        University university = universities.get(name);
        if(university == null){
            // doesn't have this university yet, let's add it
            university = new University(name);
            universities.put(name,university);
        }
        return university;
    }

    /**
     * Populates the University STs and others from bd.json file
     */
    public void populateSTsFromFile(){
        FileManager fileManager = FileManager.getInstance();    // FileManager singleton that has all the file related stuff methods
        try{
            SeparateChainingHashST<Class,ArrayList<Student>> classes = fileManager.getClasses();
            for(Class nclass : classes.keys()){
                // iterate each class and adds all the students properly (calling the addStudent method)
                ArrayList<Student> students = classes.get(nclass);
                for(Student student : students){
                    try{
                        nclass.addStudent(student);
                    }catch (PersonNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }catch (IOException e){
            System.out.println("[WARNING] Exception made in populateSTsFromFile(): " + e.getMessage());
        }
    }
}
