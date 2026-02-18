package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.exception.*;

import com.toedter.calendar.JCalendar;

public class AltaVisitaRetiro extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private IApi api;

    private JTextField txtCodOR;
    private JComboBox<String> comboTipo;
    private JTextArea txtObservaciones;
    private JTextField txtCodDonante;
    private JTextField textCodVoluntario;

    private LocalDate fecha = null;
    private OrdenRetiroDTO orden;
    private DonacionDTO donacion;
    private JCalendar calendar;
    private ArrayList<BienDTO> bienesrecolectados = new ArrayList<>();

    public AltaVisitaRetiro(IApi api, String codOrdenRetiro) throws DataNullException {

        this.api = api;

        try {
            orden = api.obtenerOrdenRetiro(codOrdenRetiro);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            donacion = api.obtenerDonacionDTO(orden.getPedido());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 520, 607);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // ===================== ORDEN RETIRO =====================
        JLabel lblCodigo = new JLabel("Codigo OrdenRetiro:");
        lblCodigo.setBounds(10, 10, 150, 14);
        contentPane.add(lblCodigo);

        txtCodOR = new JTextField(codOrdenRetiro);
        txtCodOR.setEditable(false);
        txtCodOR.setBounds(170, 7, 143, 20);
        contentPane.add(txtCodOR);

        // ===================== TIPO =====================
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(10, 52, 47, 14);
        contentPane.add(lblTipo);

        comboTipo = new JComboBox<>();
        comboTipo.setBounds(168, 48, 145, 22);
        comboTipo.addItem("Regular");
        comboTipo.addItem("Visita Final");
        comboTipo.addItem("Seguimiento");
        contentPane.add(comboTipo);

        // ===================== BIENES =====================
        JLabel lblSeleccion = new JLabel("Seleccionar bienes:");
        lblSeleccion.setBounds(10, 93, 120, 14);
        contentPane.add(lblSeleccion);

        JButton btnSeleccionBien = new JButton("Bienes");
        btnSeleccionBien.setBounds(170, 89, 143, 23);
        btnSeleccionBien.addActionListener(e -> abrirSelectorBienes(donacion));
        contentPane.add(btnSeleccionBien);

        // ===================== VOLUNTARIO =====================
        JLabel lblVoluntario = new JLabel("Voluntario asignado:");
        lblVoluntario.setBounds(10, 135, 150, 14);
        contentPane.add(lblVoluntario);

        textCodVoluntario = new JTextField(orden.getCodVoluntario());
        textCodVoluntario.setEditable(false);
        textCodVoluntario.setBounds(170, 132, 143, 20);
        contentPane.add(textCodVoluntario);

        // ===================== DONANTE =====================
        JLabel lblCodDonante = new JLabel("Donante:");
        lblCodDonante.setBounds(10, 177, 170, 14);
        contentPane.add(lblCodDonante);

        txtCodDonante = new JTextField(donacion.getCodDonante());
        txtCodDonante.setEditable(false);
        txtCodDonante.setBounds(170, 174, 143, 20);
        contentPane.add(txtCodDonante);

        // ===================== FECHA =====================
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(10, 220, 47, 14);
        contentPane.add(lblFecha);

        // ===================== OBSERVACIONES =====================
        JLabel lblObserv = new JLabel("Observaciones:");
        lblObserv.setBounds(10, 376, 100, 14);
        contentPane.add(lblObserv);
        JScrollPane scrollObs = new JScrollPane();
        scrollObs.setBounds(10, 401, 480, 120);
        contentPane.add(scrollObs);
        
                txtObservaciones = new JTextArea();
                scrollObs.setViewportView(txtObservaciones);
                txtObservaciones.setLineWrap(true);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		onGuardar();
        	}
        });
        btnGuardar.setBounds(213, 532, 100, 25);
        contentPane.add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(368, 532, 100, 25);
        contentPane.add(btnCancelar);
        
        calendar = new JCalendar();
        calendar.setBounds(67, 212, 184, 153);
        contentPane.add(calendar);

        btnCancelar.addActionListener(e -> limpiarCampos());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ===================== MÉTODOS =====================

    private void abrirSelectorBienes(DonacionDTO donacion) {
        List<BienDTO> listaBienes = donacion.getBienes();
        ArrayList<BienDTO> listaParaMostrar = new ArrayList<>(listaBienes);

        ListadoBienes listado = new ListadoBienes(api, listaParaMostrar, seleccion -> {
            if (seleccion != null) {
                bienesrecolectados = seleccion;
            }
        });

        listado.setLocationRelativeTo(this);
        listado.setVisible(true);
    }

    private void onGuardar() {

    	boolean esFinal=false;
    	String tipo= (String) comboTipo.getSelectedItem();
    	if(tipo.equals("Visita Final")) {
    		esFinal=true;
    	}
    	
    	fecha  = calendar.getDate()
                 .toInstant()
                .atZone(ZoneId.systemDefault())
               .toLocalDate();
     
        try {
            VisitaDTO visita = new VisitaDTO(
                    fecha,
                    orden.getCodVoluntario(),
                    txtCodOR.getText(),
                    bienesrecolectados,
                    txtObservaciones.getText(),
                    (String) comboTipo.getSelectedItem(),
                    esFinal
            );

            api.cargarVisita(visita);
            JOptionPane.showMessageDialog(this, "Visita registrada correctamente");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar visita: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtObservaciones.setText("");
        comboTipo.setSelectedIndex(0);
    }
}
