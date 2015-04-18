package Train;

import java.io.File;
import java.io.IOException;

import cs421.PosTagger;
import cs421.Scorer;
import cs421.UserInterface;
import Syntax.LengthCheck;
import Syntax.SequenceCheck;
import Syntax.SpellCheck;
import Syntax.SubjectVerbCheck;
import Syntax.TenseCheck;

public class Trainer {
	
	private String low_folder;
	private String med_folder;
	private String high_folder;
	private LengthCheck lengthcheck;
	
	SpellCheck spell;
	SubjectVerbCheck subjectverb;
	TenseCheck tense;
	SequenceCheck sequence;
	Scorer scorer;
	
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
		subjectverb = new SubjectVerbCheck(posTagger);
		tense = new TenseCheck(posTagger);
		sequence = new SequenceCheck();
		lengthcheck = new LengthCheck();
		scorer = new Scorer();
	}
	
	public void getScores() throws IOException{
		File[] low_files = new File(low_folder).listFiles();
		float average_length = getAverageLength();
		for(File low_file : low_files){
			lengthcheck.splitSentences(low_file);
			String[] sentences = lengthcheck.getSplitSentences();
			float a_1 = scorer.Score1a(spell.getSpellScores(sentences));
			float b_1 = scorer.Score1b(subjectverb.getSubVerbErrors(sentences), sentences.length);
			float c_1 = scorer.Score1c(tense.getTenseErrors(sentences), sentences.length);
			float d_1 = 0.0f;
			float a_2 = 0.0f;
			float b_2 = 0.0f;
			float a_3 = scorer.Score3a(sentences.length, average_length);
			float final_score = a_1 + b_1 + c_1 +2*d_1 + 2*a_2 + 3*b_2 + 2*a_3;  
			UserInterface.resultarea.append(low_file.getName() + "     " + a_1 + "\t" + b_1 + "\t" + c_1 + "\t" + d_1 + "\t" + a_2 + "\t" + b_2 + "\t" + a_3 + "\t" + final_score + "\n");
		}
		File[] med_files = new File(med_folder).listFiles();
		for(File med_file : med_files){
			lengthcheck.splitSentences(med_file);
			String[] sentences = lengthcheck.getSplitSentences();
			float a_1 = scorer.Score1a(spell.getSpellScores(sentences));
			float b_1 = scorer.Score1b(subjectverb.getSubVerbErrors(sentences), sentences.length);
			float c_1 = scorer.Score1c(tense.getTenseErrors(sentences), sentences.length);
			float d_1 = 0.0f;
			float a_2 = 0.0f;
			float b_2 = 0.0f;
			float a_3 = scorer.Score3a(sentences.length, average_length);
			float final_score = a_1 + b_1 + c_1 +2*d_1 + 2*a_2 + 3*b_2 + 2*a_3;  
			UserInterface.resultarea.append(med_file.getName() + "     " + a_1 + "\t" + b_1 + "\t" + c_1 + "\t" + d_1 + "\t" + a_2 + "\t" + b_2 + "\t" + a_3 + "\t" + final_score + "\n");
		}
		File[] high_files = new File(high_folder).listFiles();
		for(File high_file : high_files){
			lengthcheck.splitSentences(high_file);
			String[] sentences = lengthcheck.getSplitSentences();
			float a_1 = scorer.Score1a(spell.getSpellScores(sentences));
			float b_1 = scorer.Score1b(subjectverb.getSubVerbErrors(sentences), sentences.length);
			float c_1 = scorer.Score1c(tense.getTenseErrors(sentences), sentences.length);
			float d_1 = 0.0f;
			float a_2 = 0.0f;
			float b_2 = 0.0f;
			float a_3 = scorer.Score3a(sentences.length, average_length);
			float final_score = a_1 + b_1 + c_1 +2*d_1 + 2*a_2 + 3*b_2 + 2*a_3;  
			UserInterface.resultarea.append(high_file.getName() + "     " + a_1 + "\t" + b_1 + "\t" + c_1 + "\t" + d_1 + "\t" + a_2 + "\t" + b_2 + "\t" + a_3 + "\t" + final_score + "\n");
		}
	}
	
	public float getAverageLength() throws IOException{
		File[] high_files = new File(high_folder).listFiles();
		int total = 0;
		for(File high_file : high_files){
			lengthcheck.splitSentences(high_file);
			String[] sentences = lengthcheck.getSplitSentences();
			total = total + sentences.length;
		}
		float average = total/high_files.length;
		return average;
	}
	
	
}
