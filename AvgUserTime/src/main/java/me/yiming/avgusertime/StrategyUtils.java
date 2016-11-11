package me.yiming.avgusertime;

/**
 * This is an Utility class for applying different strategy to the calculation of average user time spent between two consecutive timestamp of one user.
 * There are 4 cases for the two consecutive timestamps and the return result is different based on the strategy used. See the comment in the code for details.
 */
public class StrategyUtils {
    enum Strategy {
        DROP ("drop"),
        AVERAGE ("avg"),
        MAX ("max"),
        DEFAULT ("default");
        private final String strategy;
        Strategy(String strategy) {
            this.strategy = strategy;
        }

        public String getStrategy() {
            return this.strategy;
        }
    }

    private static Strategy strategy;

    public static void setStrategy(String s) {
        strategy = Strategy.valueOf(s.toUpperCase());
    }

    public static long getAvgTime(long timestamp1, long timestamp2) {
        if (timestamp1 == 0 || timestamp2 == 0) {
            System.err.println("Invalid timestamp 0.");
            return 0;
        }
        if (timestamp1 < 0 && timestamp2 > 0) {         // case 1, normal case, a close after open
            return timestamp2 + timestamp1;
        } else if (timestamp1 < 0 && timestamp2 < 0) {  // case 2, missing close between two open
            if (strategy == Strategy.AVERAGE)
                return (timestamp1 - timestamp2) / 2;
            else if (strategy == Strategy.DROP || strategy == Strategy.DEFAULT)
                return 0;
            else if (strategy == Strategy.MAX)
                return timestamp1 - timestamp2;
            else
                throw new RuntimeException("Strategy is not set!");
        } else if (timestamp1 > 0 && timestamp2 > 0) {  // case 3, missing open between two close
            if (strategy == Strategy.AVERAGE)
                return (timestamp2 - timestamp1) / 2;
            else if (strategy == Strategy.DROP || strategy == Strategy.DEFAULT)
                return 0;
            else if (strategy == Strategy.MAX)
                return timestamp2 - timestamp1;
            else
                throw new RuntimeException("Strategy is not set!");
        } else
            return 0;                                   // case 4, normal case, an open after close
    }
}
