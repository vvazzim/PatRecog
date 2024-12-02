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
     * Chaque ligne est convertie en un double et ajoutée à une liste.
     *
     * @param filePath Le chemin absolu du fichier descripteur à lire.
     * @return Une liste contenant les lignes du fichier comme doubles.
     * @throws IOException           Si une erreur se produit lors de la lecture du
     *                               fichier
     *                               (exemple : fichier non trouvé).
     * @throws NumberFormatException Si une ligne ne peut pas être convertie en
     *                               double.
     */
    public static List<Double> readDesc(String filePath) throws IOException {

        List<Double> features = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Lire chaque ligne du fichier
            while ((line = br.readLine()) != null) {
                // Supprimer les espaces inutiles et convertir en double
                features.add(Double.parseDouble(line.trim()));
            }
        } catch (NumberFormatException e) {
            System.err.println("Erreur de conversion de ligne en double dans le fichier : " + filePath);
            throw e;
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
}