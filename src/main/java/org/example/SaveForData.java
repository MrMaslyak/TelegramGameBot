package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SaveForData {


    public static String getToken(String filePath) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            return reader.readLine();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
