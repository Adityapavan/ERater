package Syntax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import cs421.PosTagger;


/*
 * #verb tags that can occur next to each other

#VBP can be am. then vbn is wrong shud be vbg.
VBP VBN
VBP VBG
VBZ VBG
VBZ VBD
VBZ VBN

#from bigrams
VB VBN
VBD VBN
VBG VBN
#VBG VBD => Standing in water WAS a bad idea. here were is not possible. so check if subj=VBG then verb has to be VBD singular (is was)
#VBG VBZ => Standing in water HAS its problems.
VBN VBD
VBP VBG
VBZ VBN
 */
public class TenseCheck {
	private PosTagger posTagger;
	private String[] vb1;
	private String[] vb2;
	
	public TenseCheck(PosTagger posTagger) throws IOException{
		this.posTagger = posTagger; 
		this.vb1 = "VBP,VBP,VBZ,VBZ,VBZ,VB,VBD,VBG,VBG,VBG,VBN,VBP,VBZ".split(",");
		this.vb2 = "VBN,VBG,VBG,VBD,VBN,VBN,VBN,VBN,VBD,VBZ,VBD,VBG,VBN".split(",");
	}
	
	public int getTenseErrors(String[] sentences){
		int total_errors = 0;
		for(int i=0; i< sentences.length; i++){
			String tagged_sentence = posTagger.getTaggedSentence(sentences[i].replaceAll("[\"/()}{*&^$@!#~]", "").replaceAll("[-.?,:;]", " "));
//			System.out.println(sentences[i] + "----" + tagged_sentence);
			total_errors = total_errors + checkTense(tagged_sentence);
		}
//		System.out.println(total_errors);
		return total_errors;
	}
	
	public int checkTense(String sentence){
		ArrayList<String> tags = new ArrayList<String>();
		String[] words = sentence.split("\\s+");
		int error = 0;
		for(String word : words){
			if(word!= null && !word.isEmpty()){
				String tag = null;
				tag = word.split("/")[1];
				tags.add(tag);
			}
		}
		boolean[] rules = new boolean[vb1.length];
		for(int i=0; i<vb1.length;i++){
			int vb1_index = tags.indexOf(vb1[i]);
			int vb2_index = tags.indexOf(vb2[i]);
			if(vb1_index > 0 && vb2_index > 0){
//				if(vb1_index > vb2_index){
//					rules[i] = false;
//				}
//				else{
					rules[i] = true;
//				}
			}
		}
		for(int i=0; i<rules.length;i++){
			if(rules[i] == true){
				error = 0;
				break;
			}
			else{
				error = 1;
			}
		}
//		System.out.println(sentence + "-" + error);
		return error;
	}
}
