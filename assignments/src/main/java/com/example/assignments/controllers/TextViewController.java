package com.example.assignments.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.assignments.model.Result;
import com.example.assignments.service.FileProcessorService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

@SuppressWarnings("restriction")
public class TextViewController {

	FileProcessorService service = new FileProcessorService();

	private ObservableList<Result> results = FXCollections.observableArrayList();
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	// The table and columns
	@FXML
	TableView<Result> table;

	@FXML
	TableColumn<Result, Integer> firstEmpIdCol;
	@FXML
	TableColumn<Result, Integer> secondEmpIdCol;
	@FXML
	TableColumn<Result, String> projectIdCol;
	@FXML
	TableColumn<Result, Long> daysWorkedCol;

	@FXML // fx:id="myTextArea"
	private TextArea myTextArea;

	@FXML
	void draggingOver(DragEvent event) {
		Dragboard board = event.getDragboard();
		if (board.hasFiles()) {
			event.acceptTransferModes(TransferMode.ANY);
		}

	}

	@FXML
	void dropping(DragEvent event) {
		Dragboard board = event.getDragboard();
		List<File> files = board.getFiles();

		File file = files.get(0);

		Result result = service.processFiles(file).get(0);

		results.add(result);
		
	}

	@FXML
	void initialize() {
		firstEmpIdCol.setCellValueFactory(new PropertyValueFactory<Result,Integer>("firstEmployeeID"));
		secondEmpIdCol.setCellValueFactory(new PropertyValueFactory<Result,Integer>("secondEmployeeID"));
		projectIdCol.setCellValueFactory(new PropertyValueFactory<Result,String>("projects"));
		daysWorkedCol.setCellValueFactory(new PropertyValueFactory<Result,Long>("period"));
		table.setItems(results);
	}
}
