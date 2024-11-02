package fr.vmiad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataPreprocessor {

    // Méthode pour normaliser une liste de caractéristiques
    public static List<Double> normalizeFeatures(List<Double> features) {
        double min = features.stream().mapToDouble(v -> v).min().orElse(0);
        double max = features.stream().mapToDouble(v -> v).max().orElse(1);
        return features.stream().map(v -> (v - min) / (max - min)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        String directoryPath = "src/main/resources/=Signatures/=Zernike7"; // Remplace par le chemin du dossier

        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("Le chemin spécifié n'est pas un dossier valide.");
            return;
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".zrk"));
        if (files == null || files.length == 0) {
            System.out.println("Aucun fichier correspondant trouvé dans le dossier.");
            return;
        }

        // Parcourir chaque fichier et normaliser les caractéristiques
        for (File file : files) {
            try {
                List<String> features = DescReader.readDesc(file.getAbsolutePath());

                // Convertir les caractéristiques en double
                List<Double> doubleFeatures = features.stream().map(Double::parseDouble).collect(Collectors.toList());

                // Normaliser les caractéristiques
                List<Double> normalizedFeatures = normalizeFeatures(doubleFeatures);

                // Afficher le nom du fichier et les caractéristiques normalisées
                System.out.println("Caractéristiques normalisées pour " + file.getName() + " : " + normalizedFeatures);

            } catch (IOException e) {
                System.err.println("Erreur de lecture du fichier : " + file.getName());
                e.printStackTrace();
            }
        }
    }
}
