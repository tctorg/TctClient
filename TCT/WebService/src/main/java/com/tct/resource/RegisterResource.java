package com.tct.resource;

import com.tct.SessionManager;
import com.tct.database.DBManager;
import com.tct.datamodel.EventRegister;
import com.tct.datamodel.EventRegisterResult;
import com.tct.datamodel.LoginResult;
import com.tct.datamodel.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: binl
 * Date: 6/15/15
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("register")
public class RegisterResource {
    @Context
    HttpServletRequest request;

    @POST
    @Path("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResult registerUser(User user){
        LoginResult loginResult = new LoginResult();
        loginResult.setSucceed(false);
        loginResult.setToken("");
        if (user == null)
            return loginResult;

        if (DBManager.addUser(user)){
            loginResult.setSucceed(true);
            loginResult.setToken(request.getSession().getId());
            SessionManager.getInstance().login(request);
        }

        return loginResult;
    }

    @POST
    @Path("event")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EventRegisterResult registerEvent(EventRegister register){
        if (!SessionManager.getInstance().isLogin(request))
            return null;

        EventRegisterResult result = new EventRegisterResult();
        result.setSucceed(false);
        if (register == null)
            return result;

        if (DBManager.addEventRegister(register)){
            result.setSucceed(true);
        }

        return result;
    }
}
