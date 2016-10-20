package br.com.unionoffice.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import br.com.unionoffice.dao.MovimentoDao;
import br.com.unionoffice.modelo.Movimento;
import br.com.unionoffice.tablemodel.MovimentoTableModel;

public class PainelMovimentos extends JPanel {
	private MovimentoDao dao;
	private List<Movimento> movimentos;
	private JTable tbMovimentos;
	private MovimentoTableModel modelMovimentos;
	private JScrollPane spMovimentos;
	private DefaultTableCellRenderer renderCenter;
	private DefaultTableCellRenderer renderRight;

	public PainelMovimentos() {
		dao = new MovimentoDao();
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {
		renderCenter = new DefaultTableCellRenderer();
		renderCenter.setHorizontalAlignment(SwingConstants.CENTER);

		renderRight = new DefaultTableCellRenderer();
		renderRight.setHorizontalAlignment(SwingConstants.RIGHT);

		// modelMovimentos
		modelMovimentos = new MovimentoTableModel(movimentos = dao.listar());

		// tbMovimentos
		tbMovimentos = new JTable(modelMovimentos);
		tbMovimentos.setRowHeight(23);
		tbMovimentos.getColumnModel().getColumn(0).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(1).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(2).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(3).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(4).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(5).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(6).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(7).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(8).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(9).setCellRenderer(renderCenter);
		tbMovimentos.getColumnModel().getColumn(10).setCellRenderer(renderCenter);
		
		//tbMovimentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//update();

		// spMovimentos
		spMovimentos = new JScrollPane(tbMovimentos);

		setLayout(new BorderLayout());
		add(spMovimentos, BorderLayout.CENTER);
	}

	private void update() {
		//adjustJTableRowSizes(tbMovimentos);
		for (int i = 0; i < tbMovimentos.getColumnCount(); i++) {
			adjustColumnSizes(tbMovimentos, i, 2);
		}
	}
/*
	private void adjustJTableRowSizes(JTable jTable) {
		for (int row = 0; row < jTable.getRowCount(); row++) {
			int maxHeight = 0;
			for (int column = 0; column < jTable.getColumnCount(); column++) {
				TableCellRenderer cellRenderer = jTable.getCellRenderer(row, column);
				Object valueAt = jTable.getValueAt(row, column);
				Component tableCellRendererComponent = cellRenderer.getTableCellRendererComponent(jTable, valueAt,
						false, false, row, column);
				int heightPreferable = tableCellRendererComponent.getPreferredSize().height;
				maxHeight = Math.max(heightPreferable, maxHeight);
			}
			jTable.setRowHeight(row, maxHeight);
		}

	}
*/
	public void adjustColumnSizes(JTable table, int column, int margin) {
		DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
		TableColumn col = colModel.getColumn(column);
		int width;

		TableCellRenderer renderer = col.getHeaderRenderer();
		if (renderer == null) {
			renderer = table.getTableHeader().getDefaultRenderer();
		}
		Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);
		width = comp.getPreferredSize().width;

		for (int r = 0; r < table.getRowCount(); r++) {
			renderer = table.getCellRenderer(r, column);
			comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, column), false, false, r, column);
			int currentWidth = comp.getPreferredSize().width+5;
			width = Math.max(width, currentWidth);
		}

		width += 2 * margin;

		col.setPreferredWidth(width);
		col.setWidth(width);
	}

	private void definirEventos() {

	}
}
