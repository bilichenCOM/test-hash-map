package com.bilichenko.test.hashmap;

public interface LightIntLongMap {

    void put(int key, long value);

    long get(int key);

    int size();

    class LightEntry {
        private int key;
        private long value;

        public LightEntry() {
        }

        public LightEntry(int key, long value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LightEntry entry = (LightEntry) o;
            return key == entry.key &&
                    value == entry.value;
        }

        @Override
        public int hashCode() {
            int hashCode = 31 * key;
            hashCode = hashCode + 31 * Long.hashCode(value);
            return hashCode;
        }
    }
}
