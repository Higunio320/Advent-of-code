package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public final class CamelPoker {

    private static final String PATH_TO_INPUT = "src/day7/input";

    private static final Logger logger = Logger.getLogger(CamelPoker.class.getName());

    public static void main(String[] args) {
        String solution = String.valueOf(solution());
        logger.info(solution);
    }

    private CamelPoker() {

    }

    private static long solution() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH_TO_INPUT,
                StandardCharsets.UTF_8))) {

            String line = bufferedReader.readLine();
            List<Hand> handList = new ArrayList<>();

            while(line != null) {
                String[] values = line.split(" ");
                Hand current = new Hand(values[0], Long.parseLong(values[1]));
                handList.add(current);
                line = bufferedReader.readLine();
            }

            long sum = 0;
            long counter = 1L;

            Collections.sort(handList);

            for(Hand hand : handList) {
                sum += hand.getBid() * counter++;
            }

            return sum;

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return -1L;
        }
    }
}
