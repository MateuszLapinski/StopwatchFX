package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;


public class HelloController {
    @FXML
    private Label timeLabel, eggTimeLabel;
    @FXML
    private Button startTimerButton, resetTimerButton, startEggButton, resetEggButton, hourButton, minuteButton, secondButton, exitButton;
    //String iconStartPath="/com/example/demo/iconplay.png";
    //public Image startImage=new Image(iconStartPath);
//    public Image pauseImage=new Image("/iconpause.png");

    @FXML
    private ImageView startStopImageView;

    private int elapsedTime,elapsedTimeEgg,seconds, minutes,hours, secondsEgg,minutesEgg,hoursEgg=0;
    boolean started, startedEgg= false;
    String seconds_string,minutes_string,hours_string, seconds_stringEgg,minutes_stringEgg,hours_stringEgg;

    private Timeline timeLine, eggTimerTimeline;
    private Sound sound= new Sound();

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {

//        startStopImageView.setImage(startImage);
        timeLine = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTimer));
        timeLine.setCycleCount(Timeline.INDEFINITE);

        eggTimerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateEggTimer));
        eggTimerTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    @FXML
    protected void startStopTimer() {


        if (started == false) {
                started = true;
//                if(pauseImage!=null) {
//                    startStopImageView.setImage(pauseImage);
//                }
                timeLine.play();
        } else {
                started = false;
//                if (startImage != null) {
//                startStopImageView.setImage(startImage);
//                }

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
                sound.play();
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

    protected void getSound(){
        for(int i=0; i<=3;i++){
            sound.play();
        }
        sound.stop();
    }

    @FXML
    public void handleExitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz zamknąć program?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            stage.close();
        }
    }

}


