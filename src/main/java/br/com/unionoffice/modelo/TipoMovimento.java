package br.com.unionoffice.modelo;

public enum TipoMovimento {
	CP("Contas a pagar"), NF("Nota fiscal");

	private String descricao;

	private TipoMovimento(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
