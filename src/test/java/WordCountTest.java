import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class WordCountTest {

    @Test
    public void testGetFile() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        PrintStream sysOutBackup = System.out;
        ByteArrayInputStream in = new ByteArrayInputStream("src/test/java/test.txt".getBytes());
        File file = new File("src/test/java/test.txt");
        assertEquals(file, WordCount.getFile(in, System.out));
    }

    @Test
    public void testGetExtension() {
        assertEquals("txt", WordCount.getExtension("test.txt"));
        assertEquals("zip", WordCount.getExtension("test.zip"));
        assertEquals("rar", WordCount.getExtension("test.rar"));
        assertEquals("", WordCount.getExtension("test."));
        assertEquals(null, WordCount.getExtension("test"));
    }

    @Test
    public void testFormatDecimalThreeDP() {

        assertEquals("1.111", WordCount.formatDecimalThreeDP(1.1111));
        assertEquals("1.112", WordCount.formatDecimalThreeDP(1.1119));
        assertEquals("2", WordCount.formatDecimalThreeDP(1.9999999));
        assertEquals("0.1", WordCount.formatDecimalThreeDP(0.1));
        assertEquals("-1.023", WordCount.formatDecimalThreeDP(-1.02334));
        assertEquals("0", WordCount.formatDecimalThreeDP(0));
    }

    @Test
    public void testGetResults() {
        int wordCount = 10;
        String average = "20";
        List<WordStat> wordStats = new ArrayList<>();
        List<Integer> maxKey = new ArrayList<>();
        wordStats.add(new WordStat(6, 9));
        wordStats.add(new WordStat(4, 10));
        maxKey.add(4);
        int maxValueInMap = 10;

        String expected = "Word count = 10\n" +
                "Average word length = 20\n" +
                "Number of words of length 6 is 9\n" +
                "Number of words of length 4 is 10\n" +
                "The most frequently occurring word length is 10, for word lengths of 4";

        assertEquals(expected, WordCount.getResults(wordCount, average, wordStats, maxKey, maxValueInMap));
    }

    @Test
    public void testGetCleansedString() {
        assertEquals("Hello World", WordCount.getCleansedString("Hello World!"));
        assertEquals(" ", WordCount.getCleansedString(" "));
        assertEquals("&", WordCount.getCleansedString("!@£$$%£%^%&*%"));
        assertEquals("29/08/1997", WordCount.getCleansedString("29/08/1997"));

    }


}