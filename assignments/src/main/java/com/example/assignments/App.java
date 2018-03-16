package com.example.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
@SuppressWarnings("restriction")
public class App extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO add exception handling

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root,400,400);
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
}
