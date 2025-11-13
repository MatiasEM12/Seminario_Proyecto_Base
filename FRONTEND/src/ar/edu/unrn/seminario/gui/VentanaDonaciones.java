package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class VentanaDonaciones extends JFrame {

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
					VentanaDonaciones frame = new VentanaDonaciones();
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
	public VentanaDonaciones() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 719, 383);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSeleccionar.setBounds(116, 310, 129, 23);
		contentPane.add(btnSeleccionar);
		
		JLabel lblNewLabel = new JLabel("Listado Donaciones Pendientes");
		lblNewLabel.setBounds(246, 11, 148, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(437, 310, 129, 23);
		contentPane.add(btnCancelar);
		
		table = new JTable();
		table.setBounds(10, 65, 660, 233);
		contentPane.add(table);

	}

}
