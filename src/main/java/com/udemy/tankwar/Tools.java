package com.udemy.tankwar;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tools {

    static Image getImage(String filename){
        return new ImageIcon("assets/images/"+filename).getImage();
    }

    static void playAudio(String filename) {
        Media sound = new Media(new File("assets/audios/" + filename).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
