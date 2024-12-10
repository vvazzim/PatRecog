# Projet de Reconnaissance des Formes

Ce projet implémente des algorithmes de classification appliqués à des fichiers de descripteurs de formes. Deux méthodes principales sont disponibles : K-Nearest Neighbors (KNN) et K-Means.

---

## Prérequis

Avant d'exécuter le projet, assurez-vous d'avoir :

- **Java JDK 8** ou supérieur installé sur votre machine.
- Un IDE (par exemple, IntelliJ IDEA ou Eclipse) ou un éditeur de texte pour travailler sur le code.
- Les fichiers de descripteurs nécessaires dans le bon répertoire (par exemple, `.zrk`, `.art`, etc.).

---

## Configuration avant exécution

### Modification du chemin du répertoire et de l'extension des fichiers

Pour tester différentes méthodes, vous devrez modifier **manuellement** :
1. **Le chemin du répertoire** : Ce chemin pointe vers le dossier contenant les fichiers de descripteurs (par exemple : `SxxNyy.zrk`).
2. **L'extension des fichiers** : Cela permet de spécifier quelle méthode de descripteur sera utilisée (par exemple, `.zrk`, `.art`, `.gfd`).

Les modifications doivent être effectuées directement dans les fichiers suivants avant exécution :
- **`MainKNN.java`**
- **`MainKMeans.java`**

Recherchez et modifiez les variables associées au chemin du répertoire et à l'extension (par exemple, `directory` et `extension`) dans le code.

Exemple de modification dans le fichier `MainKNN.java` :
```java
// Exemple de chemin et extension
String directory = "chemin/vers/le/répertoire"; // À modifier
String extension = ".zrk";                     // Modifier selon la méthode
