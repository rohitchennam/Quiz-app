package service;

import model.Question;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class QuestionLoader {

    public static List<Question> loadFromFile(String filename) {
        List<Question> questions = new ArrayList<>();

        try {
            String content = new String(
                java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(filename)
                )
            );

            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                String questionText = obj.getString("question");

                JSONArray optionsArray = obj.getJSONArray("options");
                String[] options = new String[optionsArray.length()];

                for (int j = 0; j < optionsArray.length(); j++) {
                    options[j] = optionsArray.getString(j);
                }

                int answer = obj.getInt("answer");

                questions.add(new Question(questionText, options, answer));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
}