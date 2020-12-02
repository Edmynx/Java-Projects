import java.io.*;

public class FileCompressionDecompression {

    // compresses a given file and writes it out in another file
    public static void compressFile(String InputFilename, String compressedFilename) throws IOException {

        HuffmanCode huffmanCode = new HuffmanCode(InputFilename);
        huffmanCode.runCode();

        BufferedReader input = new BufferedReader(new FileReader(InputFilename));

        BufferedBitWriter bitOutput = new BufferedBitWriter(compressedFilename);

        int characterInt;
        while ((characterInt = input.read()) != -1) {
            Character characterChar = (char) characterInt;

            String codeWord =  huffmanCode.getCodeMap().get(characterChar);
            for (int i = 0; i < codeWord.length(); i++) {
                char character = codeWord.charAt(i);
                if (character == '0'){
                    bitOutput.writeBit(false);
                }

                else {
                    bitOutput.writeBit(true);
                }
            }
        }

        bitOutput.close();
        input.close();

        BufferedReader in = new BufferedReader(new FileReader(compressedFilename));
        String str = "", line;
        while ((line = in.readLine()) != null) str += line;
        in.close();
        System.out.println(str);
    }

    // decompresses a given file and writes it out in another file
    public static void decompressFile(String compressedFilename, String OutputFilename) throws IOException {
        HuffmanCode huffmanCode = new HuffmanCode(compressedFilename);
        huffmanCode.runCode();

        BufferedWriter output = new BufferedWriter(new FileWriter(OutputFilename));

        BufferedBitReader bitInput = new BufferedBitReader(compressedFilename);

        BinTree<CharacterNode> tree = huffmanCode.getHuffmanTree();
        while (bitInput.hasNext()) {
            boolean bit = bitInput.readBit();

            if (bit) {
                tree = tree.getRight();
            }

            else {
                tree = tree.getLeft();
            }

            if (tree.isLeaf()) {
                output.write(tree.getData().getCharacter());
                tree = huffmanCode.getHuffmanTree();
            }
        }

        bitInput.close();
        output.close();
    }

    public static void main(String[] args) throws IOException {

        BufferedWriter outputFile = new BufferedWriter(new FileWriter("inputs/tryText.txt"));

        String word = "Hello";
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            outputFile.write(character);
        }

        outputFile.close();

        HuffmanCode huffmanCode = new HuffmanCode("inputs/tryText.txt");
        huffmanCode.runCode();
        System.out.println(huffmanCode.getFrequencyTable());

        huffmanCode.getHuffmanTree().traverse();

        compressFile("inputs/tryText.txt", "outputs/compressedTryText.txt");
        decompressFile("outputs/compressedTryText.txt", "inputs/decompressedTryText.txt");


        compressFile("inputs/text.txt", "outputs/compressedText.txt");
        decompressFile("outputs/compressedText.txt", "inputs/decompressedText.txt");

        compressFile("inputs/UsConstitution.txt", "outputs/compressedUSConstitution.txt");
        decompressFile("outputs/compressedUSConstitution.txt", "inputs/decompressedUSConstitution.txt");


        compressFile("inputs/WarAndPeace.txt", "outputs/compressedTWarAndPeace.txt");
        decompressFile("outputs/compressedWarAndPeace.txt", "inputs/decompressedWarAndPeace.txt");
    }
}
