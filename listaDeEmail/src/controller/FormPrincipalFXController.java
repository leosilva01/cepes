package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.FileChooser;
import jidefx.scene.control.field.DateField;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;
import util.ExcelHelper;
import util.GeradorDeEmail;

public class FormPrincipalFXController {

	@FXML
	private FormattedTextField<String> formattedTextFieldTurma;

	@FXML
	private DateField dateFieldData;

	@FXML
	private ProgressIndicator progressIndicator;

	@FXML
	private Button btSelecionarPlanilha;

	@FXML
	public void onSelecionaPlanilha() {

		final File arquivo = this.selecionaArquivo();

		if (arquivo != null) {

			try {
				final InputStream inputStream = new FileInputStream(arquivo.getAbsolutePath());

				final ExcelHelper excel = new ExcelHelper(inputStream);

				final Task<Void> task = GeradorDeEmail.geraEmail(excel.getListaDeEmpregados(), excel.getListaDeSr(), this.formattedTextFieldTurma.getText(), this.dateFieldData.getText());

				this.progressIndicator.progressProperty().bind(task.progressProperty());

				this.btSelecionarPlanilha.disableProperty().bind(task.runningProperty());

				new Thread(task).start();

			} catch (final Exception e) {

				e.printStackTrace();

				this.mostraMensagem(AlertType.ERROR, e.getMessage()); // TODO nao chega a mensagem de erro

			}
		}

	}

	public void initialize() {

		// Colocando a mascara no campo turma para só permitir numeros de 0 a 999
		this.formattedTextFieldTurma.setPattern("nome_mascara");

		this.formattedTextFieldTurma.getPatternVerifiers().put("nome_mascara", new IntegerRangePatternVerifier(0, 999));

	}

	private File selecionaArquivo() {

		final FileChooser fileChooser = new FileChooser();

		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Planilha Excel 2003 (.xls)", "*.xls"));

		return fileChooser.showOpenDialog(null); // retorna arquivo selecionado.
	}

	private void mostraMensagem(final AlertType tipo, final String mensagem) {

		final Alert alerta = new Alert(tipo);

		alerta.setContentText(mensagem);

		alerta.show();
	}
}
