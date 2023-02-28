import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CipherText {


    private final String ct;
    private final double[] ctLetterFrequencies;
    private final double[] letterFrequenciesInText = {
            8.2, 1.5, 2.8, 4.3, 13.0, 2.2, 2.0, 6.1, 7.0,
            0.15, 0.77, 4.0, 2.4, 6.7, 7.5, 1.9, 0.095, 6.0,
            6.3, 9.1, 2.8, 0.98, 2.4, 0.15, 2.0, 0.074
    };
    private final double[] letterFrequenciesInDict = {
            7.8, 2.0, 4.0, 3.8, 11.0, 1.4, 3.0, 2.3, 8.6,
            0.21, 0.97, 5.3, 2.7, 7.2, 6.1, 2.8, 0.19, 7.3,
            8.7, 6.7, 3.3, 1.0, 0.91, 0.27, 1.6, 0.44
    };

    private final Set<String> commonStopWords = new HashSet<>(Arrays.asList(
            "a", "an", "and", "are", "as", "at", "be",
            "for", "from", "has", "he", "in", "is", "it",
            "of", "on", "that", "the", "to", "with"
    ));


    public CipherText(String ct) {
        this.ct = ct;
        this.ctLetterFrequencies = calculateLetterFrequencies();
    }


    public int getWordCount() {
        return ct.split(" ").length;
    }


    public int getStopWordCount() {
        String[] allWords = ct.split(" ");
        int stopWordCounter = 0;
        for (String word : allWords) {
            if (commonStopWords.contains(word)) stopWordCounter++;
        }
        return stopWordCounter;
    }


    private double[] calculateLetterFrequencies() {
        double[] letterFrequencies = new double[26];
        int[] letterCounts = new int[26];
        int totalLetterCounter = 0;
        Arrays.fill(letterFrequencies, 0);
        Arrays.fill(letterCounts, 0);
        for (int i = 0; i < ct.length(); i++) {
            int ascii = Character.toLowerCase(ct.charAt(i));
            if ((ascii < 97) || (ascii > 122)) continue;
            letterCounts[ascii - 97]++;
            totalLetterCounter++;
        }

        for (int i = 0; i < 26; i++) {
            letterFrequencies[i] = (double) letterCounts[i] / totalLetterCounter;
        }

        return letterFrequencies;
    }


    private double calcElementWiseDifference(double[] x, double[] y) {
        double[] elementwiseDifference = new double[26];
        Arrays.fill(elementwiseDifference, 0);
        for (int i = 0; i < 26; i++) {
            double diff = Math.abs(x[i] - y[i]);
            elementwiseDifference[i] += diff;
        }

        return Arrays.stream(elementwiseDifference).sum() / 26;
    }


    private double calcCorrelationCoefficient(double[] x, double[] y) {
        double mean_x = Arrays.stream(x).sum() / 26;
        double mean_y = Arrays.stream(y).sum() / 26;

        double sum_N = 0;
        double sum_X = 0;
        double sum_Y = 0;

        for (int i = 0; i < 26; i++) {
            sum_N += (x[i] - mean_x) * (y[i] - mean_y);
            sum_X += Math.pow(x[i] - mean_x, 2);
            sum_Y += Math.pow(y[i] - mean_y, 2);
        }

        return sum_N / (Math.sqrt(sum_X) * Math.sqrt(sum_Y));
    }


    private double calcEuclideanDistance(double[] x, double[] y) {
        double sum = 0;
        for (int i = 0; i < 26; i++) {
            sum += Math.pow(x[i] - y[i], 2);
        }
        return Math.sqrt(sum);
    }
}
