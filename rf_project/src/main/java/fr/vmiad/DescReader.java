package fr.vmiad;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe utilitaire pour gérer les fichiers descripteurs.
 * 
 * Cette classe permet d'effectuer plusieurs opérations sur les fichiers
 * descripteurs, telles que :
 * 
 * Lire le contenu d'un fichier ligne par ligne.
 * Parcourir un répertoire pour lister les fichiers ayant une extension
 * donnée.
 */
public class DescReader {

    /**
     * Lit le contenu d'un fichier descripteur ligne par ligne.
     * Chaque ligne est ajoutée à une liste après suppression des espaces
     * inutiles.
     *
     * @param filePath Le chemin absolu du fichier descripteur à lire.
     * @return Une liste contenant les lignes du fichier comme chaînes de
     *         caractères.
     * @throws IOException Si une erreur se produit lors de la lecture du fichier
     *                     (exemple : fichier non trouvé).
     */
    public static List<String> readDesc(String filePath) throws IOException {
        List<String> features = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Lire chaque ligne du fichier
            while ((line = br.readLine()) != null) {
                features.add(line.trim()); // Supprimer les espaces inutiles
            }
        }
        return features;
    }

    /**
     * Liste tous les fichiers ayant une extension spécifique dans un répertoire
     * donné.
     *
     * @param directoryPath Le chemin du répertoire à parcourir.
     * @param extension     L'extension des fichiers à rechercher (par exemple :
     *                      ".zrk").
     * @return Une liste d'objets {@link File} représentant les fichiers ayant
     *         l'extension donnée.
     */
    public static List<File> listDescFiles(String directoryPath, String extension) {
        List<File> descFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        // Vérifier si le chemin est un répertoire valide
        if (directory.isDirectory()) {
            // Filtrer et récupérer les fichiers ayant l'extension spécifiée
            File[] files = directory.listFiles((dir, name) -> name.endsWith(extension));
            if (files != null) {
                Collections.addAll(descFiles, files); // Ajouter les fichiers à la liste
            }
        } else {
            System.out.println("Directory not found : " + directoryPath);
        }
        return descFiles;
    }

    /**
     * Méthode principale pour tester les fonctionnalités de la classe DescReader.
     * Elle parcourt un répertoire, liste les fichiers ayant une extension donnée,
     * et lit les caractéristiques des fichiers pour les afficher.
     *
     * @param args Arguments de la ligne de commande (non utilisés dans cet
     *             exemple).
     */
    public static void main(String[] args) {
        // Répertoire contenant les fichiers descripteurs
        String directory = "rf_project\\src\\main\\resources\\=Signatures\\=Zernike7";
        String extension = ".zrk";

        // Lister les fichiers descripteurs ayant l'extension ".zrk"
        List<File> descFiles = listDescFiles(directory, extension);

        // Parcourir et traiter chaque fichier trouvé
        for (File file : descFiles) {
            try {
                // Lire les caractéristiques du fichier et les afficher
                List<String> features = readDesc(file.getAbsolutePath());
                System.out.println("File : " + file.getName());
                System.out.println("Features : " + features);
            } catch (IOException e) {
                // Gestion des erreurs de lecture
                System.err.println("Error trying to read file : " + file.getName());
                e.printStackTrace();
            }
        }
    }
}
