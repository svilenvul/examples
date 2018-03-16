package com.example.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.net.URL;

/**
 * Hello world!
 *
 */
@SuppressWarnings("restriction")
public class App extends Application{
	@Override
	  public void start(Stage stage) {
		
	    try {
	      URL url = 
	    	getClass().getClassLoader()
	          .getResource("textView.fxml");
	      VBox root = FXMLLoader.load(url);

	      Scene scene = new Scene(root);
	      stage.setScene(scene);
	      stage.setTitle("Drop File Into the upper box");
	      stage.show();
	    } catch(Exception e) {
	      e.printStackTrace();
	    } 
	  }
	  
	  public static void main(String[] args) {
	    launch(args);
	  }
}
