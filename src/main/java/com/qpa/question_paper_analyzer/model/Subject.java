
package com.qpa.question_paper_analyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subjects")
public class Subject {

    @Id
    private String id;

    private String name;
    private String subjectCode;
    private int semester;
    private int totalUnits;

    public Subject() {
    }

    public Subject(String name, String subjectCode,
                   int semester, int totalUnits) {
        this.name = name;
        this.subjectCode = subjectCode;
        this.semester = semester;
        this.totalUnits = totalUnits;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public int getSemester() {
        return semester;
    }

    public int getTotalUnits() {
        return totalUnits;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setTotalUnits(int totalUnits) {
        this.totalUnits = totalUnits;
    }
}
