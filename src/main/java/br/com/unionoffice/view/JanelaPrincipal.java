package br.com.unionoffice.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;

public class JanelaPrincipal extends JFrame {
	JMenuBar barraMenu;
	JMenu menuArquivo, menuMovimento;
	JMenuItem mitUsuarios, mitSair, mitNovoMovimento;
	PainelMovimentos pnMovimentos;
	SplashScreen splash;
	JFrame content = new JFrame();
	JLabel contador;
	int carregou;

	public JanelaPrincipal() {
		inicializarComponentes();
		definirEventos();
		carregou = 1;
	}

	private void inicializarComponentes() {
		showSplash();

		Thread t = new Thread() {
			public void run() {
				content.setVisible(true);
				int pontos = 0;
				while (true) {
					if (pontos < 5) {
						contador.setText(contador.getText() + ".");
						pontos++;
					} else {
						pontos = 0;
						contador.setText("Carregando .");
					}
					if (carregou == 1) {
						System.out.println("FINALIZOU");
						content.dispose();
						content = null;
						break;
					}
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			};
		};
		t.start();

		// frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setExtendedState(MAXIMIZED_BOTH);

		// mitUsuarios
		// mitUsuarios = new JMenuItem("Usuários");
		// mitUsuarios.setAccelerator(KeyStroke.getKeyStroke("CTRL + U"));

		// mitSair
		mitSair = new JMenuItem("Sair");

		// menuArquivo
		menuArquivo = new JMenu("Arquivo");
		// menuArquivo.add(mitUsuarios);
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
		setTitle("Movimentos Financeiros Union Office");

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

		mitSair.addActionListener(e -> {
			System.exit(0);
		});

	}

	public static void main(String[] args) {
		try {
			// UIManager.setLookAndFeel(
			// "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			new JanelaPrincipal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopSplash() {
		content.setVisible(false);
		content = null;
	}

	public void showSplash() {

		content.getContentPane().setBackground(Color.white);

		// Configura a posição e o tamanho da janela
		int width = 250;
		int height = 90;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		content.setBounds(x, y, width, height);

		// Constrói o splash screen
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		contador = new JLabel("Carregando ", JLabel.CENTER);
		contador.setFont(new Font("Sans-Serif", Font.BOLD, 12));
		contador.setPreferredSize(new Dimension(300, 25));
		Color oraRed = new Color(156, 20, 20, 255);
		contador.setForeground(oraRed);
		content.add(progressBar, BorderLayout.CENTER);
		content.add(contador, BorderLayout.SOUTH);
		content.setDefaultCloseOperation(EXIT_ON_CLOSE);
		content.setResizable(false);
		content.setTitle("Financeiro Union");

	}

}
