import java.util.*;

public class MapForPD {
    public static void main(String[] args){
        Map<String, List<String>> myMap = new TreeMap<String, List<String>>();
        myMap.put("Edmund", new ArrayList<>());
        myMap.get("Edmund").add("CS 10");
        System.out.println(myMap);
    }
}
