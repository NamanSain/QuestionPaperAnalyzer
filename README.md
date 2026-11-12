# Question Paper Analyzer

## 1. Project Overview

Question Paper Analyzer is a Java-based backend application designed to analyze previous years' examination question papers.

The application allows users to upload PDF question papers, extract their text, identify individual questions, and store the extracted information in MongoDB.

It also provides analytical insights such as marks distribution, year-wise question analysis, repeated questions, and question frequency.

## 2. Technologies Used

- Java 25
- Spring Boot 4.1.2
- MongoDB
- Spring Data MongoDB
- Apache PDFBox
- Maven
- Postman

## 3. Key Features

1. Subject Management – Add and retrieve subjects.
2. PDF Upload – Upload question papers in PDF format.
3. Text Extraction – Extract text from uploaded PDFs.
4. Question Extraction – Identify and store individual questions.
5. Duplicate Prevention – Prevent duplicate question entries during repeated extraction of the same paper.
6. Marks Distribution – Analyze questions according to their marks.
7. Year-wise Analysis – Analyze question papers and extracted questions across examination years.
8. Repeated Question Analysis – Identify questions appearing across multiple papers.
9. Question Frequency – Calculate how frequently questions appear across different papers.

## 4. Project Setup

### Prerequisites

- Java JDK 25
- MongoDB
- Postman

### Steps

1. Clone or download the project.
2. Start your local MongoDB server.
3. Open the project directory in VS Code.
4. Run the following command:

`.\mvnw.cmd spring-boot:run`

5. The application starts on:

`http://localhost:8081`

## 5. API Documentation

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/subjects` | Add a subject |
| GET | `/api/subjects` | Retrieve subjects |
| POST | `/api/question-papers/upload` | Upload a PDF |
| GET | `/api/question-papers/extract-text/{fileName}` | Extract PDF text |
| GET | `/api/question-papers/extract-questions/{fileName}` | Extract questions |
| GET | `/api/analysis/marks/{subjectCode}/{examYear}` | Marks distribution |
| GET | `/api/analysis/years/{subjectCode}` | Year-wise analysis |
| GET | `/api/analysis/repeated/{subjectCode}` | Repeated questions |
| GET | `/api/analysis/frequency/{subjectCode}` | Question frequency |

## 6. Database

The project uses MongoDB with the database name:

`question_paper_analyzer`

Main collections:

- subjects
- question_papers
- questions

## 7. Future Enhancements

- OCR support for scanned question papers.
- Improved question detection using NLP.
- Automatic topic classification.
- Frontend dashboard for visualizing analysis.
- Advanced similarity detection for differently worded questions.

## 8. Conclusion

Question Paper Analyzer simplifies the process of analyzing previous examination papers by automating PDF text extraction, question storage, and statistical analysis. It provides a foundation for understanding examination patterns and identifying frequently asked questions.