package fr.vmiad;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Classe principale pour exécuter le clustering K-Means sur des descripteurs.
 * 
 * <p>
 * Ce programme lit des fichiers descripteurs, normalise leurs caractéristiques,
 * applique la méthode du coude pour déterminer le nombre optimal de clusters,
 * puis exécute l'algorithme K-Means.
 * </p>
 */
public class MainKMeans {
    public static void main(String[] args) {
        // Chemin du répertoire contenant les fichiers descripteurs
        String directoryPath = "rf_project\\src\\main\\resources\\=Signatures\\=Zernike7";
        // Extension des fichiers descripteurs à traiter
        String extension = ".zrk";
        int maxIterations = 100; // Nombre maximum d'itérations pour l'algorithme K-Means

        try {
            // Étape 1 : Lister les fichiers de descripteurs avec une extension spécifique
            List<File> descFiles = DescReader.listDescFiles(directoryPath, extension);

            // Limiter les fichiers utilisés pour n'inclure que les 120 premiers (par
            // exemple)
            descFiles = descFiles.subList(0, Math.min(120, descFiles.size()));

            if (descFiles.isEmpty()) {
                System.out.println("Aucun fichier descripteur trouvé dans le répertoire spécifié.");
                return;
            }

            // Étape 2 : Lire les descripteurs et associer chaque fichier à ses
            // caractéristiques et son label
            List<DescriptorWithLabel> descriptors = new ArrayList<>();
            for (File file : descFiles) {
                List<Double> features = DescReader.readDesc(file.getAbsolutePath()); // Lire les caractéristiques
                String label = ClassName.getLabelFromFile(file.getName()); // Obtenir le label à partir du nom de
                                                                           // fichier
                descriptors.add(new DescriptorWithLabel(file.getName(), features, label));
            }

            // Extraire uniquement les caractéristiques pour K-Means
            List<List<Double>> featuresList = descriptors.stream()
                    .map(d -> d.getFeatures().stream().collect(Collectors.toList()))
                    .collect(Collectors.toList());

            // Normalisation des caractéristiques
            List<List<Double>> normalizedFeaturesList = new ArrayList<>();
            for (List<Double> features : featuresList) {
                List<Double> normalizedFeatures = DataPreprocessor.normalizeFeatures(features);
                normalizedFeaturesList.add(normalizedFeatures);
            }

            // Étape 3 : Appliquer la méthode du coude pour choisir le nombre optimal de
            // clusters
            List<Double> inerties = new ArrayList<>();
            int maxClusters = 10; // Tester de 1 à 10 clusters

            for (int k = 1; k <= maxClusters; k++) {
                // Initialiser et ajuster K-Means pour chaque valeur de k
                KMeans kmeans = new KMeans(k, maxIterations);
                Map<Integer, List<List<Double>>> clusters = kmeans.fit(normalizedFeaturesList);

                // Calculer l'inertie pour évaluer la qualité du clustering
                double inertie = InertieCalculator.calculateInertie(clusters, kmeans.getCentroids());
                inerties.add(inertie);

                System.out.println("Nombre de clusters: " + k + " - Inertie: " + inertie);
            }

            // Afficher les résultats de la méthode du coude
            System.out.println("\nInerties:");
            IntStream.range(1, inerties.size() + 1)
                    .forEach(k -> System.out.println("k = " + k + ", Inertie = " + inerties.get(k - 1)));

            // Remarque : Pour visualiser la courbe des inerties, une bibliothèque graphique
            // serait nécessaire

        } catch (IOException e) {
            System.err.println("Une erreur est survenue lors de la lecture des fichiers :");
            e.printStackTrace();
        }
    }
}
