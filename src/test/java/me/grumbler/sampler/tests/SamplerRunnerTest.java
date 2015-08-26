package me.grumbler.sampler.tests;

import me.grumbler.sampler.SamplerRunner;
import org.junit.Assert;
import org.junit.Test;

/**
 * sampler
 * me.grumbler.sampler.tests
 */
public class SamplerRunnerTest {

    @Test
    public void testMain() throws Exception {
        String[] args = new String[2];
        args[0] = "--length";
        args[1] = "0";
        SamplerRunner instance = new SamplerRunner();
        int code = instance.doMain(args);
        Assert.assertEquals(code, 0);
    }
}
