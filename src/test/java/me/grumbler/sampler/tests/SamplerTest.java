package me.grumbler.sampler.tests;

import me.grumbler.sampler.NotEnoughDataException;
import me.grumbler.sampler.RandomWrapper;
import me.grumbler.sampler.Sampler;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * sampler
 * me.grumbler.sampler.tests
 */
public class SamplerTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    private RandomWrapper generator;

    @Before
    public void setUp() {
        generator = mock(RandomWrapper.class);
    }

    @Test
    public void testProcessData() throws Exception {
        Sampler sampler = new Sampler(generator, 1);
        byte[] data = new byte[3];
        data[0] = 1;
        data[1] = 1;
        data[2] = 1;
        when(generator.nextLong(3)).thenReturn(0L);
        sampler.processData(data, 3);
    }

    @Test
    public void testGetResultRaisesError() throws Exception {
        Sampler sampler = new Sampler(generator, 1);
        // no data was processed, calling getResult
        exception.expect(NotEnoughDataException.class);
        sampler.getResult();
    }

}
