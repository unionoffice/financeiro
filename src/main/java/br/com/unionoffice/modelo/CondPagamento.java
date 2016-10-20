package br.com.unionoffice.modelo;

public enum CondPagamento {
	_10DDF("10 DDF", 1, 10),
	_15DDF("15 DDF", 1, 15),
	_28DDF("28 DDF", 1, 28),
	_30DDF("30 DDF", 1, 30),
	_56DDF("56 DDF", 1, 56),
	_28_56DDF("28 / 56 DDF", 2, 28), 
	_30_60DDF("30 / 60 DDF", 2, 30), 
	_28_56_84DDF("28 / 56 / 84 DDF", 3, 28), 
	_30_60_90("30 / 60 / 90 DDF", 3, 30), 
	_30DDF_10("30 DDF", 10, 30), 
	_30DDF_12("30 DDF", 12, 30);

	private String descricao;
	public int qtdParcelas;
	public int dias;

	private CondPagamento(String descricao, int qtdParcelas, int dias) {
		this.descricao = descricao;
		this.qtdParcelas = qtdParcelas;
		this.dias = dias;
	}

	@Override
	public String toString() {
		String retorno = String.format("%d parcela(s) %s", qtdParcelas, descricao);
		return retorno;
	}
}
