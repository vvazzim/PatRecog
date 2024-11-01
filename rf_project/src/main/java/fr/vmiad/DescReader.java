package fr.vmiad;

import java.io.File;
import java.io.BufferedReader;
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

    public static void renameFiles(List<String> directories, String oldExtension, String newExtension) {
        for (String directoryPath : directories) {
            File directory = new File(directoryPath);
            if (directory.isDirectory()) {
                File[] files = directory.listFiles((dir, name) -> name.endsWith(oldExtension));
                if (files != null) {
                    for (File file : files) {
                        File renamedFile = new File(file.getAbsolutePath().replace(oldExtension, newExtension));
                        if (file.renameTo(renamedFile)) {
                            System.out.println("Renamed: " + file.getName() + " to " + renamedFile.getName());
                        } else {
                            System.out.println("Failed to rename: " + file.getName());
                        }
                    }
                }
            } else {
                System.out.println("Directory not found: " + directoryPath);
            }
        }
    }

    public static void main(String[] args) {
        // Liste des dossiers, chaque dossier correspondant à un type de descripteur
        List<String> directories = List.of(
                "C:\\Users\\HOUdo\\OneDrive\\Documents\\Ecole\\Université\\M1\\RF\\RF_projet\\=SharvitB2\\=Signatures\\=Zernike7",
                "C:\\Users\\HOUdo\\OneDrive\\Documents\\Ecole\\Université\\M1\\RF\\RF_projet\\=SharvitB2\\=Signatures\\=ART",
                "C:\\Users\\HOUdo\\OneDrive\\Documents\\Ecole\\Université\\M1\\RF\\RF_projet\\=SharvitB2\\=Signatures\\=Yang",
                "C:\\Users\\HOUdo\\OneDrive\\Documents\\Ecole\\Université\\M1\\RF\\RF_projet\\=SharvitB2\\=Signatures\\=GFD");

        // Renommer les fichiers
        renameFiles(directories, ".zrk.txt", ".zrk");

        // Test pour vérifier que les fichiers sont bien renommés
        List<File> descFiles = listDescFiles(directories, ".zrk");
        for (File file : descFiles) {
            try {
                List<String> features = readDesc(file.getAbsolutePath());
                System.out.println("Caractéristiques lues de : " + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
