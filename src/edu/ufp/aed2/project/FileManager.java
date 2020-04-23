package edu.ufp.aed2.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Singleton for a File Manager Class
 * All the operation for the file related stuff are done here
 */
public class FileManager {
    private static FileManager obj;

    private final File file;

    private FileManager(){
        this.file = new File("/app/bd.txt");
    }

    public static FileManager getInstance(){
        if (obj==null) obj = new FileManager();
        return obj;
    }

    /**
     * Read line by line the file and returns all of it.
     * @return All lines from the file.
     * @throws IOException reading the file.
     */
    public String readAll() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line);
            content.append(System.lineSeparator());
        }
        return content.toString();
    }




}
