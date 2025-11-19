package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class AltaOrdenPedido extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTextField txtFecha;
    private JTextField txtEstado;
    private JTextField txtCodigo;
    private DonacionDTO donacionSeleccionada;
    private IApi api;
    private JButton btnGuardar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // para pruebas en memoria
                IApi api = new ar.edu.unrn.seminario.api.MemoryApi();
                AltaOrdenPedido frame = new AltaOrdenPedido(api);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AltaOrdenPedido(IApi api) {
        this.api = api;

        setTitle("Alta Orden de Pedido");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 381, 260);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblSeleccionar = new JLabel("Seleccionar:");
        lblSeleccionar.setBounds(10, 10, 133, 13);
        contentPane.add(lblSeleccionar);

        JLabel lblCodigoDonacion = new JLabel("Codigo Donacion:");
        lblCodigoDonacion.setBounds(10, 33, 133, 13);
        contentPane.add(lblCodigoDonacion);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(10, 78, 102, 13);
        contentPane.add(lblEstado);

        JLabel lblFecha = new JLabel("Fecha Emision:");
        lblFecha.setBounds(10, 102, 96, 13);
        contentPane.add(lblFecha);

        // Campos
        txtFecha = new JTextField();
        txtFecha.setBounds(140, 100, 96, 19);
        contentPane.add(txtFecha);

        txtEstado = new JTextField();
        txtEstado.setEditable(false);
        txtEstado.setBounds(140, 74, 96, 19);
        contentPane.add(txtEstado);

        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBounds(140, 29, 150, 19);
        contentPane.add(txtCodigo);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(30, 159, 85, 21);
        btnGuardar.setEnabled(false); // inicialmente deshabilitado hasta seleccionar donación
        contentPane.add(btnGuardar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(232, 159, 85, 21);
        btnCerrar.addActionListener(e -> dispose());
        contentPane.add(btnCerrar);

        JButton btnDonaciones = new JButton("Donaciones");
        btnDonaciones.setBounds(140, 6, 151, 21);
        contentPane.add(btnDonaciones);

        JLabel lblCarga = new JLabel("Carga:");
        lblCarga.setBounds(10, 55, 102, 13);
        contentPane.add(lblCarga);

        JCheckBox chckbxPesada = new JCheckBox("Pesada");
        chckbxPesada.setBounds(140, 47, 93, 21);
        contentPane.add(chckbxPesada);

        JCheckBox chckbxLiviana = new JCheckBox("Liviana");
        chckbxLiviana.setBounds(242, 47, 93, 21);
        contentPane.add(chckbxLiviana);

        // Abrir listado de donaciones (pasa 'this' para callback)
        btnDonaciones.addActionListener(e -> {
            try {
                ListadoDonaciones listadoDonaciones = new ListadoDonaciones(api, this);
                listadoDonaciones.setLocationRelativeTo(this);
                listadoDonaciones.setVisible(true);
            } catch (DataNullException | DataEmptyException | DataObjectException | DataDateException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al abrir listado de donaciones: " + ex.getMessage());
            }
        });

        // Guardar la orden usando la donación seleccionada
        btnGuardar.addActionListener(e -> {
            try {
                if (donacionSeleccionada == null) {
                    JOptionPane.showMessageDialog(this, "Seleccioná primero una donación.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Parseo seguro de fecha
                LocalDate fecha;
                String fechaTexto = txtFecha.getText();
                if (fechaTexto == null || fechaTexto.trim().isEmpty()) {
                    fecha = LocalDate.now();
                } else {
                    fecha = LocalDate.parse(fechaTexto.trim()); // formato yyyy-MM-dd
                }

                // Determinar estado (si tu DTO espera un estado, lo pasamos; si no, ajustá)
                String estado = txtEstado.getText();
                if (estado == null || estado.trim().isEmpty()) estado = "PENDIENTE";

                // Usamos el codigo de la donacion seleccionada
                String codDonacion = donacionSeleccionada.getCodigo();

                // Construir DTO (ajustá el orden de parámetros si tu constructor es diferente)
                OrdenPedidoDTO ordenPedido = new OrdenPedidoDTO(
                        fecha,
                        estado,
                        null,               // tipo (puede ser null según tu DTO)
                        codDonacion,        // lo pasamos en el campo codigo (tu código previo usaba txtCodigo aquí)
                        chckbxPesada.isSelected(), // cargaPesada
                        null,
                        donacionSeleccionada.getCodDonante(), // codDonante
                        codDonacion        // codDonacion (duplica por seguridad si tu DTO lo requiere)
                );

                api.registrarOrdenPedido(ordenPedido);

                // limpieza
                donacionSeleccionada = null;
                txtCodigo.setText("");
                txtEstado.setText("");
                txtFecha.setText("");
                btnGuardar.setEnabled(false);

                JOptionPane.showMessageDialog(this, "Orden de pedido registrada correctamente.");
                dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al crear la orden. Asegurate del formato de fecha yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Método que será invocado por ListadoDonaciones cuando el usuario seleccione una donación.
     */
    public void recibirDonacion(DonacionDTO donacion) {
        this.donacionSeleccionada = donacion;

        if (donacion == null) {
            txtCodigo.setText("");
            txtEstado.setText("");
            txtFecha.setText("");
            btnGuardar.setEnabled(false);
            return;
        }

        txtCodigo.setText(donacion.getCodigo() != null ? donacion.getCodigo() : "");
        // Si la donación ya tiene pedido, mostramos su estado; si no, dejamos SIN_ORDEN
        String estado = donacion.getCodPedido() != null ? api.obtenerEstadoOrdenPedido(donacion.getCodPedido()) : "SIN_ORDEN";
        txtEstado.setText(estado != null ? estado : "SIN_ORDEN");

        LocalDate fecha = donacion.getFechaDonacion() != null ? donacion.getFechaDonacion() : LocalDate.now();
        txtFecha.setText(fecha.toString());

        btnGuardar.setEnabled(true);
    }
}
