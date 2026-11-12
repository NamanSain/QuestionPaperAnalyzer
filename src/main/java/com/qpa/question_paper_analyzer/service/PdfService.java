
package com.qpa.question_paper_analyzer.service;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

    public String extractText(Path filePath) throws IOException {

        try (PDDocument document = Loader.loadPDF(filePath.toFile())) {

            PDFTextStripper pdfStripper = new PDFTextStripper();

            return pdfStripper.getText(document);
        }
    }
}
