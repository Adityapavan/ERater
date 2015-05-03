package Syntax;

import java.io.IOException;
import cs421.SParser;
import edu.stanford.nlp.trees.Tree;

public class SequenceCheck {
	private SParser sparser;
	
	public SequenceCheck(SParser sparser){
		this.sparser = sparser;
	}
	public int getSequenceErrors(String[] sentences) throws IOException{
		int total_errors = 0;
		for(int i=0; i< sentences.length; i++){
			Tree tree = sparser.getParseTree(sentences[i].replaceAll("[\"/()}{*&^$@!#~]", "").replaceAll("[-.?,:;]", " "));
			total_errors = total_errors + checkSequence(tree);
		}
		return total_errors;
	}
	
	public int checkSequence(Tree tree){
		int error = 0;
		if(tree.label().value().equals("ROOT")){
			if(tree.firstChild().label().value().equals("FRAG")){
				error = 1;
			}
			else{
				for(Tree subtree: tree){
					if(subtree.value().equals("SBAR")){
						error = checkSBARRules(subtree);
					}
				}
			}
		}
		return error;
	}
	
	public int checkSBARRules(Tree tree){
		int error = 0;
		Tree[] children = tree.children();
		if(children.length != 2){
			error = 1;
		}
		else{
			if(!children[0].value().equals("IN") && !children[1].value().equals("S")){
				error = 1;
			}
		}
		return error;
	}
	
}
