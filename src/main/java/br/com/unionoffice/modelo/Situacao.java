package br.com.unionoffice.modelo;

public enum Situacao {
	ABERTO("Aberto"), LIQUIDADO("Liquidado"), PENDENTE("Pendente");

	private String descricao;

	private Situacao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
