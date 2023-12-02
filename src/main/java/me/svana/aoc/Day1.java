package me.svana.aoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day1 {
    private static String[] digitWords = {
            "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine" };

    private static final String inputPath = "inputs/1.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int sum = 0;

        while ((line = bufferedReader.readLine()) != null) {
            StringBuilder transformedLineBuilder = new StringBuilder();
            StringBuilder charBuffer = new StringBuilder();
            String builtString = "";

            for (char ch : line.toCharArray()) {
                if (Character.isDigit(ch)) {
                    transformedLineBuilder.append(ch);
                    continue;
                }

                charBuffer.append(ch);
                builtString = charBuffer.toString();

                for (int i = 0; i < digitWords.length; i++) {
                    if (builtString.endsWith(digitWords[i])) {
                        transformedLineBuilder.append(i + 1);
                    }
                }
            }

            line = transformedLineBuilder.toString();
            char firstDigit = 0, lastDigit = 0;

            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    if (firstDigit == 0) {
                        firstDigit = c;
                    }

                    lastDigit = c;
                }
            }

            sum += (firstDigit - 48) * 10 + (lastDigit - 48);
        }

        System.out.println(sum);
        bufferedReader.close();
    }
}
