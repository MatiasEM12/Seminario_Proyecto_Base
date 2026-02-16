package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.exception.*;

import com.toedter.calendar.JCalendar;

public class AltaBienes extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    private JComboBox<String> tipoComboBox;
    private JTextField nombreTextField;
    private JTextArea descripcionTextArea;
    private JTextField talleTextField;
    private JTextField pesoTextField;
    private JTextField materialTextField;

    private JCalendar calendar;

    private IApi api;

    public AltaBienes(IApi api) {
        this.api = api;
        initialize();
        configurarBloqueosPorTipo();
    }

    // Constructor solo para pruebas
    public AltaBienes() {
        initialize();
        configurarBloqueosPorTipo();
    }

    private void initialize() {

        setTitle("Registrar ingreso de bienes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 520, 540);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel tipoLabel = new JLabel("Seleccionar tipo:");
        tipoLabel.setBounds(40, 25, 120, 16);
        contentPane.add(tipoLabel);

        tipoComboBox = new JComboBox<>();
        tipoComboBox.setBounds(170, 22, 200, 22);
        contentPane.add(tipoComboBox);

        tipoComboBox.addItem("alimento");
        tipoComboBox.addItem("medicamento");
        tipoComboBox.addItem("mueble");
        tipoComboBox.addItem("electrodoméstico");
        tipoComboBox.addItem("ropa");
        

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
        contentPane.add(materialTextField = new JTextField());
        materialTextField.setBounds(170, 437, 200, 22);

        JLabel talleLabel = new JLabel("Talle:");
        talleLabel.setBounds(40, 471, 80, 16);
        contentPane.add(talleLabel);

        talleTextField = new JTextField();
        talleTextField.setBounds(170, 468, 200, 22);
        contentPane.add(talleTextField);

        JButton guardarButton = new JButton("Guardar");
        guardarButton.setBounds(399, 436, 95, 25);
        guardarButton.addActionListener(this::guardarBien);
        contentPane.add(guardarButton);

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.setBounds(399, 404, 95, 25);
        cancelarButton.addActionListener(e -> dispose());
        contentPane.add(cancelarButton);
        
        JLabel lblMaterial = new JLabel("Material");
        lblMaterial.setBounds(40, 441, 80, 16);
        contentPane.add(lblMaterial);
    }



    private void guardarBien(ActionEvent e) {
        try {
            BienDTO dto = crearBienDTO();

            api.registrarBien(dto);
            api.registrarBienInventario(dto.getCodigo(), dto.getTipo(), true);

            JOptionPane.showMessageDialog(this, "Bien registrado correctamente");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private BienDTO crearBienDTO() {

        String codigo = null;
        String tipo = (String) tipoComboBox.getSelectedItem();
        String nombre = nombreTextField.getText();
        String descripcion = descripcionTextArea.getText();

        int nivelNecesidad = 1;

        LocalDate fechaVencimiento = null;

        if ("Alimento".equalsIgnoreCase(tipo) || "Medicamento".equalsIgnoreCase(tipo)) {
            fechaVencimiento = calendar.getDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        }
    

        Double peso = pesoTextField.getText().isEmpty()
                ? null
                : Double.valueOf(pesoTextField.getText());

        Double talle = talleTextField.getText().isEmpty()
                ? null
                : Double.valueOf(talleTextField.getText());

        String material = materialTextField.getText().isEmpty()
                ? null
                : materialTextField.getText();
        
        

        return new BienDTO(
                codigo,
                tipo,
                peso,
                nombre,
                descripcion,
                nivelNecesidad,
                fechaVencimiento,
                talle,
                material
        );
    }

    private void configurarBloqueosPorTipo() {

        tipoComboBox.addActionListener(e -> aplicarReglasPorTipo());
        aplicarReglasPorTipo();
    }

    private void aplicarReglasPorTipo() {

        String tipo = (String) tipoComboBox.getSelectedItem();

        habilitarCampo(talleTextField, true);
        habilitarCampo(pesoTextField, true);
        habilitarCampo(materialTextField, true);

        if ("Mueble".equalsIgnoreCase(tipo)) {
            habilitarCampo(talleTextField, false);

        } else if ("Electrodoméstico".equalsIgnoreCase(tipo)) {
            habilitarCampo(talleTextField, false);

        } else if ("Vestimenta".equalsIgnoreCase(tipo)) {
            habilitarCampo(pesoTextField, false);

        } else if ("Alimento".equalsIgnoreCase(tipo)) {
            habilitarCampo(talleTextField, false);
            habilitarCampo(materialTextField, false);

        } else if ("Medicamento".equalsIgnoreCase(tipo)) {
            habilitarCampo(talleTextField, false);
            habilitarCampo(pesoTextField, false);
            habilitarCampo(materialTextField, false);
        }
    }

    private void habilitarCampo(JTextField field, boolean habilitar) {
        field.setEnabled(habilitar);
        if (!habilitar) {
            field.setText("");
        }
    }

    // ========= MAIN DE PRUEBA =========
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AltaBienes(new PersistenceApi()).setVisible(true));
    }
}

