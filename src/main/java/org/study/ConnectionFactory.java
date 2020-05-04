package org.study;

import org.postgresql.ds.PGSimpleDataSource;
import org.study.exceptions.ConnectionDBException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static ConnectionFactory instance;

    private String url;
    private String user;
    private String password;

    private ConnectionFactory() {}

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public void setConnectionInfo(Properties dbConfiguration) {
        this.url = dbConfiguration.getProperty("url");
        this.user = dbConfiguration.getProperty("user");
        this.password = dbConfiguration.getProperty("password");
    }

    public void setConnectionInfo(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnectionToDB() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        try {
            return dataSource.getConnection();
        } catch (SQLException exception) {
            throw new ConnectionDBException("Can't get connection to DB!", exception);
        }
    }
}
