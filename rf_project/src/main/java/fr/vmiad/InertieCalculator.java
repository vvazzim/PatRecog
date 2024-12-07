package fr.vmiad;

import java.util.List;
import java.util.Map;

public class InertieCalculator {

    /**
     * Calcule l'inertie totale pour les clusters formés par KMeans.
     *
     * @param clusters  Une map contenant les clusters (points associés à chaque
     *                  centroïde).
     * @param centroids La liste des centroïdes.
     * @return L'inertie totale.
     */
    public static double calculateInertie(Map<Integer, List<List<Double>>> clusters, List<List<Double>> centroids) {
        double totalInertie = 0.0;

        // Parcourir chaque cluster
        for (Map.Entry<Integer, List<List<Double>>> entry : clusters.entrySet()) {
            int clusterIndex = entry.getKey();
            List<List<Double>> points = entry.getValue();
            List<Double> centroid = centroids.get(clusterIndex);

            // Calculer la somme des distances au carré entre les points et le centroïde
            for (List<Double> point : points) {
                totalInertie += squaredEuclideanDistance(point, centroid);
            }
        }

        return totalInertie;
    }

    /**
     * Calcule la distance euclidienne au carré entre deux points.
     *
     * @param point1 Premier vecteur.
     * @param point2 Second vecteur.
     * @return La distance euclidienne au carré.
     */
    private static double squaredEuclideanDistance(List<Double> point1, List<Double> point2) {
        double sum = 0.0;
        for (int i = 0; i < point1.size(); i++) {
            double diff = point1.get(i) - point2.get(i);
            sum += diff * diff;
        }
        return sum;
    }
}
