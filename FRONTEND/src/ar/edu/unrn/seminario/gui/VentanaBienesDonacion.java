package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;

public class VentanaBienesDonacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBienesDonacion frame = new VentanaBienesDonacion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaBienesDonacion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 357, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(10, 300, 321, 40);
		contentPane.add(bottomPanel);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		bottomPanel.add(btnSeleccionar);
		
		JButton btnCancelar = new JButton("Cancelar");
		bottomPanel.add(btnCancelar);
		
		table = new JTable();
		table.setBounds(10, 11, 321, 268);
		contentPane.add(table);

	}
}
