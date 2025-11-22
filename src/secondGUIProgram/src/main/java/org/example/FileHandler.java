package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public void saveToBinary(List<Firm> firms, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(new ArrayList<>(firms));
        }
    }

    @SuppressWarnings("unchecked")
    public List<Firm> loadFromBinary(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Firm>) ois.readObject();
        }
    }
}