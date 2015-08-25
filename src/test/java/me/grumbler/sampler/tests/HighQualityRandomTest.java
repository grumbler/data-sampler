package me.grumbler.sampler.tests;

import me.grumbler.sampler.HighQualityRandom;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * sampler
 * me.grumbler.sampler
 */
public class HighQualityRandomTest {

    @Test
    public void testNextLong() throws Exception {
        Random rng = new HighQualityRandom();
        long value1 = rng.nextLong();
        long value2 = rng.nextLong();
        // this may fail but it's almost impossible
        assertNotEquals(value1, value2);
    }
}
