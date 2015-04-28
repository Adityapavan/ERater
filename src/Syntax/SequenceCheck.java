package Syntax;

import java.io.IOException;

import cs421.SParser;

public class SequenceCheck {
	private SParser sparser;
	
	public SequenceCheck(SParser sparser){
		this.sparser = sparser;
	}
	public int getSequenceErrors(String[] sentences) throws IOException{
		int total_errors = 0;
//		try{
//			sparser.parse("My dog also likes eating sausage.");
//		}
//		catch(Exception ex){
//			ex.printStackTrace();
//		}
		
		for(int i=0; i< sentences.length; i++){
			sparser.parse(sentences[i].replaceAll("[\"/()}{*&^$@!#~]", "").replaceAll("[-.?,:;]", " "));
//			total_errors = total_errors + checkSequence(tagged_sentence);
		}
//		System.out.println(total_errors);
		return total_errors;
	}
	
	public int checkSequence(String sentence){
		return 0;
	}
}
