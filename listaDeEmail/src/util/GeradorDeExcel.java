package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import modelo.Empregado;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class GeradorDeExcel {

	private final List<Empregado> empregados;

	public GeradorDeExcel( final List<Empregado> empregados ) {
		this.empregados = empregados;
	}


	public void geraExcel() throws Exception {
		

		final FileSystemView system = FileSystemView.getFileSystemView();

		final OutputStream arquivoExcel = new FileOutputStream(system.getHomeDirectory().getAbsolutePath() + File.separator + "emails nao enviados.xls");
		
		this.geraPlanilha(this.empregados, arquivoExcel);

		arquivoExcel.close();
		
		
	}

	private void geraPlanilha(final List<Empregado> empregados, OutputStream arquivo) {

		final HSSFWorkbook workbookSaida = new HSSFWorkbook();
		
		final HSSFSheet sheet = workbookSaida.createSheet("emails nao enviados");
		
		int l = 0;

		for (final Empregado empregado : empregados) {

			final Row linha = sheet.createRow(l);

			final Cell SR = linha.createCell(0);

			final Cell unidade = linha.createCell(1);

			final Cell nome = linha.createCell(2);

			final Cell matricula = linha.createCell(4);

			final Cell dataVencimento = linha.createCell(6);

			final Cell tipoCertificacao = linha.createCell(7);

			SR.setCellValue(empregado.getSr().getCodigo());

			unidade.setCellValue(empregado.getSr().getUnidade());

			nome.setCellValue(empregado.getNome());

			matricula.setCellValue(empregado.getMatricula());

			dataVencimento.setCellValue(empregado.getDataVencimento());

			tipoCertificacao.setCellValue(empregado.getTipoCertificacao());

			sheet.autoSizeColumn(l);

			l++;
		}
		
		try {
			workbookSaida.write(arquivo);

			workbookSaida.close();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
}
