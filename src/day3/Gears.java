package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public final class Gears {

    private static final String PATH_TO_INPUT = "src/day3/input";

    private static final Logger logger = Logger.getLogger(Gears.class.getName());

    private Gears() {
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
            if(i > 0) {
                if(isNumber(line1[i-1])) {
                    sum += registerNumber(line1, i - 1);
                }
                if(isNumber(line2[i-1])) {
                    sum += registerNumber(line2, i-1);
                }
            }
            if(i < line1.length) {
                if(isNumber(line1[i+1])) {
                    sum += registerNumber(line1, i+1);
                }
                if(isNumber(line2[i+1])) {
                    sum += registerNumber(line2, i+1);
                }
            }
            if(isNumber(line2[i])) {
                sum += registerNumber(line2, i+1);
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
            if(i > 0) {
                if(isNumber(line1[i-1])) {
                    sum += registerNumber(line1, i-1);
                }
                if(isNumber(line2[i-1])) {
                    sum += registerNumber(line2, i-1);
                }
                if(isNumber(line3[i-1])) {
                    sum += registerNumber(line3, i-1);
                }
            }
            if(i < line2.length) {
                if(isNumber(line1[i+1])) {
                    sum += registerNumber(line1, i+1);
                }
                if(isNumber(line2[i+1])) {
                    sum += registerNumber(line2, i+1);
                }
                if(isNumber(line3[i+1])) {
                    sum += registerNumber(line3, i+1);
                }
            }
            if(isNumber(line1[i])) {
                sum += registerNumber(line1, i);
            }
            if(isNumber(line3[i])) {
                sum += registerNumber(line3, i);
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
