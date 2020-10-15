import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * This is my solution for the Synalogik's Technical Test.
 * The program takes a text file and returns stats that were highlighted in the requirements.
 * @author Redwan Khan
 */
public class Count {
    private static final String WORD_PATTERN = "[^a-zA-Z0-9&/ ]";

    public static void main(String[] args) {
        new Count().run();
    }

    public void run() {
        File file = getFile(System.in, System.out);

        int wordCount = 0;
        Map<Integer, Integer> wordLengthFrequencyMap = new HashMap<>();

        try (Scanner sc = new Scanner(new FileInputStream(file))) {
            while (sc.hasNext()) {
                String[] part = sc.next().split(" ");
                for (String value : part) {
                    value = getCleansedString(value);
                    if (!value.isEmpty()) {
                        if (wordLengthFrequencyMap.containsKey(value.length())) {
                            wordLengthFrequencyMap.put(value.length(), wordLengthFrequencyMap.get(value.length()) + 1);
                        } else {
                            wordLengthFrequencyMap.put(value.length(), 1);
                        }
                        wordCount++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int charCount = 0;
        int maxValueInMap = Collections.max(wordLengthFrequencyMap.values());

        List<WordStat> numberOfWordsList = new ArrayList<>();
        List<Integer> maxKey = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : wordLengthFrequencyMap.entrySet()) {
            numberOfWordsList.add(new WordStat(entry.getKey(), entry.getValue()));
            charCount += (entry.getKey() * entry.getValue());
            if (entry.getValue() == maxValueInMap) {
                maxKey.add(entry.getKey());
            }
        }

        String average = average(charCount, wordCount);
        System.out.println(getResults(wordCount, average, numberOfWordsList, maxKey, maxValueInMap));

    }

    /**
     * Gets the text file that is in the root folder however you can use a relative path to the file to get it or the directory.
     * Checks are made to make sure that its a file of type txt.
     * If the file doesn't exist or it's not the correct file type you are prompted to type it again.
     * @param in System input as an InputStream which will allow the scanner to take user input.
     * @param out System out put as a PrintStream so that the user will receive a message with instructions on what to do.
     * @return file that has been requested
     */
    public static File getFile(InputStream in, PrintStream out) {
        boolean validFile = false;
        File file = null;
        Scanner input = new Scanner(in);
        while (!validFile) {
            out.println("Please enter text file path: ");
            String source = input.nextLine().trim();
            String extension = getExtension(source);

            if ("txt".equals(extension)) {
                file = new File(source);
                if (file.exists() && !file.isDirectory()) {
                    validFile = true;
                    input.close();
                } else {
                    out.println("File does not exist!");
                }
            } else {
                out.println("Not a valid file format!");
            }
        }
        return file;
    }

    /**
     * Grabs the characters after the period and assuming that this is the extension
     * @param fileName name of the file with the extension
     * @return extension of the file or null if no extension provided
     */
    public static String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i >= 0) {
            return fileName.substring(i + 1);
        }
        return null;
    }

    /**
     * Calculates the average and also rounds the average to three decimal places using the method roundAverage
     * @param character number of characters int he text file
     * @param words number of words in text file
     * @return average word length
     */
    public String average(int character, int words) {
        double average = (double) character / (double) words;
        return formatDecimalThreeDP(average);
    }

    /**
     * Formats the input to three decimal places
     * @param number the double that needs to be rounded
     * @return the input rounded to three decimal places as a String
     */
    public static String formatDecimalThreeDP(double number) {
        DecimalFormat df = new DecimalFormat("###.###");
        return df.format(number);
    }

    /**
     * Builds the String with all the stats outlined in the specification.
     * @param wordCount Total number of words
     * @param average Average word length
     * @param wordStats List of word length associated with its tally in textual form
     * @param maxKey List of word lengths with largest frequency
     * @param maxValueInMap Largest frequency
     * @return string that has all the required output
     */
    public static String getResults(int wordCount, String average, List<WordStat> wordStats, List<Integer> maxKey, int maxValueInMap) {
        StringBuilder result = new StringBuilder();
        result.append("Word count = ").append(wordCount).append("\n");
        result.append("Average word length = ").append(average).append("\n");
        for (WordStat wordStat : wordStats) {
            result.append(wordStat).append("\n");
        }
        String maxKeysList = maxKey.toString();
        String withoutSym = maxKeysList.substring(1, maxKeysList.length() - 1);
        result.append("The most frequently occurring word length is ").append(maxValueInMap).append(", for word lengths of ").append(withoutSym.replace(",", " &"));
        return result.toString();
    }

    /**
     * Cleans the word by removing any punctuation however only allowing the ones that were included in the example so that includes "&" and "/".
     * Any further inclusions can be added to the variable WORD_PATTERN
     * @param value string to be cleansed
     * @return cleansed string
     */
    public static String getCleansedString(String value) {
        return value.replaceAll(WORD_PATTERN, "");
    }
}