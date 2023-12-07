package me.svana.aoc;

public class Day6 {

    public static void main(String[] args) {
        //int[] times = { 49, 97, 94, 94 };
        //int[] distances = { 263, 1532, 1378, 1851};
        long times[] = { 49979494L };
        long distances[] = { 263153213781851L };
        long product = 1;
        long a = -1, b, c;
        double discriminant, x1, x2;

        for (int i = 0; i < times.length; i++) {
            b = times[i];
            c = -distances[i];
            System.out.println(b*b - 4*a*c);
            discriminant = (double) Math.sqrt(b * b - 4 * a * c);
            x1 = 0.0001 + (-b + discriminant) / (2 * a);
            x2 = -0.0001 + (-b - discriminant) / (2 * a);
            System.out.println(x1 + " " + x2);
            product *= Math.floor(x2) - Math.ceil(x1) + 1;
        }

        System.out.println(product);
    }
}
