package com.lotto.lottogenerator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LottoApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LottoApplication.class.getResource("LottoApplication.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 670);
        stage.setTitle("로또 번호 생성기");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}