package fr.vmiad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Split {

    private final List<List<Double>> trainingSet = new ArrayList<>();
    private final List<List<Double>> validationSet = new ArrayList<>();
    private final List<List<Double>> testSet = new ArrayList<>();

    // Méthode pour diviser les fichiers de descripteur en ensembles d’entraînement,
    // validation et test
    public void splitData(List<File> descFiles, double trainRatio, double validationRatio) throws IOException {
        // Mélanger les fichiers pour une distribution aléatoire
        Collections.shuffle(descFiles);

        // Calculer les tailles des ensembles
        int totalFiles = descFiles.size();
        int trainSize = (int) (totalFiles * trainRatio);
        int validationSize = (int) (totalFiles * validationRatio);

        for (int i = 0; i < totalFiles; i++) {
            System.out.println("Reading file: " + descFiles.get(i).getAbsolutePath());
            List<String> features = DescReader.readDesc(descFiles.get(i).getAbsolutePath());
            System.out.println("Features read: " + features);
            List<Double> doubleFeatures = convertFeatures(features);
            List<Double> normalizedFeatures = DataPreprocessor.normalizeFeatures(doubleFeatures);

            // Ajouter les caractéristiques normalisées à l'ensemble approprié
            if (i < trainSize) {
                trainingSet.add(normalizedFeatures); // Ajout au training set
            } else if (i < trainSize + validationSize) {
                validationSet.add(normalizedFeatures); // Ajout au validation set
            } else {
                testSet.add(normalizedFeatures); // Ajout au test set
            }
        }
        System.out.println(
                "Total files: " + totalFiles + ", Train size: " + trainSize + ", Validation size: " + validationSize);
        System.out.println("Training set size after split: " + trainingSet.size());
        System.out.println("Validation set size after split: " + validationSet.size());
        System.out.println("Test set size after split: " + testSet.size());

    }

    private List<Double> convertFeatures(List<String> features) {
        List<Double> doubleFeatures = new ArrayList<>();
        for (String feature : features) {
            doubleFeatures.add(Double.parseDouble(feature));
        }
        return doubleFeatures;
    }

    // Accesseurs pour obtenir les ensembles
    public List<List<Double>> getTrainingSet() {
        return trainingSet;
    }

    public List<List<Double>> getValidationSet() {
        return validationSet;
    }

    public List<List<Double>> getTestSet() {
        return testSet;
    }

    public static void main(String[] args) {
        // Exemples de chemins de répertoires pour chaque descripteur
        List<String> directories = List.of(
                "C:\\Users\\HOUdo\\OneDrive\\Documents\\Ecole\\Université\\M1\\RF\\RF_projet\\=SharvitB2\\=Signatures\\=Zernike7",
                "C:\\Users\\HOUdo\\OneDrive\\Documents\\Ecole\\Université\\M1\\RF\\RF_projet\\=SharvitB2\\=Signatures\\=ART",
                "C:\\Users\\HOUdo\\OneDrive\\Documents\\Ecole\\Université\\M1\\RF\\RF_projet\\=SharvitB2\\=Signatures\\=Yang",
                "C:\\Users\\HOUdo\\OneDrive\\Documents\\Ecole\\Université\\M1\\RF\\RF_projet\\=SharvitB2\\=Signatures\\=GFD");

        String extension = ".zrk"; // Remplacez par l'extension correcte pour chaque descripteur
        double trainRatio = 0.6;
        double validationRatio = 0.2;

        Split splitter = new Split();

        try {
            for (String directory : directories) {
                List<File> descFiles = DescReader.listDescFiles(List.of(directory), extension);
                System.out.println("Files in directory " + directory + ": " + descFiles.size());
                splitter.splitData(descFiles, trainRatio, validationRatio);
            }

            // Afficher les tailles des ensembles
            System.out.println("Training Set Size: " + splitter.getTrainingSet().size());
            System.out.println("Validation Set Size: " + splitter.getValidationSet().size());
            System.out.println("Test Set Size: " + splitter.getTestSet().size());

            // Afficher un échantillon des données
            System.out.println("Sample Training Data: "
                    + (splitter.getTrainingSet().isEmpty() ? "No data" : splitter.getTrainingSet().get(0)));
            System.out.println("Sample Validation Data: "
                    + (splitter.getValidationSet().isEmpty() ? "No data" : splitter.getValidationSet().get(0)));
            System.out.println("Sample Test Data: "
                    + (splitter.getTestSet().isEmpty() ? "No data" : splitter.getTestSet().get(0)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
