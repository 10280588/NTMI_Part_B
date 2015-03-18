import java.util.*;
//(ROOT (S (NP (NNP Ms.) (NNP Haag)) (VP (VBZ plays) (NP (NNP Elianti))) (. .)))
//(ROOT (S (NP (NNP Ms.) (@NP->_NNP (NNP Haag))) (@S->_NP (VP (VBZ plays) (@VP->_VBZ (NP (NNP Elianti)))) (@S->_NP_VP (. .)))))

//   (NP (NNP Rolls-Royce) (@NP-> NNP (NNP Motor) (@NP-> NNP NNP (NNPS
//   Cars) (@NP-> NNP NNP NNPS (NNP Inc.)))))

public class TreeConverter{

    private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    public String convert(ArrayList<String> input){
        String[] splitList = new String[0];

        String convertedString = "";
        System.out.println("Starting converting the file");
        int key = 0;
        int value = 0;

        for(String sentence : input){
            if(!sentence.isEmpty()){
                System.out.println("Original sentence: " + sentence);
                splitList = sentence.split(" ");
                for (int i=0;i<splitList.length;i++) {
                    key = i;
                    value = branchLength(splitList, i);
                    map.put(i, value);
                }
                System.out.println(Arrays.toString(splitList));
                System.out.println("Done converting the file");
                mapper(splitList);
            }
        }

        return convertedString;
    }

    public static int branchLength(String[] sentence, int where){
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


/*try to make recursive
public static String getTags(String[] sentence,  String[] edit, int where) {

int sentenceLength = branchLength(sentence, where);
System.out.println(sentenceLength);
System.out.println("Recurse it baby!");
if( sentenceLength < 2 ){
return "b";
}
else{
System.out.println(Arrays.toString(sentence));
String s = "";
where = where + 1;
System.out.println(where);
getTags(sentence, edit, where);
}
return "";
}
*/

    /*

    public static ArrayList<ArrayList<String>> addTagsToBranch(ArrayList<ArrayList<String>> branch) {
    	ArrayList<ArrayList<String>> newBranch = new ArrayList<ArrayList<String>>();
    	String currentEditedPart = "(@";
    	String tagPart;

    	if(branch.size() < 2) {
    		return branch;
    	} else {
    		ArrayList<String> subBranch = new ArrayList<String>();
    		newBranch.add(branch.get(0));
    		newBranch.add(branch.get(1));
    		tagPart = branch.get(0).get(0);
    		tagPart = tagPart.replace("(", "");
    		currentEditedPart += tagPart + "->";
    		int bracketsToAdd = branch.size() - 2;
    		for(int i = 2; i < branch.size(); i++) {
    			tagPart = "_" + branch.get(i-1).get(0);
    			tagPart = tagPart.replace("(", "");
    			currentEditedPart += tagPart;
    			subBranch.add(currentEditedPart);
    			newBranch.add(subBranch);
    			if(i == branch.size()-1) {
    				String bracketString = "";
    				for(int j = 0; j < bracketsToAdd; j++) {
    					bracketString += ")";
    				}
    				branch.get(i).set(branch.get(i).size()-1, branch.get(i).get(branch.get(i).size()-1) + bracketString);
    				newBranch.add(branch.get(i));
    			} else {
    				newBranch.add(branch.get(i));
    			}
    			subBranch = new ArrayList<String>();
    		}
    	}
    	return newBranch;
    }

    public static String getTags(String[] sentence) {
    	ArrayList<ArrayList<String>> currentBranch = null;
    	ArrayList<ArrayList<String>> branches = getBranches(sentence);
    	ArrayList<ArrayList<ArrayList<String>>> taggedBranchList = new ArrayList<ArrayList<ArrayList<String>>>(); //2crazy
    	ArrayList<ArrayList<String>> lastBranch = null;
    	for(int i = 0; i < 100; i++) {
			if(hasMoreTags(branches)) {
				for(ArrayList<String> subBranch : branches) {
					currentBranch = addTagsToBranch(branches);
					if(currentBranch.size() > 1) {
						System.out.println(currentBranch);
						taggedBranchList.add(currentBranch);
					}
					String[] subBranchSentence = subBranchToStringArray(subBranch);
					ArrayList<ArrayList<String>> subbranches = getBranches(subBranchSentence);
					branches = subbranches;
					lastBranch = branches;
				}
	    	} else {
	    		System.out.println("breaking");
	    		break;
	    	}
    	}
		if(lastBranch.size() > 1) {
			System.out.println(lastBranch);
			taggedBranchList.add(lastBranch);
		}

		StringBuilder sb = new StringBuilder();
        for(String str : branchToStringArray(currentBranch)) sb.append(str + " ");
        return sb.toString();
    }



    public static boolean hasMoreTags(ArrayList<ArrayList<String>> branch) {
    	boolean tags = false;
    	if(branch.size() > 2) {
    		tags = true;
    	} else {
    		for(ArrayList<String> subBranch : branch) {
    			if(subBranch.size() > 2) {
    				tags = true;
    			}
    		}
    	}
    	return tags;
    }

    public static String[] branchToStringArray(ArrayList<ArrayList<String>> branch) {
    	String branchPart = "";
        for(int j = 0; j < branch.size(); j++) {
			 for(int k = 0; k < branch.get(j).size(); k++) {
				 branchPart += branch.get(j).get(k) + " ";
			 }
        }
        return branchPart.split(" ");
    }

    public static String[] subBranchToStringArray(ArrayList<String> subBranch) {
    	String branchPart = "";
        for(int j = 0; j < subBranch.size(); j++) {
				 branchPart += subBranch.get(j) + " ";
        }
        return branchPart.split(" ");
    }

    public static ArrayList<ArrayList<String>> getBranches(String[] sentence) {
        int openCounter = 0;
        ArrayList<String> subBranch = new ArrayList<String>();
        subBranch.add(sentence[0]);
        int numbOfClose;
        ArrayList<ArrayList<String>> branch = new ArrayList<ArrayList<String>>();

        branch.add(subBranch);
        subBranch = new ArrayList<String>();
        for(int i = 1; i < sentence.length; i++) {
        	if (sentence[i].contains("(")) {
                openCounter++;
                subBranch.add(sentence[i]);
            } else if (sentence[i].contains(")")) {
            	numbOfClose = sentence[i].length() - sentence[i].replace(")", "").length();
            	openCounter-=numbOfClose;
            	subBranch.add(sentence[i]);
            	if (openCounter < 0) {
                    branch.add(subBranch);
                    subBranch = new ArrayList<String>();
                    break;
                } else if (openCounter == 0) {
                    branch.add(subBranch);
                    subBranch = new ArrayList<String>();
                }
            }
        }
        return branch;
    }
}

*/
