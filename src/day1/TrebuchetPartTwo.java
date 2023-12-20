package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.logging.Logger;

public final class TrebuchetPartTwo {

    private static final String PATH_TO_INPUT = "src/day1/input";

    private static final Logger logger = Logger.getLogger(TrebuchetPartTwo.class.getName());

    private static final String[] numbers = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private TrebuchetPartTwo() {
    }

    private static int solution(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();

            int sum = 0;

            while(line != null) {
                line = line.strip();
                sum += readSum(line.toCharArray());
                line = bufferedReader.readLine();
            }


            return sum;
        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1;
        }
    }

    private static int readSum(char[] line) {
        int first = 0;
        int second = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for(char c: line) {
            if(c >= '0' && c <= '9') {
                first = c - '0';
                break;
            }
            stringBuilder.append(c);
            if(equalsNumber(stringBuilder.toString())) {
                first = Arrays.asList(numbers).indexOf(stringBuilder.toString()) + 1;
                break;
            }
            while(!isPrefix(stringBuilder.toString())) {
                stringBuilder.deleteCharAt(0);
            }
        }

        stringBuilder = new StringBuilder();

        for(int i = line.length - 1; i >= 0; i--) {
            char c = line[i];

            if(c >= '0' && c <= '9') {
                second = c - '0';
                break;
            }
            stringBuilder.append(c);
            stringBuilder.reverse();
            if(equalsNumber(stringBuilder.toString())) {
                second = Arrays.asList(numbers).indexOf(stringBuilder.toString()) + 1;
                break;
            }
            while(!isSuffix(stringBuilder.toString())) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            stringBuilder.reverse();
        }

        return 10*first + second;
    }

    private static boolean isPrefix(String s) {
        int length = s.length();

        for(String number: numbers) {
            if(length > number.length()) {
                continue;
            }

            if(s.equals(number.substring(0, length))) {
                return true;
            }
        }

        return false;
    }

    private static boolean isSuffix(String s) {
        int length = s.length();

        for(String number: numbers) {
            if(length > number.length()) {
                continue;
            }

            if(s.equals(number.substring(number.length() - length))) {
                return true;
            }
        }

        return false;
    }

    private static boolean equalsNumber(String s) {
        for(String number: numbers) {
            if(s.equals(number)) {
                return true;
            }
        }

        return false;
    }
}
