package me.yiming.avgusertime;

/**
 * Created by ymyue on 9/25/16.
 */
public class Event {
    private final String userId;
    private final long timestamp;
    private final ActionType actionType;

    public Event(String userId, long timestamp, ActionType actionType) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.actionType = actionType;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public String getUserId() {
        return this.userId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
