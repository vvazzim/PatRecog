package fr.vmiad;

import java.util.HashMap;
import java.util.List;

public class PerformanceEvaluator {

    /**
     * Cette méthode calcule et affiche les métriques de performance.
     * 
     * @param actualLabels    Liste des labels réels
     * @param predictedLabels Liste des labels prédits
     */
    public static void evaluatePerformance(List<String> actualLabels, List<String> predictedLabels) {
        // Créer une map pour stocker les résultats de la matrice de confusion
        HashMap<String, Integer> truePositive = new HashMap<>();
        HashMap<String, Integer> falsePositive = new HashMap<>();
        HashMap<String, Integer> trueNegative = new HashMap<>();
        HashMap<String, Integer> falseNegative = new HashMap<>();

        // Initialiser les compteurs de la matrice de confusion
        for (String label : actualLabels) {
            truePositive.put(label, 0);
            falsePositive.put(label, 0);
            trueNegative.put(label, 0);
            falseNegative.put(label, 0);
        }

        // Calcul de la matrice de confusion
        for (int i = 0; i < actualLabels.size(); i++) {
            String actual = actualLabels.get(i);
            String predicted = predictedLabels.get(i);

            if (actual.equals(predicted)) {
                truePositive.put(actual, truePositive.get(actual) + 1); // TP
            } else {
                falsePositive.put(predicted, falsePositive.get(predicted) + 1); // FP
                falseNegative.put(actual, falseNegative.get(actual) + 1); // FN
            }
        }

        // Affichage de la matrice de confusion
        System.out.println("Matrice de confusion :");
        for (String label : truePositive.keySet()) {
            int tp = truePositive.get(label);
            int fp = falsePositive.get(label);
            int fn = falseNegative.get(label);
            int tn = trueNegative.get(label);

            System.out.println("Classe: " + label);
            System.out.println("  True Positive (TP): " + tp);
            System.out.println("  False Positive (FP): " + fp);
            System.out.println("  False Negative (FN): " + fn);
            System.out.println("  True Negative (TN): " + tn);
        }

        // Calcul des métriques : Précision, Rappel, F1-score
        double precision = 0.0;
        double recall = 0.0;
        double f1Score = 0.0;

        for (String label : actualLabels) {
            int tp = truePositive.get(label);
            int fp = falsePositive.get(label);
            int fn = falseNegative.get(label);

            // Précision = TP / (TP + FP)
            double classPrecision = tp / (double) (tp + fp);
            precision += classPrecision;

            // Rappel = TP / (TP + FN)
            double classRecall = tp / (double) (tp + fn);
            recall += classRecall;

            // F1-score = 2 * (précision * rappel) / (précision + rappel)
            double classF1Score = 2 * (classPrecision * classRecall) / (classPrecision + classRecall);
            f1Score += classF1Score;
        }

        // Moyenne des métriques
        int numClasses = actualLabels.size();
        precision /= numClasses;
        recall /= numClasses;
        f1Score /= numClasses;

        // Afficher les résultats
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("F1 Score: " + f1Score);
    }

    /**
     * Cette méthode calcule et affiche le taux de réussite (accuracy) du modèle.
     * 
     * @param actualLabels    Liste des labels réels
     * @param predictedLabels Liste des labels prédits
     */
    public static void evaluateAccuracy(List<String> actualLabels, List<String> predictedLabels) {
        // Compter le nombre de prédictions correctes
        int correctPredictions = 0;
        int totalPredictions = actualLabels.size();

        // Comparer les labels réels avec les labels prédits
        for (int i = 0; i < totalPredictions; i++) {
            if (actualLabels.get(i).equals(predictedLabels.get(i))) {
                correctPredictions++;
            }
        }

        // Calculer le taux de réussite (accuracy)
        double accuracy = (double) correctPredictions / totalPredictions;

        // Afficher les résultats
        System.out.println("Accuracy: " + accuracy);
    }
}
