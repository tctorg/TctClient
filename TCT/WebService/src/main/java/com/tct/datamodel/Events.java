package com.tct.datamodel;

import java.util.ArrayList;

/**
 * Created by libin on 15/6/14.
 */
public class Events {
    private ArrayList<Event> events = new ArrayList<>();

    public void addEvents(Event event) {
        this.events.add(event);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
