package Train;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rita.RiWordNet;
import cs421.PosTagger;

public class LexiconGenerator {
	private HashMap<String, String> noun_list;
	private String highfolder;
	private PosTagger postagger;
	private List<String> noun_tags = Arrays.asList("NN", "NNS",	"NNP",	"NNPS");
	private RiWordNet wordnet; 
	
	public LexiconGenerator(String high_folder, PosTagger postagger){
		noun_list = new HashMap<String, String>();
		this.highfolder = high_folder;
		this.postagger = postagger;
		wordnet = new RiWordNet("resources\\WordNet-3.0");
	}
	
	public void makeTopicsandVerbs(String[] sentences) throws IOException{
		for(int i=0; i< sentences.length; i++){
			String tagged_sentence = postagger.getTaggedSentence(sentences[i].replaceAll("[\"/()}{*&^$@!#~]", "").replaceAll("[-.?,:;]", " "));
			String[] words = tagged_sentence.split("\\s+");
			for(int j=0; j< words.length; j++){
				String tag = words[j].split("/")[1].trim();
				String word = words[j].split("/")[0].trim();
				if(noun_tags.contains(tag)){
						noun_list.put(word, tag);
				}
			}
		}
		createLexiconFiles();
//		System.out.println(noun_list.keySet() + "-----------------------------------------------");
	}
	
	public void createLexiconFiles() throws IOException{
		File noun_lexicon = new File("resources\\noun_lexicon.txt");
		if(!noun_lexicon.exists()){
			noun_lexicon.createNewFile();
		}
		BufferedWriter bufout = new BufferedWriter(new FileWriter(noun_lexicon, true));
		for(Map.Entry<String, String> entry : noun_list.entrySet()){
			bufout.write(entry.getKey());
			bufout.newLine();
		}
		bufout.close();
	}
	
}
