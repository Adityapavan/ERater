package Test;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import Syntax.LengthCheck;
import Syntax.SequenceCheck;
import Syntax.SpellCheck;
import Syntax.SubjectVerbCheck;
import Syntax.TenseCheck;
import cs421.PosTagger;
import cs421.SParser;
import cs421.Scorer;
//import cs421.UserInterface;

public class Tester {
	private String test_folder;
	private LengthCheck lengthcheck;
	
	SpellCheck spell;
	SubjectVerbCheck subjectverb;
	TenseCheck tense;
	SequenceCheck sequence;
	Scorer scorer;
	PrintStream pt;
	
	public Tester(String folder_path) throws IOException, ClassNotFoundException {
		this.test_folder = new File(folder_path).getAbsolutePath();
		
		PosTagger posTagger = new PosTagger("lib\\models\\english-left3words-distsim.tagger.");
		SParser sparser = new SParser();
		spell = new SpellCheck();
		subjectverb = new SubjectVerbCheck(posTagger);
		tense = new TenseCheck(posTagger);
		sequence = new SequenceCheck(sparser);
		lengthcheck = new LengthCheck();
		scorer = new Scorer();
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
//			sequence.getSequenceErrors(sentences);
			float d_1 = 0.0f;
			float a_2 = 0.0f;
			float b_2 = 0.0f;
			float a_3 = scorer.Score3a(sentences.length, average_length);
			float final_score = a_1 + b_1 + c_1 +2*d_1 + 2*a_2 + 3*b_2 + 2*a_3; 
			String tag = scorer.finalTag(final_score, a_3);
////			UserInterface.resultarea.append(test_file.getName() + "     " + a_1 + "\t" + b_1 + "\t" + c_1 + "\t" + d_1 + "\t" + a_2 + "\t" + b_2 + "\t" + a_3 + "\t" + final_score +  + "\t" + tag + "\n");
			pt.println(test_file.getName() + "    " + a_1 + "    " + b_1 + "    " + c_1 + "    " + d_1 + "    " + a_2 + "    " + b_2 + "    " + a_3 + "    " + final_score + "    " + tag);
		}
		pt.close();
	}
}
