package me.grumbler.sampler.tests;

import me.grumbler.sampler.Main;
import org.junit.Assert;
import org.junit.Test;

/**
 * sampler
 * me.grumbler.sampler.tests
 */
public class MainTest {

    @Test
    public void testMain() throws Exception {
        String[] args = new String[2];
        args[0] = "--length";
        args[1] = "0";
        Main instance = new Main();
        int code = instance.doMain(args);
        Assert.assertEquals(code, 0);
    }
}
