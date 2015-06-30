package com.tct.resource;

import com.tct.SessionManager;
import com.tct.database.DBManager;
import com.tct.datamodel.*;
import com.tct.utilities.Configuration;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Login: binl
 * Date: 6/15/15
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("user")
public class UserResource {
    @Context
    HttpServletRequest request;

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User registerUser(UserRegister userRegister) {
        if (userRegister == null)
            return null;

        int id = DBManager.addUser(userRegister);
        if (id > 0) {
            User user = new User();
            user.setId(id);
            user.getAvatar().setId(0);
            user.setName(userRegister.getName());
            SessionManager.getInstance().login(request, id);
            return user;
        } else {
            return null;
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User login(Login login) {
        if (login == null)
            return null;

        User user = DBManager.getUser(login);
        if (user != null) {
            SessionManager.getInstance().login(request, user.getId());
            return user;
        } else {
            return null;
        }
    }

    @POST
    @Path("{id}/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public APIResult updateAvatar(@PathParam("id") int id,
                               @FormDataParam("file") InputStream file,
                               @FormDataParam("file") FormDataContentDisposition fileDisposition){

        APIResult result = new APIResult();
        result.setResult(false);

        String path = Configuration.get("folder.avatar");
        String fileName = UUID.randomUUID().toString();
        try {
            OutputStream outputStream = new FileOutputStream(new File(path, fileName));
            int length;

            byte[] buff = new byte[256];

            while (-1 != (length = file.read(buff))) {
                outputStream.write(buff, 0, length);
            }
            file.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }

        User user = DBManager.getUser(id);

        result.setResult(DBManager.updateAvatar(user, fileName));
        return result;
    }
}
