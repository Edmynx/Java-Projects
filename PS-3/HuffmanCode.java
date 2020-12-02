import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.*;
import java.util.Set;
import java.util.PriorityQueue;

public class HuffmanCode {
    private PriorityQueue<BinTree<CharacterNode>> priorityQueue;  // priority key for huffmanCode
    private Map<Character, Integer> frequencyTable;   // holds frequency table for characters
    private BinTree<CharacterNode> huffmanTree;       // holds the final tree version used for coding
    private Map<Character,String> codeMap;            // stores each character and its codeWord

    // provide access to codeMap
    public Map<Character, String> getCodeMap() {
        return codeMap;
    }

    // provide access to huffmanTree
    public BinTree<CharacterNode> getHuffmanTree() {
        return huffmanTree;
    }

    public Map<Character, Integer> getFrequencyTable() {
        return frequencyTable;
    }

    public PriorityQueue<BinTree<CharacterNode>> getPriorityQueue() {
        return priorityQueue;
    }

    // create a huffman code object
    // and a frequency table for the characters in the given file
    public HuffmanCode(String filename) throws IOException {
        frequencyTable = FrequencyTable.createFrequencyTable(filename);
    }

    // form the priority queue for the code based on the frequency table
    private void formQueue() {
        priorityQueue = new PriorityQueue<>(frequencyTable.size(), new TreeComparator());

        Set<Entry<Character, Integer>> entrySet = frequencyTable.entrySet();
        for (Entry<Character, Integer> entry : entrySet) {
            priorityQueue.add(new BinTree(new CharacterNode(entry.getKey(), entry.getValue())));
        }
    }

    //
    private void createTree() {
        while (priorityQueue.size() > 1) {

            // extract the two lowest-frequency trees tree1 and tree2 from the priority queue.
            BinTree<CharacterNode> tree1 = priorityQueue.poll();
            BinTree<CharacterNode> tree2 = priorityQueue.poll();

            // creates a new tree by creating a new root node,
            // attaching tree1 as left subtree of tree, and attaching tree2 as right subtree
            // Which of T1 and T2 is the left or right subtree does not matter
            // assigns to the new tree a frequency that equals the sum of the frequencies of tree1 and tree2
            priorityQueue.add(new BinTree(new CharacterNode(
                    tree1.getData().getFrequency()
                            +  tree2.getData().getFrequency()),
                    tree1, tree2));
        }

        huffmanTree = priorityQueue.poll();
    }

    // Helper for generating code word for each character
    private void codeRetrievalHelper(BinTree<CharacterNode> tree, String pathSoFar) {
        if (tree.hasLeft()) {
            if (tree.getLeft().isLeaf()) {

                // test code word is being formed properly
                System.out.println(tree.getLeft().getData().getCharacter() + " left= " + tree.getRight().getData().getFrequency() );

                // generate codeWord for tree if it is a leaf (which contains a character)
                codeMap.put(tree.getLeft().getData().getCharacter(), pathSoFar + 0);
            }

            else {
                pathSoFar += 0;  // update codeWord appropriately
                codeRetrievalHelper(tree.getLeft(), pathSoFar);
            }
        }

        if (tree.hasRight()) {
            if (tree.getRight().isLeaf()) {

                // test if code word is being formed properly
                System.out.println(tree.getRight().getData().getCharacter() + " }= " + tree.getRight().getData().getFrequency() );

                // generate codeWord for tree if it is a leaf (which contains a character)
                codeMap.put(tree.getRight().getData().getCharacter(), pathSoFar + 1);
            }

            else {

                pathSoFar += 1;  // update codeWord appropriately
                codeRetrievalHelper(tree.getRight(), pathSoFar);
            }
        }
    }

    // generate codeword for each character
    private void codeRetrieval() {
        codeMap = new HashMap<>();
        String pathSoFar = "";   // keep track of code word for where we are in the tree
        codeRetrievalHelper(huffmanTree, pathSoFar);
    }

    // runs HuffmanCode
    public void runCode() {
        formQueue();
        createTree();
        codeRetrieval();
    }

    public static void main(String[] args) throws Exception {

        // Test for HuffmanCode
        BufferedWriter outputFile = new BufferedWriter(new FileWriter("inputs/tryText.txt"));

        String word = "Hello";
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            outputFile.write(character);
        }
        outputFile.close();

        HuffmanCode huffmanCode1 = new HuffmanCode("inputs/tryText.txt");
        huffmanCode1.runCode();

        System.out.println("\t" + huffmanCode1.frequencyTable + "\n");

        System.out.println(huffmanCode1.codeMap);


        HuffmanCode huffmanCode2 = new HuffmanCode("inputs/text.txt");
        huffmanCode2.runCode();


        System.out.println("\t" + huffmanCode2.frequencyTable + "\n");

        System.out.println(huffmanCode2.codeMap);
    }
}
