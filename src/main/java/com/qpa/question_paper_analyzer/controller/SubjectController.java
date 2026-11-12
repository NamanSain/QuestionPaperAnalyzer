
package com.qpa.question_paper_analyzer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.qpa.question_paper_analyzer.model.Subject;
import com.qpa.question_paper_analyzer.repository.SubjectRepository;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @PostMapping
    public Subject addSubject(@RequestBody Subject subject) {

        if (subjectRepository.existsBySubjectCode(subject.getSubjectCode())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Subject code already exists"
            );
        }

        return subjectRepository.save(subject);
    }

    @GetMapping("/test")
    public String test() {
        return "Subject Controller is working!";
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
}
