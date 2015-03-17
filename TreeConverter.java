import java.util.*;

//(ROOT (S (NP (NNP Ms.) (@NP->_NNP (NNP Haag))) (@S->_NP (VP (VBZ plays) (@VP->_VBZ (NP (NNP Elianti)))) (@S->_NP_VP (. .)))))

//   (NP (NNP Rolls-Royce) (@NP-> NNP (NNP Motor) (@NP-> NNP NNP (NNPS
//   Cars) (@NP-> NNP NNP NNPS (NNP Inc.)))))

public class TreeConverter{

    public String convert(ArrayList<String> input){
        String[] splitList = new String[0];
        String convertedString = "";
        System.out.println("Starting converting the file");

        for(String sentence : input){
            if(!sentence.isEmpty()){
                System.out.println("Original sentence: " + sentence);
                splitList = sentence.split(" ");
                convertedString = getTags(splitList);
                System.out.println("Tagged sentence: " + convertedString + "\n");
            }
        }
        
        System.out.println("Done converting the file");
        return convertedString;
    }
    
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