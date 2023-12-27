package day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class PredictorPartTwo {

    private static final String PATH_TO_INPUT = "src/day9/input";

    private static final Logger logger = Logger.getLogger(PredictorPartTwo.class.getName());

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private PredictorPartTwo() {

    }

    private static long solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();
            long sum = 0;

            while(line != null) {
                sum += predictPrevious(line);
                line = bufferedReader.readLine();
            }

            return sum;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1L;
        }
    }

    private static long predictPrevious(String line) {
        String[] values = line.trim().split(" ");
        List<Long> numbers = Arrays.stream(values)
                .mapToLong(Long::parseLong)
                .boxed().toList();


        List<Long> left = new ArrayList<>(10);

        while(!allZeroes(numbers)) {
            left.add(numbers.get(0));

            List<Long> finalNumbers = numbers;

            numbers = IntStream.range(1, numbers.size())
                    .mapToObj(i -> finalNumbers.get(i) - finalNumbers.get(i - 1))
                    .toList();
        }

        long current = 0;

        for(int i = left.size() - 1; i >= 0; i--) {
            current = left.get(i) - current;
        }

        return current;
    }

    private static boolean allZeroes(List<Long> numbers) {
        return numbers.stream().allMatch(number -> number == 0);
    }
}
