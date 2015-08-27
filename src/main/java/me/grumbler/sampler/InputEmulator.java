package me.grumbler.sampler;

import java.util.ArrayList;

/**
 * sampler
 * me.grumbler.sampler
 */
public abstract class InputEmulator {
    protected RandomWrapper generator = null;

    public InputEmulator(RandomWrapper generator) {
        this.generator = generator;
    }

    abstract public ArrayList<Byte> getBytes(int length);

}
