public class WordStat {
    /**
     * This class will be used to build the string for the word length the associated tally
     */
    private final int wordLength;
    private final int wordFrequency;

    /**
     * Initiating the wordLength and wordFrequency
     *
     * @param wordLength    the length of the word
     * @param wordFrequency the number of times the word occurs
     */
    public WordStat(int wordLength, int wordFrequency) {
        this.wordLength = wordLength;
        this.wordFrequency = wordFrequency;
    }

    /**
     * toString method that returns the textual form
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Number of words of length " + wordLength + " is " + wordFrequency;
    }

}
