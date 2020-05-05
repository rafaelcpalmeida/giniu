package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.RedBlackBST;

public abstract class Person {
    private String id;
    private String name;
    private final RedBlackBST<InstantTime, Class> instanttimeClass;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
        instanttimeClass = new RedBlackBST<>();
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
        System.out.println("[WARNING] Person.java - addClass():");
        System.out.println("[WARNING] This person already has a class in this InstantTime.");
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
        System.out.println("[WARNING] Person.java - removeClass():");
        System.out.println("[WARNING] This person doesn't have this class in that InstantTime.");
    }

    /**
     * Prints this person's schedule.
     */
    public void printClassSchedule() {
        if (this.instanttimeClass.isEmpty()) {
            System.out.println("No schedule yet for this person.");
            return;
        }
        System.out.println(this.name + "'s class schedule:");
        for (InstantTime instantTime : this.instanttimeClass.keys()) {
            System.out.println(this.instanttimeClass.get(instantTime).toString());
            System.out.println("=================================");
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
}
