package me.svana.aoc;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Day5 {

    private record Range(long start, long length) {
    };

    private record Overlap(Range before, Range overlap, Range after) {
    };

    private class RangeMapper {

        private ArrayList<Long[]> maps;

        public RangeMapper() {
            maps = new ArrayList<>();
        }

        public void addRangeMap(long sourceStart, long destinationStart, long range) {
            maps.add(new Long[] { sourceStart, destinationStart, range });
        }

        public ArrayList<Long[]> map(long sourceStart, long range) {
            ArrayList<Long[]> mappedRanges = new ArrayList<>();
            Overlap overlap;
            long overlapOffset;

            for (Long[] map : maps) {
                overlap = getOverlap(new Range(sourceStart, range), new Range(map[0], map[2]));

                if (overlap == null || overlap.overlap().length() == 0) {
                    continue;
                }

                if (overlap.overlap().length() > 0) {
                    overlapOffset = overlap.overlap().start - map[0];
                    mappedRanges.add(new Long[] { map[1] + overlapOffset, overlap.overlap().length() });
                }

                if (overlap.before().length() > 0) {
                    mappedRanges.addAll(map(overlap.before().start(), overlap.before().length()));
                }

                if (overlap.after().length() > 0) {
                    mappedRanges.addAll(map(overlap.after().start(), overlap.after().length()));
                }
            }

            if (mappedRanges.size() == 0) {
                mappedRanges.add(new Long[] { sourceStart, range });
            }

            return mappedRanges;
        }

        private Overlap getOverlap(Range source, Range destination) {

            long sourceEnd = source.start() + source.length();
            long destinationEnd = destination.start() + destination.length();

            if (source.start() <= destination.start() && sourceEnd <= destinationEnd
                    && destination.start() <= sourceEnd) {
                return new Overlap(
                        new Range(source.start(), destination.start() - source.start()),
                        new Range(destination.start(), sourceEnd - destination.start()),
                        new Range(sourceEnd, 0));
            }

            if (destination.start() <= source.start() && destinationEnd <= sourceEnd
                    && source.start() <= destinationEnd) {
                return new Overlap(
                        new Range(source.start(), 0),
                        new Range(source.start(), destinationEnd - source.start()),
                        new Range(destinationEnd, sourceEnd - destinationEnd));
            }

            if (destination.start() <= source.start() && sourceEnd <= destinationEnd) {
                return new Overlap(
                        new Range(source.start(), 0),
                        new Range(source.start(), sourceEnd - source.start()),
                        new Range(sourceEnd, 0));
            }

            if (source.start() <= destination.start() && destinationEnd <= sourceEnd) {
                return new Overlap(
                        new Range(source.start(), destination.start() - source.start()),
                        new Range(destination.start(), destinationEnd - destination.start()),
                        new Range(destinationEnd, sourceEnd - destinationEnd));
            }

            return null;
        }
    }

    private static final String inputPath = "inputs/5.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        long minLocation = -1;
        String[] lineParts;

        RangeMapper currentMap = null;
        ArrayList<RangeMapper> maps = new ArrayList<>();
        ArrayList<Long[]> mappedRanges = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("seeds:")) {
                lineParts = line.split(":")[1].trim().split(" ");
                for (int i = 0; i < lineParts.length / 2; i++) {
                    mappedRanges.add(new Long[] {
                            Long.parseLong(lineParts[i * 2]),
                            Long.parseLong(lineParts[i * 2 + 1]) });
                }
                continue;
            }

            if (line.equals("")) {
                continue;
            }

            if (line.endsWith("map:")) {
                if (currentMap != null) {
                    maps.add(currentMap);
                }

                currentMap = new Day5().new RangeMapper();
                continue;
            }

            lineParts = line.split(" ");

            currentMap.addRangeMap(
                    Long.parseLong(lineParts[1]),
                    Long.parseLong(lineParts[0]),
                    Long.parseLong(lineParts[2]));
        }

        maps.add(currentMap);

        ArrayList<Long[]> newMappedRanges;

        for (int i = 0; i < maps.size(); i++) {
            currentMap = maps.get(i);
            newMappedRanges = new ArrayList<>();

            System.out.println("Map " + i);

            for (int j = 0; j < mappedRanges.size(); j++) {
                newMappedRanges.addAll(currentMap.map(mappedRanges.get(j)[0], mappedRanges.get(j)[1]));
            }

            mappedRanges = newMappedRanges;
        }

        for (int i = 0; i < mappedRanges.size(); i++) {
            if (minLocation == -1 || mappedRanges.get(i)[0] < minLocation)
                minLocation = mappedRanges.get(i)[0];

        }

        System.out.println(minLocation);
        bufferedReader.close();
    }
}
