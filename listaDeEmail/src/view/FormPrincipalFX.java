package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FormPrincipalFX extends Application {

	public static void main(String[] args) {
		
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("LayoutPrincipal.fxml"));

		Scene scene = new Scene(root, 400, 200);
		
		stage.setTitle("Enviador de Emails");
		
		stage.setScene(scene);
		
		stage.show();

	}

}
