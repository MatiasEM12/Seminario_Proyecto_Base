package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.dto.OrdenEntregaDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.SolicitudBienDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AltaVisitaEntrega extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	IApi api;
	  private JTextField txtCodOrden;
	    private JComboBox<String> comboTipo;
	    private JTextArea txtObservaciones;
	    private JTextField txtCodBeneficiario;
	    private JTextField textCodVoluntario;

	    private LocalDate fecha = null;
	    private OrdenEntregaDTO orden;
	  
	    private ArrayList<BienDTO> bienesEntregados = new ArrayList<>();
	    JRadioButton rdbtnRadioButtonEsFinal;
	    private JTextField textFieldFecha;
	    private JTextField textCodBeneficiario;

	public AltaVisitaEntrega(IApi api, String codOrdenEntrega) {
		this.api=api;
		  try {
	            orden = api.obtenerOrdenEntrega(codOrdenEntrega);
	        } catch (DAOException e) {
	            JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setBounds(100, 100, 520, 607);
	        contentPane = new JPanel();
	        contentPane.setLayout(null);
	        setContentPane(contentPane);

	        // ===================== ORDEN RETIRO =====================
	        JLabel lblCodigo = new JLabel("Codigo OrdenEntrega:");
	        lblCodigo.setBounds(10, 10, 150, 14);
	        contentPane.add(lblCodigo);

	        txtCodOrden = new JTextField(codOrdenEntrega);
	        txtCodOrden.setEditable(false);
	        txtCodOrden.setBounds(170, 7, 143, 20);
	        contentPane.add(txtCodOrden);

	        // ===================== TIPO =====================
	        JLabel lblTipo = new JLabel("Tipo:");
	        lblTipo.setBounds(10, 52, 47, 14);
	        contentPane.add(lblTipo);

	        comboTipo = new JComboBox<>();
	        comboTipo.setBounds(168, 48, 145, 22);
	        comboTipo.addItem("ENTREGA");
	      
	        contentPane.add(comboTipo);

	        // ===================== BIENES =====================
	        JLabel lblSeleccion = new JLabel("Seleccionar bienes:");
	        lblSeleccion.setBounds(10, 111, 120, 14);
	        contentPane.add(lblSeleccion);

	        JButton btnSeleccionBien = new JButton("Bienes");
	        btnSeleccionBien.setBounds(170, 107, 143, 23);
	        btnSeleccionBien.addActionListener(e -> abrirSelectorBienes(orden.getSolicitud()));
	        contentPane.add(btnSeleccionBien);

	        // ===================== VOLUNTARIO =====================
	        JLabel lblVoluntario = new JLabel("Voluntario asignado:");
	        lblVoluntario.setBounds(10, 166, 150, 14);
	        contentPane.add(lblVoluntario);

	        textCodVoluntario = new JTextField(orden.getVoluntario().getCodigo());
	        textCodVoluntario.setEditable(false);
	        textCodVoluntario.setBounds(170, 163, 143, 20);
	        contentPane.add(textCodVoluntario);

	        // ===================== DONANTE =====================
	        JLabel lblCodBeneficiario = new JLabel("Beneficiario");
	        lblCodBeneficiario.setBounds(10, 225, 170, 14);
	        contentPane.add(lblCodBeneficiario);

	        textCodBeneficiario = new JTextField(orden.getBeneficiario().getCodigo());
	        textCodBeneficiario.setEditable(false);
	        textCodBeneficiario.setBounds(170, 222, 143, 20);
	        contentPane.add(textCodBeneficiario);


	        // ===================== FECHA =====================
	        JLabel lblFecha = new JLabel("Fecha Emision:");
	        lblFecha.setBounds(10, 277, 84, 14);
	        contentPane.add(lblFecha);

	        // ===================== OBSERVACIONES =====================
	        JLabel lblObserv = new JLabel("Observaciones:");
	        lblObserv.setBounds(10, 411, 100, 14);
	        contentPane.add(lblObserv);
	        JScrollPane scrollObs = new JScrollPane();
	        scrollObs.setBounds(10, 461, 480, 60);
	        contentPane.add(scrollObs);
	        
	                txtObservaciones = new JTextArea();
	                scrollObs.setViewportView(txtObservaciones);
	                txtObservaciones.setLineWrap(true);

	        JButton btnGuardar = new JButton("Guardar");
	        btnGuardar.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        		onGuardar();
	        	}
	        });
	        btnGuardar.setBounds(213, 532, 100, 25);
	        contentPane.add(btnGuardar);

	        JButton btnCancelar = new JButton("Cancelar");
	        btnCancelar.setBounds(368, 532, 100, 25);
	        contentPane.add(btnCancelar);
	        
	         rdbtnRadioButtonEsFinal = new JRadioButton("esFinal");
	        rdbtnRadioButtonEsFinal.setBounds(21, 350, 109, 23);
	        contentPane.add(rdbtnRadioButtonEsFinal);
	        
	        fecha= LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String fechaString = fecha.format(formatter);
	        
	        textFieldFecha = new JTextField(fechaString);
	        textFieldFecha.setEditable(false);
	        textFieldFecha.setBounds(170, 274, 143, 20);
	        contentPane.add(textFieldFecha);
	        
	      
	        btnCancelar.addActionListener(e -> dispose());
	   
	        setLocationRelativeTo(null);
	        setVisible(true);
	    }

	    // ===================== MÉTODOS =====================

	    private void abrirSelectorBienes(SolicitudBienDTO solicitud) {

	        List<BienDTO> bienesSolicitud = solicitud.getBienesSolicitados();
	        List<BienDTO> bienesYaEntregados=null;

	        try {
	            
					bienesYaEntregados =
					    api.obtenerBienesPorOrdenEntrega(orden.getCodigo());
			
	        } catch (DAOException e) {
	            JOptionPane.showMessageDialog(this,
	                    "Error al obtener bienes retirados",
	                    "Error",
	                    JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        ArrayList<BienDTO> bienesPendientes =
	                noRetirados(bienesYaEntregados, bienesSolicitud);

	        ListadoBienesOrden listado = new ListadoBienesOrden(
	                api,
	                bienesPendientes,
	                seleccion -> {
	                    if (seleccion != null) {
	                        bienesEntregados = seleccion;
	                    }
	                }
	        );

	        listado.setLocationRelativeTo(this);
	        listado.setVisible(true);
	    }

	    private void onGuardar() { 
	     
	    	String estado=null;
	    	if(bienesEntregados.isEmpty()) {
	    		estado="Pendiente";
	    	}else {
	    		estado="En proceso";
	    	}
	        try {
	            VisitaDTO visita = new VisitaDTO(
	                    fecha,
	                    orden.getVoluntario().getCodigo(),
	                    txtCodOrden.getText(),
	                    bienesEntregados,
	                    txtObservaciones.getText(),
	                    (String) comboTipo.getSelectedItem(),
	                    rdbtnRadioButtonEsFinal.isSelected(),estado
	                   
	            );

	            api.cargarVisitaEntrega(visita);
	            JOptionPane.showMessageDialog(this, "Visita registrada correctamente");

	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(this,
	                    "Error al registrar visita: " + ex.getMessage(),
	                    "Error",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private ArrayList<BienDTO> noRetirados(List<BienDTO> listaBienesRetirados,List<BienDTO> listaBienesARetirar) {

	    	ArrayList<BienDTO> faltantes = new ArrayList<>();

	    	if (listaBienesARetirar == null) {
	    		return faltantes;
	    	}

	    	java.util.Set<String> codigosRetirados = new java.util.HashSet<>();

	    	if (listaBienesRetirados != null) {
	    		for (BienDTO b : listaBienesRetirados) {
	    			if (b != null && b.getCodigo() != null) {
	    				codigosRetirados.add(b.getCodigo());
	    			}
	    		}
	    	}

	    	for (BienDTO bien : listaBienesARetirar) {

	    		if (bien != null && bien.getCodigo() != null) {

	    			if (!codigosRetirados.contains(bien.getCodigo())) {
	    				faltantes.add(bien);
	    			}
	    		}
	    	}

	    	return faltantes;
	    }
}



