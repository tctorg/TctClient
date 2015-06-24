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

    public static boolean addUser(User user) {
        String sql = "INSERT INTO \"user\" (\"username\", \"password\") "
                + "VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "');";
        if (pool.execute(sql) == 1)
            return true;
        else
            return false;
    }

    public static User getUser(User user) {
        String sql = "SELECT \"id\", \"username\", \"password\" FROM \"user\" WHERE \"username\" = '" + user.getUsername() + "' AND \"password\" = '" + user.getPassword() + "'";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return null;

        User user1;
        try {
            if (resultSet.next()) {
                user1 = new User();
                user1.setId(resultSet.getInt(1));
                user1.setUsername(resultSet.getString(2).trim());
                user1.setPassword(resultSet.getString(3).trim());
            } else {
                user1 = null;
            }
        } catch (Exception e){
            e.printStackTrace();
            user1 = null;
        }

        return user1;
    }

    public static boolean addEventRegister(EventRegister eventRegister) {
        String sql = "INSERT INTO \"register\" (\"eid\", \"uid\") select " +
                String.valueOf(eventRegister.getEventId()) + ", " + String.valueOf(eventRegister.getUserId()) +
                " from \"user\", \"events\" where not exists (select * from \"register\" where eid = " + String.valueOf(eventRegister.getEventId()) +
                " and uid = " +  String.valueOf(eventRegister.getUserId()) + ") limit 1;";
        if (pool.execute(sql) == 1)
            return true;
        else
            return false;
    }

    public static Events getEvents(){
        String sql = "SELECT \"id\", \"name\" FROM \"events\"";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return null;

        Events events = new Events();

        try {
            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt(1));
                event.setName(resultSet.getString(2).trim());
                events.addEvents(event);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return events;
    }

    public static List<Topic> getTopics(){
        String sql = "select \"topic\".id, \"topic\".time, \"topic\".subject, \"user\".username from \"topic\" inner join \"user\" on \"user\".id = \"topic\".uid ORDER BY \"topic\".time DESC";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return null;

        ArrayList<Topic> topics = new ArrayList<>();
        try {
            while (resultSet.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(resultSet.getString(4).trim());

                Topic topic = new Topic();
                topic.setId(resultSet.getInt(1));
                topic.setTimestamp(resultSet.getTimestamp(2).getTime());
                topic.setSubject(resultSet.getString(3));
                topic.setUser(userInfo);
                topics.add(topic);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return topics;
    }

    public static Topic getCommentByTopic(String topicId){
        String sql = "select \"topic\".id as tid, \"topic\".time as ttime, \"topic\".subject, \"topic\".content, \"user\".id as tuid, \"user\".username as tusername, \"c\".cid, \"c\".time as ctime, \"c\".comment, \"c\".uid as cuid, \"c\".username as cusername from \"topic\" inner join \"user\" on \"user\".id = \"topic\".uid inner join (select \"comment\".id as cid, \"comment\".tid, \"comment\".time, \"comment\".comment, \"user\".id as uid, \"user\".username from \"comment\" inner join \"user\" on \"user\".id = \"comment\".uid) as c on \"c\".tid = \"topic\".id where \"topic\".id = " + topicId + " ORDER BY \"c\".time DESC";
        ResultSet resultSet = pool.executeSelect(sql);
        if (resultSet == null)
            return null;

        Topic topic = null;

        try {
            while (resultSet.next()) {
                if (topic == null){
                    topic = new Topic();

                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(resultSet.getInt(5));
                    userInfo.setUserName(resultSet.getString(6).trim());

                    topic.setId(resultSet.getInt(1));
                    topic.setTimestamp(resultSet.getTimestamp(2).getTime());
                    topic.setSubject(resultSet.getString(3));
                    topic.setContent(resultSet.getString(4));
                    topic.setUser(userInfo);
                }

                Comment comment = new Comment();
                comment.setId(resultSet.getInt(7));
                comment.setTimestamp(resultSet.getTimestamp(8).getTime());
                comment.setContent(resultSet.getString(9));

                UserInfo userInfo = new UserInfo();
                userInfo.setId(resultSet.getInt(10));
                userInfo.setUserName(resultSet.getString(11).trim());
                comment.setUser(userInfo);

                topic.addComments(comment);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return topic;
    }
}
