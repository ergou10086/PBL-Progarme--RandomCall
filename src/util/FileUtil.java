package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    public static Map<String, String> readUsers(String filename) {
        Map<String, String> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean writeUsers(Map<String, String> users, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
} 