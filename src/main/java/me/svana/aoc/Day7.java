package me.svana.aoc;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.NoSuchElementException;

public class Day7 {

    private static final int GREATER = 1;
    private static final int LESS = -1;
    private static final int EQUAL = 0;

    private static class Hand implements Comparable<Hand> {

        private char[] cards;
        private int score;
        private HashMap<Character, Integer> cardCounts;

        public Hand(char[] cards, int score) {
            this.cards = cards;
            this.score = score;
            this.cardCounts = this.countCards();
        }

        public char[] getCards() {
            return cards;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(Hand other) {
            int thisRank = this.handRank();
            int otherRank = other.handRank();

            if (thisRank > otherRank) {
                return GREATER;
            }

            if (thisRank < otherRank) {
                return LESS;
            }

            for (int i = 0; i < this.cards.length; i++) {
                if (Hand.cardScore(cards[i]) > Hand.cardScore(other.getCards()[i])) {
                    return GREATER;
                }

                if (Hand.cardScore(this.cards[i]) < Hand.cardScore(other.getCards()[i])) {
                    return LESS;
                }
            }

            return EQUAL;
        }

        public int handRank() {
            if (this.cardCounts.containsValue(5)) {
                return 7;
            } else if (this.cardCounts.containsValue(4)) {
                return 6;
            } else if (this.cardCounts.containsValue(3) && this.cardCounts.containsValue(2)) {
                return 5;
            } else if (this.cardCounts.containsValue(3)) {
                return 4;
            } else if (this.cardCounts.entrySet().stream().filter((e) -> e.getValue() == 2).count() == 2) {
                return 3;
            } else if (this.cardCounts.containsValue(2)) {
                return 2;
            }

            return 1;
        }

        private HashMap<Character, Integer> countCards() {
            int jCount = 0;

            HashMap<Character, Integer> cardCounts = new HashMap<Character, Integer>();

            for (char card : this.cards) {
                if (card == 'J') {
                    jCount++;
                }

                if (cardCounts.containsKey(card)) {
                    cardCounts.put(card, cardCounts.get(card) + 1);
                } else {
                    cardCounts.put(card, 1);
                }
            }

            // Following code implements task 2
            int maxCount;

            try {
                maxCount = Collections.max(cardCounts.entrySet().stream().filter((e) -> e.getKey() != 'J')
                        .map((e) -> e.getValue()).toList());
            } catch (NoSuchElementException e) {
                maxCount = 5;
            }

            for (char card : cardCounts.keySet()) {
                if (cardCounts.get(card) == maxCount && card != 'J') {
                    cardCounts.put(card, cardCounts.get(card) + jCount);
                    cardCounts.remove('J');
                    break;
                }

            }

            return cardCounts;
        }

        private static int cardScore(char card) {
            switch (card) {
                case 'A':
                    return 14;
                case 'K':
                    return 13;
                case 'Q':
                    return 12;
                case 'J':
                    return 1; // Change to 10 for task 1
                case 'T':
                    return 10;
                default:
                    return Character.getNumericValue(card);
            }
        }
    }

    private static final String inputPath = "inputs/7.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] splitLine;
        ArrayList<Hand> hands = new ArrayList<Hand>();

        while ((line = bufferedReader.readLine()) != null) {
            splitLine = line.split(" ");
            hands.add(new Hand(splitLine[0].toCharArray(), Integer.parseInt(splitLine[1])));
        }

        bufferedReader.close();
        Collections.sort(hands);

        int sum = 0;

        for (int i = 0; i < hands.size(); i++) {
            sum += hands.get(i).getScore() * (i + 1);
        }

        System.out.println(sum);
    }
}
