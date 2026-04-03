package service;

import model.Score;

import java.io.*;
import java.util.*;

public class LeaderboardService {

    private static final String FILE_NAME = "leaderboard.dat";

    // ✅ Save score
    public void saveScore(Score newScore) {
        List<Score> scores = loadScores();
        scores.add(newScore);

        try (ObjectOutputStream out =
                 new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ Load scores
    public List<Score> loadScores() {
        File file = new File(FILE_NAME);

        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in =
                 new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Score>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // ✅ Get top scores
    public List<Score> getTopScores(int n) {
        List<Score> scores = loadScores();

        scores.sort((a, b) -> b.getScore() - a.getScore()); // descending

        return scores.subList(0, Math.min(n, scores.size()));
    }
}