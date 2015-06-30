package com.tct.resource;

import com.tct.SessionManager;
import com.tct.database.DBManager;
import com.tct.datamodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Login: binl
 * Date: 6/15/15
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("event")
public class EventResource {
    @Context
    HttpServletRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getEvents() {
        if (!SessionManager.getInstance().isLogin(request))
            return new ArrayList<>();

        return DBManager.getEvents();
    }

    @POST
    @Path("{id}/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public APIResult registerEvent(@PathParam("id") int id) {
        APIResult result = new APIResult();
        result.setResult(false);

        if (!SessionManager.getInstance().isLogin(request))
            return result;

        User user = DBManager.getUser(SessionManager.getInstance().getUserId(request));
        if (DBManager.addEventRegister(user, id)) {
            result.setResult(true);
        }

        return result;
    }
}
