package fr.vmiad;

import java.util.List;
import java.util.Map;

public class InertieCalculator {

    /**
     * Calcule l'inertie totale pour les clusters formés par KMeans.
     * L'inertie mesure la qualité d'un clustering en quantifiant la dispersion
     * des points autour de leur centroïde respectif.
     *
     * @param clusters  Une map où chaque clé est un indice de cluster,
     *                  et la valeur est une liste des points appartenant à ce
     *                  cluster.
     * @param centroids La liste des centroïdes (un centroïde par cluster).
     * @return L'inertie totale : la somme des distances au carré entre les points
     *         et leurs centroïdes respectifs.
     */
    public static double calculateInertie(Map<Integer, List<List<Double>>> clusters, List<List<Double>> centroids) {
        double totalInertie = 0.0; // Initialisation de l'inertie totale

        // Parcourir chaque cluster
        for (Map.Entry<Integer, List<List<Double>>> entry : clusters.entrySet()) {
            int clusterIndex = entry.getKey(); // Indice du cluster
            List<List<Double>> points = entry.getValue(); // Points associés au cluster
            List<Double> centroid = centroids.get(clusterIndex); // Centroïde du cluster

            // Calculer la somme des distances au carré entre les points et le centroïde
            for (List<Double> point : points) {
                totalInertie += squaredEuclideanDistance(point, centroid);
            }
        }

        return totalInertie; // Retourner l'inertie totale
    }

    /**
     * Calcule la distance euclidienne au carré entre deux vecteurs.
     * Cette méthode est utilisée pour éviter de calculer la racine carrée,
     * ce qui est suffisant pour les comparaisons et la mesure d'inertie.
     *
     * @param point1 Premier vecteur (coordonnées d'un point).
     * @param point2 Second vecteur (coordonnées du centroïde).
     * @return La distance euclidienne au carré entre les deux points.
     */
    private static double squaredEuclideanDistance(List<Double> point1, List<Double> point2) {
        double sum = 0.0; // Initialisation de la somme des carrés des différences
        for (int i = 0; i < point1.size(); i++) {
            double diff = point1.get(i) - point2.get(i); // Différence pour une dimension
            sum += diff * diff; // Ajouter le carré de la différence
        }
        return sum; // Retourner la distance au carré
    }
}
