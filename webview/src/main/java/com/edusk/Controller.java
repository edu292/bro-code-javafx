package com.edusk;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class Controller implements Initializable {
    @FXML
    private WebView webview;

    @FXML
    private TextField urlField;

    private WebEngine engine;
    private String homePage;
    private double webZoom;
    private WebHistory history;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        engine = webview.getEngine();
        homePage = "www.google.com";
        urlField.setText(homePage);
        webZoom = 1.0;

        loadPage();
    }

    public void loadPage() {
        // engine.load("https://www.google.com");
        engine.load("https://" + urlField.getText());
    }

    public void refreshPage() {
        engine.reload();
    }

    public void zoomIn() {
        webview.setZoom(webZoom += 0.25);
    }

    public void zoomOut() {
        webview.setZoom(webZoom -= 0.25);
    }

    public void displayHistory() {
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        for (WebHistory.Entry entry : entries) {
            // System.out.println(entry);
            System.out.println(entry.getUrl() + " " + entry.getLastVisitedDate());
        }
    }

    public void back() {
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(-1);
        urlField.setText(entries.get(history.getCurrentIndex()).getUrl());
    }

    public void forward() {
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(1);
        urlField.setText(entries.get(history.getCurrentIndex()).getUrl());
    }

    public void executeJS() {
        engine.executeScript("window.location = \"https://www.youtube.com\";");
    }

}
