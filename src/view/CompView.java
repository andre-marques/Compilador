package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import processo.MeuScanner;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;

public class CompView {

	private JFrame f_Principal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompView window = new CompView();
					window.f_Principal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CompView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		f_Principal = new JFrame();
		f_Principal.setTitle("Projeto Compilador");
		f_Principal.setBounds(100, 100, 845, 668);
		f_Principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f_Principal.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 829, 634);
		f_Principal.getContentPane().add(panel);
		panel.setLayout(null);
		
		JTextArea txtA_Origem = new JTextArea();
		txtA_Origem.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtA_Origem.setBounds(10, 11, 385, 600);
		txtA_Origem.setRows(10);
		panel.add(txtA_Origem);
		
		JTextArea txtA_Destino = new JTextArea();
		txtA_Destino.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtA_Destino.setRows(10);
		txtA_Destino.setBounds(467, 11, 352, 600);
		panel.add(txtA_Destino);
			
		JButton btnC = new JButton("");
		btnC.setBackground(Color.LIGHT_GRAY);
		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				FileWriter arquivo;
				try {
					
					File f = new File("entrada.txt");
					FileOutputStream fos = new FileOutputStream(f);  
		            String texto = txtA_Origem.getText();  
		            fos.write(texto.getBytes());  
		            		            
					MeuScanner scan = new MeuScanner(f.getPath());
					String s = scan.leitura();
					s = s.trim();
					txtA_Destino.setText(s);
					  
					fos.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		});
		btnC.setBounds(405, 285, 52, 52);
		
		Icon icone = new ImageIcon("img/play.jpg");
		btnC.setIcon(icone);
		
		panel.add(btnC);
		
		
	}
}
