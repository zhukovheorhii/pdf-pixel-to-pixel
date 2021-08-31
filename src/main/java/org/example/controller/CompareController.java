package org.example.controller;

import org.example.ImageComparator;
import org.example.PdfToImage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.qatools.ashot.comparison.ImageDiff;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
public class CompareController {
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity upload(
            @RequestPart("expectedPdf") MultipartFile expectedPdf,
            @RequestPart("actualPdf") MultipartFile actualPdf,
            @RequestParam(value = "pageIndexExpectedPdf", required = false, defaultValue = "0") int pageIndexExpectedPdf,
            @RequestParam(value = "pageIndexActualPdf", required = false, defaultValue = "0") int pageIndexActualPdf
    ) throws IOException {
        BufferedImage actualPdfToImage = PdfToImage.convert(actualPdf.getInputStream(), pageIndexActualPdf);
        BufferedImage expectedPdfToImage = PdfToImage.convert(expectedPdf.getInputStream(), pageIndexExpectedPdf);

        ImageDiff diff = ImageComparator.compare(expectedPdfToImage, actualPdfToImage);

        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        ImageIO.write(diff.getMarkedImage(), "jpeg", byteArrayStream);
        return new ResponseEntity(byteArrayStream.toByteArray(), HttpStatus.OK);
    }
}
