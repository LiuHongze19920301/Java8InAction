package lambdasinaction.chap1;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilteringApples {

    private static final Predicate<Apple> GREEN_APPLE_PREDICATE = FilteringApples::isGreenApple;
    private static final Predicate<Apple> HEAVY_APPLE_PREDICATE = FilteringApples::isHeavyApple;
    private static final Predicate<Plant> HIGH_PLANT_PREDICATE = FilteringApples::isHighPlant;

    public static void main(String... args) {

        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green", 0.8),
                new Apple(155, "green", 0.7),
                new Apple(120, "red", 0.6),
                new Apple(180, "brown", 0.5)
        );

        // [Apple{color='green', weight=80, height=0.8}, Apple{color='green', weight=155, height=0.7}]
        List<Apple> greenApples = filterApples(inventory, FilteringApples::isGreenApple);
        System.out.println(greenApples);

        List<Apple> greenTypeApples = filterTypes(inventory, GREEN_APPLE_PREDICATE);
        System.out.println(greenTypeApples);

        List<Apple> greenTypesStreamApples = filterTypesWithStream(inventory, GREEN_APPLE_PREDICATE);
        System.out.println(greenTypesStreamApples);

        // [Apple{color='green', weight=155, height=0.7}, Apple{color='brown', weight=180, height=0.5}]
        List<Apple> heavyApples = filterApples(inventory, FilteringApples::isHeavyApple);
        System.out.println(heavyApples);

        List<Apple> heavyTypeApples = filterTypes(inventory, HEAVY_APPLE_PREDICATE);
        System.out.println(heavyTypeApples);

        List<Apple> heavyTypesStreamApples = filterTypesWithStream(inventory, HEAVY_APPLE_PREDICATE);
        System.out.println(heavyTypesStreamApples);

        // [Apple{color='green', weight=80, height=0.8}, Apple{color='green', weight=155, height=0.7}]
        List<Apple> greenApples2 = filterApples(inventory, apple -> "green".equals(apple.getColor()));
        System.out.println(greenApples2);

        // [Apple{color='green', weight=155, height=0.7}, Apple{color='brown', weight=180, height=0.5}]
        List<Apple> heavyApples2 = filterApples(inventory, apple -> apple.getWeight() > 150);
        System.out.println(heavyApples2);

        // [Apple{color='brown', weight=180, height=0.5}]
        List<Apple> weirdApples = filterApples(inventory, apple -> apple.getWeight() < 80 ||
                "brown".equals(apple.getColor()));
        System.out.println(weirdApples);

        // [Apple{color='green', weight=80, height=0.8}, Apple{color='green', weight=155, height=0.7}, Apple{color='brown', weight=180, height=0.5}]
        Predicate<Apple> greenAndHeavyApplePredicate = ((Predicate<Apple>) FilteringApples::isGreenApple)
                .or((Predicate<? super Apple>) FilteringApples::isHeavyApple);
        List<Apple> greenOrHeavyApple = filterApples(inventory, greenAndHeavyApplePredicate);
        System.out.println(greenOrHeavyApple);

        // [Apple{color='green', weight=80, height=0.8}, Apple{color='green', weight=155, height=0.7}]
        List<Apple> highPlants = filterApples(inventory, HIGH_PLANT_PREDICATE);
        System.out.println(highPlants);

    }

    private static boolean isHighPlant(Plant plant) {
        return plant.height > 0.6;
    }

    private static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    private static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    private static List<Apple> filterApples(List<Apple> inventory, Predicate<? super Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    private static <T> List<T> filterTypes(List<T> types, Predicate<? super T> predicate) {
        if (Objects.isNull(types)) {
            return null;
        }
        if (types.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>();
        for (T type : types) {
            if (predicate.test(type)) {
                result.add(type);
            }
        }
        return result;
    }

    private static <T> List<T> filterTypesWithStream(List<T> types, Predicate<? super T> predicate) {
        if (Objects.isNull(types)) {
            return null;
        }
        if (types.isEmpty()) {
            return Collections.emptyList();
        }
        return types.stream().filter(predicate).collect(Collectors.toList());
    }

    public static class Apple extends Plant {
        private int weight;
        private String color;

        public Apple() {
            super();
        }

        public Apple(int weight, String color) {
            super();
            this.weight = weight;
            this.color = color;
        }

        Apple(int weight, String color, double height) {
            super(height);
            this.weight = weight;
            this.color = color;
        }

        Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    ", height=" + getHeight() +
                    '}';
        }
    }

    public static abstract class Plant {
        private double height;

        Plant() {
        }

        Plant(double height) {
            this.height = height;
        }

        double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }
    }

}
