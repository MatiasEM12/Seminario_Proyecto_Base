package ar.edu.unrn.seminario.accesos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.api.IApi;

import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;

import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class TestAcceso {

    public static void main(String[] args) throws Exception {

        IApi api = new PersistenceApi();

        System.out.println("======= SIMULACIÓN COMPLETA =======");

        // =====================================================
        // 1) CREAR DONANTE
        // =====================================================
        Ubicacion ubic = new Ubicacion("-40.8112", "-63.0005", "San Martín 100", "Viedma");

        Donante donante = new Donante(
                "Carlos",
                "González",
                LocalDate.of(1990, 1, 20),
                "33123456",
                "carlos@mail.com",
                ubic
        );

        api.registrarDonante(donante);
        System.out.println("✓ Donante registrado: " + donante.getCodigo());


        // =====================================================
        // 2) CREAR DONACIÓN con BIENES
        // =====================================================
        ArrayList<Bien> bienesDon = new ArrayList<>();

        Bien arroz = new Bien("Arroz", "Bolsa 1kg", LocalDate.now(), "ALIMENTO");
        Bien aceite = new Bien("Aceite", "Botella 1L", LocalDate.now(), "ALIMENTO");

        bienesDon.add(arroz);
        bienesDon.add(aceite);

        Donacion donacion = new Donacion(
                LocalDate.now(),
                "Donación alimentos básicos",
                bienesDon,
                donante,
                null   // NO hay pedido vinculado todavía
        );

        api.registrarDonacion(donacion);
        System.out.println("✓ Donación registrada: " + donacion.getCodigo());


        // =====================================================
        // 3) GENERAR ORDEN DE PEDIDO
        // =====================================================
        OrdenPedido pedidoModelo = new OrdenPedido(
                null,                             // generar código en DB
                LocalDate.now(),
                "Pedido generado automáticamente",
                false,
                donante.getCodigo()
        );

        api.registrarOrdenPedido(pedidoModelo);
        System.out.println("✓ OrdenPedido creada");


        // Convertir Pedido → DTO
        OrdenPedidoDTO pedidoDTO = new OrdenPedidoDTO(
                pedidoModelo.getFechaEmision(),
                pedidoModelo.getEstado().toString(),
                OrdenPedido.getTipo(),
                pedidoModelo.getCodigo(),
                pedidoModelo.isCargaPesada(),
                pedidoModelo.getObservaciones(),
                donante.getCodigo(),
                donacion.getCodigo()
        );


        // =====================================================
        // 4) CREAR ORDEN DE RETIRO
        // =====================================================
        OrdenRetiroDTO retiroDTO = new OrdenRetiroDTO(
                LocalDate.now(),
                "PENDIENTE",
                OrdenPedido.getTipo(),
                null,                         // generar código
                pedidoModelo.getCodigo(),
                null,                         // sin voluntario
                null                          // sin visitas aún
        );

        api.registrarOrdenRetiro1(retiroDTO);
        System.out.println("✓ OrdenRetiro registrada");


        // =====================================================
        // 5) REGISTRAR VISITAS
        // =====================================================

        // ---- VISITA PREVIA ----
        VisitaDTO visita1 = new VisitaDTO(
                null,
                LocalDate.now(),
                null,
                retiroDTO.getCodigo(),
                new ArrayList<>(),
                "Verificación inicial del domicilio",
                "PREVIA"
        );

        api.registrarVisita(visita1);
        System.out.println("✓ Visita PREVIA registrada");

        // ---- VISITA DE RETIRO ----
        ArrayList<BienDTO> bienesRecolectados = new ArrayList<>();
        bienesRecolectados.add(
                new BienDTO(
                        aceite.getCodigo(),
                        aceite.getTipo(),
                        0,
                        aceite.getNombre(),
                        aceite.getDescripcion(),
                        1,
                        aceite.getFechaVencimiento(),
                        null,
                        null
                )
        );

        VisitaDTO visita2 = new VisitaDTO(
                null,
                LocalDate.now().plusDays(1),
                null,
                retiroDTO.getCodigo(),
                bienesRecolectados,
                "Retiro exitoso",
                "RETIRO"
        );

        api.registrarVisita(visita2);
        System.out.println("✓ Visita RETIRO registrada");


        // =====================================================
        // 6) COMPLETAR ORDEN RETIRO
        // =====================================================
        api.completarOrdenRetiro(retiroDTO.getCodigo());
        System.out.println("✓ OrdenRetiro completada");


        System.out.println("======= FIN DE SIMULACIÓN =======");
    }
}

