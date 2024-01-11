package com.example.demo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Calendar;


public class HelloController {
    @FXML
    private Label timeLabel, eggTimeLabel;
    @FXML
    private Button startTimerButton, resetTimerButton, startEggButton, resetEggButton, hourButton, minuteButton, secondButton,
            hourUPButton, exitButton, minuteUPButton, secondUPButton;
    @FXML
    private ImageView startStopImageView, startStopEggImageView;

    public Image pauseImage=new Image(getClass().getResourceAsStream("iconpause.png"));
    public Image startImage=new Image(getClass().getResourceAsStream("iconplay.png"));
    private StrokeTransition strokeTransition;


    private int elapsedTime,elapsedTimeEgg,seconds, minutes,hours, secondsEgg,minutesEgg,hoursEgg=0;
    private int remaningTime= elapsedTime/1000;
    boolean started, startedEgg= false;
    String seconds_string,minutes_string,hours_string, seconds_stringEgg,minutes_stringEgg,hours_stringEgg;

    private Timeline timeLine, eggTimerTimeline;
    private Sound sound= new Sound();
    private Parent root;

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setRoot(Parent root) {
        this.root = root;
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
               if(pauseImage!=null) {
                   startStopImageView.setImage(pauseImage);
               }
                timeLine.play();
        } else {
                started = false;
                if (startImage != null) {
                startStopImageView.setImage(startImage);
                }

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
        startStopImageView.setImage(startImage);
        timeLine.stop();
    }

    @FXML
    protected void startStopEggTimer() {
        if (startedEgg == false && (hoursEgg+minutesEgg+secondsEgg!=0)) {
            startedEgg = true;
            if(pauseImage!=null) {
                startStopEggImageView.setImage(pauseImage);
            }
            eggTimerTimeline.play();
            //updateCircle();
            hideButtons();
        } else {
            startedEgg = false;
            if (startImage != null) {
                startStopEggImageView.setImage(startImage);
            }
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
                startStopEggImageView.setImage(startImage);
                showButtons();
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
    @FXML
    protected void subHour() {
        if (hoursEgg >= 1) {
            hoursEgg--;
            formatTimeEgg(eggTimeLabel);
            elapsedTimeEgg -= 3600000;
        }
    }
    @FXML
    protected void subMinutes() {
        if(minutesEgg>=1){
            minutesEgg--;
            elapsedTimeEgg-=60000;
        }
        formatTimeEgg(eggTimeLabel);

    }
    @FXML
    protected void subSecond() {
        if(secondsEgg>=1){
            secondsEgg--;
            System.out.println(elapsedTimeEgg);
            elapsedTimeEgg-=1000;
        }
        formatTimeEgg(eggTimeLabel);
    }


    @FXML
    public void handleExitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz zamknąć program?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            stage.close();
        }
    }

    private void hideButtons(){
        hourButton.setVisible(false);
        hourUPButton.setVisible(false);
        minuteButton.setVisible(false);
        minuteUPButton.setVisible(false);
        secondButton.setVisible(false);
        secondUPButton.setVisible(false);
    }
    private void showButtons(){
        hourButton.setVisible(true);
        hourUPButton.setVisible(true);
        minuteButton.setVisible(true);
        minuteUPButton.setVisible(true);
        secondButton.setVisible(true);
        secondUPButton.setVisible(true);
    }


}


