package diver;

import datastructures.SlowPQueue;
import game.*;
import graph.ShortestPaths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Map;


/** This is the place for your implementation of the {@code SewerDiver}.
 */
public class McDiver implements SewerDiver {

    /** See {@code SewerDriver} for specification. */
    @Override
    public void seek(SeekState state) {
        // TODO : Look for the ring and return.
        // DO NOT WRITE ALL THE CODE HERE. DO NOT MAKE THIS METHOD RECURSIVE.
        // Instead, write your method (it may be recursive) elsewhere, with a
        // good specification, and call it from this one.
        //
        // Working this way provides you with flexibility. For example, write
        // one basic method, which always works. Then, make a method that is a
        // copy of the first one and try to optimize in that second one.
        // If you don't succeed, you can always use the first one.
        //
        // Use this same process on the second method, scram.
        look(state);
    }

    public void aStar(SeekState state) {
        if(state.distanceToRing() == 0) {
            return;
        }

        SlowPQueue frontier = new SlowPQueue();
        Map<Long, Double> distances = new HashMap<>();

        //NodeStatus start = new NodeStatus(state.currentLocation(), state.distanceToRing());
        frontier.add(state.currentLocation(), 0);
        distances.put(state.currentLocation(), 0.0);

        while (!frontier.isEmpty()) {
            long currId = (long) frontier.extractMin();

            for(NodeStatus neighbor : state.neighbors()) {
                int distToRing = neighbor.getDistanceToRing();
                if(!distances.containsKey(neighbor.getId())) {

                }
            }
        }



        for(NodeStatus neighbor : state.neighbors()) {

        }
    }



    /**
     *
     * @param state the state of the diver, including its current location, distance to the ring,
     *              and its neighbors
     * precondition: state cannot be null
     */
    public void look(SeekState state) {
        assert state != null;

        Set<Long> visited = new HashSet<>();
        Stack<Long> previousLocs = new Stack<>();
        visit(state, visited, previousLocs);



//        Set<Long> visited = new HashSet<Long>();
//        visited.add(loc);
//
//        visit(dist, loc, state, visited);
    }

    public void visit(SeekState state, Set<Long> visited, Stack<Long> previousLocs) {

        long loc = state.currentLocation();

        visited.add(loc);

        //this does not feel needed
        //base case: exit out of the method when the diver is already on the ring
//        if(state.distanceToRing() == 0) {
//           return;
//        }

        //make the diver explore the neighbors in smallest cost
        SlowPQueue frontier = new SlowPQueue();
        for(NodeStatus neighbor : state.neighbors()) {
            frontier.add(neighbor, neighbor.getDistanceToRing());
        }

        while(!frontier.isEmpty()) {
            //base case: exit out of the method when the diver is already on the ring
            if(state.distanceToRing() == 0) {
                return;
            }
            //return diver to the previous location if at a dead end
            if(deadEnd(state.neighbors(),visited)) {
                state.moveTo(previousLocs.pop());
                visit(state, visited, previousLocs);
            }
            NodeStatus neighbor = (NodeStatus) frontier.extractMin();
            long neighborId =  neighbor.getId();
            //recursive case to visit if the neighbor has not been visited
            if(!visited.contains(neighborId)) {
                state.moveTo(neighborId);
                previousLocs.push(loc);
                visit(state,visited,previousLocs);

            }
        }
    }

    /*
    returns if the diver is at a dead end
     */
    public boolean deadEnd(Collection<NodeStatus> neighbors, Set<Long> visited) {
        //if there is no neighbors, go back to previous location
        boolean allVisited = true;
        for(NodeStatus neighbor : neighbors) {
            long neighborId = neighbor.getId();
            if(!visited.contains(neighborId)) {
                allVisited = false;
                break;
            }
        }
        return allVisited;
    }

    /** See {@code SewerDriver} for specification. */
    @Override
    public void scram(ScramState state) {
        // TODO: Get out of the sewer system before the steps are used up.
        // DO NOT WRITE ALL THE CODE HERE. Instead, write your method elsewhere,
        // with a good specification, and call it from this one.
        Set<Node> coinNodes = new HashSet<>();
        for (Node n : state.allNodes()) {
            int coins = n.getTile().coins();
            if (coins > 0) {
                coinNodes.add(n);
            }
        }
        bar(state, coinNodes);
        //loot(state);
    }

    public void loot(ScramState state) {
        //set up for finding the shortest path to exit
        Maze map = new Maze((Set<Node>) state.allNodes());
        ShortestPaths maze = new ShortestPaths(map);
        maze.singleSourceDistances(state.currentNode());
        List<Edge> path = maze.bestPath(state.exit());

        //get a full map of nodes that have coins and their path
        //not sure what this is for
        Map coinsNodes = new HashMap();
        for(Node node: state.allNodes()) {
            int coins = node.getTile().coins();
            if(coins != 0) {
                List<Edge> pathToCoin = maze.bestPath(node);
                coinsNodes.put(pathToCoin,node);
            }
        }




        while(path.size() > state.stepsToGo()) {
            //coin looting algo

            //update best path after each move
            maze.singleSourceDistances(state.currentNode());
            path = maze.bestPath(state.exit());
        }

        //go back to destination at the end
        for(Edge e : path) {
            state.moveTo(e.destination());
        }
    }

    public void bar(ScramState state, Set<Node> coinNodes) {
        Maze maze = new Maze((Set<Node>) state.allNodes());
        ShortestPaths s = new ShortestPaths(maze);
        s.singleSourceDistances(state.currentNode());
        List<Edge> bestPaths = s.bestPath(state.exit());

        for (Edge e : bestPaths) {
            int closestCoin = Integer.MAX_VALUE;
            for (Node n : coinNodes) {
                s.singleSourceDistances(e.source());
                if (s.bestPath(n).size() < closestCoin) {

                }
            }
            Node bestNeighbor = e.destination();

            s.singleSourceDistances(e.source());
            state.moveTo(bestNeighbor);

        }
    }
}
