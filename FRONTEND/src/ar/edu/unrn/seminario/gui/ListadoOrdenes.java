package ar.edu.unrn.seminario.gui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.*;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

import java.util.stream.Collectors;
import java.awt.event.ActionListener;

public class ListadoOrdenes extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JComboBox<String> listadoBox;
    private JTextField textField;
    private IApi api;

    public ListadoOrdenes(IApi api) {
        this.api = api;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblListarPor = new JLabel("Listar por:");
        lblListarPor.setBounds(25, 23, 74, 13);
        contentPane.add(lblListarPor);

        listadoBox = new JComboBox<>();
        listadoBox.addItem("Todos");
        listadoBox.addItem("OrdenRetiro");
        listadoBox.addItem("OrdenPedido");
        listadoBox.setBounds(94, 19, 150, 21);
        contentPane.add(listadoBox);

        textField = new JTextField();
        textField.setEnabled(false);
        textField.setBounds(260, 19, 150, 21);
        contentPane.add(textField);

        // Tabla inicial
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(10, 65, 660, 280);
        contentPane.add(scrollPane);

        // Panel de botones
        JPanel panelButtom = new JPanel();
        panelButtom.setBounds(10, 360, 660, 40);
        contentPane.add(panelButtom);

        JButton btnConsultarVisitas = new JButton("Consultar Visita");
        btnConsultarVisitas.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		int filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada >= 0) {
                  
                  if((String) tabla.getValueAt(filaSeleccionada, 1)=="ORDEN_RETIRO") {

                      	ListadoVisitas visitas = null;
						try {
							visitas = new ListadoVisitas(api,(String) tabla.getValueAt(filaSeleccionada, 0) );
						} catch (DataNullException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (DataLengthException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
      	        		visitas.setLocationRelativeTo(null);
      	        		visitas.setVisible(true);
                	  
                  } 
                   
                }
				
        		
        	}
        });
        
        JButton btAgregarVisita = new JButton("AgregarVisita");
        btAgregarVisita.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		int filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada >= 0) {
                    String tipo =(String) tabla.getValueAt(filaSeleccionada,0);
					if((String) tabla.getValueAt(filaSeleccionada, 1)=="ORDEN_RETIRO") {
							
						AltaVisita av= new AltaVisita(api);
						av.setLocationRelativeTo(null);
						av.setVisible(true);
					}else {
					
					}
                }
        		
        		
        	}
        });
        panelButtom.add(btAgregarVisita);
        panelButtom.add(btnConsultarVisitas);

        JButton btnVerBienes = new JButton("Ver bienes");
        btnVerBienes.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		
        		int filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada >= 0) {
                    String tipo =(String) tabla.getValueAt(filaSeleccionada,0);
					if((String) tabla.getValueAt(filaSeleccionada, 1)=="ORDEN_RETIRO") {
						
						String codOR = (String) tabla.getValueAt(filaSeleccionada, 0);
	                    ArrayList<BienDTO> lista = (ArrayList<BienDTO>) api.obtenerBienesPorOrdenRetiro(codOR);
	                    ListadoBienes bienes = new ListadoBienes(api, lista);
	                    bienes.setLocationRelativeTo(null);
	                    bienes.setVisible(true);
						
					}else {
						String codOP = (String) tabla.getValueAt(filaSeleccionada, 0);
	                    ArrayList<BienDTO> lista = (ArrayList<BienDTO>) api.obtenerBienesPorOrdenPedido(codOP);
	                    ListadoBienes bienes = new ListadoBienes(api, lista);
	                    bienes.setLocationRelativeTo(null);
	                    bienes.setVisible(true);
					}
                }
				
        		
        		
        	}
        });
        panelButtom.add(btnVerBienes);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setEnabled(false);
        panelButtom.add(btnEliminar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelButtom.add(btnCerrar);

        actualizarTabla("Todos", "");

        listadoBox.addActionListener(e -> actualizarTabla((String) listadoBox.getSelectedItem(), textField.getText()));
        textField.addActionListener(e -> actualizarTabla((String) listadoBox.getSelectedItem(), textField.getText()));
    }

    private void actualizarTabla(String filtro, String busqueda) {

        List<OrdenDTO> ordenes = api.obtenerOrdenes();   
        modelo.setRowCount(0); // Limpia la tabla antes de cargar

        if ("OrdenRetiro".equals(filtro)) {
           
        	// ordenes = api.obtenerOrdenesRetiro(ordenes);   // Filtra la lista de órdenes para quedarse solo con las órdenes de retiro
        	List<OrdenDTO> ordenesOR= ordenes.stream().filter(o->o.getTipo().equals("ORDEN_RETIRO")).collect(Collectors.toList());
            modelo.setColumnIdentifiers(new String[] { "Codigo", "Tipo", "Fecha", "Estado", "Codigo Pedido", "Voluntario", "Visitas" });

            // Recorre todas las órdenes de retiro obtenidas
            for (OrdenDTO o : ordenesOR) {
                OrdenRetiroDTO or = (OrdenRetiroDTO) o;
                String visitas = or.getCodVisitas() != null ? String.join(", ", or.getCodVisitas()) : "";   // Convierte la lista de códigos de visitas en una cadena separada por comas
                modelo.addRow(new Object[] { or.getCodigo(), or.getTipo(), or.getFechaEmision(), or.getEstado(), or.getPedido(), or.getCodVoluntario(), visitas });
                
            }
        } else if ("OrdenPedido".equals(filtro)) {    // Si el filtro seleccionado es "OrdenPedido"
        	List<OrdenDTO> ordenesOP= ordenes.stream().filter(o->o.getTipo().equals("ORDEN_PEDIDO")).collect(Collectors.toList());
            modelo.setColumnIdentifiers(new String[] { "Codigo", "Tipo", "Observaciones", "Fecha", "Estado", "Donante", "Donacion" });

            for (OrdenDTO o : ordenesOP) {
                OrdenPedidoDTO op = (OrdenPedidoDTO) o;
               
                    modelo.addRow(new Object[] { op.getCodigo(), op.getTipo(), op.getObservaciones(), op.getFechaEmision(), op.getEstado(), op.getCodDonante(), op.getCodDonacion() });
                
            }
        } else {
        	 // Recorre todas las órdenes (tanto pedido como retiro)
        	
            modelo.setColumnIdentifiers(new String[] { "Codigo", "Tipo", "Fecha", "Estado" });
            for (OrdenDTO o : ordenes) {
                String codigo = "";   // Determina el código según el tipo de orden
                if (o instanceof OrdenPedidoDTO) {
                    codigo = ((OrdenPedidoDTO) o).getCodigo();
                } else if (o instanceof OrdenRetiroDTO) {
                    codigo = ((OrdenRetiroDTO) o).getCodigo();
                }
                modelo.addRow(new Object[] { codigo, o.getTipo(), o.getFechaEmision(), o.getEstado() });
            }
        }
    }
}
