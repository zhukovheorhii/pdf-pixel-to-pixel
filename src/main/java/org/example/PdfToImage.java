package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

public class PdfToImage {
    public static String convert(InputStream inputStream, int pageIndex) {
        PDDocument document = null;
        try {
            document = PDDocument.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PDFRenderer pdfRender = new PDFRenderer(document);

        BufferedImage imageRender = null;
        try {
            imageRender = pdfRender.renderImageWithDPI(pageIndex, 200f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String imagePath = "./src/tmp/" + UUID.randomUUID() + ".jpeg";
        try {
            ImageIO.write(Objects.requireNonNull(imageRender), "JPEG", new File(imagePath));
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imagePath;
    }
}
