package fr.vmiad;

import java.util.*;

/**
 * Classe pour calculer la courbe précision-rappel (Precision-Recall
 * Curve).
 * 
 * <p>
 * La courbe précision-rappel est souvent utilisée pour évaluer les performances
 * des modèles de classification, particulièrement dans des contextes où les
 * classes sont déséquilibrées.
 * </p>
 */
public class PRCurve {

    /**
     * Calcule les points (rappel, précision) nécessaires pour tracer la courbe
     * précision-rappel.
     * 
     * <p>
     * Cette méthode trie les scores associés aux labels réels par ordre
     * décroissant,
     * et calcule les métriques de précision et de rappel à chaque seuil possible.
     * </p>
     * 
     * @param actualLabels Liste des labels réels, un par instance.
     * @param scores       Liste des scores de classification pour la classe cible,
     *                     correspondant aux labels.
     * @param targetClass  La classe pour laquelle on souhaite tracer la courbe.
     * @return Une liste de points (rappel, précision) représentés par des tableaux
     *         double[2].
     */
    public static List<double[]> calculatePrecisionRecallCurve(List<String> actualLabels, List<Double> scores,
            String targetClass) {
        // Étape 1 : Associer chaque score à son label réel
        List<ScoreWithLabel> scoreWithLabels = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            scoreWithLabels.add(new ScoreWithLabel(scores.get(i), actualLabels.get(i)));
        }

        // Étape 2 : Trier les scores par ordre décroissant
        scoreWithLabels.sort((a, b) -> Double.compare(b.score, a.score));

        // Initialisation des compteurs pour les calculs
        int tp = 0; // Vrais positifs
        int fp = 0; // Faux positifs
        int fn = 0; // Faux négatifs

        // Étape 3 : Calculer le nombre initial de FN (toutes les instances positives)
        for (String label : actualLabels) {
            if (label.equals(targetClass)) {
                fn++;
            }
        }

        // Liste pour stocker les points (rappel, précision) de la courbe
        List<double[]> precisionRecallPoints = new ArrayList<>();

        // Étape 4 : Parcourir les scores triés et mettre à jour les compteurs
        for (ScoreWithLabel swl : scoreWithLabels) {
            if (swl.label.equals(targetClass)) {
                tp++; // L'instance est correctement prédite comme positive
                fn--; // Une instance positive est retirée des FN
            } else {
                fp++; // L'instance est mal prédite comme positive
            }

            // Calcul des métriques précision et rappel
            double precision = (tp + fp > 0) ? (double) tp / (tp + fp) : 1.0; // Gérer division par zéro
            double recall = (tp + fn > 0) ? (double) tp / (tp + fn) : 0.0;

            // Ajouter le point (rappel, précision) à la courbe
            precisionRecallPoints.add(new double[] { recall, precision });
        }

        return precisionRecallPoints;
    }

    /**
     * Classe interne pour lier un score et son label associé.
     * <p>
     * Utile pour trier les scores tout en conservant l'information de classe.
     * </p>
     */
    private static class ScoreWithLabel {
        double score; // Score de classification pour une instance
        String label; // Label réel correspondant à l'instance

        /**
         * Constructeur de la classe ScoreWithLabel.
         * 
         * @param score Le score de classification.
         * @param label Le label réel associé.
         */
        ScoreWithLabel(double score, String label) {
            this.score = score;
            this.label = label;
        }
    }
}
