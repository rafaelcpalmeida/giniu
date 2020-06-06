package edu.ufp.aed2.project;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.ufp.aed2.project.exceptions.VertexNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Subgraph implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(LocationManager.class.getName());
    private LocationManager locationManager;
    private EdgeWeightedDigraph graph;
    private int offset;                                      // offset between sub graph and global graph

    public Subgraph(ArrayList<DirectedEdge> directedEdges, int min, int max, LocationManager locationManager){
        this.graph = new EdgeWeightedDigraph((max - min) + 1);
        LOGGER.info(" graph created!");
        LOGGER.info(this.graph.toString());
        this.offset = min;
        LOGGER.info("offset -> " + this.offset);
        this.addEdges(directedEdges);
        this.locationManager = locationManager;
    }

    /**
     * Simulates all the edges from the given domain from global graph
     * with the correspondent directedEdges
     * @param directedEdges from global graph
     */
    private void addEdges(ArrayList<DirectedEdge> directedEdges){
        // Add direct edges
        for(DirectedEdge directedEdge : directedEdges){
            LOGGER.info(directedEdge.toString());
            DirectedEdge offsetDirectedEdge = new DirectedEdge(directedEdge.from() - offset , directedEdge.to() - offset , directedEdge.weight());
            this.graph.addEdge(offsetDirectedEdge);
        }
    }

    public EdgeWeightedDigraph getGraph() {
        return graph;
    }

    /**
     * Returns the location from the vertexId of this sub graph
     * @param vertexId from the Location
     * @return Location or null if not found
     * @throws VertexNotFoundException exception
     */
    public Location getLocationFromVertexId(int vertexId) throws VertexNotFoundException {
        int globalVertexId = vertexId + this.offset;
        return this.locationManager.getLocationWhereVertexIs(globalVertexId);
    }
}
