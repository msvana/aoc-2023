package me.svana.aoc;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day4 {

    private static final String inputPath = "inputs/4.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line, numbersPart, winningNumbersPart, cardNumbersPart;
        ArrayList<Integer> winningNumbers = new ArrayList<>();
        ArrayList<Integer> cardNumbers = new ArrayList<>();
        HashMap<Integer, Integer> cardCopies = new HashMap<>();
        int cardNo = 1, cardPoints = 0, cardPointsSum = 0, currentCardCopies, nextCard;
        int numCards = 0;

        while ((line = bufferedReader.readLine()) != null) {
            numbersPart = line.split(":")[1];
            winningNumbersPart = numbersPart.split("\\|")[0].trim();
            cardNumbersPart = numbersPart.split("\\|")[1].trim();

            for (String n : winningNumbersPart.split(" ")) {
                try {
                    winningNumbers.add(Integer.parseInt(n));
                } catch (NumberFormatException e) {
                }
            }

            for (String n : cardNumbersPart.split(" ")) {
                try {
                    cardNumbers.add(Integer.parseInt(n));
                } catch (NumberFormatException e) {
                }
            }

            currentCardCopies = cardCopies.getOrDefault(cardNo, 0) + 1;
            numCards += currentCardCopies;

            for (int j = 0; j < currentCardCopies; j++) { // Removing this outer loop will give the answer for part 1
                nextCard = cardNo + 1;
                for (int i = 0; i < cardNumbers.size(); i++) {
                    if (winningNumbers.contains(cardNumbers.get(i))) {
                        if (cardCopies.containsKey(nextCard)) {
                            cardCopies.put(nextCard, cardCopies.get(nextCard) + 1);
                        } else {
                            cardCopies.put(nextCard, 1);
                        }

                        nextCard++;

                        if (cardPoints == 0) {
                            cardPoints = 1;
                        } else {
                            cardPoints *= 2;
                        }
                    }
                }
            }

            cardPointsSum += cardPoints;
            cardPoints = 0;
            cardNo++;
            winningNumbers.clear();
            cardNumbers.clear();
        }

        bufferedReader.close();
        System.out.println(cardPointsSum);
        System.out.println(numCards);
    }
}
