import java.io.*;
import java.util.*;

public class Bstep1 {


    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {

        // taking care of the arguments
        String input ="";
        String output ="";
        ArrayList<String> list =  new ArrayList<String>();
        if (args.length != 2) {
            System.err.println("The program accepts only exact two arguments, see help");
            System.exit(1);
        }
        else{
            try {
                input = args[0];
            } catch (NumberFormatException e) {
                System.err.println("Argument: " + args[0] + " must be an integer.");
                System.exit(1);
            }
            try {
                output = args[1];
            } catch (NumberFormatException e) {
                System.err.println("Argument: " + args[0] + " must be an integer.");
                System.exit(1);
            }
            System.out.println("This program wil binarize your text file."); // Display the string.
            System.out.printf("The used input file is: %s \n", input); // Display the string.
            System.out.printf("The results will be saved in: %s \n", output); // Display the string.
        }

        // actual steps of the program
        list = reader(input);
        list = converter(list);
        saver(list, output);
        System.out.println("Program is done running.");

    }

    public static ArrayList<String> reader(String file){

        System.out.println("Reading the input file");
        System.out.println(file);
        ArrayList<String> list = new ArrayList<String>();


        BufferedReader br = null;
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(file));

            while ((sCurrentLine = br.readLine()) != null) {
                list.add(sCurrentLine);
                System.out.println(sCurrentLine);
            }

            System.out.println(list);

        } catch (IOException e) {
            e.printStackTrace();

        }

        //int data = reader.read();
        //while(data != -1) {
            //do something with data...
            //doSomethingWithData(data);

            //data = reader.read();
        //}
        //reader.close();

        return list;

    }

    public static void saver(ArrayList<String> input, String file){

        System.out.printf("Saving the results to: %s \n", file);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            for (String sentence : input) {
                pw.write(sentence + "\n");
            }

            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }


    public static ArrayList<String> converter(ArrayList<String> input){

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



    //to do
    //Create Filreader class that reads the input and outputs the
    //Create ConvertBinarized, transformation is done here =)
    //Create FileSave class that saves our transformed text

}
