package teste;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.swing.filechooser.FileSystemView;

import modelo.Empregado;
import modelo.SR;
import util.ExcelHelper;
import util.GeradorDeEmail;

public class Teste {

	public static void main(final String[] args) throws Exception {
		
		double inicio = System.currentTimeMillis();

		final FileSystemView system = FileSystemView.getFileSystemView();

		final InputStream arquivo = new FileInputStream(system.getHomeDirectory().getAbsolutePath() + File.separator + "planilha.xls");

		final ExcelHelper excel = new ExcelHelper(arquivo);

		final List<Empregado> todosEmpregados = excel.getListaDeEmpregados();

		final Set<SR> todasSR = excel.getListaDeSr();

		final GeradorDeEmail gerador = new GeradorDeEmail();

		gerador.geraEmail(todosEmpregados, todasSR, "10", "10/10/2016");
		
		System.out.println("Total: " + (System.currentTimeMillis() - inicio));

	}
}
