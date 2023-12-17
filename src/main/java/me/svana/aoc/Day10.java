package me.svana.aoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Day10 {

    private static final String inputPath = "inputs/10.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        ArrayList<Character[]> map = new ArrayList<Character[]>();
        int x = 0, y = 0;
        Integer start[] = new Integer[5];

        while ((line = bufferedReader.readLine()) != null) {
            Character[] row = new Character[line.length()];

            for (x = 0; x < line.length(); x++) {
                row[x] = line.charAt(x);
                if (row[x] == 'S') {
                    start[0] = x;
                    start[1] = y;
                    start[2] = 0;
                    start[3] = -1;
                    start[4] = -1;
                }
            }

            map.add(row);
            y++;
        }

        ArrayList<Integer[]> path = new ArrayList<>();
        LinkedList<Integer[]> queue = new LinkedList<>();
        char[][] upCombos = {
                { 'S', '|' }, { 'S', 'F' }, { 'S', '7' },
                { '|', '|' }, { '|', 'F' }, { '|', '7' }, { '|', 'S' },
                { 'L', '|' }, { 'L', 'F' }, { 'L', '7' }, { 'L', 'S' },
                { 'J', '|' }, { 'J', 'F' }, { 'J', '7' }, { 'J', 'S' } };
        char[][] downCombos = {
                { 'S', '|' }, { 'S', 'J' }, { 'S', 'L' },
                { '|', '|' }, { '|', 'J' }, { '|', 'L' }, { '|', 'S' },
                { 'F', '|' }, { 'F', 'J' }, { 'F', 'L' }, { 'F', 'S' },
                { '7', '|' }, { '7', 'J' }, { '7', 'L' }, { '7', 'S' } };
        char[][] leftCombos = {
                { 'S', '-' }, { 'S', 'F' }, { 'S', 'L' },
                { '-', '-' }, { '-', 'F' }, { '-', 'L' }, { '-', 'S' },
                { 'J', '-' }, { 'J', 'F' }, { 'J', 'L' }, { 'J', 'S' },
                { '7', '-' }, { '7', 'F' }, { '7', 'L' }, { '7', 'S' } };
        char[][] rightCombos = {
                { 'S', '-' }, { 'S', 'J' }, { 'S', '7' },
                { '-', '-' }, { '-', 'J' }, { '-', '7' }, { '-', 'S' },
                { 'F', '-' }, { 'F', 'J' }, { 'F', '7' }, { 'F', 'S' },
                { 'L', '-' }, { 'L', 'J' }, { 'L', '7' }, { 'L', 'S' } };

        queue.add(start);

        while (!queue.isEmpty()) {
            Integer[] current = queue.poll();
            path.add(new Integer[] { current[0], current[1] });
            Integer[] prevCoords = { current[3], current[4] };

            if (current[0] == start[0] && current[1] == start[1] && current[2] != 0) {
                System.out.println(current[2] / 2);
                break;
            }

            // Can go up
            if (current[1] > 0) {
                char up = map.get(current[1] - 1)[current[0]];
                if (current[1] - 1 != prevCoords[1] || current[0] != prevCoords[0]) {
                    for (char[] combo : upCombos) {
                        if (combo[1] == up && combo[0] == map.get(current[1])[current[0]]) {
                            queue.add(new Integer[] { current[0], current[1] - 1, current[2] + 1, current[0],
                                    current[1] });
                            continue;
                        }
                    }
                }

            }

            // Can go down
            if (current[1] < map.size() - 1) {
                char down = map.get(current[1] + 1)[current[0]];
                if (current[1] + 1 != prevCoords[1] || current[0] != prevCoords[0]) {
                    for (char[] combo : downCombos) {
                        if (combo[1] == down && combo[0] == map.get(current[1])[current[0]]) {
                            queue.add(new Integer[] { current[0], current[1] + 1, current[2] + 1, current[0],
                                    current[1] });
                            continue;
                        }
                    }
                }
            }

            // Can go left
            if (current[0] > 0) {
                char left = map.get(current[1])[current[0] - 1];
                if (current[1] != prevCoords[1] || current[0] - 1 != prevCoords[0]) {
                    for (char[] combo : leftCombos) {
                        if (combo[1] == left && combo[0] == map.get(current[1])[current[0]]) {
                            queue.add(new Integer[] { current[0] - 1, current[1], current[2] + 1, current[0],
                                    current[1] });
                            continue;
                        }
                    }
                }

            }

            // Can go right
            if (current[0] < map.get(current[1]).length - 1) {
                char right = map.get(current[1])[current[0] + 1];
                if (current[1] != prevCoords[1] || current[0] + 1 != prevCoords[0]) {
                    for (char[] combo : rightCombos) {
                        if (combo[1] == right && combo[0] == map.get(current[1])[current[0]]) {
                            queue.add(new Integer[] { current[0] + 1, current[1], current[2] + 1, current[0],
                                    current[1] });
                            continue;
                        }
                    }
                }

            }
        }

        bufferedReader.close();

        printPath(path, map);

        // Part 2
        map.get(start[1])[start[0]] = '7';
        char currentChar, prevSwitch = ' ';
        Character currentRow[];
        int colored = 0;
        boolean color, inPath;
        char[] switchChars = { '|', '7', 'L', 'J', 'F' };

        for (y = 0; y < map.size(); y++) {
            currentRow = map.get(y);
            color = false;
            prevSwitch = ' ';

            for (x = 0; x < currentRow.length; x++) {
                currentChar = currentRow[x];
                inPath = isInPath(path, x, y);

                if (!inPath && color) {
                    colored++;
                }

                if (inPath && charArrayContains(switchChars, currentChar)) {

                    if (prevSwitch == 'L' && currentChar == '7') {
                    } else if (prevSwitch == 'F' && currentChar == 'J') {
                    } else {
                        color = !color;
                    }

                    prevSwitch = currentChar;

                }
            }
        }

        System.out.println(colored);
    }

    private static boolean isInPath(ArrayList<Integer[]> path, int x, int y) {
        for (Integer[] coords : path) {
            if (coords[0] == x && coords[1] == y) {
                return true;
            }
        }

        return false;
    }

    private static boolean charArrayContains(char[] array, char value) {
        for (char c : array) {
            if (c == value) {
                return true;
            }
        }

        return false;
    }

    private static void printPath(ArrayList<Integer[]> path, ArrayList<Character[]> map) {
        Character[] row;
        int x, y;

        for (y = 0; y < map.size(); y++) {
            row = map.get(y);

            for (x = 0; x < row.length; x++) {
                if (isInPath(path, x, y)) {
                    System.out.print(row[x]);
                } else {
                    System.out.print('.');
                }
            }

            System.out.println();
        }
    }
}
