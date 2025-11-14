package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.dto.VoluntarioDTO;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;

public class AltaVisita extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private IApi api;

    private JTextField txtCodigo;            // código de la orden de retiro (referencia)
    private JComboBox<String> comboTipo;
    private JTextArea txtObservaciones;
    private JTextField txtCodDonante;       // campo readonly para mostrar codDonante del pedido asociado
    private JRadioButton rdbVisitaFinal;

    
    private ArrayList<BienDTO> bienesrecolectados = new ArrayList<>(); // listade bienes seleccionados
    private JTextField textCodVoluntario;
    
    public AltaVisita(IApi api) {
        this.api = api;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 520, 607);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Código OrdenRetiro (referencia)
        JLabel lblCodigo = new JLabel("Codigo OrdenRetiro:");
        lblCodigo.setBounds(10, 10, 150, 14);
        contentPane.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(170, 7, 250, 20);
        contentPane.add(txtCodigo);
        txtCodigo.setColumns(10);

        // Agrego listener para que al perder foco se busque codDonante y lo muestre
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                actualizarCodDonanteDesdeOrdenRetiro();
            }
        });

        // Fecha
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(10, 220, 47, 14);
        contentPane.add(lblFecha);

        // Tipo
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(10, 52, 47, 14);
        contentPane.add(lblTipo);

        comboTipo = new JComboBox<>();
        comboTipo.setBounds(168, 48, 200, 22);
        comboTipo.addItem("Regular");
        comboTipo.addItem("Visita Final");
        comboTipo.addItem("Seguimiento");
        contentPane.add(comboTipo);

        // Seleccionar bienes (placeholder)
        JLabel lblSeleccion = new JLabel("Seleccionar bienes:");
        lblSeleccion.setBounds(10, 93, 120, 14);
        contentPane.add(lblSeleccion);

        JButton btnSeleccionBien = new JButton("Bienes");
        btnSeleccionBien.setBounds(170, 89, 120, 23);
        btnSeleccionBien.addActionListener(e -> {
            JOptionPane.showMessageDialog(AltaVisita.this, "Selector de bienes no implementado.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        contentPane.add(btnSeleccionBien);

        // Voluntarios
        JLabel lblVoluntario = new JLabel("Voluntario asignado:");
        lblVoluntario.setBounds(10, 135, 150, 14);
        contentPane.add(lblVoluntario);

        // CodDonante (solo lectura)
        JLabel lblCodDonante = new JLabel("Cod Donante (encargado OP):");
        lblCodDonante.setBounds(10, 177, 170, 14);
        contentPane.add(lblCodDonante);

        txtCodDonante = new JTextField();
        txtCodDonante.setBounds(170, 174, 150, 20);
        txtCodDonante.setEditable(false);
        contentPane.add(txtCodDonante);

        // Observaciones
        JLabel lblObserv = new JLabel("Observaciones:");
        lblObserv.setBounds(10, 330, 100, 14);
        contentPane.add(lblObserv);

        txtObservaciones = new JTextArea();
        txtObservaciones.setLineWrap(true);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        scrollObs.setBounds(10, 355, 480, 120);
        contentPane.add(scrollObs);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(213, 532, 100, 25);
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(368, 532, 100, 25);
        contentPane.add(btnCancelar);
        
        // Radio Visita Final
        rdbVisitaFinal = new JRadioButton("Visita Final");
        rdbVisitaFinal.setBounds(10, 482, 120, 23);
        contentPane.add(rdbVisitaFinal);
        
        textCodVoluntario = new JTextField();
        textCodVoluntario.setText("");
        textCodVoluntario.setEditable(false);
        textCodVoluntario.setBounds(170, 132, 150, 20);
        contentPane.add(textCodVoluntario);
        
        JCalendar calendar = new JCalendar();
        calendar.setBounds(76, 220, 141, 105);
        contentPane.add(calendar);
        
        JButton btnFecha = new JButton("Guardar Fecha");
        btnFecha.setBounds(231, 302, 137, 23);
        contentPane.add(btnFecha);

        // Eventos
        btnCancelar.addActionListener(e -> limpiarCampos());

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGuardar();
            }
        });

       
        cargarVoluntarios();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarVoluntarios() {
        
    }

    private void actualizarCodDonanteDesdeOrdenRetiro() {
        
    }

    private void onGuardar() {
        String codOrdenRetiro = txtCodigo.getText();
        String fechaTexto = txtFecha.getText();
        String tipo = (String) comboTipo.getSelectedItem();
        VoluntarioDTO voluntarioSel = (VoluntarioDTO) comboVoluntarios.getSelectedItem();
        String observaciones = txtObservaciones.getText();


    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtObservaciones.setText("");
        txtCodDonante.setText("");
        if (comboVoluntarios.getItemCount() > 0) comboVoluntarios.setSelectedIndex(0);
        if (comboTipo.getItemCount() > 0) comboTipo.setSelectedIndex(0);
        rdbVisitaFinal.setSelected(false);
    }
}
