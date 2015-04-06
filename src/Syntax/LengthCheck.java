package Syntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class LengthCheck {
	private String[] splitsentences;
	private int num_sentences;
	private SentenceModel model;
	private InputStream is;
	private SentenceDetectorME sdetector;
	private BufferedReader br;
	
	public LengthCheck() throws IOException{
	    is = new FileInputStream("resources/en-sent.bin");
	    model = new SentenceModel(is);
	    sdetector = new SentenceDetectorME(model);
	}
	
	public void splitSentences(File file) throws IOException{
		br = new BufferedReader(new FileReader(file));
	    String delimitor = br.readLine();
	    splitsentences = sdetector.sentDetect(delimitor);
	    num_sentences = splitsentences.length;
	}
	
	public int getNumberOfSentences(){
		return num_sentences;
	}
	
	public String[] getSplitSentences(){
		return splitsentences;
	}
}
