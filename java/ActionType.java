package me.yiming.avgusertime;

/**
 * Created by ymyue on 9/25/16.
 */
public enum ActionType {
    OPEN ("open"),
    CLOSE ("close");

    private final String actionType;
    ActionType(String actionType) {
        this.actionType = actionType;
    }

    String getActionType() {
        return this.actionType;
    }
}
