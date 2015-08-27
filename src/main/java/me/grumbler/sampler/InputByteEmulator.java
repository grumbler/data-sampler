package me.grumbler.sampler;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * sampler
 * me.grumbler.sampler
 */
public class InputByteEmulator extends InputEmulator {
    protected static int longBytes = Long.SIZE / Byte.SIZE;
    protected ByteBuffer buf = ByteBuffer.allocate(longBytes);

    public InputByteEmulator(RandomWrapper generator) {
        super(generator);
    }

    public ArrayList<Byte> getBytes(int length) {
        ArrayList<Byte> res = new ArrayList<>(length);
        int count = 0;
        while (count < length) {
            Long rnd = generator.nextLong();
            buf.putLong(0, rnd);
            byte[] array = buf.array();
            for (short i = 0; i < longBytes && count < length; i++, count++) {
                byte el = array[i];
                res.add(el);
            }
        }
        return res;
    }
}
