package com.quiz.boot.service;

import com.opencsv.exceptions.CsvException;
import com.quiz.boot.entity.Question;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface QuestionService {
    List<Question> getAll() throws FileNotFoundException, IOException, CsvException;
}
