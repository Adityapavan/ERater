package cs421;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;


public class SParser {
	private Properties props;
	public StanfordCoreNLP pipeline;
	private Annotation document;
	private List<CoreMap> sentence;
	
	public SParser() throws IOException{
		this.props = new Properties();
		this.props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
		pipeline = new StanfordCoreNLP(props);
	}
	
	public SParser(String coref) throws IOException{
		this.props = new Properties();
		this.props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		pipeline = new StanfordCoreNLP(props);
	}
	
//	public void Parse(String sentence){			
//		opennlp.tools.parser.Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
//		for (opennlp.tools.parser.Parse p : topParses){
//			p.show();	
//		}
//	}
	
	public Tree getParseTree(String text){
		Tree tree = null;
		document = new Annotation(text);
	    // run all Annotators on this text
	    pipeline.annotate(document);

	    // these are all the sentences in this document
	    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
	    sentence = document.get(SentencesAnnotation.class);

	    for(CoreMap sent: sentence) {
	      // this is the parse tree of the current sentence
	      tree = sent.get(TreeAnnotation.class);
	    }
	    return tree;
	}
	
}
