package com.tct.resource;

import com.tct.SessionManager;
import com.tct.database.DBManager;
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
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("login")
public class LoginResource {
    @Context
    HttpServletRequest request;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResult login(User user){
        LoginResult result = new LoginResult();
        result.setSucceed(false);
        result.setToken("");
        if (user == null)
            return result;

        User user1 = DBManager.getUser(user);
        if (user1 != null &&
                user1.getUsername().equalsIgnoreCase(user.getUsername()) &&
                user1.getPassword().equals(user.getPassword())){
            result.setSucceed(true);
            result.setToken(request.getSession().getId());
            SessionManager.getInstance().login(request, user1.getId());
        }
        return result;
    }
}
