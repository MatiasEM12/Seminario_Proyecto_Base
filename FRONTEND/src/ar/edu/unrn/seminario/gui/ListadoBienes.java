package ar.edu.unrn.seminario.gui;



import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    private Consumer<ArrayList<BienDTO>> onSeleccion; // callback
	IApi api;
	private ArrayList<BienDTO> bienes; //para la seleccion 
	
	
	
	public ListadoBienes(IApi api,ArrayList<BienDTO> bienesDTO,Consumer<ArrayList<BienDTO>> onSeleccion) {
	
		
    
        
		this.api=api;
		  this.setOnSeleccion(onSeleccion);
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
	    table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setBounds(340, 227, 89, 23);
		contentPane.add(btnSeleccionar);
		
		btnSeleccionar.addActionListener( new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				
				  int[] filasSeleccionadas = table.getSelectedRows();

			        if (filasSeleccionadas.length == 0) {
			            JOptionPane.showMessageDialog(null,
			                    "Debe seleccionar al menos un bien",
			                    "Atención",
			                    JOptionPane.WARNING_MESSAGE);
			            return;
			        }

			        ArrayList<BienDTO> seleccionados = new ArrayList<>();

			        for (int filaVista : filasSeleccionadas) {

			            int filaModelo = table.convertRowIndexToModel(filaVista);

			            String codigo = (String) modelo.getValueAt(filaModelo, 0);

			            try {
			                BienDTO bien = api.obtenerBien(codigo);
			                if (bien != null) {
			                    seleccionados.add(bien);
			                }
			            } catch (DataNullException | DAOException ex) {
			                JOptionPane.showMessageDialog(null,
			                        ex.getMessage(),
			                        "Error",
			                        JOptionPane.ERROR_MESSAGE);
			            }
			        }

			        if (onSeleccion != null) {
			            onSeleccion.accept(seleccionados);
			        }

			        setVisible(false);
			        dispose();
			    }
		});

	}



	public Consumer<ArrayList<BienDTO>> getOnSeleccion() {
		return onSeleccion;
	}



	public void setOnSeleccion(Consumer<ArrayList<BienDTO>> onSeleccion) {
		this.onSeleccion = onSeleccion;
	}
}
