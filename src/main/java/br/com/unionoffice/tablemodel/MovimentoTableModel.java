package br.com.unionoffice.tablemodel;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.unionoffice.modelo.Movimento;
import br.com.unionoffice.modelo.Situacao;

public class MovimentoTableModel extends AbstractTableModel {
	public List<Movimento> movimentos;
	private String[] COLUNAS = { "EMISSÃO", "VALOR", "QTD_PARC", "PARCELA", "VENCIMENTO", "REFERÊNCIA", "EMITENTE",
			"NUM", "SITUAÇÃO", "COMPROV", "OBSERVAÇÃO" };

	public MovimentoTableModel(List<Movimento> movimentos) {
		this.movimentos = movimentos;

	}
	
	public void refreshAdd() {		
		fireTableRowsInserted(0, getRowCount());
	}
	
	public void refreshDelete() {		
		fireTableRowsDeleted(0, getRowCount()+1);
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
			if(m.getSituacao() == Situacao.LIQUIDADO && m.getDataLiquidacao() != null){
				return m.getSituacao() + " - " + formatador.format(m.getDataLiquidacao().getTime());
			}
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

	public Color getRowColor(int row) {
		Movimento m = movimentos.get(row);
		switch (m.getSituacao()) {
		case ABERTO:
			return new Color(255, 102, 102);
		case AGUARDANDO_BOLETO:
			return new Color(255, 255, 153);
		case LIQUIDADO:
			return new Color(204, 255, 153);
		default:
			return Color.white;
		}
	}
		

}
