import java.util.*;

public class TreeConverter{

    //public TreeConverter(ArrayList<String> list){
    //    list = list;
    //}

    public static ArrayList<String> convert(ArrayList<String> input){

        ArrayList<String> convertedList =  input;
        String[] splitList =   new String[0];

        System.out.println("Starting converting the file");

        for(String sentence : input){
            if(!sentence.isEmpty()){
                System.out.println(sentence);
                splitList = sentence.split(" ");
                splitList = addTags(splitList);

                System.out.println(Arrays.toString(splitList));
            }
        }


        System.out.println("Done converting the file");
        return convertedList;
    }

    public static String[] addTags(String[] sentence) {

        int openCounter = 1;
        int numbOfClose = 0;


        for ( int i = 0; i < sentence.length; i++) {
            String part = sentence[i];
            if( part.contains("(")){
                System.out.println();
                System.out.println(part);
                openCounter = 1;
                int branchSize = 1;
                branchSize = calcBranchSize(i, sentence);


                if (branchSize > 2){
                    System.out.println(part);
                    for ( int j = i+1; j < sentence.length; j++) {
                        if ( openCounter > 0){
                            if( sentence[j].contains("(") && openCounter > 0){
                                branchSize += 1;
                                openCounter += 1;
                            }
                            else if( sentence[j].contains(")") && openCounter > 0){
                                numbOfClose = sentence[j].length() - sentence[j].replace(")", "").length() / 1;
                                openCounter = openCounter - numbOfClose;
                                //System.out.printf("The numb of open is close found: %d \n", openCounter);
                            }
                        }
                    }
                }


            }
            else if( part.contains(")")){
                //System.out.println(part);
                //numbOfClose = part.length() - part.replace(")", "").length() / 1;
                //openCounter = openCounter - numbOfClose;
                //System.out.printf("bigger: %d \n", openCounter);
            }
        }
        System.out.println(openCounter);
        return sentence;


    }

    public static int calcBranchSize(int i, String[] sentence){
        int openCounter = 1;
        int branchSize = 1;
        int numbOfClose = 0;
        for ( int j = i+1; j < sentence.length; j++) {
            if ( openCounter > 0){
                if( sentence[j].contains("(") && openCounter > 0){
                    branchSize += 1;
                    openCounter += 1;
                }
                else if( sentence[j].contains(")") && openCounter > 0){
                    numbOfClose = sentence[j].length() - sentence[j].replace(")", "").length() / 1;
                    openCounter = openCounter - numbOfClose;
                    //System.out.printf("The numb of open is close found: %d \n", openCounter);
                }
            }
        }
        System.out.printf("The length of the tree is: %d \n", branchSize);
        return branchSize;
    }

}
