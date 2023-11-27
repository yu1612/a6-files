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
//        Stack<Long> visited = new Stack<Long>();
//        Set<Long> forbidden = new HashSet<>();
//        dfsWalk(state, visited, forbidden);
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
//
//        long loc = state.currentLocation();
//        int dist = state.distanceToRing();
//
//        if (dist == 0) {
//            return;
//        }
//
//        Collection<NodeStatus> neighbors = state.neighbors();
//        if(neighbors.isEmpty()) {
//            return;
//        }
//        for(NodeStatus neighbor : state.neighbors()) {
//            state.moveTo(neighbor.getId());
//            look(state);
//        }


        Set<Long> visited = new HashSet<>();
        Stack<Long> previousLocs = new Stack<>();
        long loc = state.currentLocation();
        visit(state, visited, loc, previousLocs);
        return;



//        Set<Long> visited = new HashSet<Long>();
//        visited.add(loc);
//
//        visit(dist, loc, state, visited);
    }

    public void visit(SeekState state, Set<Long> visited, long previousLoc, Stack<Long> previousLocs) {

        long loc = state.currentLocation();

        visited.add(loc);

        //base case: exit out of the method when the diver is already on the ring
        if(state.distanceToRing() == 0) {
           return;
        }

        for(NodeStatus neighbor : state.neighbors()) {
            if(state.distanceToRing() == 0) {
                return;
            }
            //continuously return diver to the previous location while in a dead end
            if(deadEnd(state.neighbors(),visited)) {
//                if(previousLoc == neighbor.getId()) {
//
//                }
                System.out.println("dsfdgfjnkldh");
                state.moveTo(previousLocs.pop());
                visit(state, visited, previousLoc, previousLocs);
            }
            long neighborId =  neighbor.getId();
            //if the neighbor has not been visited
            if(!visited.contains(neighborId)) {
                state.moveTo(neighborId);
                previousLocs.push(loc);
                visit(state,visited,loc,previousLocs);

            }
        }





//        if(dist != 0) {
//            int shortestDis = Integer.MAX_VALUE;
//            NodeStatus smallestNeighbor = null;
//            for(NodeStatus neighbor : state.neighbors()) {
//                int neighborDis = neighbor.getDistanceToRing();
//                if(neighborDis <= shortestDis) {
//                    shortestDis = neighborDis;
//                    smallestNeighbor = neighbor;
//                }
//
//            }
//            if(!visited.contains(smallestNeighbor.getId())) {
//                state.moveTo(smallestNeighbor.getId());
//                loc = state.currentLocation();
//                dist = state.distanceToRing();
//                visited.add(loc);
//                visit(dist, loc, state, visited);
//            }
//
//        }
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

    public void dfsWalk(SeekState state, Stack<Long> visited, Set<Long> forbidden) {
        //ArrayList<Long> visited = new ArrayList<Long>();
        boolean noMore = true;
        long u = state.currentLocation();
        visited.push(u);
        int distance = state.distanceToRing();
        if (distance == 0) {
            return;
        }
        for (NodeStatus neighbor : state.neighbors()) {
            if (!visited.contains(neighbor.getId()) && !forbidden.contains(neighbor.getId())) {
                noMore = false;
                state.moveTo(neighbor.getId());
                dfsWalk(state, visited, forbidden);
            }
        }
        if (noMore) {
            state.moveTo(visited.pop());
            forbidden.add(u);
            dfsWalk(state, visited, forbidden);
        }

    }

    /** See {@code SewerDriver} for specification. */
    @Override
    public void scram(ScramState state) {
        // TODO: Get out of the sewer system before the steps are used up.
        // DO NOT WRITE ALL THE CODE HERE. Instead, write your method elsewhere,
        // with a good specification, and call it from this one.
        //bar(state);
    }

    public void bar(ScramState state) {
        SlowPQueue frontier = new SlowPQueue();
        Node bestNeighbor = null;
        for (Node neighbors : state.currentNode().getNeighbors()) {
//            if neighbors.
        }
        //Collection<Node> allNodes = state.allNodes();
        Maze maze = new Maze(state.currentNode().getNeighbors());

        ShortestPaths s = new ShortestPaths(maze);
        while (!state.currentNode().equals(state.exit())) {
            s.singleSourceDistances(state.currentNode());
            List<Edge> paths = s.bestPath(state.currentNode());
            for (Edge e : paths) {
                state.moveTo(maze.dest(e));
            }
        }
    }

}
