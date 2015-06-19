package com.tct.datamodel;

/**
 * Created by libin on 15/6/14.
 */
public class LoginResult {
    private boolean succeed;
    private String token;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
