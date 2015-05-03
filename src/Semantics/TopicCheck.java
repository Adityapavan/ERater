package Semantics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rita.RiWordNet;
import cs421.PosTagger;

public class TopicCheck {
	private PosTagger posTagger;
	private RiWordNet wordnet;
	private List<String> noun_tags = Arrays.asList("NN", "NNS",	"NNP",	"NNPS"); 
	private ArrayList<String> nouns_lexicon_list;
	private HashMap<String, String> noun_list;
	private HashMap<String, Float> similarities;
	
	public TopicCheck(PosTagger posTagger) throws IOException{
		this.posTagger = posTagger; 
		wordnet = new RiWordNet("resources\\WordNet-3.0");
		nouns_lexicon_list = new ArrayList<String>();
		BufferedReader bufin = new BufferedReader(new FileReader("resources\\noun_lexicon.txt"));
		String temp = null;
		while((temp = bufin.readLine()) != null){
			nouns_lexicon_list.add(temp.trim());
		}
		bufin.close();
	}
	
	public float makeTopicsandVerbs(String[] sentences){
		noun_list = new HashMap<String, String>(); 
		similarities = new HashMap<String, Float>();
		for(int i=0; i< sentences.length; i++){
			String tagged_sentence = posTagger.getTaggedSentence(sentences[i].replaceAll("[\"/()}{*&^$@!#~]", "").replaceAll("[-.?,:;]", " "));
			String[] words = tagged_sentence.split("\\s+");
			for(int j=0; j< words.length; j++){
				String tag = words[j].split("/")[1].trim();
				String word = words[j].split("/")[0].trim();
				if(noun_tags.contains(tag)){
					noun_list.put(word, tag);
				}
			}
		}
		float total_errors = 5.00f - getTopicSimilarity();
		return total_errors;
//		System.out.println(total_errors);
//		System.out.println(noun_list.keySet() + "-----------------------------------------------" + verb_list.keySet());
	}
	
	
	public float getTopicSimilarity(){
		String word1 = null;
		String word2 = null;
		for(Map.Entry<String, String> entry : noun_list.entrySet()){
			word1 = entry.getKey();
			for(String noun : nouns_lexicon_list){
				word2 = noun;
				float dist = wordnet.getDistance(word1, word2, "n");
				similarities.put(word1 + "_" + word2 , dist);
			}
		}
		float errors = getTopicErrors();
		return errors;
	}
	
	public float getTopicErrors(){
		ArrayList<Float> z_scores = new ArrayList<Float>();
		float sum = 0.00f;
		for(Map.Entry<String, Float> sim : similarities.entrySet()){
			sum = sum + sim.getValue();
		}
		int total = similarities.size();
		float mean = sum/total;
		float total_squared_error = 0.00f;
		for(Map.Entry<String, Float> sim : similarities.entrySet()){
			float diff = sim.getValue() - mean;
			float squared_error = diff*diff;
			total_squared_error = total_squared_error + squared_error;
		}
		float variance = total_squared_error/total;
		for(Map.Entry<String, Float> sim : similarities.entrySet()){
			float numerator = sim.getValue() - mean;
			float z_score = (float) (numerator/Math.sqrt(variance));
			z_scores.add(z_score);
		}
		int errors = 0;
//		System.out.println(z_scores);
		for(Float z : z_scores){
			if(z < (mean - 3*Math.sqrt(variance)) || z > (mean + 3*Math.sqrt(variance))){
				errors = errors + 1;
			}
		}
//		System.out.println(errors);
		return (50.00f*(total-errors)/(noun_list.size()*total));
	}
	
}
