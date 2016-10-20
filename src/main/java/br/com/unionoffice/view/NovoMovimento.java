package br.com.unionoffice.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import br.com.unionoffice.modelo.TipoMovimento;

public class NovoMovimento extends JDialog {
	JLabel lbData, lbEmitente, lbVencimento, lbValorTotal, lbCondPagamento, lbReferencia, lbObservacoes,
			lbTipoMovimento, lbNumero;
	JFormattedTextField tfData, tfVencimento;
	JTextField tfEmitente, tfValorTotal, tfReferencia, tfObservacoes, tfNumero;
	MaskFormatter maskData;
	JComboBox<CondPagamento> cbCondPagamento;
	JComboBox<TipoMovimento> cbTipoMovimento;
	JButton btCriar;
	JPanel pnSuperior;
	Calendar dataEmissao = Calendar.getInstance(), dataVencimento = Calendar.getInstance();
	SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
	MovimentoDao dao;
	CondPagamento condicao;

	public NovoMovimento() {
		try {
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
		// cbCondPagamento
		cbCondPagamento.addActionListener(e -> {
			try {
				dataEmissao.setTime(formatador.parse(tfData.getValue().toString()));
				dataVencimento.setTime(dataEmissao.getTime());
				condicao = (CondPagamento) cbCondPagamento.getSelectedItem();
				dataVencimento.add(Calendar.DAY_OF_YEAR, condicao.dias);
				tfVencimento.setValue(formatador.format(dataVencimento.getTime()));
			} catch (Exception erro) {
				System.out.println(erro.getMessage());
			}
		});

		// btCriar
		btCriar.addActionListener(e -> {
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
				condicao = (CondPagamento) cbCondPagamento.getSelectedItem();
				double total = Double.parseDouble(tfValorTotal.getText());
			
				for (int i = 0; i < condicao.qtdParcelas; i++) {
					Movimento movimento = new Movimento();
					movimento.setValorParcela(new BigDecimal(total / condicao.qtdParcelas));
					movimento.setReferencia(tfReferencia.getText());
					movimento.setEmitente(tfEmitente.getText());
					movimento.setTipoMovimento((TipoMovimento) cbTipoMovimento.getSelectedItem());
					movimento.setCondPagamento((CondPagamento)cbCondPagamento.getSelectedItem());
					movimento.setNumParcela((byte) (i + 1));
					movimento.setObservacao(tfObservacoes.getText());					
					movimento.setVencimento((Calendar)dataVencimento.clone());
					movimento.setData(dataEmissao);
					movimento.setNumero(tfNumero.getText());
					dao.inserir(movimento);
					dataVencimento.add(Calendar.DAY_OF_YEAR, condicao.dias);
				}
				limpar();				
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

		// lbData
		lbData = new JLabel("Data Emissão:");

		// mskData
		maskData = new MaskFormatter("##/##/####");

		// tfData
		tfData = new JFormattedTextField(maskData);
		tfData.setHorizontalAlignment(SwingConstants.CENTER);
		tfData.setValue(new SimpleDateFormat("dd/MM/yyyy").format(dataEmissao.getTime()));

		// lbEmitente
		lbEmitente = new JLabel("Emitente:");

		// tfEmitente
		tfEmitente = new JTextField();
		tfEmitente.setHorizontalAlignment(SwingConstants.CENTER);

		// lbVencimento
		lbVencimento = new JLabel("Vencimento:");

		// tfVencimento
		tfVencimento = new JFormattedTextField(maskData);
		tfVencimento.setHorizontalAlignment(SwingConstants.CENTER);

		// lbValorTotal
		lbValorTotal = new JLabel("Valor Total:");

		// tfValorTotal
		tfValorTotal = new JTextField();
		tfValorTotal.setHorizontalAlignment(SwingConstants.CENTER);
		tfValorTotal.setDocument(new ValorDocument());

		// lbQtdParcela
		lbCondPagamento = new JLabel("Cond. Pagamento:");

		// cbTipoMovimento
		cbCondPagamento = new JComboBox<>(CondPagamento.values());
		((JLabel) cbCondPagamento.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		// lbReferencia
		lbReferencia = new JLabel("Referência:");

		// tfReferencia
		tfReferencia = new JTextField();
		tfReferencia.setHorizontalAlignment(SwingConstants.CENTER);
		
		// lbNumero
		lbNumero = new JLabel("Número:");
		
		// tfNumero
		tfNumero = new JTextField();
		tfNumero.setHorizontalAlignment(SwingConstants.CENTER);

		// lbObservacoes
		lbObservacoes = new JLabel("Observações:");

		// tfObservacoes
		tfObservacoes = new JTextField();

		// btCriar
		btCriar = new JButton("Gerar Movimento");
		btCriar.setPreferredSize(new Dimension(0, 45));

		// add pnSuperior
		pnSuperior.add(lbTipoMovimento);
		pnSuperior.add(cbTipoMovimento);
		pnSuperior.add(lbData);
		pnSuperior.add(tfData);
		pnSuperior.add(lbEmitente);
		pnSuperior.add(tfEmitente);
		pnSuperior.add(lbCondPagamento);
		pnSuperior.add(cbCondPagamento);
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

		// add frame
		add(pnSuperior, BorderLayout.CENTER);
		add(btCriar, BorderLayout.SOUTH);

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

	public static void main(String[] args) {
		new NovoMovimento();
	}
	
	private void limpar(){
		tfEmitente.setText(null);
		tfValorTotal.setText(null);
		tfReferencia.setText(null);
		tfObservacoes.setText(null);
		tfEmitente.requestFocus();
	}
}
