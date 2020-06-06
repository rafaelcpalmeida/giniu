package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.RedBlackBST;

import java.util.logging.Logger;

public abstract class Person{
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private String id;
    private String name;
    private final RedBlackBST<InstantTime, Class> instanttimeClass;
    private int x;
    private int y;
    private int floor;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
        this.instanttimeClass = new RedBlackBST<>();
    }




    /**
     * Add class to instanttimeClass redblack
     *
     * @param pclass this person is attending to.
     */
    public void addClass(Class pclass) {
        InstantTime instantTime = pclass.getSchedule().getStart(); // time the class starts
        if (this.instanttimeClass.get(instantTime) == null) {
            // if the person has no classes in this time, than the class is added to instanttimeClass
            this.instanttimeClass.put(instantTime, pclass);
            return;
        }
        LOGGER.info("[WARNING] Person.java - addClass():");
        LOGGER.info("[WARNING] This person already has a class in this InstantTime.");
    }

    /**
     * @param aclass class to be removed from the instanttimeClass redblack.
     */
    public void removeClass(Class aclass) {
        InstantTime instantTime = aclass.getSchedule().getStart();
        Class c1 = this.instanttimeClass.get(instantTime);
        if (c1 != null) {
            this.instanttimeClass.delete(instantTime);
            return;
        }
        LOGGER.warning("Person.java - removeClass():");
        LOGGER.warning("This person doesn't have this class in that InstantTime.");
    }

    public RedBlackBST<InstantTime, Class> getInstantTimeClass() {
        return instanttimeClass;
    }

    /**
     * Prints this person's schedule.
     */
    public void printClassSchedule() {
        if (this.instanttimeClass.isEmpty()) {
            LOGGER.info("No schedule yet for this person.");
            return;
        }
        LOGGER.info(this.name + "'s class schedule:");
        for (InstantTime instantTime : this.instanttimeClass.keys()) {
            LOGGER.info(this.instanttimeClass.get(instantTime).toString());
            LOGGER.info("=================================");
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person:" +
                "\n\tid: " + id +
                "\n\tname: " + name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
