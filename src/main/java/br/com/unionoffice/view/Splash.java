package br.com.unionoffice.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class Splash extends JWindow {

	private int duration;

	public Splash(int d) {
		duration = d;
	}

	// Este � um m�todo simples para mostrar uma tela de apresent��o
	// no centro da tela durante a quantidade de tempo passada no construtor
	public void showSplash() {
		JPanel content = (JPanel) getContentPane();
		content.setBackground(Color.white);

		// Configura a posi��o e o tamanho da janela
		int width = 450;
		int height = 115;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);

		// Constr�i o splash screen
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		JLabel copyrt = new JLabel("Aguarde, iniciando..", JLabel.CENTER);
		copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
		content.add(progressBar, BorderLayout.CENTER);
		content.add(copyrt, BorderLayout.SOUTH);
		Color oraRed = new Color(156, 20, 20, 255);
		content.setBorder(BorderFactory.createLineBorder(oraRed, 10));
		// Torna vis�vel
		setVisible(true);

		// Espera ate que os recursos estejam carregados
		try {
			Thread.sleep(duration);
		} catch (Exception e) {
		}
		setVisible(false);
	}

	public void showSplashAndExit() {
		showSplash();
		System.exit(0);
	}

	public static void main(String[] args) {
		// Mostra uma imagem com o t�tulo da aplica��o
		Splash splash = new Splash(10000);
		splash.showSplashAndExit();
	}
}