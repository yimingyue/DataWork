package me.yiming.avgusertime;

import org.junit.Assert;
import org.junit.Test;

public class TestStrategy {

    @Test
    public void testDropStrategy() {
        StrategyUtils.setStrategy("drop");
        Assert.assertEquals(0, StrategyUtils.getAvgTime(1474441200, 1474441456));
        Assert.assertEquals(0, StrategyUtils.getAvgTime(-1474441200, -1474441456));
        Assert.assertEquals(256, StrategyUtils.getAvgTime(-1474441200, 1474441456));
        Assert.assertEquals(0, StrategyUtils.getAvgTime(1474441200, -1474441456));
    }

    @Test
    public void testAverageStrategy() {
        StrategyUtils.setStrategy("average");
        Assert.assertEquals(128, StrategyUtils.getAvgTime(1474441200, 1474441456));
        Assert.assertEquals(128, StrategyUtils.getAvgTime(-1474441200, -1474441456));
        Assert.assertEquals(256, StrategyUtils.getAvgTime(-1474441200, 1474441456));
        Assert.assertEquals(0, StrategyUtils.getAvgTime(1474441200, -1474441456));
    }

    @Test
    public void testMaxStrategy() {
        StrategyUtils.setStrategy("max");
        Assert.assertEquals(256, StrategyUtils.getAvgTime(1474441200, 1474441456));
        Assert.assertEquals(256, StrategyUtils.getAvgTime(-1474441200, -1474441456));
        Assert.assertEquals(256, StrategyUtils.getAvgTime(-1474441200, 1474441456));
        Assert.assertEquals(0, StrategyUtils.getAvgTime(1474441200, -1474441456));
    }

    @Test
    public void testDefaultStrategy() {
        StrategyUtils.setStrategy("default");
        Assert.assertEquals(0, StrategyUtils.getAvgTime(1474441200, 1474441456));
        Assert.assertEquals(0, StrategyUtils.getAvgTime(-1474441200, -1474441456));
        Assert.assertEquals(256, StrategyUtils.getAvgTime(-1474441200, 1474441456));
        Assert.assertEquals(0, StrategyUtils.getAvgTime(1474441200, -1474441456));
    }
}
