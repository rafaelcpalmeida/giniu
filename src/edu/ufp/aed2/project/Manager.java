package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Singleton that manages all the universities
 */

public class Manager implements Serializable{
    private static final Logger LOGGER = Logger.getLogger(Manager.class.getName());

    private static Manager manager;
    private SeparateChainingHashST<String, University> universities;
    private SeparateChainingHashST<Class, ArrayList<Student>> classes;
    private SeparateChainingHashST<String,LocationManager> locations;

    private Manager() {
        this.universities = new SeparateChainingHashST<>();
        this.locations = new SeparateChainingHashST<>();
    }

    public static Manager getInstance() {
        if (manager == null) manager = new Manager();
        return manager;
    }

    /**
     * Returns a university stored in universities
     *
     * @param name wanted.
     * @return The university called by the name.
     */
    public University getUniversity(String name) {
        University university = universities.get(name);
        if (university == null) {
            // doesn't have this university yet, let's add it
            university = new University(name);
            universities.put(name, university);
        }
        return university;
    }

    /**
     * Populates the University STs and others from bd.json file
     */
    public void populateSTsFromFile() {
        FileManager fileManager = FileManager.getInstance();    // FileManager singleton that has all the file related stuff methods
        try {
            this.classes = fileManager.getClasses();
        } catch (IOException e) {
            LOGGER.info("[WARNING] Exception made in populateSTsFromFile(): " + e.getMessage());
        }
    }

    public SeparateChainingHashST<Class, ArrayList<Student>> getClasses() {
        return classes;
    }

    /**
     * Returns the LocationManager from given university
     * @param university we want the LocationManager
     * @return LocationManager from the university
     */
    public LocationManager getLocationManager(String university) {
        if(this.locations.contains(university)) return this.locations.get(university);
        this.locations.put(university,new LocationManager());
        return this.locations.get(university);
    }

    /**
     * Write this object to binary and stores the file
     */
    public void writeToBinary() throws IOException {
        FileOutputStream file = new FileOutputStream("/app/data/manager.giniu");
        ObjectOutputStream oos = new ObjectOutputStream(file);
        oos.writeObject(this);
        oos.close();
        LOGGER.info("Binary file successfully created!");
    }

    /**
     * Read from file and changes this manager
     */
    public void readFromBinary() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(new File("/app/data/manager.giniu"));
        ObjectInputStream ois = new ObjectInputStream(file);
        manager = (Manager) ois.readObject();
        LOGGER.info("Binary file loaded!");
    }
}
