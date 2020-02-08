package com.bilichenko.test.hashmap;

import com.bilichenko.test.hashmap.impl.HashLightIntLongMap;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Main {

    public static void main(String[] args) {
        runTests();
    }

    private static void runTests() {
        runPutTest();
        runGetTest();
        runSizeTest();
        runOverallTest();
    }

    private static void runOverallTest() {
        HashLightIntLongMap map = new HashLightIntLongMap();
        int testQuantity = 1000;
        List<Integer> keys = IntStream.range(0, testQuantity)
                .mapToObj(Integer::new)
                .collect(Collectors.toList());
        List<Long> values = LongStream.iterate(0, l -> new Random().nextLong())
                .limit(testQuantity)
                .mapToObj(Long::new)
                .collect(Collectors.toList());
        IntStream.range(0, testQuantity)
                .forEach(i -> map.put(keys.get(i), values.get(i)));

        boolean successfulGet = keys.stream()
                .allMatch(k -> map.get(k) == values.get(k));
        if (!successfulGet) {
            failTest("Overall Test", "method get() works not properly");
            return;
        }
        if (map.size() != testQuantity) {
            failTest("Overall Test", "too much insertion was done, or method size() works not properly");
            return;
        }
        int testKey = testQuantity - 1;
        long testValue = 123L;
        map.put(testKey, testValue);
        if (map.get(testKey) != testValue) {
            failTest("Overall Test", "method put() doesn't update the value of the entry with the same key");
            return;
        }
        passTest("Overall Test");
    }

    private static void runSizeTest() {
        HashLightIntLongMap map = new HashLightIntLongMap();
        int key1 = 1;
        long value1 = 1234L;

        int testNumber = 100;
        map.put(key1, value1);
        if (map.size() != 1) {
            failTest("Size", "method returned wrong size after first addition: " + map.size());
            return;
        }

        for (int key = 2; key <= testNumber; key++) {
            map.put(key, new Random().nextLong());
        }

        if (map.size() != 100) {
            failTest("Size", "method returned wrong size after iterative addition: " + map.size());
            return;
        }
        passTest("Size");
    }

    private static void runGetTest() {
        HashLightIntLongMap map = new HashLightIntLongMap();
        int key = 5678;
        long value = new Random().nextLong();

        LightIntLongMap.LightEntry expected = new LightIntLongMap.LightEntry();
        expected.setKey(key);
        expected.setValue(value);

        LightIntLongMap.LightEntry[] entries = map.getEntries();
        int expBucketInd = key % entries.length;
        entries[expBucketInd] = expected;

        long actual = map.get(key);
        if (actual != expected.getValue()) {
            failTest("Get", "method returned wrong value");
            return;
        }
        passTest("Get");
    }

    private static void runPutTest() {
        HashLightIntLongMap map = new HashLightIntLongMap();
        int key = 1234;
        long value = new Random().nextLong();
        map.put(key, value);
        LightIntLongMap.LightEntry[] entries = map.getEntries();

        LightIntLongMap.LightEntry expected = new LightIntLongMap.LightEntry();
        expected.setKey(key);
        expected.setValue(value);

        boolean notAdded = Arrays.stream(entries)
                .allMatch(e -> e == null);
        if (notAdded) {
            failTest("Put", "no value was added to map");
            return;
        }

        int expBucketInd = key % entries.length;
        LightIntLongMap.LightEntry actual = entries[expBucketInd];
        if (!expected.equals(actual)) {
            failTest("Put", "entry was added in wrong bucket number");
            return;
        }

        passTest("Put");
    }

    private static void passTest(String testName) {
        System.out.println(testName + " was successfully PASSED!");
    }

    private static void failTest(String testName, String reason) {
        System.err.printf("test %s FAILED: %s%s", testName, reason, System.lineSeparator());
    }
}
