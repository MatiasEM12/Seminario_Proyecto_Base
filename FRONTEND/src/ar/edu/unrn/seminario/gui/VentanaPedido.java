package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.SolicitudBienDTO;

public class VentanaPedido extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField beneficiarioTextField;
	private DefaultTableModel modelo;
	private JTable bienesTable;
	private DefaultTableModel tableModel;
	private JTable table;
	private JButton verificarStockButton;

	private JButton notificarBeneficiarioButton;
	private JButton crearOrdenEntregaButton;
	private JButton cerrarButton;
	private ArrayList<BienDTO> bienes;
	private boolean disponible;

	public VentanaPedido(IApi api, SolicitudBienDTO solicitud  ) {

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

		beneficiarioTextField = new JTextField(solicitud.getCodigo());
		beneficiarioTextField.setBounds(120, 17, 150, 22);
		beneficiarioTextField.setEditable(false); 
		contentPane.add(beneficiarioTextField);
		String[] titulos = { "Codigo", "Tipo","Nombre","Desc","Peso","Vencimiento","Talle","Material"};
		modelo = new DefaultTableModel(new Object[][] {}, titulos);
		this.bienes = new ArrayList<>(solicitud.getBienesSolicitados()== null ? new ArrayList<>() : solicitud.getBienesSolicitados());
		
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

		JScrollPane scrollTabla = new JScrollPane(bienesTable);
		scrollTabla.setBounds(30, 80, 690, 160);
		contentPane.add(scrollTabla);
		
				// Tabla bienes solicitados
				table = new JTable();
				JScrollPane scrollPane = new JScrollPane(table);
				scrollTabla.setColumnHeaderView(scrollPane);
				table.setModel(modelo);

		// Botones de verificación
		verificarStockButton = new JButton("Verificar Stock");
		verificarStockButton.setBounds(30, 255, 140, 25);
		contentPane.add(verificarStockButton);

		// Botones de acción inferiores
		notificarBeneficiarioButton = new JButton("Notificar Beneficiario");
		notificarBeneficiarioButton.setEnabled(false);
		notificarBeneficiarioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (disponible==true) {
					JOptionPane.showMessageDialog(null, "Se le notifico al Beneficiario que se le entregaran los Bienes deseados");
				}else {
					JOptionPane.showMessageDialog(null, "Se le notifico al Beneficiario que no se le prodran entregar los bienes por falta de Stock");
				}
			}
		});
		notificarBeneficiarioButton.setBounds(210, 320, 170, 28);
		contentPane.add(notificarBeneficiarioButton);

		crearOrdenEntregaButton = new JButton("Crear Orden Entrega");
		crearOrdenEntregaButton.setEnabled(false);
		crearOrdenEntregaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 AltaOrdenEntrega ventanaEntrega = new AltaOrdenEntrega(api, solicitud);
				 ventanaEntrega.setVisible(true);
			}
		});
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
				disponible=false;
				disponible= api.verificarDisponiblilidad(solicitud.getBienesSolicitados());
				if (disponible==true) {
					 JOptionPane.showMessageDialog(null, "Stock disponible");
					 crearOrdenEntregaButton.setEnabled(true);
					
				}else {
					 JOptionPane.showMessageDialog(null, "No hay Stock disponible");
					 
				}
				notificarBeneficiarioButton.setEnabled(true);
			}
		};

		verificarStockButton.addActionListener(mostrarResultadoStock);
	}


	
	public JTextField getBeneficiarioTextField() { return beneficiarioTextField; }
	public JTable getBienesTable() { return bienesTable; }
	public DefaultTableModel getTableModel() { return tableModel; }


	
}


