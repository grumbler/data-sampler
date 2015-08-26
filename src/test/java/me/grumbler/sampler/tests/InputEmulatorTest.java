package me.grumbler.sampler.tests;

import me.grumbler.sampler.InputEmulator;
import me.grumbler.sampler.RandomWrapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * sampler
 * me.grumbler.sampler.tests
 */
public class InputEmulatorTest {

    @Test
    public void testGetBytes() throws Exception {
        RandomWrapper generator = mock(RandomWrapper.class);
        when(generator.nextLong()).thenReturn((long) Math.pow(2, 8 * 7)); // make first byte 1
        InputEmulator emulator = new InputEmulator(generator);
        ArrayList<Byte> bytes = emulator.getBytes(1);
        assertEquals(bytes.size(), 1);
        assertTrue(Objects.equals(bytes.get(0), (byte) 1));
    }
}
