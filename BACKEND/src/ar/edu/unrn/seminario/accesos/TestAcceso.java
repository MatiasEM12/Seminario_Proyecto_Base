package ar.edu.unrn.seminario.accesos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;

public class TestAcceso {

    public static void main(String[] args) throws Exception {

        // API principal
        IApi api = new PersistenceApi();

        System.out.println("=== SIMULACIÓN COMPLETA ===");

        // ==========================
        // 1) CREAR DONANTE
        // ==========================
        LocalDate fecha=new LocalDate(12,03,2003);
        Donante donante = new Donante(
                "Juan","Contrera",
                ,
                "juan@mail.com",
                new Coordenada(-40.812, -65.051)
        );

        // ==========================
        // 2) CREAR DONACIÓN (Bienes incluidos)
        // ==========================
        Donacion donacion = new Donacion(
                LocalDate.now(),
                "Donación de alimentos",
                donante
        );

        // Bienes donados
        Bien arroz = new Bien("Arroz", "Bolsa 1kg", LocalDate.now().plusMonths(12).atStartOfDay(), "ALIMENTO");
        Bien fideos = new Bien("Fideos", "Paquete 500g", LocalDate.now().plusMonths(6).atStartOfDay(), "ALIMENTO");

        donacion.agregarBien(arroz);
        donacion.agregarBien(fideos);

        // Registrar donación
        api.registrarDonacion(donacion);
        System.out.println("✓ Donación registrada: " + donacion.getCodigo());

        // ==========================
        // 3) GENERAR ORDEN DE PEDIDO
        // ==========================
        OrdenPedidoDTO pedido = new OrdenPedidoDTO(
                LocalDate.now(),
                "PENDIENTE",
                "NORMAL",
                null,                // código del pedido lo genera el sistema
                false,
                donante.getCodigo(),
                donacion.getCodigo(),
                null
        );

        api.registrarOrdenPedido(pedido);
        System.out.println("✓ Orden de Pedido generada");

        // Obtener código real del pedido recién generado
        List<OrdenPedidoDTO> pedidos = api.obtenerPedidos();
        OrdenPedidoDTO pedidoCreado = pedidos.get(pedidos.size() - 1);

        // ==========================
        // 4) GENERAR ORDEN DE RETIRO
        // ==========================
        OrdenRetiroDTO retiro = new OrdenRetiroDTO(
                LocalDate.now(),
                "PENDIENTE",
                pedidoCreado.getTipo(),
                null,
                pedidoCreado.getCodigo(),
                null,
                null
        );

        api.registrarOrdenRetiro(retiro);
        System.out.println("✓ Orden de Retiro creada");

        // ==========================
        // 5) REGISTRAR VISITAS
        // ==========================
        List<OrdenRetiroDTO> retiros = api.obtenerOrdenesRetiro();
        OrdenRetiroDTO retiroCreado = retiros.get(retiros.size() - 1);

        System.out.println("Registrando VISITAS para retiro: " + retiroCreado.getCodigo());

        List<VisitaDTO> visitas = new ArrayList<>();

        VisitaDTO visita1 = new VisitaDTO(
                LocalDate.now(),
                "Se verifica la dirección del donante.",
                "PREVIA",
                retiroCreado.getCodigo(),
                new ArrayList<>()
        );

        VisitaDTO visita2 = new VisitaDTO(
                LocalDate.now(),
                "Retiro concretado con éxito.",
                "RETIRO",
                retiroCreado.getCodigo(),
                new ArrayList<>()
        );

        visitas.add(visita1);
        visitas.add(visita2);

        api.guardarVisitas(visitas);

        System.out.println("✓ Visitas registradas correctamente");

        System.out.println("=== FIN DE LA SIMULACIÓN ===");
    }
}