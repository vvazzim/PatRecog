package fr.vmiad;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainKMeans {
    public static void main(String[] args) {
        // Configuration de base
        String directoryPath = "rf_project\\src\\main\\resources\\=Signatures\\=Zernike7";
        String extension = ".zrk";
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
                List<Double> features = DescReader.readDesc(file.getAbsolutePath());
                String label = ClassName.getLabelFromFile(file.getName());
                descriptors.add(new DescriptorWithLabel(file.getName(), features, label));
            }

            List<List<Double>> featuresList = descriptors.stream()
                    .map(d -> d.getFeatures().stream().collect(Collectors.toList()))
                    .collect(Collectors.toList());

            List<List<Double>> normalizedFeaturesList = new ArrayList<>();
            for (List<Double> features : featuresList) {
                List<Double> normalizedFeatures = DataPreprocessor.normalizeFeatures(features);
                normalizedFeaturesList.add(normalizedFeatures);
            }

            // Étape 3 : Méthode du coude
            List<Double> inerties = new ArrayList<>();
            int maxClusters = 10; // Tester jusqu'à 10 clusters

            for (int k = 1; k <= maxClusters; k++) {
                KMeans kmeans = new KMeans(k, maxIterations);
                Map<Integer, List<List<Double>>> clusters = kmeans.fit(normalizedFeaturesList);

                // Calculer l'inertie pour ce nombre de clusters
                double inertie = InertieCalculator.calculateInertie(clusters, kmeans.getCentroids());
                inerties.add(inertie);

                System.out.println("Nombre de clusters: " + k + " - Inertie: " + inertie);
            }

            // Tracer les inerties (à l'aide d'un outil graphique externe ou bibliothèque
            // Java)
            System.out.println("\nInerties:");
            IntStream.range(1, inerties.size() + 1)
                    .forEach(k -> System.out.println("k = " + k + ", Inertie = " + inerties.get(k - 1)));

        } catch (IOException e) {
            System.err.println("An error occurred while reading the files:");
            e.printStackTrace();
        }
    }
}
