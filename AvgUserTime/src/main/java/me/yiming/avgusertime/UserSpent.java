package me.yiming.avgusertime;

/**
 * This class models the aggregate view of user spent time information. 
 * It stores the userId, the total time user spent and the count of user visit the app. 
 */
public class UserSpent {
    private String userId;
    private long timeSpent;
    private long count;

    public UserSpent(String userId) {
        this.userId = userId;
        this.timeSpent = 0;
        this.count = 0;
    }

    public void aggregate(long timeSpent, long counter) {
        this.timeSpent += timeSpent;
        this.count += counter;
    }

    public void aggregate(long timeSpent) {
        this.timeSpent += timeSpent;
        this.count += timeSpent > 0 ? 1 : 0;
    }

    public long getAverageTimeSpent() {
        return count == 0 ? 0 : timeSpent / count;
    }

    @Override
    public String toString() {
        return (this.userId == null ? "" : userId + ",") + getAverageTimeSpent();
    }
}
