package Syntax;

import java.util.ArrayList;

public class SubjectVerbCheck {
//	public static int grade1B(ArrayList<ArrayList<Word>> arr_sentences) {
//		double initialPoints = 0.0;
//		int finalPoints = 0;
//		
//
//		String[] count1 = {"NN", "NNS", "PRP", "PRP", "PRP", "NN", "NNP", "PRP", "PRP", "PRP", "PRP", "NNP", "PRP", "NNS"};
//		String[] count2 = {"VBZ", "VBZ", "VBG", "VBP", "VBP", "VBP", "VBZ", "VBD", "VB", "VB", "VBZ", "VBP", "VBP", "VBP"};
//
//		//Loop through all the sentences
//		for(int a = 0; a<arr_sentences.size(); a++){
//			//loop through all the words in the sentence
//			for (int b = 0; b <arr_sentences.get(a).size(); b++) {
//
//				if ( (b+1)> (arr_sentences.get(a).size()-1) ){break;}
//				if ( (b+2)> (arr_sentences.get(a).size()-1) ){break;}
//
//				if ((arr_sentences.get(a).get(b).getTag().equals(count1[0])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[0]))) {
//					initialPoints += 0.75;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[1])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[1]))){
//					initialPoints += 0.75;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[2])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[2]))) {
//					initialPoints += 0.2;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[3])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[3]))
//						&& (arr_sentences.get(a).get(b+2).getTag().equals("VBD"))) {
//					initialPoints += 0.3;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[4])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[4]))
//						&& (arr_sentences.get(a).get(b+2).getTag().equals("VBD"))) {
//					initialPoints += 0.35;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[5])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[5]))) {
//					initialPoints += 0.1; 
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[6])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[6]))) {
//					initialPoints += 0.5;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[7])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[7]))) {
//					initialPoints += 0.7;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[8])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[8]))) {
//					initialPoints += 0.7;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[9])) && (arr_sentences.get(a).get(b+2).getTag().equals(count2[9]))) {
//					initialPoints += 0.7;
//				}
//				else if((arr_sentences.get(a).get(b).getTag().equals(count1[10])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[10]))) {
//					initialPoints += 0.35;
//				}
//				
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[11])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[11]))) {
//					initialPoints += 0.35;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[12])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[12]))) {
//					initialPoints += 0.7;
//				}
//				else if ((arr_sentences.get(a).get(b).getTag().equals(count1[13])) && (arr_sentences.get(a).get(b+1).getTag().equals(count2[13]))) {
//					initialPoints += 0.7;
//				}
//				else{continue;}
//			}
//		}//end for loop
}
