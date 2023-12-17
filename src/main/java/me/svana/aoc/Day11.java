package me.svana.aoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day11 {

    private static final String inputPath = "inputs/11.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<Character[]> galaxyMap = new ArrayList<>();
        ArrayList<Integer[]> galaxyPositions = new ArrayList<>();
        ArrayList<Integer> expandedCols = new ArrayList<>();
        ArrayList<Integer> expandedRows = new ArrayList<>();
        String line;
        long distanceSum = 0;

        while ((line = bufferedReader.readLine()) != null) {
            Character[] row = new Character[line.length()];

            for (int i = 0; i < line.length(); i++) {
                row[i] = line.charAt(i);
            }

            galaxyMap.add(row);

            if (!line.contains("#")) {
                expandedCols.add(galaxyMap.size() - 1);
            }
        }

        bufferedReader.close();

        galaxyMap = transposeMap(galaxyMap);
        ArrayList<Character[]> extendedMap = new ArrayList<>();
        boolean containsGalaxy = false;

        for (int i = 0; i < galaxyMap.size(); i++) {
            Character[] row = new Character[galaxyMap.get(i).length];
            containsGalaxy = false;

            for (int j = 0; j < row.length; j++) {
                row[j] = galaxyMap.get(i)[j];

                if (row[j] == '#') {
                    containsGalaxy = true;
                }
            }

            extendedMap.add(row);

            if (!containsGalaxy) {
                expandedRows.add(i);
            }
        }

        for (int i = 0; i < extendedMap.size(); i++) {
            for (int j = 0; j < extendedMap.get(i).length; j++) {
                if (extendedMap.get(i)[j] == '#') {
                    galaxyPositions.add(new Integer[] { i, j });
                }
            }
        }

        for (int a = 0; a < galaxyPositions.size(); a++) {
            for (int b = a + 1; b < galaxyPositions.size(); b++) {
                Integer[] posA = galaxyPositions.get(a);
                Integer[] posB = galaxyPositions.get(b);

                int xDiff = differenceWithExpansion(posA[0], posB[0], expandedRows);
                int yDiff = differenceWithExpansion(posA[1], posB[1], expandedCols);

                distanceSum += xDiff + yDiff;
            }
        }

        System.out.println(distanceSum);
    }

    private static ArrayList<Character[]> transposeMap(ArrayList<Character[]> map) {
        ArrayList<Character[]> transposedMap = new ArrayList<>();

        for (int i = 0; i < map.get(0).length; i++) {
            Character[] row = new Character[map.size()];

            for (int j = 0; j < map.size(); j++) {
                row[j] = map.get(j)[i];
            }

            transposedMap.add(row);
        }

        return transposedMap;
    }

    private static int differenceWithExpansion(int a, int b, ArrayList<Integer> expanded) {
        int difference = 0;

        for (int i = Math.min(a, b); i < Math.max(a, b); i++) {
            if (expanded.contains(i)) {
                difference += 1000000;
            } else {
                difference++;
            }
        }

        return difference;

    }
}
