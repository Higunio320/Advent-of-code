package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;


public final class Trebuchet {

    private static final String PATH_TO_INPUT = "src/day1/input";


    private static final Logger logger = Logger.getLogger(Trebuchet.class.getName());

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private Trebuchet() {
    }

    private static int solution() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {

            int sum = 0;

            String line = bufferedReader.readLine();

            while(line != null) {
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
        char first = '0';
        char second = '0';

        for(char c: line) {
            if(c >= '0' && c <= '9') {
                first = c;
                break;
            }
        }

        for(int i = line.length - 1; i >= 0; i--) {
            if(line[i] >= '0' && line[i] <= '9') {
                second = line[i];
                break;
            }
        }

        return 10*(first - '0') + (second - '0');

    }
}
