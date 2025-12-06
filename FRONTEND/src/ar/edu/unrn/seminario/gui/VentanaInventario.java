package ar.edu.unrn.seminario.gui;


import java.awt.EventQueue;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.AbstractListModel;


public class VentanaInventario extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel modelo;
    IApi api;

    public VentanaInventario(IApi api) {
        initialize(api);
    }

    private void initialize(IApi api) {
        this.api = api;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 623, 468);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        table = new JTable();
        String[] titulos = { "Codigo", "Tipo","Nombre","Desc","Peso","Vencimiento","Talle","Material"};
        modelo = new DefaultTableModel(new Object[][] {}, titulos);
        table.setModel(modelo);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 65, 573, 286);
        contentPane.add(scrollPane);

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JButton btnNewButton = new JButton("Buscar");
        btnNewButton.setBounds(362, 10, 100, 30);
        contentPane.add(btnNewButton);
        
        JLabel lblNewLabel = new JLabel("Tipo de Donacion");
        lblNewLabel.setBounds(129, 10, 90, 30);
        contentPane.add(lblNewLabel);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Todos", "Alimento", "Medicamento", "Ropa", "Mueble", "Electrodomestico", "Otros"}));
        comboBox.setBounds(229, 10, 123, 30);
        contentPane.add(comboBox);
        
        JButton btnNewButton_1 = new JButton("Eliminar");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnNewButton_1.setBounds(453, 380, 100, 30);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Cantidad");
        btnNewButton_2.setBounds(328, 383, 100, 25);
        contentPane.add(btnNewButton_2);
    }
}
