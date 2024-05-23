package com.example.webeswimming;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileCont {

    public static void writeToFile(String fileName, Serializable object) {
        String directoryPath = Paths.get("").toAbsolutePath().toString() + File.separator + "data";
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = directoryPath + File.separator + fileName;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
        } catch (IOException e) {
            System.err.println("IOException occurred while writing to file: " + e.getMessage());
            // You can also add more handling logic here if needed
        }
    }


    public static ArrayList<Member> readMemberListFromFile(String fileName) {
        String directoryPath = Paths.get("").toAbsolutePath().toString() + File.separator + "data";
        String filePath = directoryPath + File.separator + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Member>) ois.readObject();
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException occurred: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
