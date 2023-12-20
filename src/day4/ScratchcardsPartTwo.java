package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class ScratchcardsPartTwo {

    private static final String PATH_TO_INPUT = "src/day4/input";

    private static final Logger logger = Logger.getLogger(ScratchcardsPartTwo.class.getName());
    private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");

    private ScratchcardsPartTwo() {
    }

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private static int solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {
            int current = 1;
            int score;
            String line = bufferedReader.readLine();
            Map<Integer, Integer> scoreMap = new HashMap<>(200);
            scoreMap.put(1, 1);

            while(line != null) {
                scoreMap.putIfAbsent(current, 1);

                score = calculateScore(line.split(":")[1].trim());

                for(int i = 1; i <= score; i++) {
                    int nextKey = current + i;
                    scoreMap.put(nextKey, scoreMap.getOrDefault(nextKey, 1) + scoreMap.get(current));
                }
                line = bufferedReader.readLine();
                current++;
            }

            return scoreMap.values().stream().mapToInt(Integer::intValue).sum();

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1;
        }
    }

    private static int calculateScore(String line) {
        String[] game = line.split("\\|");

        List<Integer> winningNumbers = extractNumbers(game[0].trim());
        List<Integer> playerNumbers = extractNumbers(game[1].trim());

        return findMatches(winningNumbers, playerNumbers);
    }

    private static List<Integer> extractNumbers(String numbers) {
        List<Integer> numberList = new ArrayList<>(30);

        String[] numberArray = SPACE_PATTERN.split(numbers);

        for(String number : numberArray) {
            numberList.add(Integer.parseInt(number));
        }

        return numberList;
    }

    private static int findMatches(Collection<Integer> winningNumbers, Iterable<Integer> playerNumbers) {
        int matches = 0;

        for(Integer number : playerNumbers) {       //not the most efficient way to find matches, but works on small scale
            if(winningNumbers.contains(number)) {
                matches++;
            }
        }

        return matches;
    }
}
