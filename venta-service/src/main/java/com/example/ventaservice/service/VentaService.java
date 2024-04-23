package com.example.ventaservice.service;

import com.example.ventaservice.dto.CarritoDTO;
import com.example.ventaservice.dto.VentaDTO;
import com.example.ventaservice.model.Venta;
import com.example.ventaservice.repository.ICarritoAPI;
import com.example.ventaservice.repository.IVentaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private IVentaRepository repoVenta;

    @Autowired
    private ICarritoAPI carritoApi;

    @Override
    public String crearVenta(Venta venta) {
        String msj = "Carrito no encontrado";
        if (carritoApi.existe(venta.getCarrito_id())) {
            repoVenta.save(venta);
            msj = "Venta creada correctamente, Id Venta: " + venta.getId_venta();
        }
        return msj;
    }

    @Override
    @CircuitBreaker(name = "carrito-service", fallbackMethod = "fallbackGetListVentaDTO")
    @Retry(name = "carrito-service")
    public List<VentaDTO> traerVentas() {
        List<VentaDTO> listaVentas = new ArrayList<>();
        List<Venta> lista = repoVenta.findAll();

        for (Venta vta : lista) {
            VentaDTO ventaDto = this.traerVenta(vta.getId_venta());
            listaVentas.add(ventaDto);
        }
        return listaVentas;
    }

    @Override
    @CircuitBreaker(name = "carrito-service", fallbackMethod = "fallbackGetVentaDTO")
    @Retry(name = "carrito-service")
    public VentaDTO traerVenta(Long id_venta) {
        // Trae la Venta
        Venta venta = repoVenta.findById(id_venta).orElse(null);

        // Trae CarritoDTO
        CarritoDTO carritoDo = carritoApi.traerCarritoDTO(venta.getCarrito_id());

        // Crea VentaDTO
        VentaDTO ventaDto = new VentaDTO(venta.getId_venta(), venta.getFecha(), venta.getCarrito_id(), carritoDo);

        // Simula Exception para probar CircuitBreaker
        //createException();

        return ventaDto;
    }

    @Override
    public Venta editarVenta(Long id_venta, Venta venta) {
        Venta ventaEdit = repoVenta.findById(id_venta).orElse(null);
        System.out.println(ventaEdit);
        System.out.println(ventaEdit.getId_venta());
        ventaEdit.setId_venta(venta.getId_venta());
        ventaEdit.setFecha(venta.getFecha());
        ventaEdit.setCarrito_id(venta.getCarrito_id());

        repoVenta.save(ventaEdit);

        VentaDTO ventaDto = this.traerVenta(id_venta);
        return ventaEdit;
    }

    @Override
    public String eliminarVenta(Long id_venta) {
        String msj = "Venta no encontrada";

        if (repoVenta.findById(id_venta).orElse(null) != null) {
            repoVenta.deleteById(id_venta);
            msj = "La venta id " + id_venta + " fue eliminada";
        }
        return msj;
    }

    private VentaDTO fallbackGetVentaDTO(Throwable throwable) {
        return new VentaDTO(0L, (LocalDate) null, 0L, null);
    }

    private List<VentaDTO> fallbackGetListVentaDTO(Throwable throwable) {
        List<VentaDTO> listaVtaDTO = new ArrayList<>();
        listaVtaDTO.add(new VentaDTO(0L, (LocalDate) null, 0L, null));
        return listaVtaDTO;
    }

    private void createException() {
        throw new IllegalArgumentException("Prueba resilience y y Circuit Breaker");
    }
}
