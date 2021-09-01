# PDF and Image Pixel To Pixel Comparator

## Overview

To run the app execute `./gradlew bootRun`

## CURL to compare PDFs

`curl --location --request POST 'http://localhost:8080/comparePdf?firstFilePageIndex=0&secondFilePageIndex=0' \
--form 'first=@"FILE_1.pdf"' \
--form 'second=@"FILE_2.pdf"'`

## CURL to compare Images

`curl --location --request POST 'http://localhost:8080/compareImage' \
--form 'first=@"FILE_1.jpg"' \
--form 'second=@"FILE_2.jpg"'`
