package me.grumbler.sampler.tests;

import me.grumbler.sampler.RandomWrapper;
import me.grumbler.sampler.Sampler;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;

/**
 * sampler
 * me.grumbler.sampler.tests
 */
public class SamplerTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private MockRandomWrapper generator;

    @Before
    public void setUp() {
        generator = new MockRandomWrapper();
    }

    @Test
    public void testProcessData() throws Exception {
        Sampler sampler = new Sampler(generator, 1);
        byte[] data = new byte[3];
        data[0] = 1;
        data[1] = 1;
        data[2] = 1;
        generator.setReturnValue(0);
        sampler.processData(data, 3);
    }

    @Test
    public void testGetResultRaisesError() throws Exception {
        Sampler sampler = new Sampler(generator, 1);
        // no data was processed, calling getResult
        exception.expect(Sampler.NotEnoughDataException.class);
        sampler.getResult();
    }

    // Get Mockito instead of self-made
    private class MockRandomWrapper extends RandomWrapper {
        private long returnValue;

        public MockRandomWrapper() {
            // just to make ctor happy
            super(new Random());
        }

        public long getReturnValue() {
            return returnValue;
        }

        public void setReturnValue(long returnValue) {
            this.returnValue = returnValue;
        }

        @Override
        public long nextLong(long n) {
            return getReturnValue();
        }
    }
}
