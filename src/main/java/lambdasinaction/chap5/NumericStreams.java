package lambdasinaction.chap5;

import lambdasinaction.chap4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static lambdasinaction.chap4.Dish.menu;

public class NumericStreams {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        Arrays.stream(numbers.toArray()).forEach(System.out::println);
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("Number of calories:" + calories);


        // max and OptionalInt
        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        int max;
        if (maxCalories.isPresent()) {
            max = maxCalories.getAsInt();
        } else {
            // we can choose a default value
            max = 1;
        }
        System.out.println(max);

        // numeric ranges
        IntStream evenNumbers = IntStream.rangeClosed(1, 100)
                .filter(n -> n % 2 == 0);

        System.out.println(evenNumbers.count());

        Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a -> IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                                .map(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));

        pythagoreanTriples.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        System.out.println("=====================");
        generateEnhance();
        System.out.println("=====================");
        generateFibonacci();
        System.out.println("=====================");
        testJava9Stream();
        System.out.println("=====================");
    }

    public static boolean isPerfectSquare(int n) {
        return Math.sqrt(n) % 1 == 0;
    }

    public static void generateEnhance() {
        Stream<double[]> streamEnhance = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0));
        List<int[]> intList = streamEnhance.map(t -> new int[]{((int) t[0]), ((int) t[1]), ((int) t[2])})
                .collect(Collectors.toList());
        System.out.println("======================");
        intList.forEach(ints -> System.out.println(ints[0] + ", " + ints[1] + ", " + ints[2]));
    }

    public static void generateFibonacci() {
        String numbers = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .map(t -> t[0])
                .limit(20)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        System.out.println(numbers);
    }

    public static void testJava9Stream() {
        Stream.of("a", "b", "c", "", "d", "e")
                .takeWhile(s -> !s.isEmpty())
                .forEach(System.out::print);
        System.out.println();
        Stream.of("a", "b", "c", "", "d", "e", "", "f")
                .dropWhile(s -> !s.isEmpty())
                .forEach(System.out::print);
        System.out.println();
        IntStream.iterate(3, x -> x < 30, x -> x + 3).forEach(System.out::println);
        System.out.println();
        long count = Stream.ofNullable(100).count();
        System.out.println(count);
        long nullCount = Stream.ofNullable(null).count();
        System.out.println(nullCount);
    }
}
