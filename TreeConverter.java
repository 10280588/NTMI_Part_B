import java.util.*;
//(ROOT (S (NP (NNP Ms.) (NNP Haag)) (VP (VBZ plays) (NP (NNP Elianti))) (. .)))
//(ROOT (S (NP (NNP Ms.) (@NP->_NNP (NNP Haag))) (@S->_NP (VP (VBZ plays) (@VP->_VBZ (NP (NNP Elianti)))) (@S->_NP_VP (. .)))))

//   (NP (NNP Rolls-Royce) (@NP-> NNP (NNP Motor) (@NP-> NNP NNP (NNPS
//   Cars) (@NP-> NNP NNP NNPS (NNP Inc.)))))

public class TreeConverter{

    private static HashMap<Integer, Integer> lengthMap = new HashMap<Integer, Integer>();
    private static HashMap<Integer, ArrayList<Integer>> ancestorMap = new HashMap<Integer, ArrayList<Integer>>();
    private static HashMap<Integer, ArrayList<Integer>> siblingMap = new HashMap<Integer, ArrayList<Integer>>();
    
    public String convert(ArrayList<String> input){
        String[] splitList = new String[0];

        String convertedString = "";
        System.out.println("Starting converting the file");
     
        for(String sentence : input){
            if(!sentence.isEmpty()){
                System.out.println("Original sentence: " + sentence);
                splitList = sentence.split(" ");
                for (int i=0;i<splitList.length;i++) {
                    lengthMap.put(i, findLength(splitList, i));
                }
                System.out.println("The length of all items:");
                System.out.println(lengthMap.values());
                for (int i=0;i<splitList.length;i++) {
                    siblingMap.put(i, findSibling(splitList, i)); 
                }
                System.out.println(Arrays.toString(splitList));
                System.out.println();
                lengthMap.clear();
                siblingMap.clear();
            }
        }

        return convertedString;
    }
    
    public static ArrayList<Integer> findSibling(String[] sentence, int where){
        ArrayList<Integer> siblingList = new ArrayList<Integer>();
        for (int i=where;i< where+lengthMap.get(where);i++){
           if( lengthMap.get(i) > 1 && i > 1 ){
               if (sentence[i-1].contains("(")) {
               System.out.printf("Found oldestchild at: %d \n", i);
               siblingList.add(1);
            }
           }
        }
        return siblingList;
    }
    public static int findLength(String[] sentence, int where){
        int openCounter = 0;
        int numbOfClose;
        int branchLength = 0;
        for(int i = where; i < sentence.length; i++) {
            if (sentence[i].contains("(")) {
                openCounter++;
                branchLength++;
            } else if (sentence[i].contains(")")) {
                branchLength++;
                numbOfClose = sentence[i].length() - sentence[i].replace(")", "").length();
                openCounter-=numbOfClose;
            }
            if (openCounter <= 0) {
                return branchLength;
            }
        }
        return 0;
    }
}
/*
    public static int mapper(String[] sentence){
        int value = 0;
        System.out.println("mapping");
        System.out.println(map.get(12));
        for (int i=0;i<map.size();i++) {
            value = map.get(i);
            System.out.println("intrest:");
            System.out.println(value);
            if(value > 2){
            //    for(int j = 0; j < i; ){
                System.out.println("SUPERINTRESTING:");
                int secondChild = findSecondChild(i);
            //    j = secondChild;
                //TODO add tag to secondchild
            //    }
            }
            //key = i;
            //value = branchLength(splitList, i);
            //map.put(i, value);
        }
        return 5;
    }

    public static int findSecondChild(int i){
        int parent = i;
        int secondChild = 0;
        System.out.println(i);

        secondChild = i + map.get(i+1) +1;
        System.out.println(map.size());
        if (secondChild >= map.size() && secondChild > 1 ){
            secondChild = 0;
        }
        System.out.printf("Secondo needs a tag: %d \n", secondChild);

        return secondChild;
    }
}

*/