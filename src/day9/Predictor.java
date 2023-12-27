package day9;

import day8.MapReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Predictor {

    private static final String PATH_TO_INPUT = "src/day9/input";

    private static final Logger logger = Logger.getLogger(Predictor.class.getName());

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private Predictor() {

    }

    private static long solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();

            long sum = 0;

            while(line != null) {
                sum += predictNext(line);
                line = bufferedReader.readLine();
            }

            return sum;
        } catch(Exception e) {
            logger.warning(e.getMessage());
            return -1L;
        }
    }

    private static long predictNext(String line) {
        String[] values = line.trim().split(" ");
        List<Long> numbers = Arrays.stream(values).mapToLong(Long::parseLong).boxed().toList();

        int sum = 0;

        while(!allZeroes(numbers)) {
            sum += numbers.get(numbers.size() - 1);

            List<Long> finalNumbers = numbers;

            numbers = IntStream.range(1, numbers.size())
                    .mapToObj(i -> finalNumbers.get(i) - finalNumbers.get(i - 1))
                    .toList();
        }

        return sum;
    }

    private static boolean allZeroes(List<Long> numbers) {
        return numbers.stream().allMatch(number -> number == 0);
    }
}
