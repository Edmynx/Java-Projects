import java.io.*;
import java.util.*;

/**
 * ViterbiTagging class
 * Takes in training data sets and trains the data
 * Uses train data to tag a given set of data via its viterbi tagging method
 *
 * @author Edmund Aduse Poku, Dartmouth CS 10, Fall 2019
 */

public class ViterbiTagging {
    private Training training; //instance variable to hold training object

    private static final double OBSERVATION_SCORE = -2000;  // assigned observation score for unseen observations

    public ViterbiTagging(String tagsTrainingText, String sentencesTrainingText) throws IOException {
        training = new Training(); //create new training object
        training.trainData(tagsTrainingText, sentencesTrainingText);  //train the module with given data
    }

    // method to test for correctness on console
    //prints result to console
    public void test1(List<List<String>> list) {
        String line = "";
        for (List<String> l : list) {
            for (String string : l) line += string + " ";
            line += "\n";
        }

        System.out.println("\nTest 1:\n" + line);
    }

    // file-based method to test for correctness
    public void test2(List<List<String>> list, String outputFilename) throws IOException {
        BufferedWriter output = new BufferedWriter(new FileWriter(outputFilename));
        for (List<String> l : list) {
            for (String string : l) {
                output.write(string + " ");
            }
            output.write("\n");
        }

        output.close();
    }

    // method to perform Viterbi decoding to
    // find the best sequence of tags for a line (sequence of words)
    public void viterbiTag(String sentencesTestText, String outputFilename) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(sentencesTestText));
        String line;

        List<List<String>> paths = new ArrayList<>();

        while ((line = input.readLine()) != null) {
            Set<String> currentStates = new HashSet<>();

            // start at the start state, before any observations
            currentStates.add("#Start#");

            Map<String, Double> currentProbs = new HashMap<>();
            currentProbs.put("#Start#", 0.0);

            Map<Integer, Map<String,String>> predecessors = new HashMap<>();

            String[] pieces = line.split("[ ]+");

            String l1 = "";

            // take one step at a time, advancing from each state we could be in now
            for (int i = 0; i < pieces.length; i++) {
                l1 += pieces[i] + " ";

                Set<String> nextStates = new HashSet<>();
                Map<String, Double> nextProbs = new HashMap<>();

                Set<String> nextKeySet;

                for (String currentState : currentStates) {
                    if (currentState.equals("#Start#")) nextKeySet = training.getTransitionsProb().keySet();

                    else nextKeySet = training.getTransitionsProb().get(currentState).keySet();

                    System.out.println("\t\t'" + currentState + "' : " + nextKeySet);

                    for (String nextState : nextKeySet) {
                        nextStates.add(nextState);

                        double observationScore;
                        if (training.getObservationsProb().get(nextState).containsKey(pieces[i])) {
                            observationScore = training.getObservationsProb().get(nextState).get(pieces[i]);
                        }

                        else observationScore = OBSERVATION_SCORE;

                        double nextScore;
                        if (currentState.equals("#Start#")) nextScore = observationScore;

                        else nextScore = currentProbs.get(currentState) +
                                training.getTransitionsProb().get(currentState).get(nextState) +
                                observationScore;

                        if (!nextProbs.containsKey(nextState) || nextScore > nextProbs.get(nextState)) {
                            nextProbs.put(nextState, nextScore);
                            System.out.println("Current: " + currentState);
                            if (!predecessors.containsKey(i)) predecessors.put(i, new HashMap<>());

                            predecessors.get(i).put(nextState, currentState);

                        }
                    }

                    System.out.println("\t\tNext states: " + nextStates + "\n");
                }

                currentStates = nextStates;
                currentProbs = nextProbs;
            }

            System.out.println(l1 + "\n");

            System.out.println("Predecessors: " + predecessors);

            List<String> path = new ArrayList<>();  // arraylist for path
            String key = "";


            double maxValue = Collections.max(currentProbs.values());
            System.out.println(maxValue);

            for (String key1 : currentProbs.keySet()) {
                if (currentProbs.get(key1) == maxValue) key = key1;
            }

            System.out.println("largest key: " + key);


            // The backtrace starts from the state with the best score
            // for the last observation and works back to the start state
            path.add(key);
            String state = predecessors.get(pieces.length - 1).get(key);

            for (int j = pieces.length - 2; j >= 0; j--) {
                path.add(0, state);
                state = predecessors.get(j).get(state);
            }

            paths.add(path);

        }
        input.close();

        test1(paths);
        test2(paths, outputFilename);
    }

    public static void main(String[] args) throws IOException {
        // Test using simple test files
        ViterbiTagging tagging = new ViterbiTagging("inputs/texts/simple-train-tags.txt", "inputs/texts/simple-train-sentences.txt");
        tagging.viterbiTag("inputs/texts/simple-test-sentences.txt", "inputs/texts/simpleTagResults.txt");

        // Test using Brown files
        ViterbiTagging tagging1 = new ViterbiTagging("inputs/texts/brown-train-tags.txt", "inputs/texts/brown-train-sentences.txt");
        tagging1.viterbiTag("inputs/texts/brown-test-sentences.txt", "inputs/texts/brownTagResults.txt");

        // Test using generated files
        BufferedWriter output1 = new BufferedWriter(new FileWriter("inputs/texts/myFile1"));
        output1.write("This is Java . \nI like Java . \n Java is an Object-Oriented Programming Language . \nIt is similare to C++ and Python .");
        output1.close();
        tagging1.viterbiTag("inputs/texts/myFile1", "inputs/texts/tagResultsMyFile1.txt");

        BufferedWriter output2 = new BufferedWriter(new FileWriter("inputs/texts/myFile2"));
        output2.write("I kind of like vacations . \nEveryone likes vacations . \n Vacations help release stress . \n No vacation is better than Christmas holidays .");
        output2.close();
        tagging1.viterbiTag("inputs/texts/myFile2", "inputs/texts/tagResultsMyFile2.txt");

        // Console-based test
        Scanner input = new Scanner(System.in);
        while (true) {
            String filePath = input.nextLine();
            tagging1.viterbiTag(filePath, "inputs/texts/consoleTagResults");
        }

    }
}

