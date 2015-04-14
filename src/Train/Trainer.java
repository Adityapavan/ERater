package Train;

import java.io.File;
import java.io.IOException;

import cs421.PosTagger;
import Syntax.SequenceCheck;
import Syntax.SpellCheck;
import Syntax.SubjectVerbCheck;
import Syntax.TenseCheck;

public class Trainer {
	
	private String low_folder;
	private String med_folder;
	private String high_folder;
	
	SpellCheck spell;
	SubjectVerbCheck subjectverb;
	TenseCheck tense;
	SequenceCheck sequence;
	
	public Trainer(String folder_path) throws IOException, ClassNotFoundException {
		File[] parent = new File(folder_path).listFiles();
		for(File sub: parent){
			if(sub.getName().contains("low")){
				this.low_folder = sub.getAbsolutePath();
			}
			else if(sub.getName().contains("medium")){
				this.med_folder = sub.getAbsolutePath();
			}
			else if(sub.getName().contains("high")){
				this.high_folder = sub.getAbsolutePath();
			}
		}
		
		PosTagger posTagger = new PosTagger("lib\\models\\english-left3words-distsim.tagger.");
		spell = new SpellCheck();
		subjectverb = new SubjectVerbCheck();
		tense = new TenseCheck(posTagger);
		sequence = new SequenceCheck();
	}
	
	public void getScores() throws IOException{
		File[] low_files = new File(low_folder).listFiles();
		for(File low_file : low_files){
			System.out.println(low_file.getName() + " --- " + spell.getSpellScores(low_file));
		}
		File[] med_files = new File(med_folder).listFiles();
		for(File med_file : med_files){
			System.out.println(med_file.getName() + " --- " + spell.getSpellScores(med_file));
		}
		File[] high_files = new File(high_folder).listFiles();
		for(File high_file : high_files){
			System.out.println(high_file.getName() + " --- " + spell.getSpellScores(high_file));
		}
	}
	
	
}
