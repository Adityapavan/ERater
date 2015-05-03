package Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import Semantics.CoherenceCheck;
import Semantics.TopicCheck;
import Syntax.LengthCheck;
import Syntax.SequenceCheck;
import Syntax.SpellCheck;
import Syntax.SubjectVerbCheck;
import Syntax.TenseCheck;
import cs421.PosTagger;
import cs421.SParser;
import cs421.Scorer;
import cs421.UserInterface;

public class Tester {
	private String test_folder;
	private LengthCheck lengthcheck;
	
	private SpellCheck spell;
	private SubjectVerbCheck subjectverb;
	private TenseCheck tense;
	private SequenceCheck sequence;
	private TopicCheck topic;
	private CoherenceCheck coherence;
	private Scorer scorer;
	private PrintStream pt;
	
	public Tester(String folder_path) throws IOException, ClassNotFoundException {
		this.test_folder = new File(folder_path).getAbsolutePath();
		PosTagger posTagger = new PosTagger("lib\\models\\english-left3words-distsim.tagger.");
		SParser sparser = new SParser();
		SParser sparser2 = new SParser("dcoref");
		spell = new SpellCheck();
		subjectverb = new SubjectVerbCheck(posTagger);
		tense = new TenseCheck(posTagger);
		sequence = new SequenceCheck(sparser);
		topic = new TopicCheck(posTagger);
		lengthcheck = new LengthCheck();
		scorer = new Scorer();
		coherence = new CoherenceCheck(sparser2, posTagger);
		pt = new PrintStream(new BufferedOutputStream(new FileOutputStream("output\\result.txt", true)));
	}
	
	public void getScores() throws IOException{
		File[] test_files = new File(test_folder).listFiles();
		float average_length = 17.0f;
		for(File test_file : test_files){
			lengthcheck.splitSentences(test_file);
			String[] sentences = lengthcheck.getSplitSentences();
			float a_1 = scorer.Score1a(spell.getSpellScores(sentences));
			float b_1 = scorer.Score1b(subjectverb.getSubVerbErrors(sentences), sentences.length);
			float c_1 = scorer.Score1c(tense.getTenseErrors(sentences), sentences.length);
			float d_1 = scorer.Score1d(sequence.getSequenceErrors(sentences),sentences.length);
			float a_2 = coherence.getCoherenceErrors(sentences);
//			float a_2 = 0.0f;
			float b_2 = topic.makeTopicsandVerbs(sentences);
			float a_3 = scorer.Score3a(sentences.length, average_length);
			float final_score = a_1 + b_1 + c_1 +2*d_1 + 2*a_2 + 3*b_2 + 2*a_3;  
			String tag = scorer.finalTag(final_score, b_2, a_3);
//			UserInterface.resultarea.append(test_file.getName() + "     " + String.format("%.2f", a_1) +
//											"     " + String.format("%.2f", b_1) + "     " + String.format("%.2f", c_1) +
//											"     " + String.format("%.2f", d_1) + "     " + String.format("%.2f", a_2) +
//											"     " + String.format("%.2f", b_2) + "     " + String.format("%.2f", a_3) +
//											"     " + String.format("%.2f", final_score) +  "     " + tag + "\n");
			pt.println(test_file.getName() + "     " + String.format("%.2f", a_1) +
					"     " + String.format("%.2f", b_1) + "     " + String.format("%.2f", c_1) +
					"     " + String.format("%.2f", d_1) + "     " + String.format("%.2f", a_2) +
					"     " + String.format("%.2f", b_2) + "     " + String.format("%.2f", a_3) +
					"     " + String.format("%.2f", final_score) +  "     " + tag + "\n");
		}
		pt.close();
	}
}
