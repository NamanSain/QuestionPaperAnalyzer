
package com.qpa.question_paper_analyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "question_papers")
public class QuestionPaper {

    @Id
    private String id;

    private String subjectCode;
    private int examYear;
    private String fileName;

    public QuestionPaper() {
    }

    public QuestionPaper(String subjectCode, int examYear, String fileName) {
        this.subjectCode = subjectCode;
        this.examYear = examYear;
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public int getExamYear() {
        return examYear;
    }

    public String getFileName() {
        return fileName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setExamYear(int examYear) {
        this.examYear = examYear;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
