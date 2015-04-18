package Syntax;

import java.util.ArrayList;

import cs421.PosTagger;

/* Subject-Verb combinations!
 VBD PRP
VB NNS
VB NNS
VB PRP
VBZ NNP
VBZ NN
VBZ PRP
MD PRP
VBD NN
VBD NNS
VBP NNS
VBP NNPS
VBP PRP
VBZ NNPS
VBN PRP
VBD NNP
VBG PRP
VBG NN
VBN NN
VBN NNP
 */

public class SubjectVerbCheck {
	private String[] subject;
	private String[] verb;
	
	private PosTagger posTagger;
	private String[] plural_s;
	private String[] singular_s;
	private String presentThird_v;
	private String[] present_v;
	private String[] past_v;

	private String[] singular_prp;
	private String[] plural_prp;
	
	public SubjectVerbCheck(PosTagger posTagger){
		this.subject = "NNS,NNPS,PRP,NNPS,NNP,NN,PRP,NN,NNP,PRP,NN,NNS,NNP,PRP,NNS,NNS,PRP,PRP,PRP,NN".split(",");
		this.verb = "VBP,VBP,VBP,VBZ,VBZ,VBZ,VBZ,VBN,VBN,VBN,VBD,VBD,VBD,VBD,VB,VB,VB,MD,VBG,VBG".split(",");
		
		this.plural_s = "NNPS,NNS".split(",");
		this.singular_s = "NN, NNP".split(",");
		this.presentThird_v = "VBZ";
		this.present_v = "VBG,VBP".split(",");
		this.past_v = "VBD,VBN".split(",");
		this.singular_prp = "yourself,I,he,yours,his,her,my,mine,your,thy,hers,herself,him,himself,yourself,I,he,yours,it,itself,me,myself,one,oneself,ownself,self,she,thee,thou,you,somebody,each,anyone,everyone,someone,nobody".split(",");
		this.plural_prp = "we,these,those,them,themselves,they,us,our,their,yourselves,yours,ours,ourselves,theirs,both,several,few".split(",");
	
		this.posTagger = posTagger;
	}

	public int getSubVerbErrors(String[] sentences) {
		int total_errors = 0;
		for(int i=0; i< sentences.length; i++){
			String tagged_sentence = posTagger.getTaggedSentence(sentences[i].replaceAll("[\"/()}{*&^$@!#~]", "").replaceAll("[-.?,:;]", " "));
//			System.out.println(sentences[i] + "----" + tagged_sentence);
			total_errors = total_errors + checkErrors(tagged_sentence);
		}
		return total_errors;
	}
	
	public int checkErrors(String sentence){
		ArrayList<String> tags = new ArrayList<String>();
		ArrayList<String> tokens = new ArrayList<String>();
		String[] words = sentence.split("\\s+");
		int error = 0;
		for(String word : words){
			if(word!= null && !word.isEmpty()){
				String tag = null;
				String token = null;
				tag = word.split("/")[1];
				tags.add(tag);
				token = word.split("/")[0];
				tokens.add(token);
			}
		}
		boolean[] rules = new boolean[subject.length];

		for(int i=0; i<verb.length;i++){
			if(tags.contains(verb[i]) && tags.contains(subject[i])){
				rules[i] = true;
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
//		System.out.println("------------------------------------------------------------");
//		System.out.println(sentence + "--" + error);
		return error;
	}

//	public ArrayList<String> getVerbs(ArrayList<String> tags){
//		ArrayList<String> verbs = new ArrayList<String>();
//		for(int i=0; i < verb.length ; i++){
//			if(tags.contains(verb[i])){
//				verbs.add(verb[i]);
//			}
//		}
//		System.out.println(verbs);
//		return verbs;
//	}
//	
//	public ArrayList<String> getSubjects(ArrayList<String> tags){
//		ArrayList<String> subjects = new ArrayList<String>();
//		for(int i=0; i < subject.length ; i++){
//			if(tags.contains(subject[i])){
//				subjects.add(subject[i]);
//			}
//		}
//		System.out.println(subjects);
//		return subjects;
//	}
	
}
		


