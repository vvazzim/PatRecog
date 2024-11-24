package fr.vmiad;

import java.util.*;

public class KMeans {

    private final int numClusters; // Nombre de clusters
    private final int maxIterations; // Nombre maximum d'itérations
    private List<List<Double>> centroids; // Liste des centroïdes

    /**
     * Constructeur de KMeans.
     *
     * @param numClusters   Nombre de clusters.
     * @param maxIterations Nombre maximum d'itérations.
     */
    public KMeans(int numClusters, int maxIterations) {
        this.numClusters = numClusters;
        this.maxIterations = maxIterations;
        this.centroids = new ArrayList<>();
    }

    /**
     * Initialise les centroïdes aléatoirement.
     *
     * @param data Les données d'entrée.
     */
    private void initializeCentroids(List<List<Double>> data) {
        Collections.shuffle(data);
        centroids = new ArrayList<>(data.subList(0, numClusters));
    }

    /**
     * Calcule la distance euclidienne entre deux vecteurs.
     *
     * @param point1 Premier vecteur.
     * @param point2 Second vecteur.
     * @return Distance euclidienne.
     */
    private double euclideanDistance(List<Double> point1, List<Double> point2) {
        double sum = 0.0;
        for (int i = 0; i < point1.size(); i++) {
            double diff = point1.get(i) - point2.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    /**
     * Affecte chaque point de données à son centroïde le plus proche.
     *
     * @param data Les données d'entrée.
     * @return Une map contenant les clusters (points associés à chaque centroïde).
     */
    private Map<Integer, List<List<Double>>> assignPointsToClusters(List<List<Double>> data) {
        Map<Integer, List<List<Double>>> clusters = new HashMap<>();
        for (int i = 0; i < numClusters; i++) {
            clusters.put(i, new ArrayList<>());
        }

        for (List<Double> point : data) {
            int closestCentroid = 0;
            double minDistance = euclideanDistance(point, centroids.get(0));
            for (int i = 1; i < centroids.size(); i++) {
                double distance = euclideanDistance(point, centroids.get(i));
                if (distance < minDistance) {
                    closestCentroid = i;
                    minDistance = distance;
                }
            }
            clusters.get(closestCentroid).add(point);
        }
        return clusters;
    }

    /**
     * Met à jour les centroïdes en fonction des moyennes des points assignés.
     *
     * @param clusters Les clusters.
     */
    private void updateCentroids(Map<Integer, List<List<Double>>> clusters) {
        for (int i = 0; i < numClusters; i++) {
            List<List<Double>> points = clusters.get(i);
            if (points.isEmpty())
                continue;

            List<Double> newCentroid = new ArrayList<>(Collections.nCopies(points.get(0).size(), 0.0));
            for (List<Double> point : points) {
                for (int j = 0; j < point.size(); j++) {
                    newCentroid.set(j, newCentroid.get(j) + point.get(j));
                }
            }
            for (int j = 0; j < newCentroid.size(); j++) {
                newCentroid.set(j, newCentroid.get(j) / points.size());
            }
            centroids.set(i, newCentroid);
        }
    }

    /**
     * Exécute l'algorithme K-means sur les données fournies.
     *
     * @param data Les données d'entrée.
     * @return Une map contenant les clusters (points associés à chaque centroïde).
     */
    public Map<Integer, List<List<Double>>> fit(List<List<Double>> data) {
        initializeCentroids(data);

        for (int iter = 0; iter < maxIterations; iter++) {
            Map<Integer, List<List<Double>>> clusters = assignPointsToClusters(data);
            List<List<Double>> oldCentroids = new ArrayList<>(centroids);
            updateCentroids(clusters);

            if (oldCentroids.equals(centroids))
                break; // Convergence
        }

        return assignPointsToClusters(data);
    }

    public List<List<Double>> getCentroids() {
        return centroids;
    }
}
