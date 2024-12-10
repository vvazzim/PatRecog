package fr.vmiad;

import java.util.*;
import java.io.File;

/**
 * Classe pour diviser des ensembles de fichiers en ensembles
 * d'entraînement et de test en utilisant la stratification.
 * 
 * <p>
 * La stratification garantit que chaque ensemble (entraînement et test)
 * contient
 * une proportion similaire de chaque classe.
 * </p>
 */
public class Split {

    // Map contenant les ensembles d'entraînement, classés par extension
    private Map<String, List<String>> trainingSets = new HashMap<>();
    // Map contenant les ensembles de test, classés par extension
    private Map<String, List<String>> testSets = new HashMap<>();
    // Générateur de nombres aléatoires pour garantir la reproductibilité
    private Random random;

    /**
     * Constructeur de la classe Split.
     * 
     * @param seed Une graine pour le générateur aléatoire, permettant de garantir
     *             la reproductibilité des divisions.
     */
    public Split(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Divise les fichiers en ensembles d'entraînement et de test avec
     * stratification. Chaque classe de fichiers est traitée séparément pour
     * garantir que les ensembles générés contiennent une proportion similaire de
     * fichiers de chaque classe.
     * 
     * @param files      Liste des fichiers à diviser.
     * @param trainRatio Ratio de fichiers à inclure dans l'ensemble
     *                   d'entraînement. Exemple : 0.6 pour 60% des données dans
     *                   l'ensemble d'entraînement.
     * @param extension  Extension des fichiers pour catégoriser. Par exemple :
     *                   ".yng".
     */
    public void splitDataByStratification(List<File> files, double trainRatio, String extension) {
        // Map pour regrouper les fichiers par classe
        Map<String, List<File>> filesByClass = new HashMap<>();

        // Étape 1 : Regrouper les fichiers par classe
        for (File file : files) {
            String fileName = file.getName();
            String label = ClassName.getLabelFromFile(fileName); // Obtenir le label depuis le nom du fichier
            filesByClass.putIfAbsent(label, new ArrayList<>());
            filesByClass.get(label).add(file);
        }

        // Initialiser les listes d'entraînement et de test pour l'extension donnée
        trainingSets.put(extension, new ArrayList<>());
        testSets.put(extension, new ArrayList<>());

        // Étape 2 : Diviser chaque classe selon le ratio spécifié
        for (Map.Entry<String, List<File>> entry : filesByClass.entrySet()) {
            List<File> classFiles = entry.getValue();
            // Mélanger les fichiers pour une distribution aléatoire
            Collections.shuffle(classFiles, random);

            // Calculer la taille de l'ensemble d'entraînement
            int trainSize = (int) Math.round(classFiles.size() * trainRatio);

            // Répartir les fichiers entre l'entraînement et le test
            for (int i = 0; i < classFiles.size(); i++) {
                if (i < trainSize) {
                    trainingSets.get(extension).add(classFiles.get(i).getName());
                } else {
                    testSets.get(extension).add(classFiles.get(i).getName());
                }
            }
        }
    }

    /**
     * Retourne les ensembles d'entraînement générés par la méthode
     * splitDataByStratification.
     * 
     * @return Une Map où la clé est l'extension et la valeur est une liste de noms
     *         de fichiers d'entraînement.
     */
    public Map<String, List<String>> getTrainingSets() {
        return trainingSets;
    }

    /**
     * Retourne les ensembles de test générés par la méthode
     * splitDataByStratification.
     * 
     * @return Une Map où la clé est l'extension et la valeur est une liste de noms
     *         de fichiers de test.
     */
    public Map<String, List<String>> getTestSets() {
        return testSets;
    }
}
