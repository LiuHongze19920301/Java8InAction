package lambdasinaction.varhandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;

public class Demo {
    public static class Instance {
        public int count = 1;
        protected long sum = 100;
        private String name = "init";
        public int[] arrayData = new int[]{3, 5, 7};

        @Override
        public String toString() {
            return "Demo{" +
                    "name='" + name + '\'' +
                    ", count=" + count +
                    ", sum=" + sum +
                    ", data=" + Arrays.toString(arrayData) +
                    '}';
        }
    }

    private static void testSetPublicField() throws NoSuchFieldException, IllegalAccessException {
        Instance instance = new Instance();
        VarHandle countHandle = MethodHandles.lookup().in(Instance.class).findVarHandle(Instance.class, "count", int.class);
        countHandle.set(instance, 99);
        System.out.println(instance);
    }

    private static void testSetProtectedField() throws NoSuchFieldException, IllegalAccessException {
        Instance instance = new Instance();
        VarHandle sumHandle = MethodHandles.lookup().in(Instance.class).findVarHandle(Instance.class, "sum", long.class);
        sumHandle.set(instance, 99999);
        System.out.println(instance);
    }

    private static void testSetPrivateField() throws IllegalAccessException, NoSuchFieldException {
        Instance instance = new Instance();
        VarHandle nameHandle = MethodHandles.privateLookupIn(Instance.class, MethodHandles.lookup())
                .findVarHandle(Instance.class, "name", String.class);
        nameHandle.set(instance, "Hello World!");
        System.out.println(instance);
    }

    private static void testSetArray() {
        Instance instance = new Instance();
        VarHandle arrayHandle = MethodHandles.arrayElementVarHandle(int[].class);
        arrayHandle.compareAndSet(instance.arrayData, 0, 3, 100);
        arrayHandle.compareAndSet(instance.arrayData, 1, 5, 300);
        Object result1 = arrayHandle.compareAndExchange(instance.arrayData, 0, 3, 800);
        Object result2 = arrayHandle.compareAndExchange(instance.arrayData, 1, 5, 999);
        System.out.println("Result1 -> " + result1);
        System.out.println("Result2 -> " + result2);
        System.out.println(instance);
    }

    public static void main(String[] args) throws Exception {
        testSetPublicField();
        testSetProtectedField();
        testSetPrivateField();
        testSetArray();
    }
}
