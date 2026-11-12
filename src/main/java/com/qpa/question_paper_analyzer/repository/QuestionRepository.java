
package com.qpa.question_paper_analyzer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qpa.question_paper_analyzer.model.Question;

public interface QuestionRepository
        extends MongoRepository<Question, String> {

    boolean existsBySubjectCodeAndExamYearAndQuestionNumber(
            String subjectCode,
            int examYear,
            int questionNumber
    );

    List<Question> findBySubjectCodeAndExamYear(
            String subjectCode,
            int examYear
    );

    boolean existsByQuestionPaperIdAndQuestionNumber(
            String questionPaperId,
            int questionNumber
    );

    long countBySubjectCodeAndExamYear(
        String subjectCode,
        int examYear
    );

    long countByQuestionPaperId(String questionPaperId);

    List<Question> findBySubjectCode(String subjectCode);
}
