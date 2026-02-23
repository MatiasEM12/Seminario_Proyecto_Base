package ar.edu.unrn.seminario.gui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.*;
import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;

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
        listadoBox.addItem("ORDEN_RETIRO");
        listadoBox.addItem("ORDEN_PEDIDO");
        listadoBox.addItem("ORDEN_ENTREGA");
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
                  
                  if("ORDEN_RETIRO".equals(tabla.getValueAt(filaSeleccionada, 1)) || "ORDEN_ENTREGA".equals(tabla.getValueAt(filaSeleccionada, 1))) {

                      	ListadoVisitas visitas = null;
						try {
							visitas = new ListadoVisitas(api,(String) tabla.getValueAt(filaSeleccionada, 0) );
						} catch (DataNullException | DataLengthException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
						}
						if (visitas != null) {
							visitas.setLocationRelativeTo(null);
	      	        		visitas.setVisible(true);
						}
                	  
                  } 
                   
                }
				
        		
        	}
        });
        
        JButton btAgregarVisita = new JButton("AgregarVisita");
        btAgregarVisita.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		int filaSeleccionada = tabla.getSelectedRow();
				if (filaSeleccionada >= 0) {
                    String tipo =(String) tabla.getValueAt(filaSeleccionada,1);
                	String codOrden = (String) tabla.getValueAt(filaSeleccionada, 0);	
					if("ORDEN_RETIRO".equals(tabla.getValueAt(filaSeleccionada, 1))) {
					
						AltaVisitaRetiro av;
						try {
							av = new AltaVisitaRetiro(api,codOrden);
							av.setLocationRelativeTo(null);
							av.setVisible(true);
							
							actualizarTabla("Todos", "");
						} catch (DataNullException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
						}
					
					}else if("ORDEN_ENTREGA".equals(tabla.getValueAt(filaSeleccionada, 1))){
						AltaVisitaEntrega av;
						try {
							av = new AltaVisitaEntrega(api,codOrden);
							av.setLocationRelativeTo(null);
							av.setVisible(true);
							
							actualizarTabla("Todos", "");
						} catch (DataNullException e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
						}
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

        		    String tipo = (String) tabla.getValueAt(filaSeleccionada, 1);
        		    String codigo = (String) tabla.getValueAt(filaSeleccionada, 0);
        		    ArrayList<BienDTO> lista = new ArrayList<>();

        		    try {

        		        if ("ORDEN_RETIRO".equalsIgnoreCase(tipo)) {

        		            lista = (ArrayList<BienDTO>) api.obtenerBienesPorOrdenRetiro(codigo);

        		        } else if ("ORDEN_PEDIDO".equalsIgnoreCase(tipo)) {

        		            lista = (ArrayList<BienDTO>) api.obtenerBienesPorOrdenPedido(codigo);

        		        } else if ("ORDEN_ENTREGA".equalsIgnoreCase(tipo)) {

        		            OrdenEntregaDTO entrega = api.obtenerOrdenEntrega(codigo);
        		            lista = entrega.getEntregados();

        		        }

        		        ListadoBienes bienes = new ListadoBienes(api, lista);
        		        bienes.setLocationRelativeTo(null);
        		        bienes.setVisible(true);

        		    } catch (Exception e1) {
        		        JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
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

        List<OrdenDTO> ordenes;
        try {
            ordenes = api.obtenerOrdenes();
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        modelo.setRowCount(0);

        // ================= ORDEN RETIRO =================
        if ("ORDEN_RETIRO".equals(filtro)) {

            List<OrdenRetiroDTO> retiros = ordenes.stream()
                .filter(o -> "ORDEN_RETIRO".equals(o.getTipo()))
                .map(o -> (OrdenRetiroDTO) o)
                .collect(Collectors.toList());

            modelo.setColumnIdentifiers(new String[]{
                "Codigo", "Tipo", "Fecha", "Estado", "Codigo Pedido", "Voluntario", "Visitas"
            });

            for (OrdenRetiroDTO or : retiros) {
                String visitas = String.join(", ", or.getCodVisitas());
                modelo.addRow(new Object[]{
                    or.getCodigo(),
                    or.getTipo(),
                    or.getFechaEmision(),
                    or.getEstado(),
                    or.getPedido(),
                    or.getCodVoluntario(),
                    visitas
                });
            }

        // ================= ORDEN PEDIDO =================
        } else if ("ORDEN_PEDIDO".equals(filtro)) {

            List<OrdenPedidoDTO> pedidos = ordenes.stream()
                .filter(o -> "ORDEN_PEDIDO".equals(o.getTipo()))
                .map(o -> (OrdenPedidoDTO) o)
                .collect(Collectors.toList());

            modelo.setColumnIdentifiers(new String[]{
                "Codigo", "Tipo", "Observaciones", "Fecha", "Estado", "Donacion"
            });

            for (OrdenPedidoDTO op : pedidos) {
                modelo.addRow(new Object[]{
                    op.getCodigo(),
                    op.getTipo(),
                    op.getObservaciones(),
                    op.getFechaEmision(),
                    op.getEstado(),
                    op.getCodDonacion()
                });
            }

  
     
        	// ================= ORDEN ENTREGA =================
        } else if ("ORDEN_ENTREGA".equals(filtro)) {

            List<OrdenEntregaDTO> entregas = ordenes.stream()
                .filter(o -> "ORDEN_ENTREGA".equals(o.getTipo()))
                .map(o -> (OrdenEntregaDTO) o)
                .collect(Collectors.toList());

            modelo.setColumnIdentifiers(new String[]{
                "Codigo", "Tipo", "Fecha", "Estado", "Beneficiario", "Voluntario"
            });

            for (OrdenEntregaDTO oe : entregas) {
                modelo.addRow(new Object[]{
                    oe.getCodigo(),
                    oe.getTipo(),
                    oe.getFechaEmision(),
                    oe.getEstado(),
                    oe.getBeneficiario().getNombre(),   
                    oe.getVoluntario().getNombre()
                });
            }
        } else {
        	  // ================= TODOS =================
            modelo.setColumnIdentifiers(new String[]{
                "Codigo", "Tipo", "Fecha", "Estado"
            });

            for (OrdenDTO o : ordenes) {

                String codigo = "";

                if (o instanceof OrdenPedidoDTO) {
                    codigo = ((OrdenPedidoDTO) o).getCodigo();
                } else if (o instanceof OrdenRetiroDTO) {
                    codigo = ((OrdenRetiroDTO) o).getCodigo();
                } else if (o instanceof OrdenEntregaDTO) {
                    codigo = ((OrdenEntregaDTO) o).getCodigo();
                }

                modelo.addRow(new Object[]{
                    codigo,
                    o.getTipo(),
                    o.getFechaEmision(),
                    o.getEstado()
                });
            }
            }
        }
    }



