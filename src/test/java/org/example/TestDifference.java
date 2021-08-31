package org.example;

import org.junit.jupiter.api.Test;
import ru.yandex.qatools.ashot.comparison.ImageDiff;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDifference {
    @Test
    void shouldFindDifferenceInPDFs() {
        String actualPdfToImage = PdfToImage.convert("./src/test/resources/testresources/google_ua.pdf", 0);
        String expectedPdfToImage = PdfToImage.convert("./src/test/resources/testresources/google_ru.pdf", 0);

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
    void shouldNotFindDifferenceInPDFs() {
        String actualPdfToImage = PdfToImage.convert("./src/test/resources/testresources/google_ua.pdf", 0);
        String expectedPdfToImage = PdfToImage.convert("./src/test/resources/testresources/google_ua.pdf", 0);

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
