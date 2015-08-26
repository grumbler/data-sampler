package me.grumbler.sampler;

/**
 * Produces data sample, using given RNG
 * This class is intentionally left thread-unsafe
 * sampler
 * me.grumbler.sampler
 */
public class Sampler {

    private RandomWrapper generator;
    private byte[] result;
    private int sampleSize;
    private long count = 0;

    /**
     * @param generator  - wrapper for desired RNG.
     * @param sampleSize - sample size to produce.
     */
    public Sampler(RandomWrapper generator, int sampleSize) {
        this.generator = generator;
        this.result = new byte[sampleSize];
        this.sampleSize = sampleSize;
    }

    /**
     * Call this method each time you have some data to process
     * Implements https://en.wikipedia.org/wiki/Reservoir_sampling
     *
     * @param data      - chunk of byte data to be processed
     * @param readBytes - chunk length
     */
    public void processData(byte[] data, int readBytes) {
        for (int i = 0; i < readBytes; i++) {
            byte item = data[i];
            this.count++;
            if (count <= this.sampleSize) {
                this.result[(int) (this.count - 1)] = item;
            } else {
                long r = this.generator.nextLong(this.count);
                if (r < this.sampleSize) {
                    this.result[(int) r] = item;
                }
            }
        }
    }

    /**
     * @return byte[] - byte array with produced sample
     * @throws NotEnoughDataException - if there wasn't
     *                                enough data processed to produce the sample
     */
    public byte[] getResult() throws NotEnoughDataException {
        //TODO: this doesn't seem to be a good idea to return field..
        if (this.count < this.sampleSize) {
            throw new NotEnoughDataException("Not enough data to produce sample");
        }
        return this.result;
    }

}
