package view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import util.ExcelHelper;
import util.GeradorDeEmail;

public class FormPrincipal extends JFrame implements ActionListener {

	/** Atributo serialVersionUID. */
	private static final long serialVersionUID = 5124024911804975446L;

	private InputStream inputStream;

	private final JButton botaoSelecionaArquivo = new JButton("Selecione a planilha");

	private final JFormattedTextField textFieldDataTurma = new JFormattedTextField();

	private final JFormattedTextField textFieldTurma = new JFormattedTextField();

	public FormPrincipal() {
		super("Enviar E-mail SR");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.montaLayout();
		this.setSize(400, 200);
		this.setVisible(true);
		this.setResizable(false);
		this.getRootPane().setDefaultButton(botaoSelecionaArquivo);
	}

	private void montaLayout() {

		final Container container = this.getContentPane();

		final FlowLayout layout = new FlowLayout(FlowLayout.LEFT);

		container.setLayout(layout);

		this.botaoSelecionaArquivo.addActionListener(this);

		final JLabel labelTurma = new JLabel("Turma: ");
		this.getContentPane().add(labelTurma);

		this.getContentPane().add(this.textFieldTurma);
		this.textFieldTurma.setColumns(5);

		final JLabel labelDataTurma = new JLabel("Data da Turma: ");
		this.getContentPane().add(labelDataTurma);

		this.getContentPane().add(this.textFieldDataTurma);
		this.textFieldDataTurma.setColumns(6);

		container.add(this.botaoSelecionaArquivo);

		final MaskFormatter mascaraData = new MaskFormatter();
		final String data = "##/##/####";
		try {
			mascaraData.setMask(data);
			mascaraData.install(this.textFieldDataTurma);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		final MaskFormatter mascaraTurma = new MaskFormatter();
		final String Turma = "###";
		try {
			mascaraTurma.setMask(Turma);
			mascaraTurma.install(this.textFieldTurma);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}

	private void enviaEmail() {

		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "desktop"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Planilha Excel 2003 (.xls)", "xls"));

		final int res = fileChooser.showOpenDialog(null);

		if (res == JFileChooser.APPROVE_OPTION) {
			final File arquivo = fileChooser.getSelectedFile();

			try {
				this.inputStream = new FileInputStream(arquivo.getAbsolutePath());
				fileChooser.setAcceptAllFileFilterUsed(false);
				final ExcelHelper excel = new ExcelHelper(this.inputStream);
				final GeradorDeEmail gerador = new GeradorDeEmail();

				gerador.geraEmail(excel.getListaDeEmpregados(), excel.getListaDeSr(), this.textFieldTurma.getText(), this.textFieldDataTurma.getText());

				JOptionPane.showMessageDialog(null, "Mensagens enviadas com sucesso.");
			} catch (final Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		if (e.getSource() == this.botaoSelecionaArquivo) {
			this.enviaEmail();
		}
	}

}
