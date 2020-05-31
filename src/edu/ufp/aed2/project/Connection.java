package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.DirectedEdge;

public class Connection extends DirectedEdge {
    private double timeWeight;

    public Connection(int v, int w, double distance, double time) {
        super(v, w, distance);
        this.timeWeight = time;
    }

    public double getTimeWeight() {
        return timeWeight;
    }

    public double getDistance() {
        return super.weight();
    }

    public void setTimeWeight(double timeWeight) {
        this.timeWeight = timeWeight;
    }


    @Override
    public String toString() {
        return super.from() + "->" + super.to() +
                " - {Distance: " + super.weight() + " , Time: " + this.timeWeight + "}";
    }

    @Override
    public double weight(){
        //System.out.println("entrei");
        if (LocationManager.costEnum == CostEnum.TIME) {
            return this.timeWeight;
        }
        return super.weight();
    }
}
