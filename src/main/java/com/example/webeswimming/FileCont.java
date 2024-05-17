package com.example.webeswimming;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileCont {

    public static void writeToFile(String fileName, Serializable object) throws IOException {
        String directoryPath = Paths.get("").toAbsolutePath().toString() + File.separator + "data";
        File file = new File(directoryPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = directoryPath + File.separator + fileName;
        try (ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            fos.writeObject(object);
        }
    }
    public static ArrayList<Member> readMemberListFromFile(String fileName) throws IOException, ClassNotFoundException {
        String directoryPath = Paths.get("").toAbsolutePath().toString() + File.separator + "data";
        String filePath = directoryPath + File.separator + fileName;
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Member>) ois.readObject();
        }
    }
}
