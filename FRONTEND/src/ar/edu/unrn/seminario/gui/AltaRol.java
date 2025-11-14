package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.exception.DataNullException;

public class AltaRol extends JFrame {

	 private static final long serialVersionUID = 1L;
	    private JPanel contentPane;
	    private JTextField textDescripcion;
	    private JTextField textCodigo;
	    private Boolean estado;
	    private Boolean activo, inactivo;

	    private JRadioButton rdbtnActivado;
	    private JRadioButton rdbtnDesactivado;
	    private JTextField textNombre;
	    //private List<RolDTO> roles = new ArrayList<>();

	/**
	 * Create the frame.
	 */
	public AltaRol(IApi api) {
		
		//this.roles = api.obtenerRoles();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelButtom = new JPanel(new GridLayout(1, 0, 5, 5));
        panelButtom.setBounds(197, 216, 227, 34);
        contentPane.add(panelButtom);

        JButton btnAceptarR = new JButton("Aceptar");
        panelButtom.add(btnAceptarR);

        JButton btnCancelarR = new JButton("Cancelar");
        
        
        btnCancelarR.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        panelButtom.add(btnCancelarR);

        JPanel panelRol = new JPanel();
        panelRol.setBounds(125, 11, 227, 194);
        contentPane.add(panelRol);
        panelRol.setLayout(null);

        textDescripcion = new JTextField();
        textDescripcion.setBounds(0, 85, 221, 56);
        panelRol.add(textDescripcion);
        textDescripcion.setColumns(10);

        rdbtnActivado = new JRadioButton("Activado");
        rdbtnActivado.setBounds(10, 148, 109, 23);
        panelRol.add(rdbtnActivado);

        rdbtnDesactivado = new JRadioButton("Desactivado");
        rdbtnDesactivado.setBounds(112, 148, 109, 23);
        panelRol.add(rdbtnDesactivado);

        textCodigo = new JTextField();
        textCodigo.setBounds(0, 11, 221, 20);
        panelRol.add(textCodigo);
        textCodigo.setColumns(10);

        JPanel panelLabel = new JPanel();
        panelLabel.setBounds(10, 11, 94, 194);
        contentPane.add(panelLabel);
        panelLabel.setLayout(null);

        JLabel lblCodigo = new JLabel("Codigo :");
        lblCodigo.setBounds(10, 11, 64, 14);
        panelLabel.add(lblCodigo);

        JLabel lblDescripcion = new JLabel("Descripcion : ");
        lblDescripcion.setBounds(10, 87, 84, 14);
        panelLabel.add(lblDescripcion);

        JLabel lblEstado = new JLabel("Estado ");
        lblEstado.setBounds(10, 153, 46, 14);
        panelLabel.add(lblEstado);
        
        JLabel lblNombre = new JLabel("Nombre :");
        lblNombre.setBounds(10, 49, 64, 14);
        panelLabel.add(lblNombre);

        // Asignar grupo
        ButtonGroup grupoEstado = new ButtonGroup();
        grupoEstado.add(rdbtnActivado);
        grupoEstado.add(rdbtnDesactivado);
        
        textNombre = new JTextField();
        textNombre.setColumns(10);
        textNombre.setBounds(0, 42, 221, 20);
        panelRol.add(textNombre);
        actualizarEstados();

        // Escuchadores para actualizar los booleanos
        rdbtnActivado.addActionListener(e -> actualizarEstados());
        rdbtnDesactivado.addActionListener(e -> actualizarEstados());

        // vinculados al  botón Aceptar
        btnAceptarR.addActionListener(e -> {
            actualizarEstados();
            estado= rdbtnActivado.isSelected();
            
            int cod = Integer.parseInt(textCodigo.getText());
            try {
            	//aqui esta el problema
				api.guardarRol(cod,textNombre.getText(),textDescripcion.getText(),estado);
			} catch (DataNullException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            
            AltaRol.this.setVisible(false);
        });
    }

    private void actualizarEstados() {
        this.activo = rdbtnActivado.isSelected();
        this.inactivo = rdbtnDesactivado.isSelected();
        
    }
}	   
	   


