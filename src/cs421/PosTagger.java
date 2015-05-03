package cs421;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
public class PosTagger {
    
    private MaxentTagger tagger;

    public PosTagger ( String pathToModel ) throws IOException, ClassNotFoundException {
        tagger = new MaxentTagger(pathToModel);
    }

    public String getTaggedSentence ( String stringToTag ) {
        String result = "";
        List<List<HasWord>> sentences = tagger.tokenizeText(new StringReader(stringToTag));
        for ( List<HasWord> sentence : sentences ) {
            ArrayList<TaggedWord> tSentence = (ArrayList<TaggedWord>) tagger.tagSentence(sentence);
            result += Sentence.listToString(tSentence, false);
        }
        return result;
    }
}
