
package com.qpa.question_paper_analyzer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.qpa.question_paper_analyzer.model.Question;
import com.qpa.question_paper_analyzer.repository.QuestionRepository;

@Service
public class QuestionExtractionService {

    private final QuestionRepository questionRepository;

    public QuestionExtractionService(
            QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> extractQuestions(
            String text,
            String subjectCode,
            int examYear,
            String questionPaperId) {

        List<Question> questions = new ArrayList<>();

        Pattern pattern = Pattern.compile(
            "(?m)^\\s*Q(\\d+)\\..*?(?=\\n\\s*(?:Q\\d+\\.|SECTION\\s+[A-Z]|\\*{3})|\\z)",
            Pattern.DOTALL
        );

        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {

            String questionText = matcher.group().trim();
            int questionNumber = Integer.parseInt(matcher.group(1));

            if (questionRepository.existsByQuestionPaperIdAndQuestionNumber(
                    questionPaperId, questionNumber)) {
                continue;
            }

            Matcher marksMatcher = Pattern.compile(
                    "\\[(\\d+)\\]\\s*$"
            ).matcher(questionText);

            int marks = 0;

            if (marksMatcher.find()) {
                marks = Integer.parseInt(marksMatcher.group(1));
            }

            Question question = new Question(
                    questionText,
                    questionNumber,
                    subjectCode,
                    examYear,
                    marks,
                    questionPaperId
            );

            questions.add(questionRepository.save(question));
        }

        return questions;
    }
}
