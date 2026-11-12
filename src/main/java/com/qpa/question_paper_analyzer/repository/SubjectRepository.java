
package com.qpa.question_paper_analyzer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qpa.question_paper_analyzer.model.Subject;

public interface SubjectRepository extends MongoRepository<Subject, String> {

    boolean existsBySubjectCode(String subjectCode);

}
