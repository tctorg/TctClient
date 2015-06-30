package com.tct.database;

import com.tct.datamodel.*;
import com.tct.utilities.Configuration;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by libin on 2015/5/19.
 */
public class DBManager {
    private static DBConnectionPool pool = new DBConnectionPool(
            Configuration.get("database.driver"),
            Configuration.get("database.url"),
            Configuration.get("database.user"),
            Configuration.get("database.password"),
            4);

    public static int addUser(UserRegister userRegister) {
        String sql = "INSERT INTO \"user\" (\"username\", \"password\", \"aid\") "
                + "VALUES ('" + userRegister.getName() + "', '" + userRegister.getPassword() + "', '0');";
        QueryResult queryResult = pool.execute(sql);
        if (queryResult == null) {
            return 0;
        } else {
            try {
                if (queryResult.getResultSet().next()) {
                    return queryResult.getResultSet().getInt(1);
                } else {
                    return 0;
                }
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public static User getUser(Login login) {
        String sql = "SELECT \"id\", \"username\", \"aid\" FROM \"user\" WHERE \"username\" = '" + login.getUsername() + "' AND \"password\" = '" + login.getPassword() + "'";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return null;

        User user;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2).trim());
                Avatar avatar = new Avatar();
                avatar.setId(resultSet.getInt(3));
                user.setAvatar(avatar);
            } else {
                user = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            user = null;
        }

        return user;
    }

    public static User getUser(int id) {
        String sql = "SELECT \"id\", \"username\", \"aid\" FROM \"user\" WHERE \"id\" = '" + id + "'";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return null;

        User user;
        try {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2).trim());
                Avatar avatar = new Avatar();
                avatar.setId(resultSet.getInt(3));
                user.setAvatar(avatar);
            } else {
                user = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            user = null;
        }

        return user;
    }

    public static boolean updateUser(User user){
        String sql = "UPDATE \"user\" SET \"username\" = '" + user.getName() + "', \"aid\" = '" + user.getAvatar().getId() + "' WHERE \"id\" = '" + user.getId() + "';";
        QueryResult queryResult = pool.execute(sql);
        if (queryResult == null) {
            return false;
        }

        return true;
    }

    private static int addAvatar(User user, String path){
        String sql = "INSERT INTO \"avatar\" (\"uid\", \"path\") VALUES ('" + String.valueOf(user.getId()) + "', '" + path + "');";
        QueryResult queryResult = pool.execute(sql);
        if (queryResult == null) {
            return 0;
        } else {
            try {
                if (queryResult.getResultSet().next()) {
                    return queryResult.getResultSet().getInt(1);
                } else {
                    return 0;
                }
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public static String getAvatar(User user){
        String sql = "SELECT \"path\" FROM \"avatar\" WHERE \"id\" = '" + user.getAvatar().getId() + "' AND \"uid\" = '" + user.getId() + "'";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return "";

        try {
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static boolean updateAvatar(User user, String path){
        if (user.getAvatar().getId() > 0){
            String sql = "UPDATE \"avatar\" SET \"path\" = '" + path + "' WHERE \"id\" = '" + user.getAvatar().getId() + "' AND \"uid\" = '" + user.getId() + "';";
            QueryResult queryResult = pool.execute(sql);
            if (queryResult == null) {
                return false;
            } else {
                return true;
            }
        } else{
            user.getAvatar().setId(addAvatar(user, path));
            return updateUser(user);
        }
    }

    public static boolean addEventRegister(User user, int eventId) {
        String sql = "INSERT INTO \"register\" (\"eid\", \"uid\") select " +
                String.valueOf(eventId) + ", " + String.valueOf(user.getId()) +
                " from \"user\", \"events\" where not exists (select * from \"register\" where eid = " + String.valueOf(eventId) +
                " and uid = " + String.valueOf(user.getId()) + ") limit 1;";
        if (pool.execute(sql) == null)
            return false;
        else
            return true;
    }

    public static List<Event> getEvents() {
        String sql = "SELECT \"id\", \"name\" FROM \"events\"";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return null;

        List<Event> events = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt(1));
                event.setName(resultSet.getString(2).trim());
                events.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;
    }

    public static List<Topic> getTopics() {
        String sql = "select \"topic\".id, \"topic\".time, \"topic\".subject, \"user\".username from \"topic\" inner join \"user\" on \"user\".id = \"topic\".uid ORDER BY \"topic\".time DESC";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return null;

        ArrayList<Topic> topics = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString(4).trim());

                Topic topic = new Topic();
                topic.setId(resultSet.getInt(1));
                topic.setTimestamp(resultSet.getTimestamp(2).getTime());
                topic.setSubject(resultSet.getString(3));
                topic.setUser(user);
                topics.add(topic);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return topics;
    }

    public static Topic getCommentByTopic(int topicId) {
        String sql = "select \"topic\".id as tid, \"topic\".time as ttime, \"topic\".subject, \"topic\".content, \"user\".id as tuid, \"user\".username as tusername, \"c\".cid, \"c\".time as ctime, \"c\".comment, \"c\".uid as cuid, \"c\".username as cusername from \"topic\" inner join \"user\" on \"user\".id = \"topic\".uid inner join (select \"comment\".id as cid, \"comment\".tid, \"comment\".time, \"comment\".comment, \"user\".id as uid, \"user\".username from \"comment\" inner join \"user\" on \"user\".id = \"comment\".uid) as c on \"c\".tid = \"topic\".id where \"topic\".id = " + String.valueOf(topicId) + " ORDER BY \"c\".time DESC";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return null;

        Topic topic = null;

        try {
            while (resultSet.next()) {
                if (topic == null) {
                    topic = new Topic();

                    User user = new User();
                    user.setId(resultSet.getInt(5));
                    user.setName(resultSet.getString(6).trim());

                    topic.setId(resultSet.getInt(1));
                    topic.setTimestamp(resultSet.getTimestamp(2).getTime());
                    topic.setSubject(resultSet.getString(3));
                    topic.setContent(resultSet.getString(4));
                    topic.setUser(user);
                }

                Comment comment = new Comment();
                comment.setId(resultSet.getInt(7));
                comment.setTimestamp(resultSet.getTimestamp(8).getTime());
                comment.setContent(resultSet.getString(9));

                User user = new User();
                user.setId(resultSet.getInt(10));
                user.setName(resultSet.getString(11).trim());
                comment.setUser(user);

                topic.addComments(comment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return topic;
    }

    public static int addTopic(Topic topic) {
        String sql = "INSERT INTO \"topic\" (\"uid\", \"time\", \"subject\", \"content\") VALUES (" + topic.getUser().getId() + ", now(), '" + topic.getSubject() + "', '" + topic.getContent() + "')";
        QueryResult queryResult = pool.execute(sql);
        if (queryResult == null) {
            return 0;
        } else if (queryResult.getRows() == 0) {
            return 0;
        } else {
            try {
                if (queryResult.getResultSet().next()) {
                    return queryResult.getResultSet().getInt(1);
                } else {
                    return 0;
                }
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public static int addComment(Comment comment, int topicId) {
        String sql = "INSERT INTO \"comment\" (\"tid\", \"uid\", \"time\", \"comment\") VALUES ('" + topicId + "', '" + comment.getUser().getId() + "', now(), '" + comment.getContent() + "')";
        QueryResult queryResult = pool.execute(sql);
        if (queryResult == null) {
            return 0;
        } else if (queryResult.getRows() == 0) {
            return 0;
        } else {
            try {
                if (queryResult.getResultSet().next()) {
                    return queryResult.getResultSet().getInt(1);
                } else {
                    return 0;
                }
            } catch (Exception e) {
                return 0;
            }
        }
    }
}
