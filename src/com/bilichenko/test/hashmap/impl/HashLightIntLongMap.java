package com.bilichenko.test.hashmap.impl;

import com.bilichenko.test.hashmap.LightIntLongMap;

import java.util.Arrays;


public class HashLightIntLongMap implements LightIntLongMap {

    private static final int INITIAL_SIZE = 20;
    private static final double LOAD_LEVEL = 0.7;
    private static final double GROWTH_COEFFICIENT = 1.5;

    private LightEntry[] entries;
    private int size;

    public HashLightIntLongMap() {
        entries = new LightEntry[INITIAL_SIZE];
    }

    @Override
    public void put(int key, long value) {
        int bucketInd = computeInd(key);
        LightEntry entry = new LightEntry(key, value);
        insertEntry(bucketInd, entry);
        size++;
        increaseSizeIfNeeded();
    }

    @Override
    public long get(int key) {
        int bucketInd = computeInd(key);
        bucketInd = findEntryInd(bucketInd, key);
        return entries[bucketInd].getValue();
    }

    @Override
    public int size() {
        return size;
    }

    private int findEntryInd(int bucketInd, int key) {
        while (bucketInd < entries.length) {
            if (entries[bucketInd].getKey() == key) {
                return bucketInd;
            }
            if (bucketInd == entries.length - 1) {
                bucketInd = 0;
                continue;
            }
            bucketInd++;
        }
        return 0;
    }

    private void insertEntry(int bucketInd, LightEntry entry) {
        while (bucketInd < entries.length && entries[bucketInd] != null) {
            if (entries[bucketInd].getKey() == entry.getKey()) {
                break;
            }
            if (bucketInd == entries.length - 1) {
                bucketInd = 0;
                continue;
            }
            bucketInd++;
        }
        entries[bucketInd] = entry;
    }


    private void increaseSizeIfNeeded() {
        double currentLoadLevel = (double) size / entries.length;
        if (currentLoadLevel > LOAD_LEVEL) {
            increaseSize();
        }
    }

    private void increaseSize() {
        int newSize = (int) (entries.length * GROWTH_COEFFICIENT);
        LightEntry[] old = entries;
        entries = new LightEntry[newSize];
        size = 0;
        Arrays.stream(old)
                .filter(e -> e != null)
                .forEach(e -> put(e.getKey(), e.getValue()));
    }

    private int computeInd(int key) {
        return key % entries.length;
    }

    public LightEntry[] getEntries() {
        return entries;
    }
}
