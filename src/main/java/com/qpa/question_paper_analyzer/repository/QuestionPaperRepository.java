
package com.qpa.question_paper_analyzer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qpa.question_paper_analyzer.model.QuestionPaper;

public interface QuestionPaperRepository
        extends MongoRepository<QuestionPaper, String> {

    java.util.Optional<QuestionPaper> findByFileName(String fileName);

    List<QuestionPaper> findBySubjectCode(String subjectCode);
}
