package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PdfToImage {
    public static BufferedImage convert(InputStream inputStream, int pageIndex) {
        PDDocument document = null;
        try {
            document = PDDocument.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PDFRenderer pdfRender = new PDFRenderer(document);

        try {
            return pdfRender.renderImageWithDPI(pageIndex, 200f);
        } catch (IOException e) {
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
        return null;
    }
}
