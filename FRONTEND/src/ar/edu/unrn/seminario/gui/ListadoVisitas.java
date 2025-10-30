package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.VisitaDTO;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ListadoVisitas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	private JTable table;
	private DefaultTableModel modelo;
	private JTextField textField;
	IApi api;
	/**
	 * Create the frame.
	 */
	public ListadoVisitas(IApi api, String codOrdenRetiro) {
		this.api=api;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		
		JPanel panelButtom = new JPanel();
		panelButtom.setBounds(292, 210, 250, 40);
		contentPane.add(panelButtom);
		
		
		
		
		JTable table = new JTable();
		String[] titulos = { "Codigo", "Fecha", "Voluntario","Tipo","Observaciones" };
		modelo = new DefaultTableModel(new Object[][] {}, titulos);
		 JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 29, 532, 170);
		contentPane.add(scrollPane);
			
		ArrayList<VisitaDTO> visitas= api.obtenerVisitas(codOrdenRetiro);
		
		for(VisitaDTO v : visitas) {
			
			modelo.addRow(new Object[] { v.getCodigo(),v.getFechaVisita(),api.obtenerUsernameVoluntarioPorOrdenRetiro(codOrdenRetiro),v.getTipo(),v.getObservaciones() });
		}
		table.setModel(modelo);
		
		JButton btnBien = new JButton("Bienes Recolectados");
		btnBien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = table.getSelectedRow();
				if (filaSeleccionada >= 0) {
                    String codVisita = (String) table.getValueAt(filaSeleccionada, 0);
                   
                   
                    ListadoBienes  bienes = new ListadoBienes(api,api.obtenerBienesDeVisita(codVisita) );
    	        	bienes.setLocationRelativeTo(null);
    	        	bienes.setVisible(true);
                }
				
				
				
				
			}
		});
		panelButtom.add(btnBien);
	
		
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		panelButtom.add(btnCerrar);

	}
}
