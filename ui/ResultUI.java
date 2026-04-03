package ui;

import javax.swing.*;
import java.awt.*;
import app.Main;
import service.LeaderboardService;
import model.Score;

public class ResultUI extends JFrame {

    public ResultUI(int score, int totalQuestions) {

        setTitle("Quiz Result");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🎯 Calculate percentage
        double percentage = (score * 100.0) / totalQuestions;

        // 🔥 Title
        JLabel title = new JLabel("Quiz Completed!");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        add(title, BorderLayout.NORTH);

        // ✅ Result Text
        JLabel resultLabel = new JLabel(
            "Your Score: " + score + " / " + totalQuestions +
            " (" + String.format("%.2f", percentage) + "%)"
        );
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // 🎨 Pass / Fail Color
        if (percentage >= 50) {
            resultLabel.setForeground(Color.GREEN);
        } else {
            resultLabel.setForeground(Color.RED);
        }
        
        // ✅ Leaderboard
        LeaderboardService leaderboard = new LeaderboardService();
        java.util.List<Score> topScores = leaderboard.getTopScores(5);

        StringBuilder boardText = new StringBuilder("<html><center>Top Scores:<br>");

        for (Score s : topScores) {
            boardText.append(s.getUsername())
                    .append(" - ")
                    .append(s.getScore())
                    .append("<br>");
        }

        boardText.append("</center></html>");

        JLabel leaderboardLabel = new JLabel(boardText.toString());
        leaderboardLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // ✅ Center Panel (IMPORTANT)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(resultLabel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(leaderboardLabel);

        add(centerPanel, BorderLayout.CENTER);
        // 🔁 Retry Button
        JButton retryButton = new JButton("Retry Quiz");

        retryButton.addActionListener(e -> {
            dispose();
            Main.startQuiz();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(retryButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}