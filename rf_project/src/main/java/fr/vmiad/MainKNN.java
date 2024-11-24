package fr.vmiad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale pour exécuter un algorithme KNN (K-Nearest Neighbors)
 * sur des données extraites de fichiers descripteurs.
 * 
 * Le programme effectue les étapes suivantes :
 * - Récupère les fichiers descripteurs d'un répertoire donné.
 * - Divise les fichiers en ensembles d'entraînement et de test.
 * - Lit les caractéristiques des fichiers pour construire des objets
 * avec leurs étiquettes (labels).
 * - Entraîne un modèle KNN avec les données d'entraînement.
 * - Fait des prédictions sur l'ensemble de test et affiche les résultats.
 */
public class MainKNN {

    /**
     * Méthode principale pour exécuter l'algorithme KNN.
     * 
     * @param args Arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        // Chemin du répertoire contenant les fichiers descripteurs
        String directory = "rf_project\\src\\main\\resources\\=Signatures\\=Zernike7";
        // Extension des fichiers à traiter
        String extension = ".zrk";

        // Liste pour stocker tous les fichiers descripteurs
        List<File> allFiles = new ArrayList<>();

        // Étape 1 : Récupérer tous les fichiers avec l'extension spécifiée
        List<File> descFiles = DescReader.listDescFiles(directory, extension);
        allFiles.addAll(descFiles);

        // Étape 2 : Diviser les fichiers en ensembles d'entraînement et de test
        Split splitter = new Split(12345L); // Graine pour garantir la reproductibilité
        splitter.splitDataByExtension(allFiles, 0.6, extension); // 60% pour l'entraînement

        // Obtenir les fichiers d'entraînement et de test
        List<String> trainingFiles = splitter.getTrainingSets().get(extension);
        List<String> testFiles = splitter.getTestSets().get(extension);

        // Étape 3 : Créer les objets DescriptorWithLabel pour les fichiers
        // d'entraînement
        List<DescriptorWithLabel> trainingData = new ArrayList<>();
        for (String fileName : trainingFiles) {
            try {
                // Chemin complet du fichier
                String filePath = directory + "\\" + fileName;
                // Lire les caractéristiques du fichier
                List<String> features = DescReader.readDesc(filePath);

                // Obtenir le label du fichier à partir de son nom ou de son répertoire
                String label = ClassName.getLabelFromFile(fileName); // Méthode à définir dans ClassName

                // Créer un objet DescriptorWithLabel et l'ajouter à la liste d'entraînement
                DescriptorWithLabel descriptor = new DescriptorWithLabel(fileName, features, label);
                trainingData.add(descriptor);
            } catch (IOException e) {
                // Gérer les erreurs de lecture
                e.printStackTrace();
            }
        }

        // Étape 4 : Entraîner le modèle KNN avec les données d'entraînement
        int k = 3; // Nombre de voisins à considérer
        KNN knn = new KNN(k, trainingData);

        // Étape 5 : Créer des objets pour les fichiers de test et effectuer des
        // prédictions
        int i = 0;
        for (String fileName : testFiles) {
            try {
                // Chemin complet du fichier de test
                String filePath = directory + "\\" + fileName;
                // Lire les caractéristiques du fichier
                List<String> features = DescReader.readDesc(filePath);

                // Obtenir le label réel du fichier à partir de son nom
                String actualLabel = ClassName.getLabelFromFile(fileName);

                // Prédire le label avec KNN
                String predictedLabel = knn.predict(features.stream().map(Double::parseDouble).toList());

                // Afficher les résultats
                System.out.println(i);
                System.out.println("Fichier : " + fileName);
                System.out.println("Label réel : " + actualLabel);
                System.out.println("Label prédit : " + predictedLabel);
                System.out.println();
                i++;

            } catch (IOException e) {
                // Gérer les erreurs de lecture
                e.printStackTrace();
            }
        }
    }
}
