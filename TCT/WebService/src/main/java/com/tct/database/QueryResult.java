package com.tct.database;

import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * Login: binl
 * Date: 6/24/15
 * Time: 1:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryResult {
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    int rows;
    ResultSet resultSet;
}
