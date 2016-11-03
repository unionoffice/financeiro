package br.com.unionoffice.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class JanelaPrincipal extends JFrame {
	JMenuBar barraMenu;
	JMenu menuArquivo, menuMovimento;
	JMenuItem mitUsuarios, mitSair, mitNovoMovimento;
	PainelMovimentos pnMovimentos;	

	public JanelaPrincipal() {
		inicializarComponentes();
		definirEventos();
	}

	private void inicializarComponentes() {
		// frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setExtendedState(MAXIMIZED_BOTH);

		// mitUsuarios
		//mitUsuarios = new JMenuItem("Usu�rios");
		//mitUsuarios.setAccelerator(KeyStroke.getKeyStroke("CTRL + U"));

		// mitSair
		mitSair = new JMenuItem("Sair");

		// menuArquivo
		menuArquivo = new JMenu("Arquivo");
		//menuArquivo.add(mitUsuarios);
		menuArquivo.addSeparator();
		menuArquivo.add(mitSair);

		// mitNovoMovimento
		mitNovoMovimento = new JMenuItem("Novo Movimento");

		// menuMovimento
		menuMovimento = new JMenu("Movimentos");
		menuMovimento.add(mitNovoMovimento);

		// menu
		barraMenu = new JMenuBar();
		barraMenu.add(menuArquivo);
		barraMenu.add(menuMovimento);

		// frame
		setJMenuBar(barraMenu);

		// pnMovimentos
		pnMovimentos = new PainelMovimentos();
		setContentPane(pnMovimentos);

		// visivel
		setVisible(true);

	}

	private void definirEventos() {
		mitNovoMovimento.addActionListener(e -> {
			new NovoMovimento();
			pnMovimentos.refreshAdd();
		});
	}
	

	public static void main(String[] args) {
		try {
			//UIManager.setLookAndFeel(
		    //        "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			new JanelaPrincipal();
		} catch (Exception e) {
			e.printStackTrace();
		}		 
	}
}
