package br.com.unionoffice.modelo;

public enum Situacao {
	ABERTO("Aberto"), PENDENTE("Pendente"), LIQUIDADO("Liquidado");

	private String descricao;

	private Situacao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
