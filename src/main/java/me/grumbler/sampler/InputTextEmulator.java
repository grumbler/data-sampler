package me.grumbler.sampler;

import java.util.ArrayList;

/**
 * sampler
 * me.grumbler.sampler
 */
public class InputTextEmulator extends InputEmulator {

    public InputTextEmulator(RandomWrapper generator) {
        super(generator);
    }

    public ArrayList<Byte> getBytes(int length) {
        ArrayList<Byte> res = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            // ASCII - 32 - 126. generate long within 126 - 32 = 94
            byte rnd = (byte) (generator.nextLong(94) + 32);
            res.add(rnd);
        }
        return res;
    }

}
