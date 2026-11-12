
package com.qpa.question_paper_analyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "questions")
public class Question {

    @Id
    private String id;

    private String questionText;
    private int questionNumber;
    private String questionPaperId;
    private String subjectCode;
    private int examYear;
    private int marks;

    public Question() {
    }

    public Question(String questionText, int questionNumber,
                String subjectCode, int examYear, int marks) {
    this(questionText, questionNumber, subjectCode, examYear, marks, null);
}

    public Question(String questionText, int questionNumber,
                    String subjectCode, int examYear, int marks,
                    String questionPaperId) {
        this.questionText = questionText;
        this.questionNumber = questionNumber;
        this.subjectCode = subjectCode;
        this.examYear = examYear;
        this.marks = marks;
        this.questionPaperId = questionPaperId;
    }

    public String getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public int getExamYear() {
        return examYear;
    }

    public int getMarks() {
        return marks;
    }
    
    public String getQuestionPaperId() {
    return questionPaperId;
    }   

    public void setQuestionPaperId(String questionPaperId) {
        this.questionPaperId = questionPaperId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setExamYear(int examYear) {
        this.examYear = examYear;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
