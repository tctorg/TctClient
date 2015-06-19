package com.tct.resource;

import com.tct.SessionManager;
import com.tct.database.DBManager;
import com.tct.datamodel.Event;
import com.tct.datamodel.Events;
import com.tct.datamodel.LoginResult;
import com.tct.datamodel.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created with IntelliJ IDEA.
 * User: binl
 * Date: 6/15/15
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("events")
public class EventsResource {
    @Context
    HttpServletRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Events getEvents(){
        if (!SessionManager.getInstance().isLogin(request))
            return null;

        return DBManager.getEvents();
    }

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Event registerUser(Event event){
//        if (event == null)
//            return null;
//
//        if (DBManager.addUser(user)){
//            loginResult.setSucceed(true);
//            loginResult.setToken(request.getSession().getId());
//            SessionManager.getInstance().login(request);
//        }
//
//        return loginResult;
//    }
}
