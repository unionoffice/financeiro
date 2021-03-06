package br.com.unionoffice.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;

import br.com.unionoffice.util.GeraExcel;

public class JanelaPrincipal extends JFrame {
	JMenuBar barraMenu;
	JMenu menuArquivo, menuMovimento;
	JMenuItem mitUsuarios, mitSair, mitNovoMovimento, mitExportar;
	PainelMovimentos pnMovimentos;
	SplashScreen splash;
	JFrame content = new JFrame();
	JLabel contador;
	int carregou;
	JFileChooser fcPasta;

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
		setSize(1024, 768);
		setExtendedState(MAXIMIZED_BOTH);

		// mitUsuarios
		// mitUsuarios = new JMenuItem("Usu�rios");
		// mitUsuarios.setAccelerator(KeyStroke.getKeyStroke("CTRL + U"));

		// mitExportar
		mitExportar = new JMenuItem("Exportar");
		mitExportar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

		// mitSair
		mitSair = new JMenuItem("Sair");

		// menuArquivo
		menuArquivo = new JMenu("Arquivo");
		menuArquivo.add(mitExportar);
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

		// fcPasta
		fcPasta = new JFileChooser();
		fcPasta.setCurrentDirectory(new File(System.getProperty("user.home"), "Desktop"));
		fcPasta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fcPasta.setDialogTitle("Escolha o local para salvar o arquivo");
		fcPasta.setApproveButtonText("Salvar");

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

		mitExportar.addActionListener(e -> {
			int returnValue = fcPasta.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File diretorio = fcPasta.getSelectedFile();
				File file = new File(diretorio.getAbsolutePath() + "/financeiro.xls");
				file.delete();
				GeraExcel.expExcel(pnMovimentos.getMovimentos(), diretorio.getAbsolutePath());
				JOptionPane.showMessageDialog(null, "Arquivo gerado com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			}
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

		// Configura a posi��o e o tamanho da janela
		int width = 250;
		int height = 90;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		content.setBounds(x, y, width, height);

		// Constr�i o splash screen
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
