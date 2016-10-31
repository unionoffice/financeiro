package br.com.unionoffice.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.unionoffice.dao.MovimentoDao;
import br.com.unionoffice.document.ValorDocument;
import br.com.unionoffice.modelo.CondPagamento;
import br.com.unionoffice.modelo.Movimento;
import br.com.unionoffice.modelo.Situacao;
import br.com.unionoffice.modelo.TipoMovimento;

public class DetalheMovimento extends JDialog {
	JLabel lbData, lbEmitente, lbVencimento, lbValorTotal, lbReferencia, lbObservacoes, lbTipoMovimento, lbNumero,
			lbSituacao, lbComprovante;
	JFormattedTextField tfData, tfVencimento;
	JTextField tfEmitente, tfValorTotal, tfReferencia, tfObservacoes, tfNumero, tfComprovante;
	MaskFormatter maskData;
	JComboBox<TipoMovimento> cbTipoMovimento;
	JComboBox<Situacao> cbSituacao;
	JButton btSalvar, btLiquidar;
	JPanel pnSuperior;
	SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
	MovimentoDao dao;
	Movimento movimento;

	public DetalheMovimento(Movimento movimento) {
		try {
			this.movimento = movimento;
			dao = new MovimentoDao();
			inicializarComponentes();
			definirEventos();
			setModal(true);
			setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void definirEventos() {
		// btSalvar
		btSalvar.addActionListener(e -> {
			if (tfData.getValue() == null) {
				JOptionPane.showMessageDialog(null, "Informe a data de emissão", "Informe",
						JOptionPane.INFORMATION_MESSAGE);
				tfData.requestFocus();
			} else if (tfEmitente.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o emitente", "Informe", JOptionPane.INFORMATION_MESSAGE);
				tfEmitente.requestFocus();
			} else if (tfVencimento.getValue() == null) {
				JOptionPane.showMessageDialog(null, "Selecione a condição de pagamento", "Informe",
						JOptionPane.INFORMATION_MESSAGE);
				tfVencimento.requestFocus();
			} else if (tfValorTotal.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe o valor total", "Informe",
						JOptionPane.INFORMATION_MESSAGE);
				tfValorTotal.requestFocus();
			} else if (tfReferencia.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Informe a referência", "Informe", JOptionPane.INFORMATION_MESSAGE);
				tfReferencia.requestFocus();
			} else {
				try {
					movimento.setReferencia(tfReferencia.getText());
					movimento.setEmitente(tfEmitente.getText());
					movimento.setTipoMovimento((TipoMovimento) cbTipoMovimento.getSelectedItem());
					movimento.setObservacao(tfObservacoes.getText());
					Calendar dataVenc = Calendar.getInstance();
					dataVenc.setTime(formatador.parse(tfVencimento.getValue().toString()));
					movimento.setVencimento(dataVenc);
					Calendar data = Calendar.getInstance();
					data.setTime(formatador.parse(tfData.getValue().toString()));
					movimento.setData(data);
					movimento.setNumero(tfNumero.getText());
					movimento.setSituacao((Situacao) cbSituacao.getSelectedItem());
					if (movimento.getSituacao() == Situacao.LIQUIDADO)
						movimento.setComprovante(tfComprovante.getText());
					dao.atualizar(movimento);
					dispose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Erro", e2.getMessage(), JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		// btLiquidar
		btLiquidar.addActionListener(e -> {
			try {
				movimento.setReferencia(tfReferencia.getText());
				movimento.setEmitente(tfEmitente.getText());
				movimento.setTipoMovimento((TipoMovimento) cbTipoMovimento.getSelectedItem());
				movimento.setObservacao(tfObservacoes.getText());
				Calendar dataVenc = Calendar.getInstance();
				dataVenc.setTime(formatador.parse(tfVencimento.getValue().toString()));
				movimento.setVencimento(dataVenc);
				Calendar data = Calendar.getInstance();
				data.setTime(formatador.parse(tfData.getValue().toString()));
				movimento.setData(data);
				movimento.setNumero(tfNumero.getText());
				movimento.setSituacao(Situacao.LIQUIDADO);
				movimento.setDataLiquidacao(Calendar.getInstance());
				movimento.setComprovante(JOptionPane.showInputDialog(null, "Informe o comprovante, caso exista",
						"Comprovante", JOptionPane.QUESTION_MESSAGE));
				dao.atualizar(movimento);
				dispose();
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Erro", e2.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void inicializarComponentes() throws ParseException {
		// frame
		GridLayout layout = new GridLayout(0, 2);
		layout.setVgap(15);

		// pnSuperior
		pnSuperior = new JPanel(layout);
		pnSuperior.setBorder(new EmptyBorder(0, 0, 10, 0));

		setLayout(new BorderLayout());
		setSize(500, 570);
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// lbTipoMovimento
		lbTipoMovimento = new JLabel("Tipo de Movimento:");

		// cbTipoMovimento
		cbTipoMovimento = new JComboBox<>(TipoMovimento.values());
		((JLabel) cbTipoMovimento.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		cbTipoMovimento.setSelectedItem(movimento.getTipoMovimento());
		cbTipoMovimento.setEnabled(false);
		cbTipoMovimento.addMouseListener(adapterEnable);

		// lbData
		lbData = new JLabel("Data Emissão:");

		// mskData
		maskData = new MaskFormatter("##/##/####");

		// tfData
		tfData = new JFormattedTextField(maskData);
		tfData.setHorizontalAlignment(SwingConstants.CENTER);
		tfData.setValue(new SimpleDateFormat("dd/MM/yyyy").format(movimento.getData().getTime()));
		tfData.addMouseListener(adapterEnable);
		tfData.setEnabled(false);

		// lbEmitente
		lbEmitente = new JLabel("Emitente:");

		// tfEmitente
		tfEmitente = new JTextField();
		tfEmitente.setHorizontalAlignment(SwingConstants.CENTER);
		tfEmitente.setText(movimento.getEmitente());
		tfEmitente.setEnabled(false);
		tfEmitente.addMouseListener(adapterEnable);

		// lbVencimento
		lbVencimento = new JLabel("Vencimento:");

		// tfVencimento
		tfVencimento = new JFormattedTextField(maskData);
		tfVencimento.setHorizontalAlignment(SwingConstants.CENTER);
		tfVencimento.setValue(new SimpleDateFormat("dd/MM/yyyy").format(movimento.getVencimento().getTime()));
		tfVencimento.setEnabled(false);
		tfVencimento.addMouseListener(adapterEnable);

		// lbValorTotal
		lbValorTotal = new JLabel("Valor Total:");

		// tfValorTotal
		tfValorTotal = new JTextField();
		tfValorTotal.setHorizontalAlignment(SwingConstants.CENTER);
		tfValorTotal.setDocument(new ValorDocument());
		tfValorTotal.setText(movimento.getValorParcela().toEngineeringString());
		tfValorTotal.setEnabled(false);
		tfValorTotal.addMouseListener(adapterEnable);

		// lbReferencia
		lbReferencia = new JLabel("Referência:");

		// tfReferencia
		tfReferencia = new JTextField();
		tfReferencia.setHorizontalAlignment(SwingConstants.CENTER);
		tfReferencia.setText(movimento.getReferencia());
		tfReferencia.setEnabled(false);
		tfReferencia.addMouseListener(adapterEnable);

		// lbNumero
		lbNumero = new JLabel("Número:");

		// tfNumero
		tfNumero = new JTextField();
		tfNumero.setHorizontalAlignment(SwingConstants.CENTER);
		tfNumero.setText(movimento.getNumero());
		tfNumero.setEnabled(false);
		tfNumero.addMouseListener(adapterEnable);

		// lbObservacoes
		lbObservacoes = new JLabel("Observações:");

		// tfObservacoes
		tfObservacoes = new JTextField();
		tfObservacoes.setText(movimento.getObservacao());
		tfObservacoes.setEnabled(false);
		tfObservacoes.addMouseListener(adapterEnable);

		// lbSituacao
		lbSituacao = new JLabel("Situação");

		// cbSituacao
		cbSituacao = new JComboBox<>(Situacao.values());
		((JLabel) cbSituacao.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		cbSituacao.setSelectedItem(movimento.getSituacao());

		// lbComprovante
		lbComprovante = new JLabel("Comprovante:");

		// tfComprovante
		tfComprovante = new JTextField();
		tfComprovante.setText(movimento.getComprovante());
		tfComprovante.setHorizontalAlignment(SwingConstants.CENTER);

		// btSalvar
		btSalvar = new JButton("Salvar");

		// btLiquidar
		btLiquidar = new JButton("Liquidar");
		btLiquidar.setEnabled(movimento.getSituacao() != Situacao.LIQUIDADO);

		// add pnSuperior
		pnSuperior.add(lbTipoMovimento);
		pnSuperior.add(cbTipoMovimento);
		pnSuperior.add(lbData);
		pnSuperior.add(tfData);
		pnSuperior.add(lbEmitente);
		pnSuperior.add(tfEmitente);
		pnSuperior.add(lbVencimento);
		pnSuperior.add(tfVencimento);
		pnSuperior.add(lbValorTotal);
		pnSuperior.add(tfValorTotal);
		pnSuperior.add(lbReferencia);
		pnSuperior.add(tfReferencia);
		pnSuperior.add(lbNumero);
		pnSuperior.add(tfNumero);
		pnSuperior.add(lbObservacoes);
		pnSuperior.add(tfObservacoes);
		pnSuperior.add(lbSituacao);
		pnSuperior.add(cbSituacao);
		if (movimento.getComprovante() != null && !movimento.getComprovante().isEmpty()) {
			pnSuperior.add(lbComprovante);
			pnSuperior.add(tfComprovante);
		}

		// pnBotoes
		JPanel pnBotoes = new JPanel(new GridLayout(1, 0, 30, 30));
		pnBotoes.setPreferredSize(new Dimension(0, 45));
		pnBotoes.add(btSalvar);
		pnBotoes.add(btLiquidar);

		// add frame
		add(pnSuperior, BorderLayout.CENTER);
		add(pnBotoes, BorderLayout.SOUTH);

		// frame
		JPanel painel = (JPanel) getContentPane();
		painel.setBorder(new EmptyBorder(10, 10, 10, 10));
		setTitle("Movimento");

		// ao ficar visível
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				tfEmitente.requestFocus();
			}
		});

	}

	private MouseAdapter adapterEnable = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			Component componente = (Component) e.getSource();
			componente.setEnabled(true);
		}
	};
}
