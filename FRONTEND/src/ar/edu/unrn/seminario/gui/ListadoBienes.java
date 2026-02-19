package ar.edu.unrn.seminario.gui;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.awt.event.ActionEvent;

public class ListadoBienes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modelo;
	IApi api;
	private ArrayList<BienDTO> bienes; 
	BienDTO bien=null;
	
	
	public ListadoBienes(IApi api,ArrayList<BienDTO> bienesDTO) {
	
		
	
    
        
		this.api=api;
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 609, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		


		table = new JTable();
		String[] titulos = { "Codigo", "Tipo","Nombre","Desc","Peso","Vencimiento","Talle","Material"};
		modelo = new DefaultTableModel(new Object[][] {}, titulos);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 573, 194);
		contentPane.add(scrollPane);
		this.bienes = new ArrayList<>(bienesDTO == null ? new ArrayList<>() : bienesDTO);
		
		for(BienDTO b : bienes) {
			
			modelo.addRow(new Object[] { 
					b.getCodigo(),
					b.getTipo(),
					b.getNombre(),
					b.getDescripcion(),
					b.getPeso(),
					b.getFechaVencimiento(),
					b.getTalle(),
					b.getMaterial(),
			});
		}
		table.setModel(modelo);
		
		
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				dispose();
			}
		});
		btnCerrar.setBounds(494, 227, 89, 23);
		contentPane.add(btnCerrar);

	}
}
