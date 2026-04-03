package service;

import model.Question;
import java.util.*;

public class QuizService {
    private List<Question> questions;
    private int score = 0;
    private int currentIndex = 0;

    public QuizService(List<Question> questions) {
        this.questions = questions;
    }

    public Question getCurrentQuestion() {
        return questions.get(currentIndex);
    }

    public void submitAnswer(int answer) {
        if (answer == questions.get(currentIndex).getCorrectAnswer()) {
            score++;
        }
        currentIndex++;
    }

    public boolean hasNext() {
        return currentIndex < questions.size();
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}