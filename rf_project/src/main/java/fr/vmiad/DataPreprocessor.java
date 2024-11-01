package fr.vmiad;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DataPreprocessor {
    public static List<Double> normalizeFeatures(List<Double> features) {
        double min = features.stream().mapToDouble(v -> v).min().orElse(0);
        double max = features.stream().mapToDouble(v -> v).max().orElse(1);
        return features.stream().map(v -> (v - min) / (max - min)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        try {
            List<String> features = DescReader.readDesc("src/main/resources/images/votre_fichier.desc");
            // Convertir les caractéristiques en doubles si nécessaire
            List<Double> doubleFeatures = features.stream().map(Double::parseDouble).collect(Collectors.toList());
            List<Double> normalizedFeatures = normalizeFeatures(doubleFeatures);
            // Utiliser les caractéristiques normalisées
            System.out.println("Caractéristiques normalisées : " + normalizedFeatures);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
