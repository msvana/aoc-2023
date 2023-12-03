package me.svana.aoc;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

public class Day3 {

    private static final String inputPath = "inputs/3.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> symbols = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer[]>> numbersByRows = new HashMap<>();
        HashMap<Integer[], Integer> numbers = new HashMap<>();
        ArrayList<Integer[]> potentialGears = new ArrayList<>();

        int currentCharNumber, number;
        char currentChar;
        int x = 0, y = 0, xStart = 0, sum = 0;
        boolean inNumber = false;
        StringBuilder numberBuilder = new StringBuilder();

        while ((currentCharNumber = bufferedReader.read()) != -1) {
            currentChar = (char) currentCharNumber;

            if (!Character.isDigit(currentChar)) {
                if (inNumber) {
                    number = Integer.parseInt(numberBuilder.toString());
                    numberBuilder = new StringBuilder();
                    numbers.put(new Integer[] { xStart, x, y }, number);

                    if (!numbersByRows.containsKey(y)) {
                        numbersByRows.put(y, new ArrayList<Integer[]>());
                    }

                    numbersByRows.get(y).add(new Integer[] { xStart, x, number });
                    inNumber = false;
                }

                if (currentChar != '.' && currentChar != '\n') {
                    symbols.add(x + "," + y);
                }

                if (currentChar == '*') {
                    potentialGears.add(new Integer[] { x, y });
                }

            } else if (Character.isDigit(currentChar)) {
                if (!inNumber) {
                    xStart = x;
                }

                inNumber = true;
                numberBuilder.append(currentChar);
            }

            if (currentChar == '\n') {
                y++;
                x = 0;
                continue;
            }

            x++;
        }

        for (Map.Entry<Integer[], Integer> n : numbers.entrySet()) {
            mainloop: for (int xx = n.getKey()[0] - 1; xx <= n.getKey()[1]; xx++) {
                for (int yy = n.getKey()[2] - 1; yy <= n.getKey()[2] + 1; yy++) {
                    if (symbols.contains(xx + "," + yy)) {
                        System.out.println(n.getValue());
                        sum += n.getValue();
                        break mainloop;
                    }
                }
            }
        }

        Integer[] g;
        int surroundingNumbers = 0, product = 1, productSum = 0;

        for (int i = 0; i < potentialGears.size(); i++) {
            g = potentialGears.get(i);
            gearloop: for (int yy = g[1] - 1; yy <= g[1] + 1; yy++) {
                if (numbersByRows.containsKey(yy)) {
                    ArrayList<Integer[]> numsInRow = numbersByRows.get(yy);
                    for(int j = 0; j < numsInRow.size(); j++) {
                        Integer[] n = numsInRow.get(j);
                        if (g[0] >= n[0] - 1 && g[0] <= n[1]) {
                            surroundingNumbers++;
                            product *= n[2];
                            if (surroundingNumbers > 2) {
                                break gearloop;
                            }
                        }
                    }
                }
            }

            if(surroundingNumbers == 2) {
                productSum += product;
            }

            surroundingNumbers = 0; product = 1;

        }

        System.out.println(sum);
        System.out.println(productSum);
        bufferedReader.close();
    }
}
