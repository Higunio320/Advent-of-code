package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class Scratchcards {

    private static final String PATH_TO_INPUT = "src/day4/input";

    private static final Logger logger = Logger.getLogger(Scratchcards.class.getName());
    private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");

    private Scratchcards() {
    }

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private static int solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {
            int sum = 0;
            String line = bufferedReader.readLine();

            while(line != null) {
                sum += calculateScore(line.split(":")[1].trim());
                line = bufferedReader.readLine();
            }

            return sum;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1;
        }

    }

    private static int calculateScore(String line) {
        String[] game = line.split("\\|");

        List<Integer> winningNumbers = extractNumbers(game[0].trim());
        List<Integer> playerNumbers = extractNumbers(game[1].trim());


        int matches = findMatches(winningNumbers, playerNumbers) - 1;

        return matches != -1 ? 1 << matches : 0;

    }

    private static List<Integer> extractNumbers(String numbers) {
        List<Integer> numberList = new ArrayList<>(10);

        String[] numberArray = SPACE_PATTERN.split(numbers);

        for(String number : numberArray) {
            numberList.add(Integer.parseInt(number));
        }

        return numberList;
    }

    private static int findMatches(Collection<Integer> winningNumbers, Iterable<Integer> playerNumbers) {
        int matches = 0;

        for(Integer number : playerNumbers) {
            if(winningNumbers.contains(number)) {
                matches++;
            }
        }

        return matches;
    }
}
