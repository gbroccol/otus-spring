package ru.otus.spring.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.otus.spring.exception.AnswerOutOfBoundException;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.model.User;
import ru.otus.spring.service.impl.QuestionService;
import ru.otus.spring.util.Color;

import java.util.Collection;
import java.util.Scanner;

@Service
public class TestService implements DisposableBean {

    private final Scanner userInput = new Scanner(System.in);

    private final Integer minRightAnswers;
    private final QuestionService questionService;

    public TestService(@Value("${min.right.answers}") Integer minRightAnswers,
                       QuestionService questionService) {
        this.minRightAnswers = minRightAnswers;
        this.questionService = questionService;
    }

    public void testUser(@NonNull User user) {

        Collection<Question> questions = questionService.findAll();
        printTestRules(questions.size());

        try {
            int rightAnswersCount = 0;
            for (Question question : questions) {
                printQuestion(question);
                int answerNumber = getAnswer();
                rightAnswersCount += checkAnswerCorrect(question, answerNumber) ? 1 : 0;
            }
            printTestResult(user, rightAnswersCount);
        } catch (AnswerOutOfBoundException e) {
            IOService.outputString(Color.ANSI_RED + "Given number of answer is out of range. Please start test again" + Color.ANSI_RESET);
        } catch (NumberFormatException e) {
            IOService.outputString(Color.ANSI_RED + "Given answer is not a number. Please start test again" + Color.ANSI_RESET);
        }
    }

    private void printTestRules(int questionSize) {
        IOService.outputString(Color.ANSI_BLUE + "There are " + questionSize + " question(-s). You must answer at least " +
                minRightAnswers + " question(-s) to receive credit\n" + Color.ANSI_RESET);
    }

    private void printQuestion(Question question) {

        IOService.outputString(question.getQuestion());

        int answerNumber = 0;
        for (Answer answer : question.getAnswers()) {
            IOService.outputString(answerNumber++ + ". " + answer.getAnswer());
        }
    }

    private int getAnswer() {
        System.out.print("Enter the number of right option: ");
        return IOService.readInt(userInput);
    }

    private boolean checkAnswerCorrect(Question question, int answerNumber) throws AnswerOutOfBoundException {

        if (answerNumber < 0 || answerNumber > question.getAnswers().size() - 1)
            throw new AnswerOutOfBoundException("Given number of answer is out of range");
        
        if (question.getAnswers().get(answerNumber).getCorrect()) {
            IOService.outputString(Color.ANSI_GREEN + "OK\n" + Color.ANSI_RESET);
            return true;
        }
        IOService.outputString(Color.ANSI_RED + "ERROR\n" + Color.ANSI_RESET);
        return false;
    }

    private void printTestResult(User user, int rightAnswersCount) {

        IOService.outputString("================================================");

        if (rightAnswersCount >= minRightAnswers) {
            IOService.outputString(user.getFirstName() + " " + user.getLastName() + ", congratulations! Test passed!");
        }
        else {
            IOService.outputString(user.getFirstName() + " " + user.getLastName() + ", you have not passed this test :(");
        }
    }

    @Override
    public void destroy() {
        userInput.close();
    }
}