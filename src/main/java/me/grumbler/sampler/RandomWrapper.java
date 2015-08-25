package me.grumbler.sampler;

import java.util.Random;


/**
 * Wraps random-generator and exposes only one method
 * to be used in the app
 * sampler
 * me.grumbler.sampler
 */
public class RandomWrapper {
    private Random rng;

    public RandomWrapper(Random rng) {
        this.rng = rng;
    }

    /**
     * Generates long within given bound. http://stackoverflow.com/a/2546186
     * @param n - non-negative upper bound for generated number
     * @return long - random within 0 and n
     * @throws IllegalArgumentException - in case of negative bound
     */
    public long nextLong(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }

        long bits, val;
        do {
            bits = (this.rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);

        return val;
    }
}
