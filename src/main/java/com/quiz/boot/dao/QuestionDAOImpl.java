package com.quiz.boot.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.quiz.boot.entity.Answer;
import com.quiz.boot.entity.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Repository
public class QuestionDAOImpl implements QuestionDAO, ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    private MessageSource messageSource;
    public void setResourceLoader(ResourceLoader loader)
    {
        this.resourceLoader = loader;
    }

    public QuestionDAOImpl(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    public List<Question> getAll() throws IOException, CsvException
    {
        List<Question> questions = new ArrayList<>();

        String filename = messageSource.getMessage("questions_filename",null, Locale.ENGLISH);
        File file  = resourceLoader.getResource(filename).getFile();

        try(CSVReader reader = new CSVReader(new FileReader(file)))
        {
            List<String[]> rows = reader.readAll();
            for(String[] row : rows)
            {
                String[] questionData = row[0].split(";");

                Question question = new Question();
                question.setQuestion(questionData[0]);

                List<String> rightAnswers = Arrays.asList(questionData[1].split("-"));

                for(int i = 1; i< row.length; i++)
                {
                    Answer answer = new Answer();
                    answer.setAnswer(row[i]);
                    question.addAnswer(answer);
                    if(rightAnswers.contains(String.valueOf(i))) question.addRightAnswer(answer);
                }
                questions.add(question);
            }
        }

        return questions;
    }
}
