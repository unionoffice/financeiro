package br.com.unionoffice.modelo;

public enum Situacao {
	ABERTO("Aberto"), LIQUIDADO("Liquidado"), AGUARDANDO_BOLETO("Aguardando boleto"), DEVOLVIDO(
			"Devolvido"), PROV_DEVOLUCAO("Prov�vel devolu��o");

	private String descricao;

	private Situacao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
