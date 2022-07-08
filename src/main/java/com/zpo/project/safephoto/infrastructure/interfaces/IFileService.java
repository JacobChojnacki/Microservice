package com.zpo.project.safephoto.infrastructure.interfaces;

import java.io.IOException;
import java.util.Set;

public interface IFileService {
    void saveToFile(String file, String content) throws IOException;
    String loadFromFile(String file) throws IOException;
    Set<String> listFilesInDirectory(String directory);
}
