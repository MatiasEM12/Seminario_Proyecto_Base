package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.exception.StateChangeException;

public class AltaUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField usuarioTextField;
	private JTextField contrasenaTextField;
	private JTextField nombreTextField;
	private JTextField emailTextField;
	private JComboBox rolComboBox;

	private List<RolDTO> roles = new ArrayList<>();

	/**
	 * Create the frame.
	 * @throws StateChangeException 
	 */
	public AltaUsuario(IApi api) throws StateChangeException {

		// Obtengo los roles
		this.roles = api.obtenerRolesActivos();
		setTitle("Alta Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel usuarioLabel = new JLabel("Usuario:");
		usuarioLabel.setBounds(43, 16, 76, 16);
		contentPane.add(usuarioLabel);

		JLabel contrasenaLabel = new JLabel("Contrase\u00F1a:");
		contrasenaLabel.setBounds(43, 56, 93, 16);
		contentPane.add(contrasenaLabel);

		usuarioTextField = new JTextField();
		usuarioTextField.setBounds(148, 13, 160, 22);
		contentPane.add(usuarioTextField);
		usuarioTextField.setColumns(10);

		contrasenaTextField = new JTextField();
		contrasenaTextField.setBounds(148, 53, 160, 22);
		contentPane.add(contrasenaTextField);
		contrasenaTextField.setColumns(10);

		JButton aceptarButton = new JButton("Aceptar");
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				RolDTO rol = roles.get(rolComboBox.getSelectedIndex());

				try {
					
					
					
					
					api.registrarUsuario(usuarioTextField.getText(), contrasenaTextField.getText(),
							nombreTextField.getText(), emailTextField.getText(), rol.getCodigo(),false);
					JOptionPane.showMessageDialog(null, "Usuario registrado con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
					//en realidad en alta usuario solo puede llegar a ocurir dataEmtyException.
				} catch (DataEmptyException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (DataObjectException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (DataNullException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (DataDateException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

			}
		});
		aceptarButton.setBounds(218, 215, 97, 25);
		contentPane.add(aceptarButton);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		cancelarButton.setBounds(323, 215, 97, 25);
		contentPane.add(cancelarButton);

		JLabel nombreLabel = new JLabel("Nombre:");
		nombreLabel.setBounds(43, 88, 56, 16);
		contentPane.add(nombreLabel);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(43, 125, 56, 16);
		contentPane.add(emailLabel);

		JLabel rolLabel = new JLabel("Rol:");
		rolLabel.setBounds(43, 154, 56, 16);
		contentPane.add(rolLabel);

		nombreTextField = new JTextField();
		nombreTextField.setBounds(148, 85, 160, 22);
		contentPane.add(nombreTextField);
		nombreTextField.setColumns(10);

		emailTextField = new JTextField();
		emailTextField.setBounds(148, 122, 160, 22);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);

		rolComboBox = new JComboBox();
		rolComboBox.setBounds(148, 151, 160, 22);
		contentPane.add(rolComboBox);

		for (RolDTO rol : this.roles) {
			rolComboBox.addItem(rol.getNombre());
		}

	}
	
	
	
}
