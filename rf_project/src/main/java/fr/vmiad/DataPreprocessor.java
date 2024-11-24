package fr.vmiad;

import java.io.File;
import java.io.IOException;
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

    /**
     * Méthode principale pour normaliser les caractéristiques des fichiers de
     * descripteurs
     * dans un répertoire donné.
     * Lit les fichiers, extrait leurs caractéristiques, applique la normalisation,
     * et affiche les résultats.
     *
     * @param args Arguments passés depuis la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        // Chemin du répertoire contenant les fichiers descripteurs
        String directoryPath = "rf_project\\src\\main\\resources\\=Signatures\\=Zernike7";

        // Vérifier si le chemin spécifié est un dossier valide
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            System.out.println("Le chemin spécifié n'est pas un dossier valide.");
            return;
        }

        // Récupérer les fichiers avec l'extension .zrk
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".zrk"));
        if (files == null || files.length == 0) {
            System.out.println("Aucun fichier correspondant trouvé dans le dossier.");
            return;
        }

        // Parcourir chaque fichier et appliquer la normalisation
        for (File file : files) {
            try {
                // Lire les caractéristiques du fichier
                List<String> features = DescReader.readDesc(file.getAbsolutePath());

                // Convertir les caractéristiques de String à Double
                List<Double> doubleFeatures = features.stream()
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());

                // Appliquer la normalisation sur les caractéristiques
                List<Double> normalizedFeatures = normalizeFeatures(doubleFeatures);

                // Afficher le nom du fichier et les caractéristiques normalisées
                System.out.println("Caractéristiques normalisées pour " + file.getName() + " : " + normalizedFeatures);

            } catch (IOException e) {
                // Gérer les erreurs de lecture de fichier
                System.err.println("Erreur de lecture du fichier : " + file.getName());
                e.printStackTrace();
            }
        }
    }
}
