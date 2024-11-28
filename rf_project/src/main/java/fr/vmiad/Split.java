package fr.vmiad;

import java.io.File;
import java.util.*;

/**
 * Classe utilitaire pour diviser les fichiers de descripteur en ensembles
 * d'entraînement et de test de manière déterministe.
 */
public class Split {

    // Map pour stocker les ensembles par extension
    private final Map<String, List<String>> trainingSets = new HashMap<>();
    private final Map<String, List<String>> testSets = new HashMap<>();
    private final Random random; // Générateur aléatoire avec graine fixe

    /**
     * Constructeur de la classe Split avec une graine pour garantir une
     * répartition déterministe.
     *
     * @param seed Graine pour le générateur aléatoire.
     */
    public Split(long seed) {
        this.random = new Random(seed); // Générateur aléatoire basé sur la graine
    }

    /**
     * Divise une liste de fichiers en ensembles d'entraînement et de test.
     *
     * @param descFiles  La liste des fichiers de descripteur à diviser.
     * @param trainRatio La proportion des fichiers à inclure dans l'ensemble
     *                   d'entraînement (entre 0 et 1).
     * @param extension  L'extension des fichiers traités (ex. : ".zrk").
     */
    public void splitDataByExtension(List<File> descFiles, double trainRatio, String extension) {

        // Mélanger les fichiers de manière déterministe
        Collections.shuffle(descFiles, random);

        // Calculer la taille de l'ensemble d'entraînement
        int totalFiles = descFiles.size();
        int trainSize = (int) (totalFiles * trainRatio);

        // Initialiser les listes pour cette extension
        trainingSets.putIfAbsent(extension, new ArrayList<>());
        testSets.putIfAbsent(extension, new ArrayList<>());

        List<String> trainingSet = trainingSets.get(extension);
        List<String> testSet = testSets.get(extension);

        // Ajouter les fichiers dans les ensembles correspondants
        for (int i = 0; i < totalFiles; i++) {
            String fileName = descFiles.get(i).getName(); // Récupérer uniquement le nom du fichier

            if (i < trainSize) {
                trainingSet.add(fileName); // Ajouter au training set
            } else {
                testSet.add(fileName); // Ajouter au test set
            }
        }
    }

    /**
     * Retourne les ensembles d'entraînement divisés par extension.
     *
     * @return Une map contenant les ensembles d'entraînement pour chaque extension.
     */
    public Map<String, List<String>> getTrainingSets() {
        return trainingSets;
    }

    /**
     * Retourne les ensembles de test divisés par extension.
     *
     * @return Une map contenant les ensembles de test pour chaque extension.
     */
    public Map<String, List<String>> getTestSets() {
        return testSets;
    }

}