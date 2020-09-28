package br.com.psg.cadastro.view;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		URL fxml = getClass().getResource("./cadastro.fxml");
		Parent parent = FXMLLoader.load(fxml);
		stage.setTitle("Cadastro de Pessoa");
		stage.setScene(new Scene(parent));
		stage.show();
	}

}