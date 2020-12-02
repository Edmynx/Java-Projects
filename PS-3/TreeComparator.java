import java.util.Comparator;

public class TreeComparator implements Comparator<BinTree> {

    @Override
    public int compare(BinTree tree1, BinTree tree2) {
        return (((CharacterNode) tree1.getData()).getFrequency().compareTo(((CharacterNode) tree2.getData()).getFrequency()));
    }

    public static void main(String[] args){
        Integer a = 2;
        Integer b = 3;
        Integer c = 2;
        Integer d = 10;

        int comp1 = a.compareTo(b);
        int comp2 = b.compareTo(c);
        int comp3 = a.compareTo(c);
        int comp4 = d.compareTo(b);

        System.out.println(comp1);
        System.out.println(comp2);
        System.out.println(comp3);
        System.out.println(comp4);
    }
}
