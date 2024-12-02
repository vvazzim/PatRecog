package fr.vmiad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainKNN {

    public static void main(String[] args) {
        // Chemin du répertoire contenant les fichiers descripteurs
        String directory = "rf_project\\src\\main\\resources\\=Signatures\\=Zernike7";
        // Extension des fichiers à traiter
        String extension = ".zrk";

        // Étape 1 : Récupérer tous les fichiers avec l'extension spécifiée
        List<File> descFiles = DescReader.listDescFiles(directory, extension);

        // On applique KNN sur 10 classes
        // Donc on enleve les 96 derniers
        descFiles = descFiles.subList(0, Math.min(120, descFiles.size()));

        if (descFiles.isEmpty()) {
            System.out.println("No descriptor files found in the specified folder.");
            return;
        }

        // Étape 2 : Diviser les fichiers en ensembles d'entraînement et de test
        Split splitter = new Split(12345L); // Graine pour garantir la reproductibilité
        splitter.splitDataByStratification(descFiles, 0.6, extension); // 60% pour l'entraînement

        // Obtenir les fichiers d'entraînement et de test
        List<String> trainingFiles = splitter.getTrainingSets().get(extension);
        List<String> testFiles = splitter.getTestSets().get(extension);
        System.out.println(testFiles);

        // Étape 3 : Créer les objets DescriptorWithLabel pour les fichiers
        // d'entraînement
        List<DescriptorWithLabel> trainingData = new ArrayList<>();
        for (String fileName : trainingFiles) {
            try {
                // Chemin complet du fichier
                String filePath = directory + "\\" + fileName;
                // Lire les caractéristiques du fichier
                List<Double> features = DescReader.readDesc(filePath);
                List<Double> normalizedFeatures = DataPreprocessor.normalizeFeatures(features);

                // Obtenir le label du fichier à partir de son nom ou de son répertoire
                String label = ClassName.getLabelFromFile(fileName); // Méthode à définir dans ClassName

                // Créer un objet DescriptorWithLabel et l'ajouter à la liste d'entraînement
                DescriptorWithLabel descriptor = new DescriptorWithLabel(fileName, normalizedFeatures, label);

                trainingData.add(descriptor);
            } catch (IOException e) {
                // Gérer les erreurs de lecture
                e.printStackTrace();
            }
        }

        // Étape 4 : Entraîner le modèle KNN avec les données d'entraînement
        int k = 3; // Nombre de voisins à considérer
        KNN knn = new KNN(k, trainingData);

        // Stocker les labels réels et prédits pour calculer les métriques plus tard
        List<String> actualLabels = new ArrayList<>();
        List<String> predictedLabels = new ArrayList<>();

        // Étape 5 : Créer des objets pour les fichiers de test et effectuer des
        // prédictions
        for (String fileName : testFiles) {
            try {
                // Chemin complet du fichier de test
                String filePath = directory + "\\" + fileName;
                // Lire les caractéristiques du fichier
                List<Double> features = DescReader.readDesc(filePath);
                List<Double> normalizedFeatures = DataPreprocessor.normalizeFeatures(features);

                // Obtenir le label réel du fichier à partir de son nom
                String actualLabel = ClassName.getLabelFromFile(fileName);

                // Prédire le label avec KNN
                String predictedLabel = knn.predict(normalizedFeatures);

                // Ajouter les résultats à nos listes
                actualLabels.add(actualLabel);
                predictedLabels.add(predictedLabel);

                // Afficher les résultats
                // System.out.println("File: " + fileName);
                // System.out.println("Actual label: " + actualLabel);
                // System.out.println("Predicted label: " + predictedLabel);
                // System.out.println();

            } catch (IOException e) {
                // Gérer les erreurs de lecture
                e.printStackTrace();
            }
        }

        // // Après avoir effectué toutes les prédictions, calculons les métriques
        PerformanceEvaluator.evaluatePerformance(actualLabels, predictedLabels);
        PerformanceEvaluator.evaluateAccuracy(actualLabels, predictedLabels);

        // System.out.println("Nombre total de labels réels : " + actualLabels.size());
        // System.out.println("Nombre total de labels prédits : " +
        // predictedLabels.size());

    }
}
