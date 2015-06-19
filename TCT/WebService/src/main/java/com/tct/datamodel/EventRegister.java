package com.tct.datamodel;

/**
 * Created by libin on 15/6/14.
 */
public class EventRegister {
    private int eventId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    private int userId;
}
