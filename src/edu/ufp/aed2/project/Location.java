package edu.ufp.aed2.project;

import edu.ufp.aed2.project.exceptions.VertexNotFoundException;

public class Location {
    private int floor;                              // Floor that is locations is in
    private int vertexId = -1;                      // The id of vertex in the sub-graph , if -1 , than it has no graph
    private int x;                                  // x coordinate
    private int y;                                  // y coordinate
    private TypeOfSpace typeOfSpace;                // Type of the space

    public Location(){
    }

    public Location(int floor, int x, int y, String university,TypeOfSpace typeOfSpace) {
        LocationManager locationManager = Manager.getInstance().getLocationManager(university);    // Manager for the given university
        this.typeOfSpace = typeOfSpace;
        this.x = x;
        this.y = y;
        this.floor = floor;
        locationManager.addLocation(this);          // add this location to the manager
    }

    public void setVertexId(int vertexId) {
        this.vertexId = vertexId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFloor() {
        return floor;
    }

    public TypeOfSpace getTypeOfSpace() {
        return typeOfSpace;
    }

    /**
     * @return The id of the vertex in the graph
     * @throws VertexNotFoundException if no location
     */
    public int getVertexId() throws VertexNotFoundException {
        if (this.vertexId == -1) throw new VertexNotFoundException("The vertixId is not define , so the sub-graph is not yet created");
        return this.vertexId;
    }

    /**
     * Calculates the distance between a Location and other Location
     * based on the coordinates
     * @param other Location we want the distance from
     * @return the total distance from one Location and other
     */
    public double getDistanceFromOtherLocation(Location other){
        return Math.sqrt((other.getY() - this.y) * (other.getY() - this.y) + (other.getX() - this.x) * (other.getX() - this.x));
    }
}
