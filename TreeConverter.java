import java.util.*;
//(ROOT (S (NP (NNP Ms.) (NNP Haag)) (VP (VBZ plays) (NP (NNP Elianti))) (. .)))
//(ROOT (S (NP (NNP Ms.) (@NP->_NNP (NNP Haag))) (@S->_NP (VP (VBZ plays) (@VP->_VBZ (NP (NNP Elianti)))) (@S->_NP_VP (. .)))))

//   (NP (NNP Rolls-Royce) (@NP-> NNP (NNP Motor) (@NP-> NNP NNP (NNPS
//   Cars) (@NP-> NNP NNP NNPS (NNP Inc.)))))

public class TreeConverter{

    private static HashMap<Integer, Integer> lengthMap = new HashMap<Integer, Integer>();
    private static HashMap<Integer, ArrayList<Integer>> ancestorMap = new HashMap<Integer, ArrayList<Integer>>();
    private static HashMap<Integer, ArrayList<Integer>> siblingMap = new HashMap<Integer, ArrayList<Integer>>();
    
    public ArrayList<String> convert(ArrayList<String> input){
        String[] splitList = new String[0];
        ArrayList<String> convertedStringArray =  new ArrayList<String>();
        String convertedString = "";
        System.out.println("Starting converting the file");
     
        for(String sentence : input){
            if(!sentence.isEmpty()){
                System.out.println();
                System.out.println("Original sentence: " + sentence);
                splitList = sentence.split(" ");
                for (int i=0;i<splitList.length;i++) {
                    lengthMap.put(i, findLength(splitList, i));
                }
                convertedString = annotateSentence(splitList, 2, 2);
                System.out.println("Annotated sentence: " + convertedString);
                convertedStringArray.add(convertedString);
                lengthMap.clear();
                siblingMap.clear();
            }
            else {
                convertedStringArray.add("");
            }
        }
        System.out.println();
        return convertedStringArray;
    }
    
    public static ArrayList<Integer> findSibling(String[] sentence, int where){
        ArrayList<Integer> siblingList = new ArrayList<Integer>();
        for (int i=where;i< where+lengthMap.get(where);i++){
           if( lengthMap.get(i) > 1 && i > 1 ){
               if (sentence[i-1].contains("(")) {
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
    
    public static String annotateSentence(String[] splitList, int hOrder, int vOrder) {
        HashMap<Integer, ArrayList<Integer>> ancestorMap = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(0);
        ancestorMap.put(1, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(0);
        tmpArrayList.add(1);
        ancestorMap.put(2, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(0);
        tmpArrayList.add(1);
        tmpArrayList.add(2);
        ancestorMap.put(3, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(0);
        tmpArrayList.add(1);
        tmpArrayList.add(2);
        ancestorMap.put(5, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(0);
        tmpArrayList.add(1);
        ancestorMap.put(7, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(0);
        tmpArrayList.add(1);
        tmpArrayList.add(7);
        ancestorMap.put(8, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(0);
        tmpArrayList.add(1);
        tmpArrayList.add(7);
        ancestorMap.put(10, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(0);
        tmpArrayList.add(1);
        tmpArrayList.add(7);
        tmpArrayList.add(10);
        ancestorMap.put(11, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(0);
        tmpArrayList.add(1);
        ancestorMap.put(13, tmpArrayList);
        
        HashMap<Integer, ArrayList<Integer>> siblingMap = new HashMap<Integer, ArrayList<Integer>>();
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(3);
        siblingMap.put(5, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(2);
        siblingMap.put(7, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(8);
        siblingMap.put(10, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        tmpArrayList.add(2);
        tmpArrayList.add(7);
        siblingMap.put(13, tmpArrayList);
        tmpArrayList = new ArrayList<Integer>();
        
        ArrayList<String> newSentenceArray = new ArrayList<String>();
        String currentSentencePart = "";
        String tagPart = "";
        for(int i = 0; i < splitList.length; i++) {
            currentSentencePart += "(";   
            if(siblingMap.get(i) != null) { 
                currentSentencePart += "@";   
            }
            tagPart = splitList[i].replaceAll("[()]", "");
            currentSentencePart += tagPart;
            if(ancestorMap.get(i) != null) {
                currentSentencePart += "^";
                for(int ancestor :  ancestorMap.get(i)) {
                    currentSentencePart += splitList[ancestor].replaceAll("[()]", "");
                    if(ancestorMap.get(i).size() > 1) {
                        currentSentencePart += "^";
                    }
                }
                if (currentSentencePart.length() > 0 && currentSentencePart.charAt(currentSentencePart.length()-1)=='^') {
                    currentSentencePart = currentSentencePart.substring(0, currentSentencePart.length()-1);
                }
            }
            if(siblingMap.get(i) != null) {
                currentSentencePart += "->";
                for(int sibling : siblingMap.get(i)) {
                    currentSentencePart += splitList[sibling].replaceAll("[()]", "");
                    if(ancestorMap.get(i).size() > 1) {
                        currentSentencePart += "_";
                    }
                }
                if (currentSentencePart.length() > 0 && currentSentencePart.charAt(currentSentencePart.length()-1)=='_') {
                    currentSentencePart = currentSentencePart.substring(0, currentSentencePart.length()-1);
                }
                int closingBracketInt = ancestorMap.get(i).get(ancestorMap.get(i).size()-1);
            }
            newSentenceArray.add(currentSentencePart);
            currentSentencePart = "";
        }
        
        for(int i = 0; i < newSentenceArray.size(); i++) {
            if(lengthMap.get(i) == 1) { 
                String bracketString = splitList[i];
                if(siblingMap.get(i) != null) {
                    for(int j = 0; j < siblingMap.get(i).size(); j++) {
                        bracketString += ')';
                    }
                    newSentenceArray.set(i, bracketString);
                }
            }
        }
        String newSentence = "";

        for (String s : newSentenceArray) {
            newSentence += s + " ";
        }
        return newSentence;
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