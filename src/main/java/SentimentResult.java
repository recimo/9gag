public class SentimentResult {
    private String sentimentType;
    private int sentimentScore;
    private NLPModel sentimentClass;

    public String getSentimentType() {
        return sentimentType;
    }

    public void setSentimentType(String sentimentType) {
        this.sentimentType = sentimentType;
    }

    public int getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(int sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public NLPModel getSentimentClass() {
        return sentimentClass;
    }

    public void setSentimentClass(NLPModel sentimentClass) {
        this.sentimentClass = sentimentClass;
    }
}
