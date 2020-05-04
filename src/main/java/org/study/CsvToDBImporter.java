package org.study;

import org.postgresql.copy.CopyManager;
import org.postgresql.jdbc.PgConnection;
import org.study.exceptions.ConnectionDBException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CsvToDBImporter {

    private final ConnectionFactory connectionFactory;

    public CsvToDBImporter(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void importDataToDB(List<File> dataFiles) {
        try (Connection connection = connectionFactory.getConnectionToDB()) {
            CopyManager copyManager = new CopyManager(connection.unwrap(PgConnection.class));

            changeTriggersMode(connection, "replica");
            copyData(dataFiles, copyManager);
            changeTriggersMode(connection, "origin");
        } catch (SQLException exception) {
            throw new ConnectionDBException("Connection unavailable!", exception);
        }
    }

    private void copyData(List<File> dataFiles, CopyManager copyManager) {
        dataFiles.forEach(file -> {
            String copySql = "COPY " + file.getName().replace(".csv", "") + " FROM STDIN WITH DELIMITER ','";

            try {
                copyManager.copyIn(copySql, new FileReader(file.getPath()));
            } catch (SQLException | IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void changeTriggersMode(Connection connection, String parameter) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SET session_replication_role = '" + parameter + "'");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
