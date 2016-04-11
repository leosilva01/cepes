package modelo;

import java.util.Date;

public class Empregado {

	private final SR sr;

	private final String nome;

	private final String matricula;

	private final String tipoCertificacao;

	private final Date dataVencimento;

	public Empregado( final SR sr, final String nome, final String matricula, final String tipoCertificacao, final Date dataVencimento ) {
		this.sr = sr;
		this.nome = nome.trim().toUpperCase();
		this.matricula = matricula.trim().toUpperCase();
		this.tipoCertificacao = tipoCertificacao.trim().toUpperCase();
		this.dataVencimento = dataVencimento;

	}

	public Date getDataVencimento() {

		return this.dataVencimento;
	}

	public SR getSr() {

		return this.sr;
	}

	public String getNome() {

		return this.nome;
	}

	public String getMatricula() {

		return this.matricula;
	}

	public String getTipoCertificacao() {

		return this.tipoCertificacao;
	}
}
