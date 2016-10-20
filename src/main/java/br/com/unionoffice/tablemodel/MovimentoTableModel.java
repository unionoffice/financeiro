package br.com.unionoffice.tablemodel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.unionoffice.modelo.Movimento;

public class MovimentoTableModel extends AbstractTableModel {
	private List<Movimento> movimentos;
	private String[] COLUNAS = { "EMISSÃO", "VALOR", "QTD_PARC", "PARCELA", "VENCIMENTO", "REFERÊNCIA", "EMITENTE","NUM",
			"SITUAÇÃO", "COMPROV", "OBSERVAÇÃO" };

	public MovimentoTableModel(List<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

	@Override
	public int getRowCount() {
		return movimentos.size();
	}

	@Override
	public int getColumnCount() {
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		Movimento m = movimentos.get(rowIndex);
		switch (columnIndex) {
		case 0:			
			return formatador.format(m.getData().getTime());
		case 1:
			return NumberFormat.getCurrencyInstance().format(m.getValorParcela().doubleValue());
		case 2:
			return m.getCondPagamento().qtdParcelas;
		case 3:
			return m.getNumParcela();
		case 4:
			return formatador.format(m.getVencimento().getTime());
		case 5:
			return m.getReferencia();
		case 6:
			return m.getEmitente();
		case 7:
			return m.getNumero();
		case 8:
			return m.getSituacao();
		case 9:
			return m.getComprovante();
		case 10:
			return m.getObservacao();
		}
		return null;
	}



	@Override
	public String getColumnName(int column) {
		return COLUNAS[column];
	}

}
