package fr.vmiad;

import java.util.HashMap;
import java.util.Map;

/**
 * La classe ClassName fournit une méthode utilitaire pour extraire un label
 * (classe) à partir d'un nom de fichier, basé sur un mapping de préfixes à des
 * noms de classes.
 */
public class ClassName {

    /**
     * Extrait le label d'un fichier en fonction de son préfixe.
     * <p>
     * Cette méthode utilise une map contenant des associations entre les préfixes
     * de noms de fichiers (par exemple, "s01", "s02") et les noms des classes
     * correspondantes (par exemple, "Pigeon", "Bone"). Le label est déterminé en
     * comparant le préfixe du nom de fichier avec les clés de la map.
     * </p>
     * 
     * @param fileName Le chemin absolu ou le nom du fichier dont le label doit
     *                 être extrait.
     * @return Le label du fichier correspondant au préfixe trouvé, ou une chaîne
     *         vide si aucun préfixe ne correspond.
     */
    public static String getLabelFromFile(String fileName) {
        // Initialisation du label par défaut
        String classLabel = "Unknown";

        // Mapping des préfixes des fichiers aux classes
        Map<String, String> classMap = new HashMap<>();
        classMap.put("s01", "Pigeon");
        classMap.put("s02", "Bone");
        classMap.put("s03", "Carpet");
        classMap.put("s04", "Camel");
        classMap.put("s05", "PickUp");
        classMap.put("s06", "Person");
        classMap.put("s07", "Car");
        classMap.put("s08", "Elephant");
        classMap.put("s09", "Face");
        classMap.put("s10", "Fork");

        // Implémentation des méthodes de classification sur 10 classes
        // classMap.put("s11", "Sword");
        // classMap.put("s12", "Glass");
        // classMap.put("s13", "Hammer");
        // classMap.put("s14", "Heart");
        // classMap.put("s15", "Key");
        // classMap.put("s16", "Monster");
        // classMap.put("s17", "Ray");
        // classMap.put("s18", "Turtle");

        // Parcours du mapping pour trouver un préfixe correspondant
        for (Map.Entry<String, String> entry : classMap.entrySet()) {
            if (fileName.startsWith(entry.getKey())) {
                classLabel = entry.getValue(); // Si un préfixe correspond, on récupère le label
                break; // On peut sortir de la boucle une fois le préfixe trouvé
            }
        }

        return classLabel;
    }

}
