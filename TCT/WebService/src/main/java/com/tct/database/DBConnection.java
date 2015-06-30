package com.tct.database;

import java.sql.*;

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

    public QueryResult execute(String sql) {
        PreparedStatement stmt;
        QueryResult queryResult;
        try {
            stmt = mConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int rows = stmt.executeUpdate();
            mConnection.commit();
            ResultSet resultSet = stmt.getGeneratedKeys();

            queryResult = new QueryResult();
            queryResult.setRows(rows);
            queryResult.setResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                mConnection.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            queryResult = null;
        }

        return queryResult;
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
    }
}
