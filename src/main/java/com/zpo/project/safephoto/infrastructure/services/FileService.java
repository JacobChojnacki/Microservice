package com.zpo.project.safephoto.infrastructure.services;

import com.zpo.project.safephoto.infrastructure.interfaces.IFileService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService implements IFileService {
    private final String mainDir = "./repository/";

    @Override
    public void saveToFile(String filePath, String content) throws IOException {
        var file = new File(mainDir + filePath);
        file.getParentFile().mkdirs();
        file.createNewFile();
        var fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

    @Override
    public String loadFromFile(String filePath) throws IOException {
        var file = new File(mainDir + filePath);
        if (file.isFile()) {
            var content = new StringBuilder();
            var fileReader = new FileReader(file);
            var bufferedReader = new BufferedReader(fileReader);
            var line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            fileReader.close();
            return content.toString();
        }
        return null;
    }

    @Override
    public Set<String> listFilesInDirectory(String directory) {
        if (!Files.exists(Paths.get(mainDir + directory))) {
            return new HashSet<String>();
        }
        return Stream.of(Objects.requireNonNull(new File(mainDir + directory).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
}
