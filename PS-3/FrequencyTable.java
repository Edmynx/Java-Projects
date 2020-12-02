import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class FrequencyTable {
    private static Map<Character, Integer> characters;

    public static Map<Character, Integer> createFrequencyTable(String filename) throws IOException {
        characters = new HashMap<>();

        BufferedReader input = new BufferedReader(new FileReader(filename));
        int characterInt;
        while ((characterInt = input.read()) != -1){
            Character characterChar = (char) characterInt;

            // Check to see if we have seen this character before, update characters appropriately
            if (characters.containsKey(characterChar)){
                // Have seen this character before, increment the count
                characters.put(characterChar, characters.get(characterChar) + 1);
            }

            else{
                // Have not seen this character before, add the new character
                characters.put(characterChar, 1);
            }
        }

        return characters;
    }

    public static void main(String[] args) throws Exception {
        BufferedWriter outputFile = new BufferedWriter(new FileWriter("inputs/tryText.txt"));

        String word = "Hello";
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            outputFile.write(character);
        }
        outputFile.close();

        Map<Character, Integer> characters1 = createFrequencyTable("inputs/tryText.txt");
        System.out.println(characters1);


        Map<Character, Integer> characters2 = createFrequencyTable("inputs/text.txt");
        System.out.println(characters2);

        for (int i = 0; i < 256; i++ ){
            if (characters.containsKey( (char) i)) {
                System.out.println((char) i + " = " + characters.get((char) i));
            }
        }
    }
}
