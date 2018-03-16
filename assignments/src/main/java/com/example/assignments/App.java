package com.example.assignments;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is the main class of the simple application
 */
@SuppressWarnings("restriction")
public class App extends Application {
	@Override
	public void start(Stage stage) {

		try {
			URL url = getClass().getClassLoader().getResource("textView.fxml");
			VBox root = FXMLLoader.load(url);

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Drop File into the upper box");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
