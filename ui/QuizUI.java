package ui;

import service.LeaderboardService;
import service.QuizService;
import service.TimerService;
import model.Question;
import model.Score;

import javax.swing.*;
import java.awt.*;
// import java.util.List;



public class QuizUI extends JFrame {
    private QuizService quizService;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup group;
    private JButton nextButton;
    private JLabel timerLabel;
    private TimerService timerService; 
    private String username;

    public QuizUI(QuizService quizService, String username) {
        this.quizService = quizService;
        this.username = username;

        setTitle("Quiz App");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ Create components ONCE
        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        timerLabel = new JLabel("Time: 30");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // ✅ Top panel (Question + Timer)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(questionLabel, BorderLayout.CENTER);
        topPanel.add(timerLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // ✅ Options
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        options = new JRadioButton[4];
        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            optionsPanel.add(options[i]);
        }

        add(optionsPanel, BorderLayout.CENTER);

        // ✅ Button
        nextButton = new JButton("Next");
        add(nextButton, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> handleNext());

        // Load first question
        loadQuestion();

        // ✅ Timer
        timerService = new TimerService(
            30,
            () -> SwingUtilities.invokeLater(this::finishQuiz),
            () -> SwingUtilities.invokeLater(() ->
                timerLabel.setText("Time: " + timerService.getTimeLeft()))
        );

        timerService.start();

        setVisible(true);
    }

    private void finishQuiz() {
        timerService.stop();

        int score = quizService.getScore();
        int total = quizService.getTotalQuestions();

        // ✅ Save score
        LeaderboardService leaderboard = new LeaderboardService();
        leaderboard.saveScore(new Score(username, score));

        dispose();

        new ResultUI(score, total);
    }

    private void loadQuestion() {
        Question q = quizService.getCurrentQuestion();
        questionLabel.setText(q.getQuestionText());

        String[] opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
            options[i].setText(opts[i]);
        }
        group.clearSelection();
    }

    private void handleNext() {
        int selected = -1;

        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selected = i;
                break;
            }
        }

        quizService.submitAnswer(selected);

        if (quizService.hasNext()) {
            loadQuestion();
        } else {
            timerService.stop(); // stop timer when finished
            finishQuiz();
        }
    }
}