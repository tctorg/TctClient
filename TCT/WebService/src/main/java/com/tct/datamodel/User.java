package com.tct.datamodel;

/**
 * Created by libin on 15/6/14.
 */
public class User {
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;
    private String password;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }
}
