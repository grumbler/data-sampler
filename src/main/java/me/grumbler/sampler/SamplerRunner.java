package me.grumbler.sampler;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


/**
 * sampler
 * me.grumbler.sampler
 */
public class SamplerRunner {

    private static final int BUFFER_SIZE = 2048;
    @Option(
            name = "--emulate",
            usage = "Set flag to use internal random stream"
    )
    private boolean emulateStream;
    @Option(
            name = "--emulate-length",
            usage = "Bytes to generate, defaults to 2048"
    )
    private int emulatorStreamLength = BUFFER_SIZE;
    @Option(
            name = "--generator",
            usage = "Random generator (defaults to classic Random)"
    )
    private Generator generator = Generator.STANDARD;
    @Option(
            name = "--byte",
            usage = "Treat stream as bytes (text by default)"
    )
    private boolean byteStream;
    @Option(
            name = "--length",
            usage = "Specify sample length",
            required = true
    )
    private int sampleLength;

    public static void main(String[] args) throws Exception {
        int code = new SamplerRunner().doMain(args);
        System.exit(code);
    }

    private RandomWrapper getGenerator() {
        switch (this.generator) {
            case STANDARD:
                return new RandomWrapper(new Random());
            case FAST:
                return new RandomWrapper(new HighQualityRandom());
            case SECURE:
                return new RandomWrapper(new SecureRandom());
            default:
                return new RandomWrapper(new Random());
        }
    }

    public int doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("sampler [options...]");

            parser.printUsage(System.err);
            System.err.println();

            System.err.println("  Example: sampler" + parser.printExample(OptionHandlerFilter.ALL));

            // UNIX args-error
            return 2;
        }

        RandomWrapper generator = this.getGenerator();
        Sampler sampler = new Sampler(generator, this.sampleLength);
        InputStream inputStream;
        if (emulateStream) {
            InputEmulator inputEmulator;
            if (byteStream) {
                inputEmulator = new InputByteEmulator(generator);
            } else {
                inputEmulator = new InputTextEmulator(generator);
            }
            inputStream = new EmulatorStream(inputEmulator, emulatorStreamLength, BUFFER_SIZE);
        } else {
            inputStream = System.in;
        }

        try (BufferedInputStream stream = new BufferedInputStream(inputStream, BUFFER_SIZE)) {
            int readBytes;
            do {
                byte[] buffer = new byte[BUFFER_SIZE];
                readBytes = stream.read(buffer);
                if (readBytes > 0) {
                    sampler.processData(buffer, readBytes);
                }
            } while (readBytes > 0);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return -1;
        }

        ArrayList<Byte> res;
        try {
            res = sampler.getResult();
        } catch (NotEnoughDataException e) {
            System.err.println(e.getMessage());
            return -1;
        }
        byte[] resArray = new byte[sampleLength];
        for (int i = 0; i < sampleLength; i++) {
            resArray[i] = res.get(i);
        }
        if (this.byteStream) {
            System.out.write(resArray);
        } else {
            System.out.print(new String(resArray, StandardCharsets.US_ASCII));
        }
        return 0;
    }

    private enum Generator {
        STANDARD,
        FAST,
        SECURE
    }

}
