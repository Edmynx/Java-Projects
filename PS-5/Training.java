import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Training module to train a given set of data
 * Makes a pass through the data just to count
 * the number of times each transition and observation is seen
 * It then goes over all the states, normalizing each state's counts
 * to probabilities (divide by the total for the state)
 *
 * @author Edmund Aduse Poku, Dartmouth CS 10, Fall 2019
 */

public class Training {
    private Map<String, Integer> partOfSpeechFreq;
    private Map<String, Map<String,Integer>> transitionsFreq;
    private Map<String, Map<String,Integer>> observationsFreq;

    private Map<String, Map<String, Double>> transitionsProb;
    private Map<String, Map<String, Double>> observationsProb;

    public Training() {
        partOfSpeechFreq = new HashMap<>();  // instance variable to hold states and their frequencies
        transitionsFreq = new HashMap<>();   // instance variable to hold transitions and their frequencies
        observationsFreq = new HashMap<>();  // instance variable to hold observations and their frequencies

        transitionsProb = new HashMap<>();   // instance variable to hold transitions and their probabilities
        observationsProb = new HashMap<>();  // instance variable to hold observations and their probabilities
    }

    public Map<String, Integer> getPartOfSpeechFreq() {
        return partOfSpeechFreq;
    }

    public Map<String, Map<String, Integer>> getTransitionsFreq() {
        return transitionsFreq;
    }

    public Map<String, Map<String, Integer>> getObservationsFreq() {
        return observationsFreq;
    }

    public Map<String, Map<String, Double>> getTransitionsProb() {
        return transitionsProb;
    }

    public Map<String, Map<String, Double>> getObservationsProb() {
        return observationsProb;
    }

    //helper method for train data method
    //finds the frequencies of observations and transitions
    //@ param tagsFile: filename of tagFile
    //@ param sentencesFilename: filename of sentencesFile
    protected void trainDataFreqs(String tagsFilename, String sentencesFilename) throws IOException {
        BufferedReader inputOne = new BufferedReader(new FileReader(tagsFilename));
        BufferedReader inputTwo = new BufferedReader(new FileReader(sentencesFilename));

        String lineOne, lineTwo;
        int lineNum = 0;

        while ((lineOne = inputOne.readLine()) != null) {
            lineTwo = inputTwo.readLine();

            String line = "";
            String[] piecesOne = lineOne.split("[ ]+");
            for (String piece : piecesOne) line += piece + " ";
            System.out.println(line);

            line = "";
            String[] piecesTwo = lineTwo.split("[ ]+");
            for (String piece : piecesTwo) line += piece + " ";
            System.out.println(line);

            lineNum ++;

            assert piecesOne.length == piecesTwo.length : "Lines " + lineNum + " for tagsText and sentencesText don't have the same number of words";
            System.out.println("Number of words: " + piecesOne.length + "\n");

            for (int i = 0; i < piecesOne.length; i++) {
                if (partOfSpeechFreq.containsKey(piecesOne[i])) {
                    partOfSpeechFreq.put(piecesOne[i], partOfSpeechFreq.get(piecesOne[i]) + 1);
                }

                else partOfSpeechFreq.put(piecesOne[i], 1);

                if (!transitionsFreq.containsKey(piecesOne[i])) transitionsFreq.put(piecesOne[i], new HashMap<>());

                if (i < piecesOne.length - 1) {
                    if (transitionsFreq.get(piecesOne[i]).containsKey(piecesOne[i + 1])) {
                            transitionsFreq.get(piecesOne[i]).put(piecesOne[i + 1], transitionsFreq.get(piecesOne[i]).get(piecesOne[i + 1]) + 1);
                    }

                    else transitionsFreq.get(piecesOne[i]).put(piecesOne[i + 1], 1);
                }

                if (!observationsFreq.containsKey(piecesOne[i])) observationsFreq.put(piecesOne[i], new HashMap<>());

                String piece = piecesTwo[i].toLowerCase();
                if (observationsFreq.get(piecesOne[i]).containsKey(piece)) {
                    observationsFreq.get(piecesOne[i]).put(piece, observationsFreq.get(piecesOne[i]).get(piece) + 1);
                }

                else observationsFreq.get(piecesOne[i]).put(piece, 1);
            }
        }

        inputOne.close();
        inputTwo.close();
    }

    // helper method for train data method
    // finds the probabilities of the observations and transitions
    protected void trainDataProbs () {
        for (String speechPart : partOfSpeechFreq.keySet()) {
            if (!transitionsProb.containsKey(speechPart)) transitionsProb.put(speechPart, new HashMap<>());

            System.out.println("Part of speech: " + speechPart + "\n");

            for (String transition : transitionsFreq.get(speechPart).keySet()) {

                System.out.println("\tTransition: " + transition);
                System.out.println("\t\tNumber of states: " + partOfSpeechFreq.get(speechPart));
                System.out.println("\t\tNumber of transitions: " + transitionsFreq.get(speechPart).get(transition));

                double value = ((double) transitionsFreq.get(speechPart).get(transition)) / partOfSpeechFreq.get(speechPart);

                System.out.println("\t\tValue: " + value + "\n");

                transitionsProb.get(speechPart).put(transition, Math.log(value));
            }

            if (!observationsProb.containsKey(speechPart)) observationsProb.put(speechPart, new HashMap<>());

            for (String observation : observationsFreq.get(speechPart).keySet()) {
                System.out.println("\tObservation: " + observation);
                System.out.println("\t\tNumber of states: " + partOfSpeechFreq.get(speechPart));
                System.out.println("\t\tNumber of observations: " + observationsFreq.get(speechPart).get(observation));

                double value = ((double) observationsFreq.get(speechPart).get(observation)) / partOfSpeechFreq.get(speechPart);

                System.out.println("\t\tValue: " + value + "\n");

                observationsProb.get(speechPart).put(observation, Math.log(value));
            }
        }

    }

    // method to train a model (observation and transition probabilities)
    protected void trainData(String tagsFilename, String sentencesFilename) throws IOException {
        trainDataFreqs(tagsFilename, sentencesFilename);
        trainDataProbs();
    }

    public static void main(String[] args) throws IOException {
        Training training = new Training();
        training.trainData("inputs/texts/simple-train-tags.txt", "inputs/texts/simple-train-sentences.txt" );

        // Test for correctness
        System.out.println(training.partOfSpeechFreq + "\n");
        System.out.println(training.transitionsFreq + "\n");
        System.out.println(training.observationsFreq + "\n");
        System.out.println(training.transitionsProb + "\n");
        System.out.println(training.observationsProb + "\n");
    }
}
