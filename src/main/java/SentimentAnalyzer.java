import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentModel;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;

import java.util.Properties;

public class SentimentAnalyzer {
    private StanfordCoreNLP pipeline;

    public void init() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(properties);
    }

    public SentimentResult getSentimentResult(String text) {
        NLPModel sentClass = new NLPModel();
        SentimentResult sentimentResult = new SentimentResult();

        try {
            Annotation annotation = pipeline.process(text);

            for(CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                //  System.out.println(sentence);

                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                SimpleMatrix simpleMatrix = RNNCoreAnnotations.getPredictions(tree);

                //System.out.println(simpleMatrix);

                sentClass.setVeryNegative((int) Math.round(simpleMatrix.get(0)*100d));
                sentClass.setNegative((int) Math.round(simpleMatrix.get(1)*100d));
                sentClass.setNeutral((int) Math.round(simpleMatrix.get(2)*100d));
                sentClass.setPositive((int) Math.round(simpleMatrix.get(3)*100d));
                sentClass.setVeryPositive((int) Math.round(simpleMatrix.get(4)*100d));

                String setimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
                sentimentResult.setSentimentType(setimentType);
                sentimentResult.setSentimentClass(sentClass);
                sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));
            }
        } catch (Exception e) {
            System.out.println("Error in text input!");
            e.printStackTrace();
        }

        return sentimentResult;
    }

}
