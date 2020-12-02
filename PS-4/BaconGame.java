import org.bytedeco.javacv.FrameFilter;

import java.io.*;
import java.util.*;

public class BaconGame {
    private Map<Integer, String> actorIDs;
    private Map<Integer, String> movieIDSs;
    private Map<Integer, Set<Integer>> moviesToActors;
    private AdjacencyMapGraph<String, Set<String>> gameGraph;

    public BaconGame() {
        actorIDs = new HashMap<>();
        movieIDSs = new HashMap<>();
        moviesToActors = new HashMap<>();
        gameGraph = new AdjacencyMapGraph<>();
    }

    public Map<Integer, String> getActorIDs() {
        return actorIDs;
    }

    public Map<Integer, String> getMovieIDSs() {
        return movieIDSs;
    }

    public Map<Integer, Set<Integer>> getMoviesToActors() {
        return moviesToActors;
    }

    public Map<Integer, String> readFileOneInteger(BufferedReader input) throws IOException{
        Map<Integer, String> map = new HashMap<>();

        String line = "";
        while ((line = input.readLine()) != null ) {
            String [] splitLine = line.split("[|]");
            map.put(Integer.parseInt(splitLine[0]), splitLine[1]);
        }

        System.out.println(map);

        return map;
    }

    public Map<Integer, Set<Integer>> readFileAllIntegers(BufferedReader input) throws IOException {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        String line = "";
        while ((line = input.readLine()) != null ){
            String [] splitLine = line.split("[ |]");

            //test
            for (String a : splitLine){System.out.println(a);}

            if (map.containsKey(Integer.parseInt(splitLine[0]))) {
                map.get(Integer.parseInt(splitLine[0])).add(Integer.parseInt(splitLine[1]));
            }

            else {
                Set<Integer> set = new HashSet<>();
                set.add(Integer.parseInt(splitLine[1]));
                map.put(Integer.parseInt(splitLine[0]), set);
            }
        }

        System.out.println(map);

        return map;
    }

    public void readGameFiles(BufferedReader input1, BufferedReader input2, BufferedReader input3) throws IOException {
        actorIDs = readFileOneInteger(input1);
        System.out.println("this is " + actorIDs);
        movieIDSs = readFileOneInteger(input2);
        moviesToActors = readFileAllIntegers(input3);
    }

    public void createGameGraph() {
        for (Integer iD : actorIDs.keySet()) gameGraph.insertVertex(actorIDs.get(iD));
        for (Integer iD : moviesToActors.keySet()) {
            Set<Integer> set = moviesToActors.get(iD);
            for (Integer n : set) {
                for (Integer m : set) {
                    if (!m.equals(n)) {
                        if (gameGraph.hasEdge(actorIDs.get(m), actorIDs.get(n))) {
                            gameGraph.getLabel(actorIDs.get(m), actorIDs.get(n)).add(movieIDSs.get(iD));
                        }

                        else{
                            Set<String> set2 = new HashSet<>();
                            set2.add(movieIDSs.get(iD));
                            gameGraph.insertUndirected(actorIDs.get(m), actorIDs.get(n), set2);
                        }
                    }
                }
            }

        }

        System.out.println(gameGraph);
    }



    public static void main(String[] args) throws IOException {
        BufferedReader input1 = null, input2 = null, input3 = null;
        try {
            System.out.println("input actors, movies, and movies-actors text filepaths");

            Scanner input = new Scanner(System.in);

            String filePath = input.nextLine();
            input1 = new BufferedReader(new FileReader(filePath));

            filePath = input.nextLine();
            input2 = new BufferedReader(new FileReader(filePath));

            filePath = input.nextLine();
            input3 = new BufferedReader(new FileReader(filePath));
        }

        catch (Exception e) {
            System.out.println("One of the filepaths does not exist; Make sure all filepaths are correct to continue");
        }

        BaconGame game = new BaconGame();
        game.readGameFiles(input1, input2, input3);
        game.createGameGraph();

        while (true) {
            Scanner input = new Scanner(System.in);
            String center = input.nextLine();

            Graph<String, Set<String>> shortestPaths = GraphLibrary.bfs(game.gameGraph, center);

            for (String vertex : game.gameGraph.vertices()) {
                if (!vertex.equals(center)) {
                    List<String> list = GraphLibrary.getPath(shortestPaths, vertex);
                    System.out.println("\nplayer: " + vertex + "\n" + vertex + "'s number is " + (list.size()));
                    System.out.println(vertex + " appeared in: ");
                    for (String s : list) {
                        if (!s.equals(vertex)) {
                            System.out.println(game.gameGraph.getLabel(vertex, s) + " with " + vertex);
                        }
                    }
                }
            }
        }
    }
}
