package fr.vmiad;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainKMeans {
    public static void main(String[] args) {
        // Configuration de base
        String directoryPath = "rf_project\\src\\main\\resources\\=Signatures\\=Zernike7";
        String extension = ".zrk";
        int numClusters = 3; // Nombre de clusters pour KMeans
        int maxIterations = 100; // Nombre maximum d'itérations pour KMeans

        try {
            // Étape 1 : Lister les fichiers descripteurs
            List<File> descFiles = DescReader.listDescFiles(directoryPath, extension);

            // On applique KNN sur 10 classes
            // Donc on enleve les 96 derniers fichiers
            descFiles = descFiles.subList(0, Math.min(120, descFiles.size()));

            if (descFiles.isEmpty()) {
                System.out.println("No descriptor files found in the specified folder.");
                return;
            }

            // Étape 2 : Associer chaque fichier à ses caractéristiques et son label
            List<DescriptorWithLabel> descriptors = new ArrayList<>();
            for (File file : descFiles) {
                // Lire les caractéristiques du fichier
                List<Double> features = DescReader.readDesc(file.getAbsolutePath());
                // Obtenir le label du fichier
                String label = ClassName.getLabelFromFile(file.getName());
                // Créer un objet DescriptorWithLabel
                descriptors.add(new DescriptorWithLabel(file.getName(), features, label));
            }

            // Afficher les descripteurs lus
            System.out.println("Descriptors and labels extracted:");
            descriptors.forEach(System.out::println);

            // Étape 3 : Extraire uniquement les caractéristiques pour KMeans
            List<List<Double>> featuresList = descriptors.stream()
                    .map(d -> d.getFeatures().stream()
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());

            // Normaliser les caractéristiques avant de les envoyer à KMeans
            List<List<Double>> normalizedFeaturesList = new ArrayList<>();
            for (List<Double> features : featuresList) {
                List<Double> normalizedFeatures = DataPreprocessor.normalizeFeatures(features);
                normalizedFeaturesList.add(normalizedFeatures);
            }

            // Étape 4 : Appliquer l'algorithme KMeans avec les données normalisées
            KMeans kmeans = new KMeans(numClusters, maxIterations);
            Map<Integer, List<List<Double>>> clusters = kmeans.fit(normalizedFeaturesList);

            // Afficher les résultats de KMeans
            System.out.println("\nCentroids calculated by KMeans:");
            kmeans.getCentroids().forEach(System.out::println);

            System.out.println("\nClusters:");
            for (Map.Entry<Integer, List<List<Double>>> entry : clusters.entrySet()) {
                System.out.println("Cluster " + entry.getKey() + ":");
                entry.getValue().forEach(System.out::println);
            }

        } catch (IOException e) {
            System.err.println("An error occurred while reading the files:");
            e.printStackTrace();
        }
    }
}
