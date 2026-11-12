
package com.qpa.question_paper_analyzer.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qpa.question_paper_analyzer.service.AnalysisService;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    // 1. Marks Distribution
    @GetMapping("/marks/{subjectCode}/{examYear}")
    public Map<String, Object> getMarksDistribution(
            @PathVariable String subjectCode,
            @PathVariable int examYear) {

        return analysisService.getMarksDistribution(
                subjectCode, examYear);
    }

    // 2. Year-wise Analysis
    @GetMapping("/years/{subjectCode}")
    public Map<String, Object> getYearWiseAnalysis(
            @PathVariable String subjectCode) {

        return analysisService.getYearWiseAnalysis(subjectCode);
    }

    // 3. Repeated Questions Analysis
    @GetMapping("/repeated/{subjectCode}")
    public Map<String, Object> getRepeatedQuestions(
            @PathVariable String subjectCode) {

        return analysisService.getRepeatedQuestions(subjectCode);
    }

    @GetMapping("/frequency/{subjectCode}")
    public Map<String, Object> getQuestionFrequency(
            @PathVariable String subjectCode) {

        return analysisService.getQuestionFrequency(subjectCode);
    }
}
