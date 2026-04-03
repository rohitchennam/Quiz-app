package model;

import java.util.*;

public class Question {
    private String questionText;
    private String[] options;
    private int correctAnswer;

    public Question(String questionText, String[] options, int correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }

    // 🔥 Correct shuffle implementation
    public void shuffleOptions() {
        List<String> opts = new ArrayList<>(Arrays.asList(options));

        // store correct answer BEFORE shuffle
        String correct = options[correctAnswer];

        Collections.shuffle(opts);

        // convert back to array
        options = opts.toArray(new String[0]);

        // update correct answer index AFTER shuffle
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(correct)) {
                correctAnswer = i;
                break;
            }
        }
    }
}