package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaPedido extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField beneficiarioTextField;

	private JTable bienesTable;
	private DefaultTableModel tableModel;

	private JButton verificarStockButton;
	private JButton checkButton;

	private JButton notificarBeneficiarioButton;
	private JButton crearOrdenEntregaButton;
	private JButton cerrarButton;

	public VentanaPedido() {

		setTitle("Pedido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 760, 420);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel beneficiarioLabel = new JLabel("Beneficiario:");
		beneficiarioLabel.setBounds(30, 20, 90, 16);
		contentPane.add(beneficiarioLabel);

		beneficiarioTextField = new JTextField();
		beneficiarioTextField.setBounds(120, 17, 250, 22);
		beneficiarioTextField.setEditable(false); // "inmutable"
		contentPane.add(beneficiarioTextField);

		// Tabla bienes solicitados
		JLabel tablaLabel = new JLabel("Campos de la Tabla datos de bienes solicitados");
		tablaLabel.setBounds(30, 55, 320, 16);
		contentPane.add(tablaLabel);

		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] { "Tipo", "Nombre", "Descripción", "Cantidad", "Estado Stock" }
		) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tabla solo lectura
			}
		};

		bienesTable = new JTable(tableModel);

		JScrollPane scrollTabla = new JScrollPane(bienesTable);
		scrollTabla.setBounds(30, 80, 690, 160);
		contentPane.add(scrollTabla);

		// Botones de verificación
		verificarStockButton = new JButton("Verificar Stock");
		verificarStockButton.setBounds(30, 255, 140, 25);
		contentPane.add(verificarStockButton);

		checkButton = new JButton("Check");
		checkButton.setBounds(180, 255, 90, 25);
		contentPane.add(checkButton);

		// Botones de acción inferiores
		notificarBeneficiarioButton = new JButton("Notificar Beneficiario");
		notificarBeneficiarioButton.setBounds(210, 320, 170, 28);
		contentPane.add(notificarBeneficiarioButton);

		crearOrdenEntregaButton = new JButton("Crear Orden Entrega");
		crearOrdenEntregaButton.setBounds(390, 320, 170, 28);
		contentPane.add(crearOrdenEntregaButton);

		cerrarButton = new JButton("Cerrar");
		cerrarButton.setBounds(570, 320, 150, 28);
		contentPane.add(cerrarButton);

		
		cerrarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

		// stock
		ActionListener mostrarResultadoStock = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DialogoStock(VentanaPedido.this, "Hay Stock / no hay Stock").setVisible(true);
			}
		};

		verificarStockButton.addActionListener(mostrarResultadoStock);
		checkButton.addActionListener(mostrarResultadoStock);
	}

	// Dialogo 
	private static class DialogoStock extends JFrame {

		private static final long serialVersionUID = 1L;

		public DialogoStock(JFrame owner, String mensaje) {

			setTitle("Stock");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(owner.getX() + 80, owner.getY() + 140, 620, 140);
			setResizable(false);

			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(10, 10, 10, 10));
			panel.setLayout(null);
			setContentPane(panel);

			JLabel msgLabel = new JLabel(mensaje);
			msgLabel.setBounds(30, 30, 300, 25);
			panel.add(msgLabel);

			JButton cerrar = new JButton("cerrar");
			cerrar.setBounds(430, 55, 130, 25);
			panel.add(cerrar);

			cerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
		}
	}

	
	public JTextField getBeneficiarioTextField() { return beneficiarioTextField; }
	public JTable getBienesTable() { return bienesTable; }
	public DefaultTableModel getTableModel() { return tableModel; }

	
}


