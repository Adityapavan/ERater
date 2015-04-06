package Syntax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;

import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class SpellCheck {
	private File dir;
	private Directory directory;
	private SpellChecker spellChecker;
	private ArrayList<String> bi_words;
	private LengthCheck lengthcheck;
	
	public SpellCheck() throws IOException{
		dir = new File("spellchecker");
		directory = FSDirectory.open(dir);
		spellChecker = new SpellChecker(directory);
		spellChecker.indexDictionary(new PlainTextDictionary(new File("resources/unabr2.txt")));
		bi_words = new ArrayList<String>();
		BufferedReader bin = new BufferedReader(new FileReader("resources/bi_words.txt"));
		String temp;
		while((temp = bin.readLine()) != null){
			bi_words.add(temp.trim());
		}
		lengthcheck = new LengthCheck();
		bin.close();
	}
	
	//returns a string with combination of number of spelling mistakes and length of essay . Both are separated by _
	public String getSpellScores(File file) throws IOException{
		ArrayList<String> words = new ArrayList<String>();
		int counter = 0;
		
		lengthcheck.splitSentences(file);
		String[] sentences = lengthcheck.getSplitSentences();
		
		//tokenizer code
		for(int i=0; i<sentences.length; i++){
			String[] tokens = sentences[i].replaceAll("[\"'/()}{*&^$@!#~]", "").replaceAll("[-.?,:;]", " ").split("\\s+");
			for(int j=0; j<tokens.length; j++){
				if(tokens[j] != null && !tokens[j].isEmpty()){
					words.add(tokens[j].trim());
				}
			}
		}
		
		for(String word : words){
			if(!spellChecker.exist(word.toLowerCase()) && !bi_words.contains(word.toLowerCase()) && !SpellCheck.isNumeric(word.toLowerCase())){
				counter++;
//				System.out.println(word + "  ---  " + counter);
			}
		}
		
		return (counter + "_" + lengthcheck.getNumberOfSentences());
	}
	
	//check if a word is a numeric
	public static boolean isNumeric(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
}

