package Train;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import cs421.PosTagger;
import cs421.SParser;
import cs421.Scorer;
import cs421.UserInterface;
import Semantics.CoherenceCheck;
import Semantics.TopicCheck;
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
	
	private SpellCheck spell;
	private SubjectVerbCheck subjectverb;
	private TenseCheck tense;
	private SequenceCheck sequence;
	private CoherenceCheck coherence;
	private Scorer scorer;
	private TopicCheck topic;
	private PrintStream pt;
	private PosTagger posTagger;
	
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
		
		posTagger = new PosTagger("lib\\models\\english-left3words-distsim.tagger");
		SParser sparser = new SParser();
		spell = new SpellCheck();
		subjectverb = new SubjectVerbCheck(posTagger);
		tense = new TenseCheck(posTagger);
		sequence = new SequenceCheck(sparser);
		lengthcheck = new LengthCheck();
		scorer = new Scorer();
		makeLexicon();
		topic = new TopicCheck(posTagger);
		coherence = new CoherenceCheck(sparser, posTagger);
		pt = new PrintStream(new BufferedOutputStream(new FileOutputStream("output\\result.txt", true)));
	}
	
	public void getScores() throws IOException{
		float average_length = getAverageLength();
		File[] low_files = new File(low_folder).listFiles();
		for(File low_file : low_files){
			lengthcheck.splitSentences(low_file);
			String[] sentences = lengthcheck.getSplitSentences();
			float a_1 = scorer.Score1a(spell.getSpellScores(sentences));
			float b_1 = scorer.Score1b(subjectverb.getSubVerbErrors(sentences), sentences.length);
			float c_1 = scorer.Score1c(tense.getTenseErrors(sentences), sentences.length);
			float d_1 = scorer.Score1d(sequence.getSequenceErrors(sentences),sentences.length);
			float a_2 = coherence.getCoherenceErrors(sentences);
			float b_2 = topic.makeTopicsandVerbs(sentences);
			float a_3 = scorer.Score3a(sentences.length, average_length);
			float final_score = a_1 + b_1 + c_1 +2*d_1 + 2*a_2 + 3*b_2 + 2*a_3;  
			String tag = scorer.finalTag(final_score, b_2, a_3);
//			UserInterface.resultarea.append(low_file.getName() + "     " + String.format("%.2f", a_1) +
//					"     " + String.format("%.2f", b_1) + "     " + String.format("%.2f", c_1) +
//					"     " + String.format("%.2f", d_1) + "     " + String.format("%.2f", a_2) +
//					"     " + String.format("%.2f", b_2) + "     " + String.format("%.2f", a_3) +
//					"     " + String.format("%.2f", final_score) +  "     " + tag + "\n");
		}
		File[] med_files = new File(med_folder).listFiles();
		for(File med_file : med_files){
			lengthcheck.splitSentences(med_file);
			String[] sentences = lengthcheck.getSplitSentences();
			float a_1 = scorer.Score1a(spell.getSpellScores(sentences));
			float b_1 = scorer.Score1b(subjectverb.getSubVerbErrors(sentences), sentences.length);
			float c_1 = scorer.Score1c(tense.getTenseErrors(sentences), sentences.length);
			float d_1 = scorer.Score1d(sequence.getSequenceErrors(sentences),sentences.length);
			float a_2 = coherence.getCoherenceErrors(sentences);
			float b_2 = topic.makeTopicsandVerbs(sentences);
			float a_3 = scorer.Score3a(sentences.length, average_length);
			float final_score = a_1 + b_1 + c_1 +2*d_1 + 2*a_2 + 3*b_2 + 2*a_3; 
			String tag = scorer.finalTag(final_score, b_2, a_3);
//			UserInterface.resultarea.append(med_file.getName() + "     " + String.format("%.2f", a_1) +
//					"     " + String.format("%.2f", b_1) + "     " + String.format("%.2f", c_1) +
//					"     " + String.format("%.2f", d_1) + "     " + String.format("%.2f", a_2) +
//					"     " + String.format("%.2f", b_2) + "     " + String.format("%.2f", a_3) +
//					"     " + String.format("%.2f", final_score) +  "     " + tag + "\n");
		}
		File[] high_files = new File(high_folder).listFiles();
		for(File high_file : high_files){
			lengthcheck.splitSentences(high_file);
			String[] sentences = lengthcheck.getSplitSentences();
			float a_1 = scorer.Score1a(spell.getSpellScores(sentences));
			float b_1 = scorer.Score1b(subjectverb.getSubVerbErrors(sentences), sentences.length);
			float c_1 = scorer.Score1c(tense.getTenseErrors(sentences), sentences.length);
			float d_1 = scorer.Score1d(sequence.getSequenceErrors(sentences),sentences.length);
			float a_2 = coherence.getCoherenceErrors(sentences);
			float b_2 = topic.makeTopicsandVerbs(sentences);
			float a_3 = scorer.Score3a(sentences.length, average_length);
			float final_score = a_1 + b_1 + c_1 +2*d_1 + 2*a_2 + 3*b_2 + 2*a_3; 
			String tag = scorer.finalTag(final_score, b_2, a_3);
//			UserInterface.resultarea.append(high_file.getName() + "     " + String.format("%.2f", a_1) +
//					"     " + String.format("%.2f", b_1) + "     " + String.format("%.2f", c_1) +
//					"     " + String.format("%.2f", d_1) + "     " + String.format("%.2f", a_2) +
//					"     " + String.format("%.2f", b_2) + "     " + String.format("%.2f", a_3) +
//					"     " + String.format("%.2f", final_score) +  "     " + tag + "\n");
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
	
	public void makeLexicon() throws IOException{
		File[] high_files = new File(high_folder).listFiles();
		for(File file : high_files){
			lengthcheck.splitSentences(file);
			String[] sentences = lengthcheck.getSplitSentences();
			LexiconGenerator lg = new LexiconGenerator(high_folder, posTagger);
			lg.makeTopicsandVerbs(sentences);
		}
	}
	
	
}
