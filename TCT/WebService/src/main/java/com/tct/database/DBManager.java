package com.tct.database;

import com.tct.datamodel.Event;
import com.tct.datamodel.EventRegister;
import com.tct.datamodel.Events;
import com.tct.datamodel.User;
import com.tct.utilities.Configuration;

import java.sql.ResultSet;

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
}
