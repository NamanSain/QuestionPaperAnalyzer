
package com.qpa.question_paper_analyzer.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.qpa.question_paper_analyzer.model.Question;
import com.qpa.question_paper_analyzer.model.QuestionPaper;
import com.qpa.question_paper_analyzer.repository.QuestionPaperRepository;
import com.qpa.question_paper_analyzer.service.PdfService;
import com.qpa.question_paper_analyzer.service.QuestionExtractionService;

@RestController
@RequestMapping("/api/question-papers")
public class QuestionPaperController {

    private final QuestionPaperRepository questionPaperRepository;
    private final PdfService pdfService;
    private final QuestionExtractionService questionExtractionService;

    @Value("${qpa.upload-dir}")
    private String uploadDir;

    public QuestionPaperController(
            QuestionPaperRepository questionPaperRepository,
            PdfService pdfService,
            QuestionExtractionService questionExtractionService) {

        this.questionPaperRepository = questionPaperRepository;
        this.pdfService = pdfService;
        this.questionExtractionService = questionExtractionService;
    }

    @PostMapping
    public QuestionPaper addQuestionPaper(
            @RequestBody QuestionPaper questionPaper) {

        return questionPaperRepository.save(questionPaper);
    }

    @GetMapping
    public List<QuestionPaper> getAllQuestionPapers() {
        return questionPaperRepository.findAll();
    }

    @PostMapping("/upload")
    public QuestionPaper uploadPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("examYear") int examYear) throws IOException {

        if (file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Please select a PDF file");
        }

        if (subjectCode == null || subjectCode.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Subject code is required");
        }

        if (examYear <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Exam year must be valid");
        }

        String originalName = file.getOriginalFilename();

        if (originalName == null
                || !originalName.toLowerCase().endsWith(".pdf")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Only PDF files are allowed");
        }

        Path directory = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        Files.createDirectories(directory);

        String storedName = UUID.randomUUID() + ".pdf";
        Path filePath = directory.resolve(storedName).normalize();

        file.transferTo(filePath);

        QuestionPaper questionPaper = new QuestionPaper(
                subjectCode.trim(), examYear, storedName);

        return questionPaperRepository.save(questionPaper);
    }

    @GetMapping("/extract-text/{fileName:.+}")
    public String extractPdfText(@PathVariable String fileName)
            throws IOException {

        if (!fileName.matches("[a-fA-F0-9-]+\\.pdf")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid PDF filename");
        }

        Path directory = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        Path filePath = directory.resolve(fileName).normalize();

        if (!Files.isRegularFile(filePath)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "PDF file not found");
        }

        return pdfService.extractText(filePath);
    }

    @GetMapping("/extract-questions/{fileName:.+}")
    public List<Question> extractQuestions(
            @PathVariable String fileName) throws IOException {

        String text = extractPdfText(fileName);

        QuestionPaper paper = questionPaperRepository
                .findByFileName(fileName)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Question paper record not found in database"));

        return questionExtractionService.extractQuestions(
                text,
                paper.getSubjectCode(),
                paper.getExamYear(),
                paper.getId()
        );
    }
}
