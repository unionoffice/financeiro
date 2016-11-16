package br.com.unionoffice.tablemodel;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.unionoffice.dao.MovimentoDao;
import br.com.unionoffice.modelo.Movimento;
import br.com.unionoffice.modelo.Situacao;

public class MovimentoTableModel extends AbstractTableModel {
	private List<Movimento> movimentos;
	private String[] COLUNAS = { "EMISSÃO", "VALOR", "QTD_PARC", "PARCELA", "VENCIMENTO", "REFERÊNCIA", "EMITENTE",
			"NUM", "OK", "SITUAÇÃO", "COMPROV", "OBSERVAÇÃO" };

	public MovimentoTableModel(List<Movimento> movimentos) {
		setMovimentos(movimentos);

	}

	public void setMovimentos(List<Movimento> movimentos) {
		//Collections.sort(movimentos);
		this.movimentos = movimentos;
	}

	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void refreshAdd() {
		fireTableRowsInserted(0, getRowCount());
	}

	public void refreshDelete() {
		fireTableRowsDeleted(0, getRowCount() + 1);
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
		SimpleDateFormat formatador;
		Movimento m = movimentos.get(rowIndex);
		switch (columnIndex) {
		case 0:
			formatador = new SimpleDateFormat("dd/MM/yyyy");
			return formatador.format(m.getData().getTime());
		case 1:
			return NumberFormat.getCurrencyInstance().format(m.getValorParcela().doubleValue());
		case 2:
			return m.getCondPagamento().qtdParcelas;
		case 3:
			return m.getNumParcela();
		case 4:
			formatador = new SimpleDateFormat("dd/MM/yyyy");
			return formatador.format(m.getVencimento().getTime());
		case 5:
			String prefixo = m.getTipoMovimento().name();
			return prefixo + " " + m.getReferencia();
		case 6:
			return m.getEmitente();
		case 7:
			return m.getNumero();
		case 8:
			return m.isAceite();
		case 9:
			if (m.getSituacao() == Situacao.LIQUIDADO && m.getDataLiquidacao() != null) {
				formatador = new SimpleDateFormat("dd/MM/yyyy");
				return m.getSituacao() + " - " + formatador.format(m.getDataLiquidacao().getTime());
			}
			return m.getSituacao();
		case 10:
			return m.getComprovante();
		case 11:
			return m.getObservacao();
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 8) {
			return Boolean.class;
		}
		return super.getColumnClass(columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		return COLUNAS[column];
	}

	public Color getRowColor(int row) {
		Movimento m = movimentos.get(row);
		if (!m.isAceite()) {
			if (m.getSituacao() == Situacao.PENDENTE && m.getObservacao().equals("PROVISÃO")) {
				return new Color(179,215,255);
			} else {
				return Color.WHITE;
			}
		}
		switch (m.getSituacao()) {
		case ABERTO:
			return new Color(255, 102, 102);
		case PENDENTE:
			return new Color(255, 255, 153);
		case LIQUIDADO:
			return new Color(204, 255, 153);
		default:
			return Color.white;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 8) {
			return true;
		}
		return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == 8) {
			boolean value = (Boolean) aValue;
			Movimento m = movimentos.get(rowIndex);
			m.setAceite(value);
			new MovimentoDao().atualizar(m);
		}
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	public Movimento get(int index) {
		return movimentos.get(index);
	}

}
