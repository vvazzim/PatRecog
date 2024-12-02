package fr.vmiad;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe pour effectuer des prétraitements de données, comme la normalisation
 * des
 * caractéristiques extraites des fichiers descripteurs.
 */
public class DataPreprocessor {

    /**
     * Normalise une liste de caractéristiques (features) en utilisant la formule :
     * (valeur - min) / (max - min).
     * Cette méthode permet de transformer les valeurs pour qu'elles soient
     * comprises
     * entre 0 et 1.
     *
     * @param features Une liste de caractéristiques sous forme de nombres réels.
     * @return Une liste de caractéristiques normalisées entre 0 et 1.
     */
    public static List<Double> normalizeFeatures(List<Double> features) {
        // Trouver la valeur minimale et maximale
        double min = features.stream().mapToDouble(v -> v).min().orElse(0);
        double max = features.stream().mapToDouble(v -> v).max().orElse(1);

        // Normaliser chaque valeur
        return features.stream().map(v -> (v - min) / (max - min)).collect(Collectors.toList());
    }
}