package com.tct.resource;

import com.tct.SessionManager;
import com.tct.database.DBManager;
import com.tct.datamodel.Topic;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: binl
 * Date: 6/24/15
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("forum")
public class ForumResource {
    @Context
    HttpServletRequest request;

    @GET
    @Path("topics")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Topic> getTopics(){
        if (!SessionManager.getInstance().isLogin(request))
            return null;

        return DBManager.getTopics();
    }

    @GET
    @Path("topic/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Topic getComments(@PathParam("id") String id){
        return DBManager.getCommentByTopic(id);
    }
}
