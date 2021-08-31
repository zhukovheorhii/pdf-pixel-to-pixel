package org.example;

import org.junit.jupiter.api.Test;
import ru.yandex.qatools.ashot.comparison.ImageDiff;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDifference {
    @Test
    void shouldFindDifferenceInPDFs() throws FileNotFoundException {
        File actualPdfFile = new File("./src/test/resources/testresources/google_ua.pdf");
        File expectedPdfFile = new File("./src/test/resources/testresources/google_ru.pdf");

        String actualPdfToImage = PdfToImage.convert(new FileInputStream(actualPdfFile), 0);
        String expectedPdfToImage = PdfToImage.convert(new FileInputStream(expectedPdfFile), 0);

        BufferedImage actualImage = null;
        try {
            actualImage = ImageIO.read(new File(actualPdfToImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File(expectedPdfToImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageDiff diff = ImageComparator.compare(expectedImage, actualImage);
        assertTrue(diff.hasDiff());
    }

    @Test
    void shouldNotFindDifferenceInPDFs() throws FileNotFoundException {
        File actualPdfFile = new File("./src/test/resources/testresources/google_ua.pdf");
        File expectedPdfFile = new File("./src/test/resources/testresources/google_ua.pdf");

        String actualPdfToImage = PdfToImage.convert(new FileInputStream(actualPdfFile), 0);
        String expectedPdfToImage = PdfToImage.convert(new FileInputStream(expectedPdfFile), 0);

        BufferedImage actualImage = null;
        try {
            actualImage = ImageIO.read(new File(actualPdfToImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage expectedImage = null;
        try {
            expectedImage = ImageIO.read(new File(expectedPdfToImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageDiff diff = ImageComparator.compare(expectedImage, actualImage);
        assertFalse(diff.hasDiff());
    }
}
