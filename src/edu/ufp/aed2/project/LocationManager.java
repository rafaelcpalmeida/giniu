package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.ufp.aed2.project.exceptions.FloorAlreadyExistsException;
import edu.ufp.aed2.project.exceptions.FloorNotFoundException;
import edu.ufp.aed2.project.exceptions.LocationsNotInitException;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Class that manages all the graphs from a university
 */
public class LocationManager {
    private static final Logger LOGGER = Logger.getLogger(LocationManager.class.getName());
    private static EdgeWeightedGraph globalGraph;       // global graph with all the sub-graphs
    private SeparateChainingHashST<Integer, EdgeWeightedGraph> subGraphs; // ST with all the sub-graphs indexed by floor number
    private ArrayList<Location> locations;

    public LocationManager() {
        this.subGraphs = new SeparateChainingHashST<>();
        this.locations = new ArrayList<>();
    }

    /**
     * Create a new sub-graph based on the amount of
     * locations . Each Location is a vertex in the sub-graph
     *
     * @param floor number of the floor
     * @throws FloorAlreadyExistsException throw if the floor being created already exists
     */
    public void createNewSubGraph(int floor) throws FloorAlreadyExistsException, LocationsNotInitException {
        if (this.subGraphs.contains(floor)) throw new FloorAlreadyExistsException(floor);
        ArrayList<Location> locationsByFloor = this.getLocationsWhereFloorIs(floor);   // get all locations from given floor
        this.subGraphs.put(floor, new EdgeWeightedGraph(locationsByFloor.size()));      // Create as many vertices as locations
        LOGGER.info("The sub-graph for the floor " + floor + " was created!");
    }

    /**
     * Returns all the locations from a certain floor
     *
     * @param floor from where we need the locations
     * @return ArrayList<Location>
     */
    private ArrayList<Location> getLocationsWhereFloorIs(int floor) throws LocationsNotInitException {
        if(this.locations.isEmpty()) throw new LocationsNotInitException();
        ArrayList<Location> locationsByFloor = new ArrayList<>();
        for (Location location : this.locations) {
            if (location.getFloor() == floor) locationsByFloor.add(location);
        }
        return locationsByFloor;
    }

    /**
     * Add a location to the ArrayList of locations and sets the VertexId
     *
     * @param location being added to locations
     */
    public void addLocation(Location location) {
        if (this.locations.contains(location)) {
            LOGGER.warning("Location already exists!");
            return;
        }
        location.setVertexId(this.locations.size());
        this.locations.add(location);
        LOGGER.info("Location successfully added to locations!");
    }

    /**
     * @param floor we want the respective sub-graph
     * @return floor's sub-graph
     * @throws FloorNotFoundException if the floor is not found in @subGraphs
     */
    public EdgeWeightedGraph getSubGraphFromFloor(int floor) throws FloorNotFoundException {
        if (!this.subGraphs.contains(floor)) throw new FloorNotFoundException(floor);
        return this.subGraphs.get(floor);
    }


}
