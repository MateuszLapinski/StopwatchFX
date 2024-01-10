package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class HelloController {
    @FXML
    private Label timeLabel, eggTimeLabel;
    @FXML
    private Button startTimerButton, resetTimerButton, startEggButton, resetEggButton, hourButton, minuteButton, secondButton;
    private int elapsedTime,elapsedTimeEgg,seconds, minutes,hours, secondsEgg,minutesEgg,hoursEgg=0;
    boolean started, startedEgg= false;
    String seconds_string,minutes_string,hours_string, seconds_stringEgg,minutes_stringEgg,hours_stringEgg;

    private Timeline timeLine, eggTimerTimeline;

    @FXML
    public void initialize() {
        timeLine = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTimer));
        timeLine.setCycleCount(Timeline.INDEFINITE);

        eggTimerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateEggTimer));
        eggTimerTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    protected void startStopTimer() {
        if (started == false) {
                started = true;
                startTimerButton.setText("Stop");
                timeLine.play();
        } else {
                started = false;
            startTimerButton.setText("Start");
                timeLine.stop();
        }
    }
    @FXML
    private void resetTimer() {
        timeLine.stop();
        elapsedTime=0;
        seconds=0;
        minutes=0;
        hours=0;
        formatTime(timeLabel);
        started=false;
        startTimerButton.setText("Start");
        timeLine.stop();
    }

    @FXML
    protected void startStopEggTimer() {
        if (startedEgg == false) {
            startedEgg = true;
            startEggButton.setText("Pause");
            eggTimerTimeline.play();
        } else {
            startedEgg = false;
            startEggButton.setText("Start");
            eggTimerTimeline.stop();
        }
    }

    private void updateTimer(ActionEvent event) {
        elapsedTime += 1000;
        hours = elapsedTime / 3600000;
        minutes = (elapsedTime / 60000) % 60;
        seconds = (elapsedTime / 1000) % 60;
        formatTime(timeLabel);
    }
    private void updateEggTimer(ActionEvent event) {
        if(elapsedTimeEgg>0){
            elapsedTimeEgg -= 1000;
            hoursEgg = elapsedTimeEgg / 3600000;
            minutesEgg = (elapsedTimeEgg / 60000) % 60;
            secondsEgg = (elapsedTimeEgg / 1000) % 60;
            if(elapsedTimeEgg==0){
                eggTimerTimeline.stop();
                startedEgg = false;
                startEggButton.setText("Start");
            }
            formatTimeEgg(eggTimeLabel);
        }
    }
    private void formatTime(Label label){
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        hours_string = String.format("%02d", hours);
        timeLabel.setText(hours_string+":"+minutes_string+":"+seconds_string);
    }

    private void formatTimeEgg(Label label){
        seconds_stringEgg = String.format("%02d", secondsEgg);
        minutes_stringEgg = String.format("%02d", minutesEgg);
        hours_stringEgg = String.format("%02d", hoursEgg);
        eggTimeLabel.setText(hours_stringEgg+":"+minutes_stringEgg+":"+seconds_stringEgg);
    }

    @FXML
    protected void addHour(ActionEvent e) {
        hoursEgg++;
        formatTimeEgg(eggTimeLabel);
        elapsedTimeEgg+=3600000;
    }
    @FXML
    protected void addMinutes(ActionEvent e) {
        if(minutesEgg<59){
            minutesEgg++;
            elapsedTimeEgg+=60000;
        }
        formatTimeEgg(eggTimeLabel);

    }
    @FXML
    protected void addSecond(ActionEvent e) {
        if(secondsEgg<59){
            secondsEgg++;
            System.out.println(elapsedTimeEgg);
            elapsedTimeEgg+=1000;
        }
        formatTimeEgg(eggTimeLabel);
    }

}


