package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;

public class AltaBienes extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JComboBox<String> tipoComboBox;
	private JTextField nombreTextField;
	private JTextArea descripcionTextArea;
	private JTextField vencimientoTextField;
	private JTextField talleTextField;
	private JTextField pesoTextField;
	private JTextField materialTextField;
	IApi api;
	/**
	 * Create the frame.
	 */
	public AltaBienes(IApi api) {
        initialize(api);
    }
	public AltaBienes() { //Borrar solo lo hice porque no le hice la logica
		initialize(null);
		configurarBloqueosPorTipo();
	}
	 private void initialize(IApi api) {
		 this.api = api;
		setTitle("Registrar ingreso de bienes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 520, 420);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel tipoLabel = new JLabel("Seleccionar tipo:");
		tipoLabel.setBounds(40, 25, 120, 16);
		contentPane.add(tipoLabel);

		tipoComboBox = new JComboBox();
		tipoComboBox.setBounds(170, 22, 200, 22);
		contentPane.add(tipoComboBox);

		tipoComboBox.addItem("Alimento");
		tipoComboBox.addItem("Medicamento");
		tipoComboBox.addItem("Mueble");
		tipoComboBox.addItem("Electrodoméstico");
		tipoComboBox.addItem("Vestimenta");
		tipoComboBox.addItem("Otro");

		JLabel nombreLabel = new JLabel("Nombre:");
		nombreLabel.setBounds(40, 65, 80, 16);
		contentPane.add(nombreLabel);

		nombreTextField = new JTextField();
		nombreTextField.setBounds(170, 62, 200, 22);
		contentPane.add(nombreTextField);
		nombreTextField.setColumns(10);

		JLabel descripcionLabel = new JLabel("Descripción:");
		descripcionLabel.setBounds(40, 105, 80, 16);
		contentPane.add(descripcionLabel);

		descripcionTextArea = new JTextArea();
		descripcionTextArea.setLineWrap(true);
		descripcionTextArea.setWrapStyleWord(true);

		JScrollPane scrollDescripcion = new JScrollPane(descripcionTextArea);
		scrollDescripcion.setBounds(170, 102, 280, 110);
		contentPane.add(scrollDescripcion);

		JLabel vencimientoLabel = new JLabel("Vencimiento:");
		vencimientoLabel.setBounds(40, 235, 100, 16);
		contentPane.add(vencimientoLabel);

		vencimientoTextField = new JTextField();
		vencimientoTextField.setBounds(170, 232, 200, 22);
		contentPane.add(vencimientoTextField);
		vencimientoTextField.setColumns(10);

		JLabel talleLabel = new JLabel("Talle:");
		talleLabel.setBounds(40, 270, 80, 16);
		contentPane.add(talleLabel);

		talleTextField = new JTextField();
		talleTextField.setBounds(170, 267, 200, 22);
		contentPane.add(talleTextField);
		talleTextField.setColumns(10);

		JLabel pesoLabel = new JLabel("Peso:");
		pesoLabel.setBounds(40, 305, 80, 16);
		contentPane.add(pesoLabel);

		pesoTextField = new JTextField();
		pesoTextField.setBounds(170, 302, 200, 22);
		contentPane.add(pesoTextField);
		pesoTextField.setColumns(10);

		JLabel materialLabel = new JLabel("Material:");
		materialLabel.setBounds(40, 340, 80, 16);
		contentPane.add(materialLabel);

		materialTextField = new JTextField();
		materialTextField.setBounds(170, 337, 200, 22);
		contentPane.add(materialTextField);
		materialTextField.setColumns(10);

		JButton guardarButton = new JButton("Guardar");
		guardarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		guardarButton.setBounds(250, 370, 95, 25);
		contentPane.add(guardarButton);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		cancelarButton.setBounds(355, 370, 95, 25);
		contentPane.add(cancelarButton);
	}
	 
	 public static void main(String[] args) { //borrar
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						AltaBienes frame = new AltaBienes(); // usa el constructor de prueba
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	// Agregá esto dentro de tu clase AltaBienes (misma clase que ya tenés)

	 private void configurarBloqueosPorTipo() {

	 	// 1) Cuando cambia el tipo, actualizo qué campos se habilitan
	 	tipoComboBox.addActionListener(new ActionListener() {
	 		public void actionPerformed(ActionEvent e) {
	 			aplicarReglasPorTipo();
	 		}
	 	});

	 	// 2) Estado inicial (por si arranca ya con un tipo seleccionado)
	 	aplicarReglasPorTipo();
	 }

	 private void aplicarReglasPorTipo() {

	 	String tipo = (String) tipoComboBox.getSelectedItem();

	 	// Defaults: habilito todo
	 	habilitarCampo(vencimientoTextField, true);
	 	habilitarCampo(talleTextField, true);
	 	habilitarCampo(pesoTextField, true);
	 	habilitarCampo(materialTextField, true);

	 	// Reglas por tipo (ajustá lo que te pidan)
	 	if ("Mueble".equalsIgnoreCase(tipo)) {
	 		// Un mueble no vence
	 		habilitarCampo(vencimientoTextField, false);

	 	} else if ("Electrodoméstico".equalsIgnoreCase(tipo)) {
	 		// Electrodoméstico: no tiene talle, pero sí peso/material (según tu criterio)
	 		habilitarCampo(talleTextField, false);

	 	} else if ("Vestimenta".equalsIgnoreCase(tipo)) {
	 		// Ropa: normalmente tiene talle, pero no peso/material (a tu gusto)
	 		habilitarCampo(pesoTextField, false);
	 		habilitarCampo(materialTextField, true); // ropa suele tener material
	 		// vencimiento normalmente no aplica (si querés bloquearlo también)
	 		habilitarCampo(vencimientoTextField, false);

	 	} else if ("Alimento".equalsIgnoreCase(tipo)) {
	 		// Alimento: vence, no talle, peso sí, material no
	 		habilitarCampo(talleTextField, false);
	 		habilitarCampo(materialTextField, false);

	 	} else if ("Medicamento".equalsIgnoreCase(tipo)) {
	 		// Medicamento: vence, no talle, peso/material no (ajustable)
	 		habilitarCampo(talleTextField, false);
	 		habilitarCampo(pesoTextField, false);
	 		habilitarCampo(materialTextField, false);

	 	} else if ("Otro".equalsIgnoreCase(tipo)) {
	 		// Otro: dejá todo habilitado (o lo que quieras)
	 	}
	 }

	 private void habilitarCampo(JTextField field, boolean habilitar) {
	 	field.setEnabled(habilitar);
	 	if (!habilitar) {
	 		field.setText("");
	 	}
	 }

}

