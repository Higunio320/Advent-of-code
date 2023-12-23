package day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandPartTwo implements Comparable<HandPartTwo>{

    private static final ArrayList<Character> symbols = new ArrayList<>(Arrays.stream(
            new Character[]{'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'}).toList());

    private final String hand;
    private final long bid;

    private final int rank;

    public HandPartTwo(String hand, long bid) {
        this.hand = hand;
        this.bid = bid;
        rank = calculateRank();
    }

    public final int getRank() {
        return this.rank;
    }

    public final String getHand() {
        return this.hand;
    }

    public final long getBid() {
        return this.bid;
    }

    private int calculateRank() {
        Map<Character, Integer> map = new HashMap<>(8);
        int jCount = 0;

        for(char c : this.hand.toCharArray()) {
            if(c == 'J') {
                jCount++;
            } else {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }

        List<Integer> values = new ArrayList<>(map.values().stream().toList());

        Collections.sort(values);

        return switch((!values.isEmpty() ? values.get(values.size() - 1) : 0) + jCount) {
            case 5 -> 7;
            case 4 -> 6;
            case 3 -> {
                if(values.size() == 2) {
                    yield 5;
                } else {
                    yield 4;
                }
            }
            case 2 -> {
                if(values.size() == 3) {
                    yield 3;
                } else {
                    yield 2;
                }
            }
            case 1 -> 1;
            default -> 0;
        };
    }

    @Override
    public int compareTo(HandPartTwo o) {
        if(o.rank != this.rank) {
            return this.rank - o.rank;
        } else {
            int i = 0;
            while(i < hand.length() && hand.charAt(i) == o.hand.charAt(i)) {
                i++;
            }
            if(i == hand.length()) {
                return 0;
            } else {
                return symbols.indexOf(o.hand.charAt(i)) - symbols.indexOf(hand.charAt(i));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(!(o instanceof HandPartTwo hand)) {
            return false;
        }

        return hand.hand.equals(this.hand);
    }

}
