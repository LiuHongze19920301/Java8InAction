package lambdasinaction.varhandle;

import java.util.Arrays;

public class ParallerArraySetDemo {

    public static void main(String[] args) {
        int[] intArr = parallelInitialize(1000);
        Arrays.stream(intArr).forEach(System.out::println);
    }

    public static int[] parallelInitialize(int size) {
        assert size >= 0;
        int[] arr = new int[size];
        Arrays.parallelSetAll(arr, i -> i);
        return arr;
    }
}
