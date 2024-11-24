package fr.vmiad;

import java.util.List;

public class DataPoint {
    private List<Double> features; // Les caractéristiques normalisées
    private String label; // L'étiquette (classe)

    // Constructeur
    public DataPoint(List<Double> features, String label) {
        this.features = features;
        this.label = label;
    }

    // Getters
    public List<Double> getFeatures() {
        return features;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "features=" + features +
                ", label='" + label + '\'' +
                '}';
    }

}
