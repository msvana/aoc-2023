package me.svana.aoc;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Day8 {

    private static final String inputPath = "inputs/8.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String instructions = bufferedReader.readLine();
        String nodeString;
        String[] nodeStringParts, pathStrings;
        HashMap<String, String[]> nodes = new HashMap<>();

        bufferedReader.readLine(); // Read empty line

        while ((nodeString = bufferedReader.readLine()) != null) {
            nodeStringParts = nodeString.split("=");
            pathStrings = nodeStringParts[1].trim().replace("(", "").replace(")", "").split(", ");
            nodes.put(nodeStringParts[0].trim(), pathStrings);
        }

        String[] currentNodeNames = nodes.keySet().stream().filter(key -> key.endsWith("A")).toArray(String[]::new);
        String[] currentNode;
        String currentNodeName;
        int stepCounters[] = new int[currentNodeNames.length];

        char currentInstruction;

        for (int k = 0; k < currentNodeNames.length; k++) {
            currentNodeName = currentNodeNames[k];
            stepCounters[k] = 0;

            mainLoop: while (true) {
                for (int i = 0; i < instructions.length(); i++) {
                    currentInstruction = instructions.charAt(i);
                    currentNode = nodes.get(currentNodeName);

                    if (currentInstruction == 'R') {
                        currentNodeName = currentNode[1];
                    } else if (currentInstruction == 'L') {
                        currentNodeName = currentNode[0];
                    }

                    stepCounters[k]++;

                    if (currentNodeName.endsWith("Z")) {
                        break mainLoop;
                    }
                }
            }
        }

        long product = 1;

        for (int i = 0; i < stepCounters.length; i++) {
            int[] factors = factorNumber(stepCounters[i]);
            product *= factors[0];
        }

        product *= 293; // All path lengths are a multiple of 293 and some other prime number

        System.out.println(product);
        bufferedReader.close();
    }

    private static int[] factorNumber(int n) {
        int[] factors = new int[2];
        int i = 2;

        while (i <= n) {
            if (n % i == 0) {
                factors[0] = i;
                factors[1] = n / i;
                break;
            }

            i++;
        }

        return factors;
    }
}
