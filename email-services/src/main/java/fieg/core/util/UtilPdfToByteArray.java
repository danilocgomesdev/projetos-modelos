package fieg.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilPdfToByteArray {


    public static byte[] pdfToByteArray(String paths) {
        try {
            Path path = Paths.get(paths);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
