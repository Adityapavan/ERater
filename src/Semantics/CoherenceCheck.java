package Semantics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import cs421.PosTagger;
import cs421.SParser;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class CoherenceCheck {
	private SParser sparser;
	private PosTagger postagger;
	
	public CoherenceCheck(SParser sparser, PosTagger postagger){
		this.sparser = sparser;
		this.postagger = postagger;
	}
	
	public float getCoherenceErrors(String[] sentences){
		ArrayList<String> pronouns = new ArrayList<String>();
		for(int num=0; num<sentences.length; num++){
			String[] words = postagger.getTaggedSentence(sentences[num].replaceAll("[\"/()}{*&^$@!#~]", "").replaceAll("[-.?,:;]", " ")).split("\\s+");
			for(int t = 0; t< words.length; t++){
				String tag = words[t].split("/")[1].trim();
				String word = words[t].split("/")[0].trim();
				if(tag.equals("PRP") || tag.equals("PRP$")){
					if(!pronouns.contains(word)){
						pronouns.add(word);
					}
				}
			}
		}
		ArrayList<String> coreferences = resolveCoreferenceErrors(sentences);
		float reference_errors = checkUnreferencedPronouns(pronouns, coreferences);
		float final_score = 5.00f*(pronouns.size() - reference_errors)/pronouns.size();
//		System.out.println(final_score);
		return final_score;
	}
	
	public ArrayList<String> resolveCoreferenceErrors(String[] sentences){
		ArrayList<String>  coreferences = new ArrayList<String>();
		String text = null;
		for(int i=0; i< sentences.length; i++){
			text = text + " " + sentences[i];
		}
		String essay = text.replace("null ", "");
		Annotation document = new Annotation(essay);
		sparser.pipeline.annotate(document);
		Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
		
//		System.out.println("-------" + essay);
		
		for (Map.Entry<Integer, CorefChain> entry : graph.entrySet()) {
			CorefChain c = entry.getValue();
			CorefMention cm = c.getRepresentativeMention();
			
			String clust = "";
            List<CoreLabel> tks = document.get(SentencesAnnotation.class).get(cm.sentNum-1).get(TokensAnnotation.class);
            for(int i = cm.startIndex-1; i < cm.endIndex-1; i++){
                clust += tks.get(i).get(TextAnnotation.class) + " ";
            	clust = clust.trim();
//            System.out.println("representative mention: \"" + clust + "\" is mentioned by:" + c.getMentionsInTextualOrder());
            }
            
            for(CorefMention m : c.getMentionsInTextualOrder()){
                String clust2 = "";
                tks = document.get(SentencesAnnotation.class).get(m.sentNum-1).get(TokensAnnotation.class);
                for(int i = m.startIndex -1; i < m.endIndex - 1; i++)
                    clust2 += tks.get(i).get(TextAnnotation.class) + " ";
                clust2 = clust2.trim();
                //don't need the self mention
                if(clust.equals(clust2))
                    continue;

//                System.out.println("\t" + clust2);
                if(!coreferences.contains(clust2)){
                	coreferences.add(clust2);
                }
            }
		}
//		System.out.println(coreferences);
//		System.out.println("****************************");
		return coreferences;
	}	
	
	public float checkUnreferencedPronouns(ArrayList<String> pronouns, ArrayList<String> coreferences){
		float errors = 0.00f;
		for(String pronoun : pronouns){
			int found = 0;
			for(int i=0; i<coreferences.size(); i++){
				String coreference = coreferences.get(i).trim();
				if(coreference.indexOf(pronoun) != -1){
//					System.out.println(pronoun + "---" + coreference);
					found = 1;
					break;
				}
			}
			if(found != 1){
				errors = errors + 1;
//				System.out.println(pronoun + "==" + pronouns.size());
			}
		}
		return errors;
	}
}
