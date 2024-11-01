package fr.vmiad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DescReader {

    public static List<String> readDesc(String filePath) throws IOException {
        List<String> features = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                features.add(line.trim());
            }
        }
        return features;
    }

    public static List<File> listDescFiles(List<String> directories, String extension) {
        List<File> descFiles = new ArrayList<>();
        for (String directoryPath : directories) {
            File directory = new File(directoryPath);
            if (directory.isDirectory()) {
                File[] files = directory.listFiles((dir, name) -> name.endsWith(extension));
                if (files != null) {
                    Collections.addAll(descFiles, files);
                }
            } else {
                System.out.println("Directory not found: " + directoryPath);
            }
        }
        return descFiles;
    }

    public static void main(String[] args) {
        // Liste des dossiers avec des chemins relatifs
        List<String> directories = List.of(
                "../resources/Signatures/Zernike7",
                "../resources/Signatures/ART",
                "../resources/Signatures/Yang",
                "../resources/Signatures/GFD");

        // Extension recherchée dans chaque dossier
        String extension = ".zrk";
        int fileCount = 0;
        List<File> descFiles = listDescFiles(directories, extension);

        for (File file : descFiles) {
            try {
                List<String> features = readDesc(file.getPath()); // Utilisation de chemins relatifs
                System.out.println("Caractéristiques lues de : " + file.getName());
                fileCount++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Total files processed: " + fileCount);
    }
}
