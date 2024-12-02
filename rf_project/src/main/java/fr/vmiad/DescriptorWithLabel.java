package fr.vmiad;

import java.util.List;

/**
 * Classe représentant un fichier de descripteurs, ses caractéristiques, et son
 * label.
 */
public class DescriptorWithLabel {
    private String fileName; // Nom du fichier
    private List<Double> features; // Liste des caractéristiques du fichier
    private String label; // Le label (classe) du fichier

    /**
     * Constructeur pour créer un objet DescriptorWithLabel.
     *
     * @param fileName Le nom du fichier.
     * @param features Les caractéristiques associées au fichier.
     * @param label    Le label (classe) associé au fichier.
     */
    public DescriptorWithLabel(String fileName, List<Double> features, String label) {
        this.fileName = fileName;
        this.features = features;
        this.label = label;
    }

    /**
     * Retourne le nom du fichier.
     *
     * @return Le nom du fichier.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Retourne la liste des caractéristiques associées au fichier.
     *
     * @return Une liste de caractéristiques.
     */
    public List<Double> getFeatures() {
        return features;
    }

    /**
     * Retourne le label (classe) associé au fichier.
     *
     * @return Le label du fichier.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Représente l'objet sous forme de chaîne de caractères.
     *
     * @return Une chaîne contenant le nom du fichier et son label.
     */
    @Override
    public String toString() {
        return "File: " + fileName + " -> Label: " + label;
    }
}
