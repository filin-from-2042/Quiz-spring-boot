package com.quiz.boot;

import com.opencsv.exceptions.CsvException;
import com.quiz.boot.entity.Answer;
import com.quiz.boot.entity.Question;
import com.quiz.boot.service.QuestionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Component
public class Quiz {
    QuestionService service;
    Scanner scanner = new Scanner(System.in);

    @Value("${passing_score}")
    private int passingScore;

    public Quiz(QuestionService service)
    {
        this.service = service;
    }

    public void start()
    {
        Map<String,List<Answer>> resultUserAnswers = new LinkedHashMap<>();
        int score = 0;
        try {
            List<Question> questions = service.getAll();
            for(Question question : questions)
            {
                showQuestion(question);
                List<Answer> currentUserAnswers = getAnswersFromUser(question);

                List<Answer> rightAnswers = question.getRightAnswers();
                if(rightAnswers.containsAll(currentUserAnswers) && currentUserAnswers.containsAll(rightAnswers)) score++;

                resultUserAnswers.put(question.getQuestion(), currentUserAnswers);
            }
            System.out.printf("You got %d scores\n", score);
            System.out.println(resultUserAnswers);

            if(score >= passingScore) System.out.println("You passed!");
            else System.out.println("You failed!");
        }
        catch (FileNotFoundException exception)
        {
            System.out.println("File not found!");
        }
        catch(IOException exception)
        {
            System.out.println("Opening file problem");
        }
        catch (CsvException exception )
        {
            System.out.println("Reading csv file exception");
        }
    }

    private void showQuestion(Question question)
    {
        System.out.println(question.getQuestion());
        List<Answer> answers = question.getAnswers();
        for (int i = 1; i<=answers.size(); i++)
        {
            System.out.printf("%d. %s\n",i,answers.get(i-1).getAnswer());
        }
    }

    private List<Answer> getAnswersFromUser(Question question)
    {
        List<Answer> allAnswers = question.getAnswers();
        List<Answer> answers = new ArrayList<>();
        while(answers.size()==0)
        {
            System.out.println("Enter your answer(s) comma separated");
            String userInput = scanner.nextLine();
            try {
                 String[] inputtedNumbers = userInput.split(",");
                for(String number : inputtedNumbers)
                {
                    int answerIndex = Integer.parseInt(number)-1;
                    answers.add(allAnswers.get(answerIndex));
                }
            } catch (NumberFormatException | IndexOutOfBoundsException exception) {
                System.out.println("You entered incorrect answer number!");
            }
        }
        return answers;
    }
}
