package org.example.controller;

import org.example.ImageComparator;
import org.example.PdfToImage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    @PostMapping(path = "/comparePdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadPdf(
            @RequestPart("first") MultipartFile first,
            @RequestPart("second") MultipartFile second,
            @RequestParam(value = "firstFilePageIndex", required = false, defaultValue = "0") int firstFilePageIndex,
            @RequestParam(value = "secondFilePageIndex", required = false, defaultValue = "0") int secondFilePageIndex
    ) throws IOException {
        BufferedImage actualPdfToImage = PdfToImage.convert(second.getInputStream(), secondFilePageIndex);
        BufferedImage expectedPdfToImage = PdfToImage.convert(first.getInputStream(), firstFilePageIndex);

        ImageDiff diff = ImageComparator.compare(expectedPdfToImage, actualPdfToImage);
        return checkDiff(diff);
    }

    @PostMapping(path = "/compareImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadImage(
            @RequestPart("first") MultipartFile first,
            @RequestPart("second") MultipartFile second
    ) throws IOException {
        ImageDiff diff = ImageComparator.compare(ImageIO.read(first.getInputStream()), ImageIO.read(second.getInputStream()));
        return checkDiff(diff);
    }

    private ResponseEntity checkDiff(ImageDiff diff) throws IOException {
        ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
        ImageIO.write(diff.getMarkedImage(), "jpeg", byteArrayStream);
        if (diff.hasDiff()) {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", MediaType.IMAGE_PNG_VALUE);
            return new ResponseEntity(byteArrayStream.toByteArray(), headers, HttpStatus.OK);
        } else {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", MediaType.TEXT_PLAIN_VALUE);
            return new ResponseEntity<String>("Files are equal!", headers, HttpStatus.OK);
        }
    }
}
