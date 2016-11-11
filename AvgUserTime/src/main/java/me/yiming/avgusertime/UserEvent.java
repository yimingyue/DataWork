package me.yiming.avgusertime;

/**
 * This class models the user event object using the userId and timestamp. 
 */
public class UserEvent {
    private final String userId;
    private final long timestamp;

    public UserEvent(String userId, long timestamp) {
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return this.userId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return (this.userId == null ? "" : userId + ",") + timestamp;
    }
}
