# PDF Pixel To Pixel Comparator

## Overview

To run the app execute `./gradlew bootRun`

## CURL to compare PDFs

`curl --location --request POST 'http://localhost:8080/upload?pageIndexExpectedPdf=0&pageIndexActualPdf=0' \
--form 'expectedPdf=@"FIRST_FILE.pdf"' \
--form 'actualPdf=@"SECOND_FILE.pdf"'`
