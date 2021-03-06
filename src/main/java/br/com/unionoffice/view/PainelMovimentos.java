package br.com.unionoffice.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import br.com.unionoffice.dao.MovimentoDao;
import br.com.unionoffice.modelo.Movimento;
import br.com.unionoffice.modelo.Situacao;
import br.com.unionoffice.render.MyTableCellRenderer;
import br.com.unionoffice.tablemodel.MovimentoTableModel;

public class PainelMovimentos extends JPanel {
	private MovimentoDao dao;
	private JTable tbMovimentos;
	private MovimentoTableModel modelMovimentos;
	private JScrollPane spMovimentos;
	private DefaultTableCellRenderer renderCenter;
	private DefaultTableCellRenderer renderRight;
	private Movimento movimento;
	private int rowSelect;
	private JPanel pnBusca, pnTotais;
	private JTextField tfBuscar, tfTotal;
	private JLabel lbBuscar, lbAte, lbTotal;
	private JButton btBuscar, btBuscarData;
	private JFormattedTextField tfDataInicial, tfDataFinal;
	private SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
	private MaskFormatter maskData;
	private JCheckBox chkAbertos, chkLiquidados, chkPendentes;

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
		modelMovimentos = new MovimentoTableModel(dao.listar());

		// tbMovimentos
		tbMovimentos = new JTable(modelMovimentos);
		tbMovimentos.setRowHeight(23);
		tbMovimentos.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		tbMovimentos.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tbMovimentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		update();

		// spMovimentos
		spMovimentos = new JScrollPane(tbMovimentos);

		// lbBuscar
		lbBuscar = new JLabel("Texto:");
		lbBuscar.setBounds(10, 15, 50, 30);

		// tfBuscar
		tfBuscar = new JTextField();
		tfBuscar.setBounds(60, 15, 200, 30);

		// btBuscar
		btBuscar = new JButton("OK");
		btBuscar.setBounds(270, 15, 60, 30);

		// mskData

		try {
			maskData = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// tfDataInicial
		tfDataInicial = new JFormattedTextField(maskData);
		Calendar dataHoje = Calendar.getInstance();
		tfDataInicial.setValue(formatador.format(dataHoje.getTime()));
		tfDataInicial.setBounds(340, 15, 80, 30);
		tfDataInicial.setHorizontalAlignment(SwingConstants.CENTER);

		// lbAte
		lbAte = new JLabel("At�");
		lbAte.setBounds(430, 15, 20, 30);

		// tfDataFinal
		tfDataFinal = new JFormattedTextField(maskData);
		dataHoje.add(Calendar.DAY_OF_MONTH, 30);
		tfDataFinal.setValue(formatador.format(dataHoje.getTime()));
		tfDataFinal.setBounds(460, 15, 80, 30);
		tfDataFinal.setHorizontalAlignment(SwingConstants.CENTER);

		// btBuscarData
		btBuscarData = new JButton("OK");
		btBuscarData.setBounds(550, 15, 60, 30);

		// chkAbertos
		chkAbertos = new JCheckBox("Abertos");
		chkAbertos.setBounds(10, 50, 70, 30);
		chkAbertos.setSelected(true);

		// chkPendentes
		chkPendentes = new JCheckBox("Pendentes");
		chkPendentes.setBounds(90, 50, 90, 30);
		chkPendentes.setSelected(true);

		// chkLiquidados
		chkLiquidados = new JCheckBox("Liquidados");
		chkLiquidados.setBounds(190, 50, 90, 30);
		chkLiquidados.setSelected(true);

		// pnBusca
		pnBusca = new JPanel();
		pnBusca.setLayout(null);
		pnBusca.setPreferredSize(new Dimension(0, 90));
		pnBusca.setBorder(new TitledBorder("Buscar"));
		pnBusca.add(lbBuscar);
		pnBusca.add(tfBuscar);
		pnBusca.add(btBuscar);
		pnBusca.add(tfDataInicial);
		pnBusca.add(lbAte);
		pnBusca.add(tfDataFinal);
		pnBusca.add(btBuscarData);
		pnBusca.add(chkAbertos);
		pnBusca.add(chkPendentes);
		pnBusca.add(chkLiquidados);

		// lbTotal
		lbTotal = new JLabel("Total no per�odo:");
		Font fonte = new Font("Arial", Font.BOLD, 20);
		lbTotal.setFont(fonte);
		lbTotal.setBounds(10, 5, 180, 30);

		// tfTotal
		tfTotal = new JTextField();
		tfTotal.setEditable(false);
		tfTotal.setBounds(200, 5, 90, 30);
		tfTotal.setHorizontalAlignment(SwingConstants.CENTER);

		// pnTotais
		pnTotais = new JPanel(null);
		pnTotais.setPreferredSize(new Dimension(0, 45));
		pnTotais.add(lbTotal);
		pnTotais.add(tfTotal);

		setLayout(new BorderLayout());
		add(pnBusca, BorderLayout.NORTH);
		add(spMovimentos, BorderLayout.CENTER);
		add(pnTotais, BorderLayout.SOUTH);

		atualizaTotal();

	}

	private void update() {
		// adjustJTableRowSizes(tbMovimentos);
		for (int i = 0; i < tbMovimentos.getColumnCount(); i++) {
			adjustColumnSizes(tbMovimentos, i, 15);
		}
	}

	public void refreshAdd() {
		modelMovimentos.setMovimentos(dao.listar());
		modelMovimentos.refreshAdd();
	}

	public void refresshUpdate() {
		modelMovimentos.fireTableDataChanged();
		atualizaTotal();
	}

	public void refreshDelete() {
		modelMovimentos.setMovimentos(dao.listar());
		modelMovimentos.refreshDelete();
	}

	/*
	 * private void adjustJTableRowSizes(JTable jTable) { for (int row = 0; row
	 * < jTable.getRowCount(); row++) { int maxHeight = 0; for (int column = 0;
	 * column < jTable.getColumnCount(); column++) { TableCellRenderer
	 * cellRenderer = jTable.getCellRenderer(row, column); Object valueAt =
	 * jTable.getValueAt(row, column); Component tableCellRendererComponent =
	 * cellRenderer.getTableCellRendererComponent(jTable, valueAt, false, false,
	 * row, column); int heightPreferable =
	 * tableCellRendererComponent.getPreferredSize().height; maxHeight =
	 * Math.max(heightPreferable, maxHeight); } jTable.setRowHeight(row,
	 * maxHeight); }
	 * 
	 * }
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
			int currentWidth = comp.getPreferredSize().width + 5;
			width = Math.max(width, currentWidth);
		}

		width += 2 * margin;

		col.setPreferredWidth(width);
		col.setWidth(width);
	}

	private void definirEventos() {
		tbMovimentos.getSelectionModel().addListSelectionListener(event -> {
			rowSelect = tbMovimentos.getSelectedRow();
			if (rowSelect >= 0 && rowSelect < modelMovimentos.getMovimentos().size()) {
				movimento = modelMovimentos.get(rowSelect);
			}
		});

		tbMovimentos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					new DetalheMovimento(movimento);
					refresshUpdate();
				}
			}
		});

		tbMovimentos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (rowSelect >= 0 && e.getKeyCode() == 127) {
					int opcao = JOptionPane
							.showConfirmDialog(PainelMovimentos.this,
									"Deseja excluir o movimento " + movimento.getEmitente() + " - "
											+ movimento.getReferencia() + "?",
									"Confirmar exclus�o", JOptionPane.YES_NO_OPTION);
					if (opcao == 0) {
						try {
							dao.excluir(movimento.getId());
							refreshDelete();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			}
		});

		// btBuscar
		btBuscar.addActionListener(e -> {
			String texto = tfBuscar.getText().trim().toLowerCase();
			if (!texto.isEmpty()) {
				modelMovimentos.setMovimentos(modelMovimentos.getMovimentos().stream()
						.filter(m -> m.getEmitente().toLowerCase().contains(texto)
								|| m.getReferencia().toLowerCase().contains(texto)
								|| m.getNumero().toLowerCase().contains(texto))
						.collect(Collectors.toList()));
				refresshUpdate();

			} else {
				modelMovimentos.setMovimentos(dao.listar());
				filtrar();
			}

		});

		// btBuscarData
		btBuscarData.addActionListener(e -> {
			try {
				modelMovimentos.setMovimentos(dao.listarTodos());
				Date dataInicio = formatador.parse(tfDataInicial.getValue().toString());
				Calendar calendarInicio = Calendar.getInstance();
				calendarInicio.setTime(dataInicio);
				calendarInicio.add(Calendar.DAY_OF_MONTH, -1);
				Date dataFim = formatador.parse(tfDataFinal.getValue().toString());
				Calendar calendarFim = Calendar.getInstance();
				calendarFim.setTime(dataFim);
				calendarFim.add(Calendar.DAY_OF_MONTH, 1);
				modelMovimentos.setMovimentos(modelMovimentos.getMovimentos().stream()
						.filter(m -> m.getVencimento().after(calendarInicio) && m.getVencimento().before(calendarFim))
						.collect(Collectors.toList()));				
				filtrar();
			} catch (Exception e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(PainelMovimentos.this, "Formato de data inv�lido: " + e2.getMessage());
			}

		});

		chkAbertos.addActionListener(e -> {
			filtrar();
		});

		chkPendentes.addActionListener(e -> {
			filtrar();
		});

		chkLiquidados.addActionListener(e -> {
			filtrar();
		});

	}

	private void filtrar() {
		int soma = 0;
		soma += chkAbertos.isSelected() ? 1 : 0;
		soma += chkPendentes.isSelected() ? 2 : 0;
		soma += chkLiquidados.isSelected() ? 4 : 0;		
		switch (soma) {
		case 1:
			modelMovimentos.setMovimentos(modelMovimentos.getMovimentos().stream()
					.filter(m -> m.getSituacao() == Situacao.ABERTO).collect(Collectors.toList()));
			break;
		case 2:
			modelMovimentos.setMovimentos(modelMovimentos.getMovimentos().stream()
					.filter(m -> m.getSituacao() == Situacao.PENDENTE).collect(Collectors.toList()));
			break;
		case 3:
			modelMovimentos.setMovimentos(modelMovimentos.getMovimentos().stream()
					.filter(m -> m.getSituacao() == Situacao.PENDENTE || m.getSituacao() == Situacao.ABERTO)
					.collect(Collectors.toList()));
			break;
		case 4:
			modelMovimentos.setMovimentos(modelMovimentos.getMovimentos().stream()
					.filter(m -> m.getSituacao() == Situacao.LIQUIDADO).collect(Collectors.toList()));
			break;

		case 5:
			modelMovimentos.setMovimentos(modelMovimentos.getMovimentos().stream()
					.filter(m -> m.getSituacao() == Situacao.LIQUIDADO || m.getSituacao() == Situacao.ABERTO)
					.collect(Collectors.toList()));
			break;
		case 6:
			modelMovimentos.setMovimentos(modelMovimentos.getMovimentos().stream()
					.filter(m -> m.getSituacao() == Situacao.LIQUIDADO || m.getSituacao() == Situacao.PENDENTE)
					.collect(Collectors.toList()));
			break;
		}
		refresshUpdate();

	}

	private void atualizaTotal() {
		BigDecimal valor = new BigDecimal(0);
		for (Movimento m : modelMovimentos.getMovimentos()) {
			valor = valor.add(m.getValorParcela());
		}
		tfTotal.setText(NumberFormat.getCurrencyInstance().format(valor.doubleValue()));
	}
	
	public List<Movimento> getMovimentos(){
		return modelMovimentos.getMovimentos();
	}
}
