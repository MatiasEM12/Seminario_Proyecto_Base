package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.modelo.Bien;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class ModificarBien extends JFrame{

	private Bien bien;
    private IApi api;
    private VentanaInventario inventario;
    
    
    private JTextField textField;//tipo
    private JTextField textField_1;//nombre
    private JTextField textField_2;//descripcion
    private JTextField textField_3;//vencimiento
    private JTextField textField_4;//talle
    private JTextField textField_5;//peso
    private JTextField textField_6;//material

	/**
	 * Create the application.
	 */
	public ModificarBien(IApi api, Bien bien, VentanaInventario inventario) {
        this.api = api;
        this.bien = bien;
        this.inventario = inventario;
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        getContentPane().setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Tipo");
        lblNewLabel.setBounds(139, 28, 31, 20);
        getContentPane().add(lblNewLabel);
        
        textField = new JTextField(bien.getTipo());
        textField.setEditable(false);
        textField.setBounds(180, 29, 96, 20);
        getContentPane().add(textField);
        textField.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Nombre");
        lblNewLabel_1.setBounds(126, 58, 44, 20);
        getContentPane().add(lblNewLabel_1);
        
        textField_1 = new JTextField(bien.getNombre());
        textField_1.setBounds(180, 59, 96, 18);
        getContentPane().add(textField_1);
        textField_1.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Descripcion");
        lblNewLabel_2.setBounds(110, 86, 60, 20);
        getContentPane().add(lblNewLabel_2);
        
        textField_2 = new JTextField(bien.getDescripcion());
        textField_2.setBounds(180, 87, 96, 18);
        getContentPane().add(textField_2);
        textField_2.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Vencimiento");
        lblNewLabel_3.setBounds(110, 114, 60, 20);
        getContentPane().add(lblNewLabel_3);
        
        if(bien.getFechaVencimiento()!=null) {
        	textField_3 = new JTextField(bien.getFechaVencimiento().toString());
        }
        else {
        	textField_3 = new JTextField("");
        }
        textField_3.setBounds(180, 115, 96, 18);
        getContentPane().add(textField_3);
        textField_3.setColumns(10);
        
        JLabel lblNewLabel_4 = new JLabel("Talle");
        lblNewLabel_4.setBounds(139, 144, 31, 16);
        getContentPane().add(lblNewLabel_4);
        
        textField_4 = new JTextField(String.valueOf(bien.getTalle()));
        textField_4.setBounds(180, 143, 96, 18);
        getContentPane().add(textField_4);
        textField_4.setColumns(10);
        
        JLabel lblNewLabel_5 = new JLabel("Peso");
        lblNewLabel_5.setBounds(139, 170, 31, 20);
        getContentPane().add(lblNewLabel_5);
        
        textField_5 = new JTextField(String.valueOf(bien.getPeso()));
        textField_5.setBounds(180, 171, 96, 18);
        getContentPane().add(textField_5);
        textField_5.setColumns(10);
        
        JLabel lblNewLabel_6 = new JLabel("Material");
        lblNewLabel_6.setBounds(126, 200, 44, 15);
        getContentPane().add(lblNewLabel_6);
        
        if (bien.getMaterial()!=null) {
        	textField_6 = new JTextField(bien.getMaterial());
        }
        else {
        	textField_6=new JTextField("");
        }
        textField_6.setBounds(180, 199, 96, 18);
        getContentPane().add(textField_6);
        textField_6.setColumns(10);
        
        
        //boton cancelar
        JButton btnNewButton = new JButton("Cancelar");
        btnNewButton.addActionListener(e->dispose());
        getContentPane().add(btnNewButton);
        
        
        //boton guardar falta implementacion
        btnNewButton.setBounds(218, 233, 84, 20);
        getContentPane().add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Guardar");
        btnNewButton_1.addActionListener(e -> {
        	try {
        		bien.setNombre(textField_1.getText());
        		bien.setDescripcion(textField_2.getText());
        		if (!textField_3.getText().isBlank()) {
        			bien.setFechaVencimiento(LocalDate.parse(textField_3.getText()));
        		}
        		if (!textField_4.getText().isBlank()) {
            		bien.setTalle(Double.parseDouble(textField_4.getText()));
        		}
        		if (!textField_5.getText().isBlank()) {
            		bien.setPeso(Double.parseDouble(textField_5.getText()));
        		}
        		if (!textField_6.getText().isBlank()) {
            		bien.setMaterial(textField_6.getText());
        		}
        		api.ModificarBienInventario(bien);
        		JOptionPane.showMessageDialog(null, "Se modifico corectamente los datos del Bien", "Actualizacion", JOptionPane.INFORMATION_MESSAGE);
        	}catch(Exception e1){
        		JOptionPane.showMessageDialog(null, "Algo salio mal al intentar actualizar"+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        		
        	}
        });
        btnNewButton_1.setBounds(86, 233, 84, 20);
        getContentPane().add(btnNewButton_1);
        
        String tipo = bien.getTipo();
        
        
        //habilita los campos que puede editar el tipo de bien
        if (tipo.equalsIgnoreCase("Mueble") || tipo.equalsIgnoreCase("Electrodomestico")) {//puede editar el peso y el material
        	habilitarCampo(textField_5);
        	habilitarCampo(textField_6);
        	
        	bloquearCampo(textField_3);
        	bloquearCampo(textField_4);
        }
        
        else { 
        	if (tipo.equalsIgnoreCase("Alimento") || tipo.equalsIgnoreCase("Medicamento")) {//puede editar la fecha de vencimiento
        		habilitarCampo(textField_3);
        		
        		bloquearCampo(textField_4);
        		bloquearCampo(textField_5);
        		bloquearCampo(textField_6);
        	}
        	
        	else{
        		if (tipo.equalsIgnoreCase("Ropa")) {//puede editar el talle y el material
        			habilitarCampo(textField_4);
        			habilitarCampo(textField_6);
        			
        			bloquearCampo(textField_3);
        			bloquearCampo(textField_5);
        		}
        		else {// otros pueden editoar todo esepto el tipo
        			habilitarCampo(textField_3);
        			habilitarCampo(textField_4);
        			habilitarCampo(textField_5);
        			habilitarCampo(textField_6);
        		}
        		}
        	}
        	
        }
	private void bloquearCampo(JTextField campo) {
        campo.setEditable(false);
    }

    private void habilitarCampo(JTextField campo) {
        campo.setEditable(true);
    }

}

