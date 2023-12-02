package me.svana.aoc;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Day2 {

    private static final String inputPath = "inputs/2.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] cubesRevealed;
        String[] revInfo;
        int gameId = 1, sum = 0, powerSum = 0;
        int maxRed, maxGreen, maxBlue, power;
        boolean valid = true;

        while ((line = bufferedReader.readLine()) != null) {
            maxRed = maxGreen = maxBlue = 0;
            cubesRevealed = line.split(":")[1].split("[;,]");

            for (String rev : cubesRevealed) {
                revInfo = rev.trim().split(" ");

                if (revInfo[1].equals("red")) {
                    maxRed = maxRed > Integer.parseInt(revInfo[0]) ? maxRed : Integer.parseInt(revInfo[0]);
                    if (maxRed > 12) {
                        valid = false;
                    }
                }

                if (revInfo[1].equals("green")) {
                    maxGreen = maxGreen > Integer.parseInt(revInfo[0]) ? maxGreen : Integer.parseInt(revInfo[0]);
                    if (maxGreen > 13) {
                        valid = false;
                    }
                }

                if (revInfo[1].equals("blue")) {
                    maxBlue = maxBlue > Integer.parseInt(revInfo[0]) ? maxBlue : Integer.parseInt(revInfo[0]);
                    if (maxBlue > 14) {
                        valid = false;
                    }
                }
            }

            power = maxRed * maxGreen * maxBlue;
            powerSum += power;

            if (valid) {
                sum += gameId;
            }

            valid = true;
            gameId++;
        }

        bufferedReader.close();
        System.out.println(sum);
        System.out.println(powerSum);
    }
}
