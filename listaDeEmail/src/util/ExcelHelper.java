package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import modelo.ChaveValor;
import modelo.Empregado;
import modelo.SR;

public class ExcelHelper {

	private static final String CAIXA_GOV_BR = "@caixa.gov.br";

	private static final String SR = "SR";

	private final HSSFWorkbook workbook;

	private final Set<SR> listaDeSr = new HashSet<>();

	public ExcelHelper( final InputStream inputStream ) throws IOException {

		this.workbook = new HSSFWorkbook(inputStream);

	}

	public List<Empregado> getListaDeEmpregados() {

		final List<Empregado> empregados = new ArrayList<>();

		final HSSFSheet sheet = this.workbook.getSheetAt(0);

		final int totalDeLinhas = sheet.getLastRowNum();

		for (int i = 0; i <= totalDeLinhas; i++) {

			final String nome = this.recuperarNome(sheet, i);

			final String matricula = this.recuperarMatricula(sheet, i);

			final String tipoCertificacao = this.recuperarTipoCertificado(sheet, i);

			final String srUnidade = this.recuperarUnidade(sheet, i);

			final ChaveValor<String, String> chaveValor = this.recuperarSREmail(sheet, i, srUnidade);// Retorna Chave=C�digo; Valor=Email

			if (chaveValor == null || chaveValor.invalida()) {

				throw new IllegalStateException("C�digo da SR ou Email est�o nulos.");
			}

			final Date dataVencimento = sheet.getRow(i).getCell(6).getDateCellValue();

			final SR sr = new SR(chaveValor.getChave(), srUnidade, chaveValor.getValor());

			final Empregado empregado = new Empregado(sr, nome, matricula, tipoCertificacao, dataVencimento);

			empregados.add(empregado);

			this.listaDeSr.add(sr);
		}

		return empregados;
	}

	private ChaveValor<String, String> recuperarSREmail(final HSSFSheet sheet, final int i, final String srUnidade) {

		final ChaveValor<String, String> retorno = new ChaveValor<>();

		if (sheet.getRow(i).getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {

			final Double srEmDecimal = sheet.getRow(i).getCell(0).getNumericCellValue();

			retorno.setChave(Integer.toString(srEmDecimal.intValue()));

			retorno.setValor(ExcelHelper.SR + retorno.getChave() + srUnidade.substring(srUnidade.length() - 2) + ExcelHelper.CAIXA_GOV_BR);

		} else if (sheet.getRow(i).getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {

			retorno.setChave(sheet.getRow(i).getCell(0).getStringCellValue());

			retorno.setValor(retorno.getChave().concat(ExcelHelper.CAIXA_GOV_BR));
		}

		return retorno;
	}

	private String recuperarUnidade(final HSSFSheet sheet, final int i) {

		final String srUnidade = sheet.getRow(i).getCell(1).getStringCellValue();
		return srUnidade;
	}

	private String recuperarTipoCertificado(final HSSFSheet sheet, final int i) {

		final String tipoCertificacao = sheet.getRow(i).getCell(7).getStringCellValue();
		return tipoCertificacao;
	}

	private String recuperarMatricula(final HSSFSheet sheet, final int i) {

		return sheet.getRow(i).getCell(4).getStringCellValue();
	}

	private String recuperarNome(final HSSFSheet sheet, final int i) {

		return sheet.getRow(i).getCell(2).getStringCellValue();
	}

	public Set<SR> getListaDeSr() {

		return this.listaDeSr;
	}

}
