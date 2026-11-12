
package com.qpa.question_paper_analyzer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.qpa.question_paper_analyzer.model.Question;
import com.qpa.question_paper_analyzer.model.QuestionPaper;
import com.qpa.question_paper_analyzer.repository.QuestionPaperRepository;
import com.qpa.question_paper_analyzer.repository.QuestionRepository;

@Service
public class AnalysisService {

    private final QuestionRepository questionRepository;
    private final QuestionPaperRepository questionPaperRepository;

    public AnalysisService(
            QuestionRepository questionRepository,
            QuestionPaperRepository questionPaperRepository) {

        this.questionRepository = questionRepository;
        this.questionPaperRepository = questionPaperRepository;
    }

    // 1. Marks Distribution
    public Map<String, Object> getMarksDistribution(
            String subjectCode, int examYear) {

        List<Question> questions =
                questionRepository.findBySubjectCodeAndExamYear(
                        subjectCode, examYear);

        Map<Integer, Integer> distribution = new HashMap<>();
        int totalMarks = 0;

        for (Question question : questions) {

            int marks = question.getMarks();

            distribution.put(
                    marks,
                    distribution.getOrDefault(marks, 0) + 1
            );

            totalMarks += marks;
        }

        Map<String, Object> result = new HashMap<>();

        result.put("subjectCode", subjectCode);
        result.put("examYear", examYear);
        result.put("totalQuestions", questions.size());
        result.put("marksDistribution", distribution);
        result.put("totalMarks", totalMarks);

        return result;
    }

    // 2. Year-wise Analysis
    public Map<String, Object> getYearWiseAnalysis(
            String subjectCode) {

        List<QuestionPaper> papers =
                questionPaperRepository.findBySubjectCode(subjectCode);

        Map<Integer, Integer> paperCount = new TreeMap<>();
        Map<Integer, Long> questionCount = new TreeMap<>();

        for (QuestionPaper paper : papers) {

            int year = paper.getExamYear();

            paperCount.put(
                    year,
                    paperCount.getOrDefault(year, 0) + 1
            );

            long totalQuestions =
                    questionRepository.countByQuestionPaperId(
                            paper.getId());

            questionCount.put(
                    year,
                    questionCount.getOrDefault(year, 0L)
                            + totalQuestions
            );
        }

        List<Map<String, Object>> yearlyResults = new ArrayList<>();

        for (Integer year : paperCount.keySet()) {

            Map<String, Object> yearData = new HashMap<>();

            yearData.put("year", year);
            yearData.put("totalPapers", paperCount.get(year));
            yearData.put("totalQuestions", questionCount.get(year));

            yearlyResults.add(yearData);
        }

        Map<String, Object> result = new HashMap<>();

        result.put("subjectCode", subjectCode);
        result.put("yearWiseAnalysis", yearlyResults);

        return result;
    }

    // 3. Repeated Questions Analysis
    public Map<String, Object> getRepeatedQuestions(
            String subjectCode) {
                

        List<Question> questions =
                questionRepository.findBySubjectCode(subjectCode);

        Map<String, Map<String, Object>> questionMap = new HashMap<>();

        for (Question question : questions) {

            String originalText = question.getQuestionText();

            // Normalize question text
            String normalizedText = originalText
                    .replaceFirst("(?i)^\\s*Q\\d+\\.\\s*", "")
                    .toLowerCase()
                    .replaceAll("[^a-z0-9\\s]", "")
                    .replaceAll("\\s+", " ")
                    .trim();

            Map<String, Object> data = questionMap.get(normalizedText);

            if (data == null) {

                data = new HashMap<>();

                data.put("questionText", originalText);
                data.put("timesRepeated", 0);
                data.put("papersFound", new HashSet<String>());

                questionMap.put(normalizedText, data);
            }

            data.put(
                    "timesRepeated",
                    (int) data.get("timesRepeated") + 1
            );

            @SuppressWarnings("unchecked")
            Set<String> papers =
                    (Set<String>) data.get("papersFound");

            if (question.getQuestionPaperId() != null) {
                papers.add(question.getQuestionPaperId());
            }
        }

        List<Map<String, Object>> repeatedQuestions = new ArrayList<>();

        for (Map<String, Object> data : questionMap.values()) {

            @SuppressWarnings("unchecked")
            Set<String> papers =
                    (Set<String>) data.get("papersFound");

            // Include only questions found in different PDFs
            if (papers.size() > 1) {

                Map<String, Object> repeated = new HashMap<>();

                repeated.put("questionText", data.get("questionText"));
                repeated.put("timesRepeated", data.get("timesRepeated"));
                repeated.put("papersFound", papers.size());

                repeatedQuestions.add(repeated);
            }
        }

        Map<String, Object> response = new HashMap<>();

        response.put("subjectCode", subjectCode);
        response.put("repeatedQuestions", repeatedQuestions);

        return response;
    }

    public Map<String, Object> getQuestionFrequency(String subjectCode) {

    List<Question> questions =
            questionRepository.findBySubjectCode(subjectCode);

    Map<String, Map<String, Object>> questionMap = new HashMap<>();

    for (Question question : questions) {

        String normalizedText = question.getQuestionText()
                .replaceFirst("(?i)^\\s*Q\\d+\\.\\s*", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", " ")
                .trim();

        Map<String, Object> data = questionMap.get(normalizedText);

        if (data == null) {
            data = new HashMap<>();
            data.put("questionText", question.getQuestionText());
            data.put("papers", new HashSet<String>());
            data.put("years", new HashSet<Integer>());

            questionMap.put(normalizedText, data);
        }

        @SuppressWarnings("unchecked")
        Set<String> papers = (Set<String>) data.get("papers");

        if (question.getQuestionPaperId() != null) {
            papers.add(question.getQuestionPaperId());
        }

        @SuppressWarnings("unchecked")
        Set<Integer> years = (Set<Integer>) data.get("years");

        years.add(question.getExamYear());
    }

    List<Map<String, Object>> frequencyResults = new ArrayList<>();

    for (Map<String, Object> data : questionMap.values()) {

        @SuppressWarnings("unchecked")
        Set<String> papers = (Set<String>) data.get("papers");

        if (papers.size() > 1) {
            @SuppressWarnings("unchecked")
            Set<Integer> years = (Set<Integer>) data.get("years");

            Map<String, Object> item = new HashMap<>();
            item.put("questionText", data.get("questionText"));
            item.put("frequency", papers.size());
            item.put("yearsAppeared", years);

            frequencyResults.add(item);
        }
    }

    Map<String, Object> response = new HashMap<>();
    response.put("subjectCode", subjectCode);
    response.put("questionFrequency", frequencyResults);

    return response;
    }
}
