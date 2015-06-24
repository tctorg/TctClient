package com.tct.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by libin on 2015/5/19.
 */
public class DBConnectionPool {
    LinkedBlockingQueue<DBConnection> mPool = null;
    LinkedBlockingQueue<DBConnection> mInUsePool = null;
    String mDriver;
    String mUrl;
    String mUser;
    String mPassword;
    int mSize;

    public DBConnectionPool(String driver, String url, String user, String password, int size) {
        mDriver = driver;
        mUrl = url;
        mUser = user;
        mPassword = password;
        mSize = size;

        createPool();
    }

    public void createPool() {
        mPool = new LinkedBlockingQueue<>();
        mInUsePool = new LinkedBlockingQueue<>();
        for (int i = 0; i < mSize; i++) {
            mPool.offer(createConnection());
        }
    }

    DBConnection createConnection() {
        Connection connection;
        try {
            Class.forName(mDriver);
            connection = DriverManager.getConnection(mUrl, mUser, mPassword);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
            connection = null;
        }

        return new DBConnection(connection);
    }

    public QueryResult execute(String sql) {
        DBConnection connection = getConnection();
        QueryResult queryResult = connection.execute(sql);
        mInUsePool.remove(connection);
        mPool.offer(connection);
        return queryResult;
    }

    public ResultSet executeSelect(String sql) {
        DBConnection connection = getConnection();
        ResultSet resultSet = connection.executeSelect(sql);
        mInUsePool.remove(connection);
        mPool.offer(connection);
        return resultSet;
    }

    DBConnection getConnection() {
        DBConnection connection;
        while (true) {
            try {
                connection = mPool.take();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (!connection.isValid()) {
            connection = createConnection();
            if (connection.isValid())
                break;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mInUsePool.offer(connection);
        return connection;
    }
}
