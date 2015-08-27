package me.grumbler.sampler.tests;

import me.grumbler.sampler.EmulatorStream;
import me.grumbler.sampler.InputByteEmulator;
import me.grumbler.sampler.InputEmulator;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * sampler
 * me.grumbler.sampler.tests
 */
public class EmulatorStreamTest {

    @Test
    public void testRead() throws Exception {
        InputEmulator emulator = mock(InputByteEmulator.class);
        EmulatorStream stream = new EmulatorStream(emulator, 2, 1);
        ArrayList<Byte> lst = new ArrayList<>(2);
        lst.add((byte) 1);
        lst.add((byte) 2);
        when(emulator.getBytes(1)).thenReturn(lst);
        int val1 = stream.read();
        int val2 = stream.read();
        int val3 = stream.read();
        assertEquals(val1, 1);
        assertEquals(val2, 2);
        assertEquals(val3, -1);
    }
}
