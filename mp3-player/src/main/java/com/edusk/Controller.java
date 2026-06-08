package com.edusk;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.beans.binding.Bindings;

public class Controller {

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label playerLabel;
    @FXML
    private ComboBox<Integer> speedSelect;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Button playPauseButton;

    private List<File> playlist;
    private int currentIndex;
    private MediaPlayer mediaPlayer;
    private Stage stage;

    public void selectFile() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Audio", "*.mp3", ".wav", ".webm"));
        File selectedFile = chooser.showOpenDialog(stage);
        if (selectedFile == null) {
            return;
        }

        File folder = selectedFile.getParentFile();

        File[] files = folder.listFiles(
                (dir, name) -> name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".wav"));
        playlist = Arrays.asList(files);
        playlist.sort((f1, f2) -> f1.getName().compareTo(f2.getName()));
        currentIndex = playlist.indexOf(selectedFile);
        setupPlayer();
    }

    private void setupPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.volumeProperty().unbind();
            mediaPlayer.rateProperty().unbind();
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        File selectedFile = playlist.get(currentIndex);
        mediaPlayer = new MediaPlayer(new Media(selectedFile.toURI().toString()));
        mediaPlayer.currentTimeProperty().addListener((observable, oldTime, newTime) -> {
            Duration total = mediaPlayer.getTotalDuration();
            if (total != null && total.toMillis() > 0) {
                double progress = newTime.toMillis() / total.toMillis();
                progressBar.setProgress(progress);
            }
        });
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100.0));
        mediaPlayer.rateProperty().bind(
                Bindings.createDoubleBinding(() -> {
                    Integer percent = speedSelect.getValue();
                    return (percent == null) ? 1.0 : (percent / 100.0);
                }, speedSelect.valueProperty()));
        mediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) -> {
            if (newStatus == MediaPlayer.Status.PLAYING) {
                playPauseButton.setText("⏸");
            } else {
                playPauseButton.setText("▶");
            }
        });
        mediaPlayer.setOnEndOfMedia(this::handleNext);
        playerLabel.setText(selectedFile.getName());

        mediaPlayer.play();
    }

    public void handlePlayPauseAction() {
        if (mediaPlayer == null)
            return;

        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
    }

    public void handleReset() {
        mediaPlayer.seek(Duration.seconds(0.0));
    }

    public void handleNext() {
        currentIndex = (currentIndex + 1) % playlist.size();
        setupPlayer();
    }

    public void handlePrevious() {
        currentIndex = Math.floorMod(currentIndex - 1, playlist.size());
        setupPlayer();
    }
}
