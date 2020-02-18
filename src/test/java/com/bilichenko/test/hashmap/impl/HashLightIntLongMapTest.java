package com.bilichenko.test.hashmap.impl;

import com.bilichenko.test.hashmap.LightIntLongMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;

//TODO: find how to test private methods using junit 5;
@RunWith(PowerMockRunner.class)
class HashLightIntLongMapTest {

    public HashLightIntLongMapTest() {
    }

    private HashLightIntLongMap hashMap;

    @Before
    public void initTestSubject() {
        hashMap = spy(HashLightIntLongMap.class);
    }

    @Test
    public void put_positiveKeys() throws Exception {
        int key1 = 12;
        int key2 = 13;
        int key3 = 14;
        int key4 = 199;

        when(hashMap, "computeInd", 12).thenReturn(1);

        hashMap.put(key1, 1L);
        hashMap.put(key2, 2L);
        hashMap.put(key3, 3L);
        hashMap.put(key4, 4L);

        verifyPrivate(hashMap, times(4)).invoke("computeInd", anyInt());
        verifyPrivate(hashMap, times(4)).invoke("insertEntry", anyInt(), any(LightIntLongMap.LightEntry.class));
        verifyPrivate(hashMap, times(4)).invoke("incrementSize");
        verifyPrivate(hashMap, times(4)).invoke("increaseSizeIfNeeded");

    }

    @Test
    public void get() {
    }

    @Test
    public void size() {
    }
}