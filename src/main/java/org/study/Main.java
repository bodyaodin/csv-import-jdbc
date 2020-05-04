package org.study;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {

    private static final String DB_CONFIG_PATH = "src/main/resources/DBConfiguration.properties";
    private static final String DATA_PATH = "src/main/resources/data/";

    public static void main(String[] args) {
        Properties dbConfiguration = new Properties();
        try {
            dbConfiguration.load(new FileInputStream(DB_CONFIG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        connectionFactory.setConnectionInfo(dbConfiguration);

        DataManager dataManager = new DataManager(DATA_PATH);
        List<File> dataFiles = dataManager.getFileList();

        CsvToDBImporter csvToDBImporter = new CsvToDBImporter(connectionFactory);
        csvToDBImporter.importDataToDB(dataFiles);
    }
}
