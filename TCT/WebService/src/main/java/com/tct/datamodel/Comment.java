package com.tct.datamodel;

/**
 * Created with IntelliJ IDEA.
 * Login: binl
 * Date: 6/24/15
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Comment {
    int id;
    String content;
    long timestamp;
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
