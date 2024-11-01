package fr.vmiad;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PGMReader {
    public static BufferedImage readPGM(String filePath) throws ImageReadException, IOException {
        File file = new File(filePath);
        return Imaging.getBufferedImage(file);
    }

    public static List<File> listPGMFiles(String directoryPath) {
        List<File> pgmFiles = new ArrayList<>();
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".pgm"));

        if (files != null) {
            for (File file : files) {
                pgmFiles.add(file);
            }
        }
        return pgmFiles;
    }

    public static void main(String[] args) {
        String directoryPath = "C:\\Users\\HOUdo\\OneDrive\\Documents\\Ecole\\Université\\M1\\RF\\RF_projet\\=SharvitB2\\=Corpus\\pgm";
        List<File> pgmFiles = listPGMFiles(directoryPath);
        int i = 0;
        for (File file : pgmFiles) {
            try {
                BufferedImage image = readPGM(file.getAbsolutePath());
                // Afficher ou traiter l'image
                System.out.println("Image lue : " + file.getName());
                i++;
            } catch (ImageReadException | IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(i);
    }
}
