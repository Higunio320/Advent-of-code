package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public final class GearsPartTwo {

    private static final String PATH_TO_INPUT = "src/day3/input";

    private static final Logger logger = Logger.getLogger(GearsPartTwo.class.getName());

    private GearsPartTwo() {
    }

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private static int solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {
            char[] line1;
            char[] line2 = bufferedReader.readLine().toCharArray();
            char[] line3 = bufferedReader.readLine().toCharArray();

            String line = bufferedReader.readLine();

            int sum = checkFirst(line2, line3);

            while(line != null) {
                line1 = line2;
                line2 = line3;
                line3 = line.toCharArray();
                sum += check(line1, line2, line3);
                line = bufferedReader.readLine();
            }

            sum += checkFirst(line3, line2);

            return sum;

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1;
        }
    }

    private static int checkFirst(char[] line1, char[] line2) {
        int i = 0;
        int sum = 0;
        while(i < line1.length) {
            while(i < line1.length && notSymbol(line1[i])) {
                i++;
            }
            if(i == line1.length) {
                return 0;
            }
            Collection<Integer> numbers = new ArrayList<>();

            if (i > 0) {
                if (isNumber(line1[i - 1])) {
                    numbers.add(registerNumber(line1, i - 1));
                }
                if (isNumber(line2[i - 1])) {
                    numbers.add(registerNumber(line2, i - 1));
                }
            }
            if (i < line1.length) {
                if (isNumber(line1[i + 1])) {
                    numbers.add(registerNumber(line1, i + 1));
                }
                if (isNumber(line2[i + 1])) {
                    numbers.add(registerNumber(line2, i + 1));
                }
            }
            if (isNumber(line2[i])) {
                numbers.add(registerNumber(line2, i + 1));
            }
            if(line1[i] == '*' && numbers.size() == 2) {
                sum += numbers.stream().reduce(1, (a, b) -> a * b);
            }
            i++;
        }
        return sum;
    }

    private static int check(char[] line1, char[] line2, char[] line3) {
        int i = 0;
        int sum = 0;
        while(i < line2.length) {
            while(i < line2.length && notSymbol(line2[i])) {
                i++;
            }
            if(i == line2.length) {
                return sum;
            }
            if(line2[i] != '*') {
                i++;
                continue;
            }
            Collection<Integer> numbers = new ArrayList<>();
            if(i > 0) {
                if(isNumber(line1[i-1])) {
                    numbers.add(registerNumber(line1, i-1));
                }
                if(isNumber(line2[i-1])) {
                    numbers.add(registerNumber(line2, i-1));
                }
                if(isNumber(line3[i-1])) {
                    numbers.add(registerNumber(line3, i-1));
                }
            }
            if(i < line2.length) {
                if(isNumber(line1[i+1])) {
                    numbers.add(registerNumber(line1, i+1));
                }
                if(isNumber(line2[i+1])) {
                    numbers.add(registerNumber(line2, i+1));
                }
                if(isNumber(line3[i+1])) {
                    numbers.add(registerNumber(line3, i+1));
                }
            }
            if(isNumber(line1[i])) {
                numbers.add(registerNumber(line1, i));
            }
            if(isNumber(line3[i])) {
                numbers.add(registerNumber(line3, i));
            }
            if(numbers.size() == 2) {
                sum += numbers.stream().reduce(1, (a, b) -> a * b);
            }
            i++;
        }

        return sum;
    }

    private static boolean notSymbol(char c) {
        return c == '.' || isNumber(c);
    }

    private static int registerNumber(char[] line, int index) {
        int i = index;
        while(i >= 0 && line[i] >= '0' && line[i] <= '9') {
            i--;
        }
        i++;
        StringBuilder stringBuilder = new StringBuilder();
        while(i < line.length && line[i] >= '0' && line[i] <= '9') {
            stringBuilder.append(line[i]);
            line[i] = '.';
            i++;
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }
}
