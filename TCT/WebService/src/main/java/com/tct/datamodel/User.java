package com.tct.datamodel;

/**
 * Created with IntelliJ IDEA.
 * Login: binl
 * Date: 6/24/15
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class User {
    int id = 0;
    String name = "";
    Avatar avatar = new Avatar();

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
