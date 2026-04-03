package model;

import java.io.Serializable;

public class Score implements Serializable {
    private String username;
    private int score;

    public Score(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}