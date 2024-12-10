# Projet de Reconnaissance de Patrons

Ce projet utilise des algorithmes de classification pour traiter des fichiers de descripteurs. Deux méthodes principales sont implémentées : K-Nearest Neighbors (KNN) et K-Means.

## Prérequis

Avant d'exécuter les classes, assurez-vous d'avoir les éléments suivants :

- Java JDK 8 ou supérieur installé sur votre machine.
- Un IDE (comme IntelliJ IDEA ou Eclipse) ou un éditeur de texte pour exécuter le code.
- Les fichiers de descripteurs au format `.zrk` dans le répertoire spécifié.

## Structure du Projet

Le projet est structuré comme suit :
rf_project/ ├── src/ 
            │ └── main/ 
            │ └── java/ 
            │ └── fr/ 
            │ └── vmiad/ 
            │ ├── MainKNN.java 
            │ └── MainKMeans.java 
            └── resources/ 
            └── =Signatures/ 
            └── =Zernike7/ 
            └── *.zrk


## Exécution de MainKNN

1. **Ouvrir le fichier `MainKNN.java`** dans votre IDE ou éditeur de texte.
2. **Vérifiez le chemin du répertoire** contenant les fichiers descripteurs. Le chemin par défaut est :

3. Assurez-vous que ce répertoire contient des fichiers avec l'extension `.zrk`.

3. **Compiler et exécuter** la classe `MainKNN`. Cela peut généralement être fait en cliquant sur le bouton "Run" dans votre IDE.

4. **Résultats** : Le programme affichera les fichiers de test et évaluera la performance du modèle KNN.

## Exécution de MainKMeans

1. **Ouvrir le fichier `MainKMeans.java`** dans votre IDE ou éditeur de texte.
2. **Vérifiez le chemin du répertoire** contenant les fichiers descripteurs. Le chemin par défaut est :

Assurez-vous que ce répertoire contient des fichiers avec l'extension `.zrk`.

3. **Compiler et exécuter** la classe `MainKMeans`. Cela peut généralement être fait en cliquant sur le bouton "Run" dans votre IDE.

4. **Résultats** : Le programme affichera l'inertie pour différents nombres de clusters et vous aidera à déterminer le nombre optimal de clusters à utiliser.

## Remarques

- Assurez-vous que toutes les dépendances nécessaires sont incluses dans votre projet.
- Les résultats peuvent varier en fonction des données d'entrée et des paramètres utilisés.

## Aide

Pour toute question ou problème, veuillez consulter la documentation ou contacter le développeur du projet.
