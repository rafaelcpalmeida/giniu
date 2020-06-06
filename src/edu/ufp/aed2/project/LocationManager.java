package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.*;
import edu.ufp.aed2.project.exceptions.*;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class that manages all the graphs from a university
 */

public class LocationManager implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(LocationManager.class.getName());
    private EdgeWeightedDigraph globalGraph;       // global graph with all the sub-graphs
    private ArrayList<Location> locations;
    protected static CostEnum costEnum = CostEnum.DISTANCE;

    public LocationManager() {
        this.locations = new ArrayList<>();
    }

    /**
     * Creates the globalGraph for the university
     *
     * @throws LocationsNotInitException if the locations Array is empty ( no vertices )
     */
    public void createGlobalGraph() throws LocationsNotInitException {
        if (this.locations.isEmpty()) throw new LocationsNotInitException();
        this.globalGraph = new EdgeWeightedDigraph(this.locations.size());
        LOGGER.info("Global graph was created successfully with " + this.locations.size() + " vertices!");
    }

    /**
     * Returns the Locations with a given vertexId
     *
     * @param vertexId from the Location we want
     * @return Location
     * @throws VertexNotFoundException exception
     */
    public Location getLocationWhereVertexIs(int vertexId) throws VertexNotFoundException {
        for (Location location : this.locations) if (location.getVertexId() == vertexId) return location;
        return null;
    }

    /**
     * Returns all the edges from a given domain of vertices
     * Used to calculate a sub-graph from @firstVertex to @lastVertex
     *
     * @param firstVertex first vertex of the sub graph - have the smallest index
     * @param lastVertex  last vertex of the sub graph - have the biggest index
     * @return all the edges from the given domain (@firstVertex , @lastVertex).
     * Returns null if there are no edges in the global graph.
     */
    private ArrayList<Connection> getEdgesFrom(int firstVertex, int lastVertex) throws GlobalGraphNotCreated {
        if (this.globalGraph == null) throw new GlobalGraphNotCreated();
        if (this.globalGraph.E() == 0) return null;
        ArrayList<Connection> edges = new ArrayList<>();
        for (DirectedEdge edge : this.globalGraph.edges()) {
            if (edge.from() >= firstVertex && edge.from() <= lastVertex && edge.to() >= firstVertex && edge.to() <= lastVertex) {
                // is between the given domain
                edges.add(((Connection)edge));
            }
        }
        return edges;
    }

    /**
     * @param floor we want the sub graph
     * @return ArrayList<DirectedEdge> directEdges from the subgrapg
     * @throws FloorNotFoundException    if no floor flound
     * @throws LocationsNotInitException if the locations array size is 0
     * @throws VertexNotFoundException   no vertices found
     * @throws GlobalGraphNotCreated     globalGraph not inited
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
        ArrayList<Connection> dEdges = this.getEdgesFrom(minIndex, maxIndex);
        LOGGER.info("returning subgraph");
        return new Subgraph(dEdges, minIndex, maxIndex, this);
    }


    /**
     * Generates a sub-graph with all the locations passed.
     *
     * @param locations we want the sub-graph
     * @return Sub-graph with all the locations
     */
    public Subgraph getSubGraphFromVertices(ArrayList<Location> locations) throws LocationsNotInitException, VertexNotFoundException, GlobalGraphNotCreated {
        if (locations.isEmpty()) throw new LocationsNotInitException();
        int minIndex = locations.get(0).getVertexId();
        int maxIndex = locations.get(0).getVertexId();
        for (Location location : locations) {
            if (location.getVertexId() > maxIndex) maxIndex = location.getVertexId();
            if (location.getVertexId() < minIndex) minIndex = location.getVertexId();
        }
        ArrayList<Connection> dEdges = this.getEdgesFrom(minIndex, maxIndex);
        LOGGER.info("returning subgraph");
        return new Subgraph(dEdges, minIndex, maxIndex, this);
    }

    /**
     * Prints a set of DirectedEdges ( sub-graph )
     *
     * @param subGraph we want to print
     */
    public void printSubGraph(ArrayList<DirectedEdge> subGraph) {
        for (DirectedEdge directedEdge : subGraph) {
            LOGGER.info(directedEdge.toString());
        }
    }

    /**
     * @param source   vertex tail
     * @param dest     vertex head
     * @param distance distance weight from the connection
     * @param timeCost time weight from the connection
     * @throws GlobalGraphNotCreated if the global graph was not created yet
     */
    public void createEdge(int source, int dest, double distance, double timeCost) throws GlobalGraphNotCreated {
        Connection edge = new Connection(source, dest, distance, timeCost);
        if (this.globalGraph != null) {
            // If the global graph exists
            this.globalGraph.addEdge(edge);
            LOGGER.info("An edge from " + source + " to " + dest + " w/ " + distance + " distance weight" + " and " + timeCost + " time cost was added to graph");
        } else throw new GlobalGraphNotCreated();
    }

    /**
     * Calculate the connection weight between 2 vertices
     * based on the Locations's coordinates.
     * The further they are , the greater the weight is.
     * They must be consequent vertices ( side by side )
     *
     * @param source head vertex
     * @param dest   tail vertex
     * @return weight
     */
    public double calculateWeight(Location source, Location dest) {
        return source.getDistanceFromOtherLocation(dest);
    }

    /**
     * Returns all the locations from a certain floor
     *
     * @param floor from where we need the locations
     * @return ArrayList<Location>
     */
    private ArrayList<Location> getLocationsWhereFloorIs(int floor) throws LocationsNotInitException {
        if (this.locations.isEmpty()) throw new LocationsNotInitException();
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

    /**
     * Applies the shortest path algorithm to a given graph
     *
     * @param source      where we start
     * @param destination where we want to go
     * @param graph       the graph we want to use
     */
    public void shortestPathBetween(int source, int destination, EdgeWeightedDigraph graph, int offset, CostEnum type) {
        costEnum = type;
        DijkstraSP dijkstraSP = new DijkstraSP(graph, source - offset);
        LOGGER.info("printing dijkstra ...");
        if (dijkstraSP.hasPathTo(destination - offset)) {
            StdOut.printf("%d to %d (%.2f)  ", source, destination, dijkstraSP.distTo(destination - offset));
            for (DirectedEdge e : dijkstraSP.pathTo(destination - offset)) {
                StdOut.print((e.from() + offset) + "->" + (e.to() + offset) + " " + String.format("%5.2f", e.weight()) + "   ");
            }
            StdOut.println();
        } else {
            StdOut.printf("%d to %d         no path\n", source + offset, destination + offset);
        }
    }


    /**
     * @param locations whole list
     * @param invalidLocations the list we dont want the locations
     * @return All the subtracted locations
     */
    public ArrayList<Location> subtractLocations(ArrayList<Location> locations, ArrayList<Location> invalidLocations){
        ArrayList<Location> locationsAux = new ArrayList<>();
        for(Location location : locations){
            if(!invalidLocations.contains(location)) locationsAux.add(location);
        }
        return locationsAux;
    }

    /**
     * @param floor where we want
     * @param x coordinate
     * @param y coordinate
     * @return Nearest location at a given coordinate
     */
    private Location getNearestLocation(int floor , int x , int y) throws LocationsNotInitException {
        ArrayList<Location> locations = this.getLocationsWhereFloorIs(floor);
        Location choosen = locations.get(0);
        double min = locations.get(0).getDistanceFromCoordinate(x,y);
        for(Location location : locations){
            double distance = location.getDistanceFromCoordinate(x,y);
            if(distance <= min) {
                min = distance;
                choosen = location;
            }
        }
        return choosen;
    }

    /**
     * @param locations we want to know the shortest
     * @param source we are at
     * @return shortest locations from the given source and locations
     */
    private Location getShortestCostVertex(ArrayList<Location> locations, int source) throws FloorNotFoundException, VertexNotFoundException, GlobalGraphNotCreated, LocationsNotInitException {
        DijkstraSP dijkstraSP = new DijkstraSP(this.globalGraph,source);
        double min = dijkstraSP.distTo(locations.get(0).getVertexId());
        Location choosen = locations.get(0);
        for(Location location : locations){
            double distance = dijkstraSP.distTo(location.getVertexId());
            if(distance <= min) {
                min = distance;
                choosen = location;
            }
        }
        return choosen;
    }

    /**
     * If has a path to an exit , it prints the nearest one
     * @param person in context
     * @param type of cost ( distance or time )
     */
    public void shortestPathToExit(Person person, CostEnum type) throws LocationsNotInitException, VertexNotFoundException, GlobalGraphNotCreated, FloorNotFoundException {
        costEnum = type;
        ArrayList<Location> locations = this.getLocationsWhereFloorIs(person.getFloor());
        ArrayList<Location> exits = new ArrayList<>();
        for(Location location : locations){
            if(location.getTypeOfSpace() == TypeOfSpace.exit) exits.add(location);
        }
        Location nearestLocation = this.getNearestLocation(person.getFloor(),person.getX(),person.getY());
        Location nearestExit = this.getShortestCostVertex(exits,nearestLocation.getVertexId());
        this.shortestPathBetween(nearestLocation.getVertexId(),nearestExit.getVertexId(),this.globalGraph,0,type);
    }

    /**
     * Check if graph is connected
     * @param g graph
     */
    public boolean isConnected(EdgeWeightedDigraph g) {
        int s = 0;
        int flag = 0;
        DijkstraSP sp = new DijkstraSP(g, s);
        for (int t = 0; t < g.V(); t++) {
            if (!sp.hasPathTo(t)) {
                LOGGER.info("Not connected in -> " + t);
                flag = 1;
            }
        }
        if (flag == 0) {
            LOGGER.info("The graph is connected!");
            return true;
        }
        LOGGER.info("The graph is not connected!");
        return false;
    }


}
