package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.*;
import edu.ufp.aed2.project.exceptions.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Class that manages all the graphs from a university
 */
public class LocationManager {
    private static final Logger LOGGER = Logger.getLogger(LocationManager.class.getName());
    private EdgeWeightedDigraph globalGraph;       // global graph with all the sub-graphs
    private ArrayList<Location> locations;

    public LocationManager() {
        this.locations = new ArrayList<>();
    }

    /**
     * Creates the globalGraph for the university
     * @throws LocationsNotInitException if the locations Array is empty ( no vertices )
     */
    public void createGlobalGraph() throws LocationsNotInitException {
        if(this.locations.isEmpty()) throw new LocationsNotInitException();
        this.globalGraph = new EdgeWeightedDigraph(this.locations.size());
        LOGGER.info("Global graph was created successfully with " + this.locations.size() + " vertices!");
    }

    /**
     * Returns the Locations with a given vertexId
     * @param vertexId from the Location we want
     * @return Location
     * @throws VertexNotFoundException exception
     */
    public Location getLocationWhereVertexIs(int vertexId) throws VertexNotFoundException {
        for (Location location : this.locations) if(location.getVertexId() == vertexId) return location;
        return null;
    }

    /**
     * Returns all the edges from a given domain of vertices
     * Used to calculate a sub-graph from @firstVertex to @lastVertex
     * @param firstVertex first vertex of the sub graph - have the smallest index
     * @param lastVertex last vertex of the sub graph - have the biggest index
     * @return all the edges from the given domain (@firstVertex , @lastVertex).
     * Returns null if there are no edges in the global graph.
     */
    private ArrayList<DirectedEdge> getEdgesFrom(int firstVertex , int lastVertex) throws GlobalGraphNotCreated {
        if(this.globalGraph == null) throw new GlobalGraphNotCreated();
        if(this.globalGraph.E() == 0) return null;
        ArrayList<DirectedEdge> edges = new ArrayList<>();
        for (DirectedEdge edge : this.globalGraph.edges()) {
            if (edge.from() >= firstVertex && edge.from() <= lastVertex && edge.to() >= firstVertex && edge.to() <= lastVertex) {
                // is between the given domain
                edges.add(edge);
            }
        }
        return edges;
    }

    /**
     * @param floor we want the sub graph
     * @throws FloorNotFoundException if no floor flound
     * @throws LocationsNotInitException if the locations array size is 0
     * @throws VertexNotFoundException no vertices found
     * @throws GlobalGraphNotCreated globalGraph not inited
     */
    public Subgraph getSubGraphFromFloor(int floor) throws FloorNotFoundException, LocationsNotInitException, VertexNotFoundException, GlobalGraphNotCreated {
        ArrayList<Location> floorLocations = this.getLocationsWhereFloorIs(floor);
        if (floorLocations.isEmpty()) throw new FloorNotFoundException(floor);
        int minIndex = floorLocations.get(0).getVertexId();
        int maxIndex = floorLocations.get(0).getVertexId();
        for (Location location : floorLocations) {
            if (location.getVertexId() > maxIndex) maxIndex = location.getVertexId();
            if (location.getVertexId() < minIndex) minIndex = location.getVertexId();
        }
        ArrayList<DirectedEdge> dEdges =  this.getEdgesFrom(minIndex,maxIndex);
        LOGGER.info("a returnar subgraph");
        return new Subgraph(dEdges, minIndex , maxIndex, this);
    }

    /**
     * Prints a set of DirectedEdges ( sub-graph )
     * @param subGraph we want to print
     */
    public void printSubGraph(ArrayList<DirectedEdge> subGraph){
        for (DirectedEdge directedEdge : subGraph){
            LOGGER.info(directedEdge.toString());
        }
    }

    /**
     * @param source vertex tail
     * @param dest vertex head
     * @param weight from the connection
     * @throws GlobalGraphNotCreated if the global graph was not created yet
     */
    public void createEdge(int source, int dest , double weight) throws GlobalGraphNotCreated {
        DirectedEdge edge = new DirectedEdge(source,dest,weight);
        if(this.globalGraph != null){
            // If the global graph exists
            this.globalGraph.addEdge(edge);
            LOGGER.info("An edge from " + source + " to "+ dest + " w/ " + weight + " weight was added to graph");
        } else throw new GlobalGraphNotCreated();
    }

    /**
     * Calculate the connection weight between 2 vertices
     * based on the Locations's coordinates.
     * The further they are , the greater the weight is.
     * They must be consequent vertices ( side by side )
     * @param source head vertex
     * @param dest tail vertex
     * @return weight
     */
    public double calculateWeight(Location source, Location dest){
        return source.getDistanceFromOtherLocation(dest);
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

    public EdgeWeightedDigraph getGlobalGraph() {
        return globalGraph;
    }
}
