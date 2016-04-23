package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FormPrincipalFX extends Application {

	public static void main(final String[] args) {

		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {

		final Parent root = FXMLLoader.load(this.getClass().getResource("LayoutPrincipal.fxml"));

		stage.setResizable(false);

		final Scene scene = new Scene(root, 300, 200);

		stage.setTitle("Enviador de Emails");

		stage.setScene(scene);

		stage.show();

	}

}
