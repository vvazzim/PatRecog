package fr.vmiad;

import java.util.*;
import java.io.File;

public class Split {

    private Map<String, List<String>> trainingSets = new HashMap<>();
    private Map<String, List<String>> testSets = new HashMap<>();
    private Random random;

    // Constructeur avec graine pour reproductibilité
    public Split(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Divise les données en ensembles d'entraînement et de test avec
     * stratification.
     * 
     * @param files      Liste des fichiers à diviser.
     * @param trainRatio Ratio de fichiers à inclure dans l'ensemble d'entraînement.
     * @param extension  Extension des fichiers pour catégoriser (ex: ".yng").
     */
    public void splitDataByStratification(List<File> files, double trainRatio, String extension) {
        // Map pour regrouper les fichiers par classe
        Map<String, List<File>> filesByClass = new HashMap<>();

        // Regrouper les fichiers par classe
        for (File file : files) {
            String fileName = file.getName();
            String label = ClassName.getLabelFromFile(fileName); // Obtenir le label depuis le nom du fichier
            filesByClass.putIfAbsent(label, new ArrayList<>());
            filesByClass.get(label).add(file);
        }

        // Initialiser les ensembles d'entraînement et de test
        trainingSets.put(extension, new ArrayList<>());
        testSets.put(extension, new ArrayList<>());

        // Diviser chaque groupe (classe) selon le ratio spécifié
        for (Map.Entry<String, List<File>> entry : filesByClass.entrySet()) {
            List<File> classFiles = entry.getValue();
            Collections.shuffle(classFiles, random); // Mélanger les fichiers pour garantir un tirage aléatoire

            int trainSize = (int) Math.round(classFiles.size() * trainRatio);

            // Ajouter les fichiers dans les ensembles respectifs
            for (int i = 0; i < classFiles.size(); i++) {
                if (i < trainSize) {
                    trainingSets.get(extension).add(classFiles.get(i).getName());
                } else {
                    testSets.get(extension).add(classFiles.get(i).getName());
                }
            }
        }
    }

    // Getters pour obtenir les ensembles
    public Map<String, List<String>> getTrainingSets() {
        return trainingSets;
    }

    public Map<String, List<String>> getTestSets() {
        return testSets;
    }
}
