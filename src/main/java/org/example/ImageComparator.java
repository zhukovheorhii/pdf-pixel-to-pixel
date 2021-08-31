package org.example;

import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import java.awt.image.BufferedImage;

public class ImageComparator {
    public static ImageDiff compare(BufferedImage expected, BufferedImage actual) {
        return new ImageDiffer().makeDiff(expected, actual);
    }

    public static BufferedImage getDiff(BufferedImage expected, BufferedImage actual) {
        ImageDiff diff = compare(expected, actual);
        return diff.getMarkedImage();
    }
}
