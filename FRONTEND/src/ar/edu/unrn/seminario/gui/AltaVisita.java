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

public class AltaVisita extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private IApi api;

    private JTextField txtCodigo;            // código de la orden de retiro (referencia)
    private JTextField txtFecha;             // cadena de fecha que parsearemos a LocalDate
    private JComboBox<String> comboTipo;
    private JTextArea txtObservaciones;
    private JComboBox<VoluntarioDTO> comboVoluntarios;
    private JTextField txtCodDonante;       // campo readonly para mostrar codDonante del pedido asociado

    public AltaVisita(IApi api) {
        this.api = api;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 520, 460);
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
        JLabel lblFecha = new JLabel("Fecha (yyyy-MM-dd o yyyy-MM-ddTHH:mm):");
        lblFecha.setBounds(10, 40, 300, 14);
        contentPane.add(lblFecha);

        txtFecha = new JTextField();
        txtFecha.setBounds(10, 60, 410, 20);
        contentPane.add(txtFecha);

        // Tipo
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(10, 95, 100, 14);
        contentPane.add(lblTipo);

        comboTipo = new JComboBox<>();
        comboTipo.setBounds(170, 92, 200, 22);
        // Asegurate de usar los tipos válidos en tu dominio
        comboTipo.addItem("Regular");
        comboTipo.addItem("Visita Final");
        comboTipo.addItem("Seguimiento");
        contentPane.add(comboTipo);

        // Botón selección de bienes (puede abrir otra ventana; ahora muestra mensaje)
        JLabel lblSeleccion = new JLabel("Seleccionar bienes:");
        lblSeleccion.setBounds(10, 130, 120, 14);
        contentPane.add(lblSeleccion);

        JButton btnSeleccionBien = new JButton("Bienes");
        btnSeleccionBien.setBounds(170, 125, 120, 23);
        btnSeleccionBien.addActionListener(e -> {
            // Aquí podés abrir una ventana para seleccionar BienDTO; por ahora aviso.
            JOptionPane.showMessageDialog(AltaVisita.this, "Selector de bienes no implementado.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
        contentPane.add(btnSeleccionBien);

        // Voluntarios
        JLabel lblVoluntario = new JLabel("Voluntario asignado:");
        lblVoluntario.setBounds(10, 165, 150, 14);
        contentPane.add(lblVoluntario);

        comboVoluntarios = new JComboBox<>();
        comboVoluntarios.setBounds(170, 162, 250, 22);
        contentPane.add(comboVoluntarios);

        // CodDonante (solo lectura) — muestra el codDonante del pedido asociado a la OrdenRetiro
        JLabel lblCodDonante = new JLabel("Cod Donante (encargado OP):");
        lblCodDonante.setBounds(10, 195, 170, 14);
        contentPane.add(lblCodDonante);

        txtCodDonante = new JTextField();
        txtCodDonante.setBounds(190, 192, 230, 20);
        txtCodDonante.setEditable(false);
        contentPane.add(txtCodDonante);

        // Observaciones
        JLabel lblObserv = new JLabel("Observaciones:");
        lblObserv.setBounds(10, 225, 100, 14);
        contentPane.add(lblObserv);

        txtObservaciones = new JTextArea();
        txtObservaciones.setLineWrap(true);
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        scrollObs.setBounds(10, 245, 480, 120);
        contentPane.add(scrollObs);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(170, 380, 100, 25);
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(290, 380, 100, 25);
        contentPane.add(btnCancelar);
        
        JRadioButton rdbtnVisitaFinal = new JRadioButton("Visita Final");
        rdbtnVisitaFinal.setBounds(21, 381, 109, 23);
        contentPane.add(rdbtnVisitaFinal);

        // Eventos
        btnCancelar.addActionListener(e -> limpiarCampos());

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGuardar();
            }
        });

        // Llenar combo de voluntarios (defensivo)
        cargarVoluntarios();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cargarVoluntarios() {
        comboVoluntarios.removeAllItems();
        List<VoluntarioDTO> voluntarios = api.obtenerVoluntarios();
        if (voluntarios == null || voluntarios.isEmpty()) {
           
            comboVoluntarios.addItem(null);
            return;
        }
        for (VoluntarioDTO v : voluntarios) {
            comboVoluntarios.addItem(v);
        }
    }

   
    private void actualizarCodDonanteDesdeOrdenRetiro() {
        String codOrdenRetiro = txtCodigo.getText();
        if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) {
            txtCodDonante.setText("");
            return;
        }

        try {
            List<OrdenRetiroDTO> ordenesRetiro = api.obtenerOrdenesRetiro();
            if (ordenesRetiro == null) {
                txtCodDonante.setText("");
                return;
            }
            OrdenRetiroDTO encontrada = null;
            for (OrdenRetiroDTO or : ordenesRetiro) {
                if (or != null && codOrdenRetiro.equalsIgnoreCase(or.getCodigo())) {
                    encontrada = or;
                    break;
                }
            }
            if (encontrada == null) {
                txtCodDonante.setText("");
                return;
            }

            String codPedido = encontrada.getPedido(); // método getPedido() en OrdenRetiroDTO devuelve codPedido
            if (codPedido == null || codPedido.trim().isEmpty()) {
                txtCodDonante.setText("");
                return;
            }

            // Buscar el pedido en la lista de ordenes pedido
            List<OrdenPedidoDTO> pedidos = api.obtenerOrdenesPedido();
            if (pedidos == null) { txtCodDonante.setText(""); return; }

            OrdenPedidoDTO pedidoEncontrado = null;
            for (OrdenPedidoDTO op : pedidos) {
                if (op != null && codPedido.equalsIgnoreCase(op.getCodigo())) {
                    pedidoEncontrado = op;
                    break;
                }
            }

            if (pedidoEncontrado == null) {
                txtCodDonante.setText("");
                return;
            }

            txtCodDonante.setText(pedidoEncontrado.getCodDonante() == null ? "" : pedidoEncontrado.getCodDonante());
        } catch (Exception ex) {
            // En caso de error no bloqueamos; mostramos vacío
            txtCodDonante.setText("");
        }
    }

    private void onGuardar() {
        String codOrdenRetiro = txtCodigo.getText();
        String fechaTexto = txtFecha.getText();
        String tipo = (String) comboTipo.getSelectedItem();
        VoluntarioDTO voluntarioSel = (VoluntarioDTO) comboVoluntarios.getSelectedItem();
        String observaciones = txtObservaciones.getText();

        // Validaciones básicas
        if (codOrdenRetiro == null || codOrdenRetiro.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresá el código de la Orden de Retiro.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (fechaTexto == null || fechaTexto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresá la fecha.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (observaciones == null) observaciones = "";

        // Parsear fecha
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaTexto);
        } catch (DateTimeParseException ex1) {
            try {
                LocalDateTime dt = LocalDateTime.parse(fechaTexto);
                fecha = dt.toLocalDate();
            } catch (DateTimeParseException ex2) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Usá yyyy-MM-dd o yyyy-MM-ddTHH:mm", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String codVoluntario = voluntarioSel == null ? null : voluntarioSel.getCodigo();

        // Si implementás selector de bienes, reemplazá esta lista vacía por la seleccionada
        ArrayList<BienDTO> bienesSeleccionados = new ArrayList<>();

        // Crear la VisitaDTO con null en el código (que lo genere la entidad)
        VisitaDTO visitaDto = new VisitaDTO(
                null,               // que lo cree la entidad Visita en su constructor
                fecha,
                codVoluntario,
                codOrdenRetiro,
                bienesSeleccionados,
                observaciones,
                tipo
        );

        try {
            api.registrarVisita(visitaDto);
            JOptionPane.showMessageDialog(this, "Visita registrada correctamente.", "OK", JOptionPane.INFORMATION_MESSAGE);
         // Si el usuario marcó "Visita Final", completar la orden de retiro asociada
            if (rdbVisitaFinal.isSelected()) {
                try {
                    api.completarOrdenRetiro(codOrdenRetiro);
                } catch (Exception exCompletar) {
                    JOptionPane.showMessageDialog(this,
                            "Visita registrada, pero no se pudo completar la OrdenRetiro: " + exCompletar.getMessage(),
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                    limpiarCampos();
                    return;
                }
            }
            
            limpiarCampos();
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar la visita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtFecha.setText("");
        txtObservaciones.setText("");
        txtCodDonante.setText("");
        if (comboVoluntarios.getItemCount() > 0) comboVoluntarios.setSelectedIndex(0);
        if (comboTipo.getItemCount() > 0) comboTipo.setSelectedIndex(0);
    }

  
}
