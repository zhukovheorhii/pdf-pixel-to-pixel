package org.example;

import org.junit.jupiter.api.Test;
import ru.yandex.qatools.ashot.comparison.ImageDiff;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDifference {
    @Test
    void shouldFindDifferenceInPDFs() throws FileNotFoundException {
        File actualPdfFile = new File("./src/test/resources/testresources/google_ua.pdf");
        File expectedPdfFile = new File("./src/test/resources/testresources/google_ru.pdf");

        BufferedImage actualPdfToImage = PdfToImage.convert(new FileInputStream(actualPdfFile), 0);
        BufferedImage expectedPdfToImage = PdfToImage.convert(new FileInputStream(expectedPdfFile), 0);

        ImageDiff diff = ImageComparator.compare(expectedPdfToImage, actualPdfToImage);
        assertTrue(diff.hasDiff());
    }

    @Test
    void shouldNotFindDifferenceInPDFs() throws FileNotFoundException {
        File actualPdfFile = new File("./src/test/resources/testresources/google_ua.pdf");
        File expectedPdfFile = new File("./src/test/resources/testresources/google_ua.pdf");

        BufferedImage actualPdfToImage = PdfToImage.convert(new FileInputStream(actualPdfFile), 0);
        BufferedImage expectedPdfToImage = PdfToImage.convert(new FileInputStream(expectedPdfFile), 0);

        ImageDiff diff = ImageComparator.compare(expectedPdfToImage, actualPdfToImage);
        assertFalse(diff.hasDiff());
    }
}
