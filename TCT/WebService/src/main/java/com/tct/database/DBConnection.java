package com.tct.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by libin on 2015/5/19.
 */
class DBConnection {
    Connection mConnection = null;

    public DBConnection(Connection connection) {
        mConnection = connection;
    }

    public boolean isValid() {
        if (mConnection == null)
            return false;

        try {
            return mConnection.isValid(3000);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int execute(String sql) {
        Statement stmt;
        int row;
        try {
            stmt = mConnection.createStatement();
            row = stmt.executeUpdate(sql);
            mConnection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                mConnection.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            row = 0;
        }

        return row;
    }

    public ResultSet executeSelect(String sql) {
        Statement stmt;
        ResultSet resultSet;
        try {
            stmt = mConnection.createStatement();
            resultSet = stmt.executeQuery(sql);
            mConnection.commit();
        } catch (Exception e) {
            resultSet = null;
            e.printStackTrace();
            try {
                mConnection.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return resultSet;
    }}
