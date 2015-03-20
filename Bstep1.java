import java.io.*;
import java.util.*;

//TODO
// make converter work!
// add comments
// check indenting
// make report

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
        TreeConverter converter = new TreeConverter();
        ArrayList<String> convertedStringArray = converter.convert(list);
        saver(convertedStringArray, output);
        System.out.println("Program is done running.");
    }

    @SuppressWarnings("resource")
	public static ArrayList<String> reader(String file){

        System.out.println("Reading the input file: " + file);
        ArrayList<String> list = new ArrayList<String>();

        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(file));

            while ((sCurrentLine = br.readLine()) != null) {
                list.add(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void saver(ArrayList<String> input, String file){

        System.out.printf("Saving the results to: %s \n", file);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            for(String sentence : input) {
                pw.write(sentence + "\n");
            }    

            pw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
}
