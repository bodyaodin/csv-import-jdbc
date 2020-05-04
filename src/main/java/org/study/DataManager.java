package org.study;

import org.study.exceptions.FileUnavailableException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataManager {

    private final String dataPath;

    public DataManager(String dataPath) {
        this.dataPath = dataPath;
    }

    public List<File> getFileList() {
        try (Stream<Path> dataPathStream = Files.walk(Paths.get(dataPath))) {
            return dataPathStream
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            throw new FileUnavailableException("File is unavailable!", exception);
        }
    }
}
