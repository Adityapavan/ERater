package cs421;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.util.InvalidFormatException;

public class SParser {
	private InputStream is;
	private ParserModel model;
	private Parser parser;
	
	public SParser() throws InvalidFormatException, IOException{
		this.is = new FileInputStream("resources\\en-parser-chunking.bin");
		this.model = new ParserModel(is);
		this.parser = ParserFactory.create(model);
	}
	
	public void parse(String sentence) throws IOException{
		Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);
		for (Parse p : topParses)
			p.show();
		is.close();
	}
}
