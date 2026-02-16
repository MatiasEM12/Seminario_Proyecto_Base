package ar.edu.unrn.seminario.gui;

import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;

import com.toedter.calendar.JCalendar;

public class ModificarBien extends JFrame {

    private static final long serialVersionUID = 1L;

    private IApi api;
    private BienDTO bien;
    private VentanaInventario inventario;

    private JPanel contentPane;

    private JTextField tipoTextField;
    private JTextField nombreTextField;
    private JTextArea descripcionTextArea;
    private JTextField talleTextField;
    private JTextField pesoTextField;
    private JTextField materialTextField;

    private JCalendar calendar;

    public ModificarBien(IApi api, BienDTO bien, VentanaInventario inventario) {
        this.api = api;
        this.bien = bien;
        this.inventario = inventario;

        initialize();
        cargarDatos();
        configurarBloqueosPorTipo();
    }

    private void initialize() {

        setTitle("Modificar Bien");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 520, 540);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel tipoLabel = new JLabel("Tipo:");
        tipoLabel.setBounds(40, 25, 120, 16);
        contentPane.add(tipoLabel);

        tipoTextField = new JTextField();
        tipoTextField.setEditable(false);
        tipoTextField.setBounds(170, 22, 200, 22);
        contentPane.add(tipoTextField);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(40, 65, 80, 16);
        contentPane.add(nombreLabel);

        nombreTextField = new JTextField();
        nombreTextField.setBounds(170, 62, 200, 22);
        contentPane.add(nombreTextField);

        JLabel descripcionLabel = new JLabel("Descripción:");
        descripcionLabel.setBounds(40, 105, 100, 16);
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

        calendar = new JCalendar();
        calendar.setBounds(170, 235, 184, 153);
        contentPane.add(calendar);

        JLabel pesoLabel = new JLabel("Peso:");
        pesoLabel.setBounds(40, 404, 80, 16);
        contentPane.add(pesoLabel);

        pesoTextField = new JTextField();
        pesoTextField.setBounds(170, 401, 200, 22);
        contentPane.add(pesoTextField);

        JLabel materialLabel = new JLabel("Material:");
        materialLabel.setBounds(40, 440, 80, 16);
        contentPane.add(materialLabel);

        materialTextField = new JTextField();
        materialTextField.setBounds(170, 437, 200, 22);
        contentPane.add(materialTextField);

        JLabel talleLabel = new JLabel("Talle:");
        talleLabel.setBounds(40, 471, 80, 16);
        contentPane.add(talleLabel);

        talleTextField = new JTextField();
        talleTextField.setBounds(170, 468, 200, 22);
        contentPane.add(talleTextField);

        JButton guardarButton = new JButton("Guardar");
        guardarButton.setBounds(399, 436, 95, 25);
        guardarButton.addActionListener(e -> guardarCambios());
        contentPane.add(guardarButton);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.setBounds(399, 404, 95, 25);
        cancelarButton.addActionListener(e -> dispose());
        contentPane.add(cancelarButton);
    }

    private void cargarDatos() {

        tipoTextField.setText(bien.getTipo());
        nombreTextField.setText(bien.getNombre());
        descripcionTextArea.setText(bien.getDescripcion());

        if (bien.getFechaVencimiento() != null) {
            calendar.setDate(java.sql.Date.valueOf(bien.getFechaVencimiento()));
        }

        if (bien.getPeso() != null) {
            pesoTextField.setText(bien.getPeso().toString());
        }

        if (bien.getTalle() != null) {
            talleTextField.setText(bien.getTalle().toString());
        }

        if (bien.getMaterial() != null) {
            materialTextField.setText(bien.getMaterial());
        }
    }

    private void guardarCambios() {
        try {
            BienDTO dto = crearBienDTO();
            api.modificarBien(dto);
            inventario.actualizarTabla();
            JOptionPane.showMessageDialog(this, "Bien modificado correctamente");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private BienDTO crearBienDTO() {

        LocalDate fechaVencimiento = null;

        if (calendar.isEnabled()) {
            fechaVencimiento = calendar.getDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        return new BienDTO(
                bien.getCodigo(), // MISMO CÓDIGO
                bien.getTipo(),   // MISMO TIPO
                pesoTextField.getText().isEmpty() ? null : Double.valueOf(pesoTextField.getText()),
                nombreTextField.getText(),
                descripcionTextArea.getText(),
                bien.getNivelNecesidad(),
                fechaVencimiento,
                talleTextField.getText().isEmpty() ? null : Double.valueOf(talleTextField.getText()),
                materialTextField.getText().isEmpty() ? null : materialTextField.getText()
        );
    }

    private void configurarBloqueosPorTipo() {

        String tipo = bien.getTipo();

        habilitarCampo(talleTextField, true);
        habilitarCampo(pesoTextField, true);
        habilitarCampo(materialTextField, true);
        calendar.setEnabled(true);

        if ("Mueble".equalsIgnoreCase(tipo) || "Electrodoméstico".equalsIgnoreCase(tipo)) {
            habilitarCampo(talleTextField, false);
            calendar.setEnabled(false);

        } else if ("Alimento".equalsIgnoreCase(tipo) || "Medicamento".equalsIgnoreCase(tipo)) {
            habilitarCampo(talleTextField, false);
            habilitarCampo(materialTextField, false);
            habilitarCampo(pesoTextField, false);

        } else if ("Ropa".equalsIgnoreCase(tipo)) {
            habilitarCampo(pesoTextField, false);
            calendar.setEnabled(false);
        }
    }

    private void habilitarCampo(JTextField field, boolean habilitar) {
        field.setEnabled(habilitar);
        if (!habilitar) {
            field.setText("");
        }
    }
}

