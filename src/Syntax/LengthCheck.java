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
	private StringBuilder str_file; 
	
	public LengthCheck() throws IOException{
	    is = new FileInputStream("resources/en-sent.bin");
	    model = new SentenceModel(is);
	    sdetector = new SentenceDetectorME(model);
	}
	
	public void splitSentences(File file) throws IOException{
		str_file = new StringBuilder();
		br = new BufferedReader(new FileReader(file));
		char[] buffer = new char[512];
	    int num = 0;
		while((num = br.read(buffer)) != -1){
			String current = String.valueOf(buffer, 0, num);
			str_file.append(current);
			buffer = new char[512];
		}
		br.close();
	    splitsentences = sdetector.sentDetect(str_file.toString());
	    num_sentences = splitsentences.length;
//	    for(int i=0; i< splitsentences.length; i++){
//	    	System.out.println(splitsentences[i]);
//			System.out.println("-------------------------------------------------------------------");
//	    }
	}
	
	public int getNumberOfSentences(){
		return num_sentences;
	}
	
	public String[] getSplitSentences(){
		return splitsentences;
	}
}
