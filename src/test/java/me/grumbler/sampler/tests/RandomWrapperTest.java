package me.grumbler.sampler.tests;

import me.grumbler.sampler.HighQualityRandom;
import me.grumbler.sampler.RandomWrapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.security.SecureRandom;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * sampler
 * me.grumbler.sampler
 */
public class RandomWrapperTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testNextLongStandardRandom() throws Exception {
        Random rng = new Random();
        RandomWrapper wrapper = new RandomWrapper(rng);
        long i = 1; // value could be only 0 or 1

        long random = wrapper.nextLong(i);
        assertTrue(random >= 0);
        assertTrue(random <= i);
    }

    @Test
    public void testNextLongFastRandom() throws Exception {
        Random rng = new HighQualityRandom();
        RandomWrapper wrapper = new RandomWrapper(rng);
        long i = 1;

        long random = wrapper.nextLong(i);
        assertTrue(random >= 0);
        assertTrue(random <= i);
    }

    @Test
    public void testNextLongSecureRandom() throws Exception {
        Random rng = new SecureRandom();
        RandomWrapper wrapper = new RandomWrapper(rng);
        long i = 1;

        long random = wrapper.nextLong(i);
        assertTrue(random >= 0);
        assertTrue(random <= i);
    }

    @Test
    public void testNextLongCheckArgument() throws Exception {
        // White box here, don't check all Randoms
        RandomWrapper wrapper = new RandomWrapper(new Random());
        exception.expect(IllegalArgumentException.class);
        wrapper.nextLong(-1);
    }

    @Test
    public void testNextLongZeroBoundReturnsExactlyZero() throws Exception {
        RandomWrapper wrapper = new RandomWrapper(new Random());
        exception.expect(IllegalArgumentException.class);
        long val = wrapper.nextLong(0);
        assertEquals(val, 0L);
    }
}
