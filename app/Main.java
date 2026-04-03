package app;

import model.Question;
import service.QuestionLoader;
import service.QuizService;
import ui.QuizUI;

import java.util.*;

import javax.swing.JOptionPane;

public class Main {

    public static void startQuiz() {

        List<Question> questions =
            QuestionLoader.loadFromFile("questions.json");

        Collections.shuffle(questions);

        QuizService quizService = new QuizService(questions);

        String username = JOptionPane.showInputDialog("Enter your name:");

        new QuizUI(quizService, username);
    }
    public static void main(String[] args) {
        startQuiz();
    }
}