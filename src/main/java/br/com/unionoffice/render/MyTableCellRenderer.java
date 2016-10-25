package br.com.unionoffice.render;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import br.com.unionoffice.tablemodel.MovimentoTableModel;

public class MyTableCellRenderer implements TableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JTextField editor = new JTextField();
		editor.setHorizontalAlignment(SwingConstants.CENTER);
		if (value != null)
			editor.setText(value.toString());
		MovimentoTableModel model = (MovimentoTableModel) table.getModel();
		if (!isSelected) {
			editor.setBackground(model.getRowColor(row));
		} else {
			editor.setBackground(new Color(102, 178, 255));
		}
		return editor;
	}

}
