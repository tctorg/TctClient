package com.tct;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by libin on 15/6/14.
 */
public class SessionManager {
    public static SessionManager getInstance() {
        return instance;
    }

    private static SessionManager instance = new SessionManager();

    public void login(HttpServletRequest request, int id) {
        request.getSession().setAttribute("login", true);
        request.getSession().setAttribute("id", id);
    }

    public boolean isLogin(HttpServletRequest request) {
        Object o = request.getSession().getAttribute("login");
        if (o == null)
            return false;

        return (boolean) o;
    }

    public int getUserId(HttpServletRequest request) {
        if (isLogin(request))
            return (int) request.getSession().getAttribute("id");
        else
            return 0;
    }
}
