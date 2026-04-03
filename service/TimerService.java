package service;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TimerService {
    private int timeLeft;
    private Timer timer;

    public TimerService(int seconds, Runnable onTimeUp, Runnable onTick) {
        this.timeLeft = seconds;

        timer = new Timer(1000, e -> {
            timeLeft--;

            onTick.run(); // 🔥 update UI every second

            if (timeLeft <= 0) {
                timer.stop();
                onTimeUp.run();
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}