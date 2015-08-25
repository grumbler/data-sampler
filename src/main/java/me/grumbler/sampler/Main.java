package me.grumbler.sampler;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;


/**
 * sampler
 * me.grumbler.sampler
 */
public class Main {

    private static int BUFFER_SIZE = 2048;
    @Option(
            name = "--emulate",
            usage = "Set flag to use internal random stream",
            hidden = true
    )
    private boolean emulateStream;
    @Option(
            name = "--generator",
            usage = "Random generator (defaults to classic Random)"
    )
    private Generator generator = Generator.FAST;
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
        int code = new Main().doMain(args);
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

        Sampler sampler = new Sampler(this.getGenerator(), this.sampleLength);

        try (BufferedInputStream stream = new BufferedInputStream(System.in, BUFFER_SIZE)) {
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

        byte[] res;
        try {
            res = sampler.getResult();
        } catch (Sampler.NotEnoughDataException e) {
            System.err.println(e.getMessage());
            return -1;
        }
        if (this.byteStream) {
            System.out.println(Arrays.toString(res));
        } else {
            System.out.println(new String(res, StandardCharsets.US_ASCII));
        }
        return 0;
    }

    private enum Generator {
        STANDARD,
        FAST,
        SECURE
    }

}
