package com.quiz.boot.service;

import com.opencsv.exceptions.CsvException;
import com.quiz.boot.dao.QuestionDAO;
import com.quiz.boot.entity.Question;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{
    QuestionDAO dao;
    public QuestionServiceImpl(QuestionDAO dao)
    {
        this.dao = dao;
    }
    public List<Question> getAll() throws FileNotFoundException, IOException, CsvException {
        return dao.getAll();
    }
}
