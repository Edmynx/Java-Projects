public class GraphLibTest {
    public static void main(String[] args) {
        Graph<String, String> relationships = new AdjacencyMapGraph<String, String>();

        relationships.insertVertex("A");
        relationships.insertVertex("B");
        relationships.insertVertex("C");
        relationships.insertVertex("D");
        relationships.insertVertex("E");
        relationships.insertDirected("A", "B", "map");
        relationships.insertDirected("A", "C", "map");
        relationships.insertDirected("A", "D", "map");
        relationships.insertDirected("A", "E", "map");
        relationships.insertUndirected("B", "A", "map");
        relationships.insertDirected("B", "C", "map"); // not symmetric!
        relationships.insertDirected("C", "A", "map");
        relationships.insertDirected("C", "B", "map");
        relationships.insertDirected("C", "D", "map");
        relationships.insertDirected("E", "B", "map");
        relationships.insertDirected("E", "C", "map");

        System.out.println("The graph:");
        System.out.println(relationships);

        System.out.println(GraphLib.randomWalk(relationships, "A", 5));

        System.out.println("indegree: " + GraphLib.verticesByInDegree(relationships));
        System.out.println("A " + relationships.inDegree("A"));
        System.out.println("B " + relationships.inDegree("B"));
        System.out.println("C " + relationships.inDegree("C"));
        System.out.println("D " + relationships.inDegree("D"));
        System.out.println("E " + relationships.inDegree("E"));

    }
}
