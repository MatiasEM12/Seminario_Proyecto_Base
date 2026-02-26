package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BeneficiarioDTO;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.OrdenEntregaDTO;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.SolicitudBienDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.dto.VoluntarioDTO;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;

import com.toedter.calendar.JCalendar;

public class AltaOrdenEntrega extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;



	private OrdenEntregaDTO ordenEntrega;
    private VoluntarioDTO voluntario;

	private JTextField txtFecha;
	private JTextField txtEstado;
	private JTextField txtCodigoBeneficiario;
	IApi api;
	private JTextField txtCodVoluntario;
	private JCalendar calendar;
	private JTextField textCodSolicitud;
	private LocalDate fechaProgramada=null;

	/**
	 * Create the frame.
	 */
	public AltaOrdenEntrega(IApi api, SolicitudBienDTO solicitud) {
		this.api=api;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 381, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel codBeneficiario = new JLabel("Codigo Beneficiario");
		codBeneficiario.setBounds(10, 73, 133, 13);
		contentPane.add(codBeneficiario);
		
		JLabel lblNewLabel_1_1 = new JLabel("Estado:");
		lblNewLabel_1_1.setBounds(10, 155, 102, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Fecha Emision:");
		lblNewLabel_2.setBounds(10, 185, 96, 13);
		contentPane.add(lblNewLabel_2);
		
		LocalDate fecha = LocalDate.now();
		String texto = fecha.toString();
		txtFecha = new JTextField(texto);
		txtFecha.setEditable(false);
		txtFecha.setBounds(140, 181, 96, 19);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);
		
		txtEstado = new JTextField("Pendiente");
		txtEstado.setEditable(false);
		txtEstado.setColumns(10);
		txtEstado.setBounds(140, 151, 96, 19);
		contentPane.add(txtEstado);
		
		txtCodigoBeneficiario = new JTextField(solicitud.getBeneficiario());
		txtCodigoBeneficiario.setEditable(false);
		txtCodigoBeneficiario.setColumns(10);
		txtCodigoBeneficiario.setBounds(140, 69, 96, 19);
		contentPane.add(txtCodigoBeneficiario);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(175, 432, 85, 21);
		contentPane.add(btnGuardar);
		
		JButton btnNewButton_1 = new JButton("Cerrar");
		btnNewButton_1.setBounds(270, 432, 85, 21);
		contentPane.add(btnNewButton_1);
		 btnNewButton_1.addActionListener(e -> dispose());
		
		JLabel seleccionarVoluntario = new JLabel("Selecionar Voluntario");
		seleccionarVoluntario.setBounds(10, 97, 133, 13);
		contentPane.add(seleccionarVoluntario);
		
		JButton btnVoluntarios = new JButton("Voluntario");
		btnVoluntarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoVoluntarios ventanaVoluntarios = new ListadoVoluntarios(null, api,AltaOrdenEntrega.this);
				ventanaVoluntarios.setLocationRelativeTo(AltaOrdenEntrega.this);
				ventanaVoluntarios.setVisible(true);
			}
		});
		btnVoluntarios.setBounds(140, 93, 151, 21);
		contentPane.add(btnVoluntarios);
		
		txtCodVoluntario = new JTextField();
		txtCodVoluntario.setEditable(false);
		txtCodVoluntario.setColumns(10);
		txtCodVoluntario.setBounds(140, 121, 96, 19);
		contentPane.add(txtCodVoluntario);
		
		JLabel codVoluntario = new JLabel("Codigo Voluntario");
		codVoluntario.setBounds(10, 131, 133, 13);
		contentPane.add(codVoluntario);
		
		JLabel lblNewLabel_2_1 = new JLabel("Fecha Programada:");
		lblNewLabel_2_1.setBounds(10, 220, 96, 13);
		contentPane.add(lblNewLabel_2_1);
		
		calendar = new JCalendar();
		calendar.setBounds(107, 222, 184, 153);
		contentPane.add(calendar);
		
		JButton btnVerBienes = new JButton("Ver Bienes");
		btnVerBienes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ListadoBienes ventanaBienes= new ListadoBienes(api,solicitud.getBienesSolicitados());
				ventanaBienes.setLocationRelativeTo(AltaOrdenEntrega.this);
				ventanaBienes.setVisible(true);
			}
		});
		btnVerBienes.setBounds(10, 386, 102, 21);
		contentPane.add(btnVerBienes);
		
		textCodSolicitud = new JTextField(solicitud.getCodigo());
		textCodSolicitud.setEditable(false);
		textCodSolicitud.setColumns(10);
		textCodSolicitud.setBounds(140, 39, 96, 19);
		contentPane.add(textCodSolicitud);
		
		JLabel codSolicitud = new JLabel("Codigo Solicitud");
		codSolicitud.setBounds(10, 42, 133, 13);
		contentPane.add(codSolicitud);
		btnGuardar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		     
		    	String codigo = txtCodigoBeneficiario.getText();
		    	String estado= "Pendiente";
		    	String fechaTexto = txtFecha.getText();
		  
		    
		    	
		    	try {
		    	    // Convertir texto a LocalDate
		    	    LocalDate fecha = LocalDate.parse(fechaTexto);
		    	    
		    	    
			    	if(voluntario==null) {
			    	       JOptionPane.showMessageDialog(
				    	            null,
				    	            "el voluntario no puede ser nulo",
				    	            "Error al cargar Entrega",
				    	            JOptionPane.ERROR_MESSAGE
				    	        );
			    	}
		    	    fechaProgramada = calendar.getDate()
		    	            .toInstant()
		    	            .atZone(ZoneId.systemDefault())
		    	            .toLocalDate();

		    	    if (fechaProgramada.isBefore(LocalDate.now())) {
		    	        JOptionPane.showMessageDialog(
		    	            null,
		    	            "La fecha programada no puede ser anterior a hoy",
		    	            "Fecha inválida",
		    	            JOptionPane.ERROR_MESSAGE
		    	        );
		    	      
		    	    }
		    	    ordenEntrega = new OrdenEntregaDTO(
		    	    		fecha,estado,null,fechaProgramada,new ArrayList<VisitaDTO>(),new ArrayList<BienDTO>(),solicitud,
		    	    		api.obtenerBeneficiarioDTO(solicitud.getBeneficiario()),voluntario		    	
		    	    	);
		    	    api.registrarOrdenEntrega(ordenEntrega);

                  
                    // Limpieza
          
                    txtCodigoBeneficiario.setText("");
                    txtEstado.setText("");
                    txtFecha.setText("");

                    JOptionPane.showMessageDialog(null, 
                        "Orden de Entrega registrada correctamente.",
                        "OK",
                        JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage(),
                        "Error al crear la orden de entrega",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }


    void recibirVoluntario(VoluntarioDTO voluntario) {
       this.voluntario=voluntario;
       txtCodVoluntario.setText(voluntario.getCodigo());
    
	}
}
