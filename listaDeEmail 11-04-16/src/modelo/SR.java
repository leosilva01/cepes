package modelo;

public class SR {

	private String codigo;

	private String unidade;

	private String emailSR;

	public SR( String codigo, String unidade, String emailSR ) {
		this.codigo = codigo.trim().toUpperCase();
		this.unidade = unidade.trim().toUpperCase();
		this.emailSR = emailSR.trim().toUpperCase();
	}

	public String getEmailSR() {

		return emailSR;
	}

	public String getCodigo() {

		return codigo;
	}

	public String getUnidade() {

		return unidade;
	}

	public void setCodigo(String codigo) {

		this.codigo = codigo;
	}

	public void setUnidade(String unidade) {

		this.unidade = unidade;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SR other = (SR) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
