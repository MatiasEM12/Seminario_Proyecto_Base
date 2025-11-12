package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal(IApi api) {
		
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 300);

		//Barra de menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		
		//Menu de Usuarios
		
		JMenu usuarioMenu = new JMenu("Usuarios");
		menuBar.add(usuarioMenu);

		//Apartado Usuario bar 
		
		JMenuItem altaUsuarioMenuItem = new JMenuItem("Alta/Modificación");
		altaUsuarioMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				AltaUsuario alta = new AltaUsuario(api);
				alta.setLocationRelativeTo(null);
				alta.setVisible(true);
			}
			
		});
		usuarioMenu.add(altaUsuarioMenuItem);
		
		JMenuItem listadoUsuarioMenuItem = new JMenuItem("Listado");
		listadoUsuarioMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				ListadoUsuario listado;
				listado = new ListadoUsuario(api);
				listado.setLocationRelativeTo(null);
				listado.setVisible(true);
				
			}
			
		});
		usuarioMenu.add(listadoUsuarioMenuItem);
		
		
		//Menu Rol
		
		JMenu rolMenu= new JMenu("Roles");
		menuBar.add(rolMenu);
		
		//Apartado Rol bar
		JMenuItem altaRolMenuItem = new JMenuItem("Alta/Modificación Rol");
		altaRolMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				AltaRol alta = new AltaRol(api);
	        	alta.setLocationRelativeTo(null);
	        	alta.setVisible(true);
			}
			
			
		});
		
		rolMenu.add(altaRolMenuItem);
		 
		JMenuItem listadoRolMenuItem = new JMenuItem("Listado");
		listadoRolMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				ListadoRol listado;
				try {
					listado = new ListadoRol(api);
					listado.setLocationRelativeTo(null);
					listado.setVisible(true);
				} catch (StateChangeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		rolMenu.add(listadoRolMenuItem);
		
		
		
		
		
		//Menu Inventario
		JMenu menuInventario=new JMenu("Inventario");
		JMenuItem itemABien=new JMenuItem("Agregar Bien");
		JMenuItem itemListarInventario=new JMenuItem("Listado");
			
						
		//Menu Vehiculo
		JMenu menuVehiculo=new JMenu("Vehiculo");
		JMenuItem itemVehiculoAM=new JMenuItem("Alta/Modificacion");		
		JMenuItem itemListarVehiculo =new JMenuItem("Listado");
			
		//Menu Evento
		JMenu menuEvento=new JMenu("Evento");
		JMenuItem itemEventoAM=new JMenuItem("Alta/Modificacion");		
		JMenuItem itemListarEvento =new JMenuItem("Listado");
				
		//Menu Ruta
		JMenu menuRuta=new JMenu("Ruta");
		JMenuItem itemPRuta=new JMenuItem("Planificar Ruta");	
		JMenuItem itemListarRuta =new JMenuItem("Listado");
				
		//Menu Ordenes
		JMenu menuOrdenes=new JMenu("Ordenes");
		JMenuItem itemOrdenesPedidoAM=new JMenuItem("Alta/Modificacion Ordenes de Pedido");
		
				
		
		JMenuItem itemOrdenesRetiroAM=new JMenuItem("Alta/Modificacion Ordenes de Retiro");
		itemOrdenesRetiroAM.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				AltaOrdenRetiro altaOr=new AltaOrdenRetiro(api);
				altaOr.setLocationRelativeTo(null);
				altaOr.setVisible(true);
				
				
			}
		});		
		JMenuItem itemListarOrdenes =new JMenuItem("Listado");
		itemListarOrdenes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ListadoOrdenes ventanaListado=new ListadoOrdenes(api);
				ventanaListado.setLocationRelativeTo(null);
				ventanaListado.setVisible(true);
				
				
			}
		});		
						
			
		
		//Apartado configuracion bar
		
		JMenu configuracionMenu = new JMenu("Configuración");
		JMenuItem registrarVisitaMenuItem=new JMenuItem("Alta Visita");
		registrarVisitaMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				AltaVisita ventanaVisita=new AltaVisita(api);
				ventanaVisita.setLocationRelativeTo(null);
				ventanaVisita.setVisible(true);
		}
		});
		configuracionMenu.add(registrarVisitaMenuItem);
		JMenuItem salirMenuItem = new JMenuItem("Salir");
		configuracionMenu.add(salirMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setJMenuBar(menuBar);

		menuBar.add(menuInventario);
		menuBar.add(menuVehiculo);
		menuBar.add(menuEvento);
		menuBar.add(menuOrdenes);
		menuBar.add(menuRuta);

		menuInventario.add(itemABien);
		menuInventario.add(itemListarInventario);
		menuVehiculo.add(itemVehiculoAM);
		menuVehiculo.add(itemListarVehiculo);
		menuEvento.add(itemEventoAM);
		menuEvento.add(itemListarEvento);
		menuOrdenes.add(itemOrdenesPedidoAM);
		menuOrdenes.add(itemOrdenesRetiroAM);
		menuOrdenes.add(itemListarOrdenes);
		menuRuta.add(itemPRuta);
		menuRuta.add(itemListarRuta);
		menuBar.add(configuracionMenu);
	}

}
