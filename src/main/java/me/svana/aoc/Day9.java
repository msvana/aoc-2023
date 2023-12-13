package me.svana.aoc;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Day9 {

    private static final String inputPath = "inputs/9.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String[] splitLine;
        ArrayList<int[]> sequences;
        int[] sequence, difference;
        int nextNumber, sum = 0;

        while ((line = bufferedReader.readLine()) != null) {
            splitLine = line.split(" ");
            sequences = new ArrayList<int[]>();
            sequence = new int[splitLine.length];

            for (int i = 0; i < splitLine.length; i++) {
                sequence[i] = Integer.parseInt(splitLine[i]);
            }

            sequences.add(sequence);

            while (isAllZeros(sequence) == false) {
                difference = new int[sequence.length - 1];
                for (int i = 0; i < sequence.length - 1; i++) {
                    difference[i] = sequence[i + 1] - sequence[i];
                }

                sequences.add(difference);
                sequence = difference;
            }

            nextNumber = 0;

            for (int i = sequences.size() - 2; i >= 0; i--) {
                sequence = sequences.get(i);
                // nextNumber = sequence[sequence.length - 1] + nextNumber; // Part 1
                nextNumber = sequence[0] - nextNumber; // Part 2
            }

            sum += nextNumber;
        }

        System.out.println(sum);
        bufferedReader.close();
    }

    private static boolean isAllZeros(int[] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
