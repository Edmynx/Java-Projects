import java.util.*;

public class GraphLibrary<V, E> {



    /**
         * Takes a random walk from a vertex, up to a given number of steps
         * So a 0-step path only includes start, while a 1-step path includes start and one of its out-neighbors,
         * and a 2-step path includes start, an out-neighbor, and one of the out-neighbor's out-neighbors
         * Stops earlier if no step can be taken (i.e., reach a vertex with no out-edge)
         * @param g		graph to walk on
         * @param start	initial vertex (assumed to be in graph)
         * @param steps	max number of steps
         * @return		a list of vertices starting with start, each with an edge to the sequentially next in the list;
         * 			    null if start isn't in graph
         */
        public static <V,E> List<V> randomWalk(Graph<V,E> g, V start, int steps) {
            // TODO: your code here
            List<V> path = new ArrayList<>();
            if (start == null){
                return path;
            }

            V current = start;
            for (int i = 0; i < steps; i++){
                path.add(current);
                Iterable<V> outNeighbors = g.outNeighbors(current);

                if (((Set<V>) outNeighbors).isEmpty()) {
                    return path;
                }

                Iterator<V> iterator = outNeighbors.iterator();
                for (int j = 0; j <= ((int) (Math.random() * ((Set<V>) outNeighbors).size())); j++) {
                    current = iterator.next();
                }
            }

            return path;
        }

        /**
         * Orders vertices in decreasing order by their in-degree
         * @param g		graph
         * @return		list of vertices sorted by in-degree, decreasing (i.e., largest at index 0)
         */
        public static <V,E> List<V> verticesByInDegree(Graph<V,E> g) {
            // TODO: your code here
            List<V> verticesByInDegree = new ArrayList<>();
            for (V v : g.vertices()){
                verticesByInDegree.add(v);
            }
            verticesByInDegree.sort(new Comparator<V>() {
                public int compare(V u, V v) {
                    return ((Integer) g.inDegree(v)).compareTo(g.inDegree(u));
                }
            });

            return verticesByInDegree;
        }

    public static <V,E> Graph<V,E> bfs(Graph<V,E> g, V source) {
        System.out.println("\nBreadth First Search from " + source);
        AdjacencyMapGraph<V, E> pathTree = new AdjacencyMapGraph<>(); //initialize pathtree
        pathTree.insertVertex(source);
        Set<V> visited = new HashSet<V>(); //Set to track which vertices have already been visited
        Queue<V> queue = new LinkedList<V>(); //queue to implement BFS

        queue.add(source); //enqueue start vertex
        visited.add(source); //add start to visited Set
        while (!queue.isEmpty()) { //loop until no more vertices
            V u = queue.remove(); //dequeue
            for (V v : g.outNeighbors(u)) { //loop over out neighbors
                if (!visited.contains(v)) { //if neighbor not visited, then neighbor is discovered from this vertex
                    visited.add(v); //add neighbor to visited Set
                    queue.add(v); //enqueue neighbor

                    //save that this vertex was discovered from prior vertex
                    pathTree.insertVertex(v);
                    pathTree.insertDirected(v, u, g.getLabel(v, u));
                }
            }
        }

        return pathTree;
    }

    /**
     * Find a path from start vertex to end vertex. Start at end vertex and work backward using
     * pathTree to start vertex.
     * @param tree -- given graph
     * @param v -- ending vertex
     * @return arraylist of nodes on path from start to end, empty if no path
     */
    public static <V,E> List<V> getPath(Graph<V,E> tree, V v) {
        //make sure v is in the graph
        if (!tree.hasVertex(v)) {
            System.out.println("Cannot determine starting point");
            return new ArrayList<>();
        }

        //start from vertex v and work backward to the root vertex
        ArrayList<V> path = new ArrayList<>(); //this will hold the path from v to root
        V current = v; //start at v
        //loop from end vertex back to start vertex
        while (!((Set<V>)tree.outNeighbors(current)).isEmpty()) {
            path.add(current); //add this vertex to arraylist path
            Set<V> currentNeighbors = (Set<V>) tree.outNeighbors(current);
            //get vertex that discovered this vertex
            for (V neighbor : currentNeighbors) current = neighbor;
        }

        System.out.println(path);
        return path;
    }

    public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph) {
        Set<V> missingVertices = new HashSet<>();
        for (V vertex : graph.vertices()){
            if (!((Set)subgraph.vertices()).contains(vertex)) missingVertices.add(vertex);
        }

        return missingVertices;
    }

    public static <V,E> double averageSeparation(Graph<V,E> tree, V root) {
        double averageSeparation;

        return 0;

    }

}


