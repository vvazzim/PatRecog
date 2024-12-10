package fr.vmiad;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe pour les opérations sur les fichiers.
 * Fournit des méthodes simplifiées pour interagir avec des fichiers.
 */
public class FileUtils {

    /**
     * Ajoute un contenu spécifié à un fichier situé au chemin donné.
     * Si le fichier n'existe pas, il sera créé. Chaque appel ajoute le contenu sur
     * une nouvelle ligne.
     *
     * @param filePath Le chemin du fichier où le contenu sera écrit.
     *                 Si le fichier n'existe pas, il sera créé automatiquement.
     * @param content  Le contenu à ajouter au fichier. Ce contenu sera suivi d'un
     *                 saut de ligne.
     * 
     *                 Exemple d'utilisation :
     * 
     * @code
     *       FileUtils.appendResultToFile("output.txt", "Bonjour, le monde !");
     * 
     *       Note : Si le fichier est inaccessible ou si une erreur se
     *       produit
     *       lors de l'écriture, la méthode affichera la trace de la pile
     *       d'erreurs dans la sortie standard d'erreur.
     *
     *       @throws IOException si une erreur d'entrée/sortie se produit (par
     *       exemple, si
     *       le fichier est verrouillé ou inaccessible).
     */

    public static void appendResultToFile(String filePath, String content) {
        // Utilisation d'un try-with-resources pour garantir la fermeture automatique du
        // BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Écrit le contenu dans le fichier
            writer.write(content);
            // Ajoute une nouvelle ligne après le contenu
            writer.newLine();
        } catch (IOException e) {
            // Affiche la trace de l'erreur dans la console pour le débogage
            e.printStackTrace();
        }
    }
}
