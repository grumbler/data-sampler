package me.grumbler.sampler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * sampler
 * me.grumbler.sampler
 */
public class EmulatorStream extends InputStream {
    private long totalSize = 0;
    private int bufferSize = 0;
    private long count = 0;
    private InputEmulator emulator;
    private ArrayList<Byte> buffer = null;
    private ListIterator<Byte> bufferIterator;

    public EmulatorStream(InputEmulator emulator, long totalSize, int bufferSize) {
        this.emulator = emulator;
        this.totalSize = totalSize;
        this.bufferSize = bufferSize;
    }

    private void fillBuffer() {
        int bytesToGenerate = (int) Math.min(totalSize - count, bufferSize);
        buffer = emulator.getBytes(bytesToGenerate);
        bufferIterator = buffer.listIterator();
    }

    @Override
    public int read() throws IOException {
        if (buffer == null || buffer.isEmpty()) {
            fillBuffer();
        }
        // if still zero - end of story
        if (buffer.isEmpty()) {
            return -1;
        }
        Byte b = bufferIterator.next();
        bufferIterator.remove();
        count++;
        // TODO: unboxing here. mb it's better to use plain array
        return (int) b;
    }
}
