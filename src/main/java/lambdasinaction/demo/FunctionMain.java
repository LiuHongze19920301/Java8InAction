package lambdasinaction.demo;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.*;
import java.util.stream.IntStream;

public class FunctionMain {

    private static final IntUnaryOperator INT_UNARY_OPERATOR = FunctionMain::addIntByTwo;
    private static final DoubleUnaryOperator DOUBLE_UNARY_OPERATOR = FunctionMain::addDoubleByTwo;
    private static final LongUnaryOperator LONG_UNARY_OPERATOR = FunctionMain::addLongByTwo;

    private static final Function<Integer, Integer> INTEGER_FUNCTION = FunctionMain::addIntByTwo;
    private static final Function<Long, Long> LONG_FUNCTION = FunctionMain::addLongByTwo;
    private static final Function<Double, Double> DOUBLE_FUNCTION = FunctionMain::addDoubleByTwo;

    private static final IntPredicate INT_PREDICATE = FunctionMain::testNumberPositive;
    private static final LongPredicate LONG_PREDICATE = FunctionMain::testNumberPositive;
    private static final DoublePredicate DOUBLE_PREDICATE = FunctionMain::testNumberPositive;

    private static int addIntByTwo(int x) {
        return x + 2;
    }

    private static double addDoubleByTwo(double x) {
        return x + 2;
    }

    private static long addLongByTwo(long x) {
        return x + 2;
    }

    private static boolean testNumberPositive(int x) {
        return x > 0;
    }

    private static boolean testNumberPositive(long x) {
        return x > 0;
    }

    private static boolean testNumberPositive(double x) {
        return x > 0;
    }

    private static int maintainBiggerNum(int x, int y) {
        return Math.max(x, y);
    }

    public static void main(String[] args) {

        IntStream.iterate(1, i -> i * 2).limit(30).forEach(System.out::println);

        double v = IntStream.rangeClosed(1, 100).boxed().map(i -> new Point(i % 3, i / 3)).mapToDouble(p -> p.distance(new Point(0, 0))).max().orElse(0);
        System.out.println(v);

        String str = "alpha-bravo-charlie";
        Map<String, String> map = new HashMap<>();
        map.put("alpha", "X");
        map.put("bravo", "Y");
        map.put("charlie", "Z");
//        map.replaceAll(str::replace);
        map.replaceAll(String::concat);
        System.out.println(map);

        System.out.println(INT_UNARY_OPERATOR.applyAsInt(2));
        System.out.println(DOUBLE_UNARY_OPERATOR.applyAsDouble(2));
        System.out.println(LONG_UNARY_OPERATOR.applyAsLong(2));

        System.out.println(INT_PREDICATE.test(2));
        System.out.println(LONG_PREDICATE.test(2));
        System.out.println(DOUBLE_PREDICATE.test(2));

    }

    public <T> void foo(Supplier<T> factory) {
        System.out.println();
    }

    public <T, U> void foo(Function<T, U> transformer) {
        System.out.println();
    }

    public void testFoo() {
        this.<Exception>foo(Exception::new);
        this.<String, Exception>foo(Exception::new);
    }

}
