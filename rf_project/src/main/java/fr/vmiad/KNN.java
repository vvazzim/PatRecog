package fr.vmiad;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe implémentant l'algorithme K-Nearest Neighbors (KNN).
 */
public class KNN {

    private final int k; // Nombre de voisins à considérer
    private final List<DescriptorWithLabel> trainingData; // Données d'entraînement

    /**
     * Constructeur de la classe KNN.
     *
     * @param k            Le nombre de voisins à considérer.
     * @param trainingData Les données d'entraînement (features + labels).
     */
    public KNN(int k, List<DescriptorWithLabel> trainingData) {
        this.k = k;
        this.trainingData = trainingData;
    }

    /**
     * Calcule la distance euclidienne entre deux vecteurs de caractéristiques.
     *
     * @param features1 Les caractéristiques du premier point.
     * @param features2 Les caractéristiques du second point.
     * @return La distance euclidienne entre les deux points.
     */
    private double euclideanDistance(List<Double> features1, List<Double> features2) {
        double sum = 0.0;
        for (int i = 0; i < features1.size(); i++) {
            double diff = features1.get(i) - features2.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    /**
     * Prédit le label pour un nouveau point donné ses caractéristiques.
     *
     * @param features Les caractéristiques du point à prédire.
     * @return Le label prédit.
     */
    public String predict(List<Double> features) {
        // Calculer les distances entre le point cible et chaque point d'entraînement
        List<Neighbor> neighbors = trainingData.stream()
                .map(data -> new Neighbor(data.getLabel(), euclideanDistance(features, data.getFeatures())))
                .sorted(Comparator.comparingDouble(Neighbor::getDistance)) // Trier par distance
                .limit(k) // Garder les K plus proches voisins
                .collect(Collectors.toList());

        // Trouver le label majoritaire parmi les K voisins
        return neighbors.stream()
                .collect(Collectors.groupingBy(Neighbor::getLabel, Collectors.counting())) // Compter les labels
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue)) // Trouver le label majoritaire
                .get()
                .getKey();
    }

    // Methode pour la courbe Précision/Rappel
    /**
     * Prédit les scores pour chaque classe pour un nouveau point donné ses
     * caractéristiques.
     *
     * @param features Les caractéristiques du point à prédire.
     * @return Un map des scores pour chaque classe.
     */
    public Map<String, Double> predictWithScores(List<Double> features) {
        // Calculer les distances entre le point cible et chaque point d'entraînement
        List<Neighbor> neighbors = trainingData.stream()
                .map(data -> new Neighbor(data.getLabel(), euclideanDistance(features, data.getFeatures())))
                .sorted(Comparator.comparingDouble(Neighbor::getDistance)) // Trier par distance
                .limit(k) // Garder les K plus proches voisins
                .collect(Collectors.toList());

        // Compter le nombre d'occurrences de chaque label parmi les K voisins
        Map<String, Integer> labelCounts = new HashMap<>();
        for (Neighbor neighbor : neighbors) {
            labelCounts.put(neighbor.getLabel(), labelCounts.getOrDefault(neighbor.getLabel(), 0) + 1);
        }

        // Calculer le score de chaque label (proportion de voisins pour chaque label)
        Map<String, Double> classScores = new HashMap<>();
        for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
            classScores.put(entry.getKey(), entry.getValue() / (double) k);
        }

        return classScores;
    }

    /**
     * Classe interne pour représenter un voisin avec son label et sa distance.
     */
    private static class Neighbor {
        private final String label;
        private final double distance;

        public Neighbor(String label, double distance) {
            this.label = label;
            this.distance = distance;
        }

        public String getLabel() {
            return label;
        }

        public double getDistance() {
            return distance;
        }
    }
}
