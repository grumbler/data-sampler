package me.grumbler.sampler;

import java.util.Random;

/**
 * Better, than usual Random (generates all long values, at least)
 * Works faster, than SecureRandom
 * http://www.javamex.com/tutorials/random_numbers/numerical_recipes.shtml
 * Thread-safety was removed in this version
 */
public class HighQualityRandom extends Random {
    private long u;
    private long v = 4101842887655102017L;
    private long w = 1;

    public HighQualityRandom() {
        this(System.nanoTime());
    }

    public HighQualityRandom(long seed) {
        u = seed ^ v;
        nextLong();
        v = u;
        nextLong();
        w = v;
        nextLong();
    }

    @Override
    public long nextLong() {
        u = u * 2862933555777941757L + 7046029254386353087L;
        v ^= v >>> 17;
        v ^= v << 31;
        v ^= v >>> 8;
        w = 4294957665L * (w & 0xffffffff) + (w >>> 32);
        long x = u ^ (u << 21);
        x ^= x >>> 35;
        x ^= x << 4;
        return (x + v) ^ w;
    }

    @Override
    protected int next(int bits)
    {
        return (int) (nextLong() >>> (64 - bits));
    }

}
