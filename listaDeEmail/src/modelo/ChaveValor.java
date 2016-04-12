package modelo;

public class ChaveValor<T, V> {

	private T chave;

	private V valor;

	public T getChave() {

		return this.chave;
	}

	public void setChave(final T chave) {

		this.chave = chave;
	}

	public V getValor() {

		return this.valor;
	}

	public void setValor(final V valor) {

		this.valor = valor;
	}

	public boolean invalida() {

		return chave == null || valor == null;
	}

}
