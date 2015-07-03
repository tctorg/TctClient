package com.tct.resource;

import com.tct.SessionManager;
import com.tct.database.DBManager;
import com.tct.datamodel.APIResult;
import com.tct.datamodel.Comment;
import com.tct.datamodel.Topic;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Login: binl
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
    public List<Topic> getTopics() {
        if (!SessionManager.getInstance().isLogin(request))
            return new ArrayList<>();

        return DBManager.getTopics();
    }

    @GET
    @Path("topic/{id}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Topic getComments(@PathParam("id") int id) {
        if (!SessionManager.getInstance().isLogin(request))
            return null;

        return DBManager.getCommentByTopic(id);
    }

    @POST
    @Path("topic")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Topic addTopic(Topic topic) {
        if (!SessionManager.getInstance().isLogin(request))
            return null;

        topic.getUser().setId(SessionManager.getInstance().getUserId(request));
        int id = DBManager.addTopic(topic);
        if (id == 0)
            return null;

        topic.setId(id);
        return topic;
    }

    @POST
    @Path("topic/{id}/comment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Comment addComment(@PathParam("id") int topicId, Comment comment) {
        if (!SessionManager.getInstance().isLogin(request))
            return null;

        comment.getUser().setId(SessionManager.getInstance().getUserId(request));
        int id = DBManager.addComment(comment, topicId);
        if (id <= 0)
            return null;

        comment.setId(id);

        return comment;
    }
}
