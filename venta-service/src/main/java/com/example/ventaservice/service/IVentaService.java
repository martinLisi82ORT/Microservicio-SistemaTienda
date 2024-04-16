package com.example.ventaservice.service;

import com.example.ventaservice.dto.VentaDTO;
import com.example.ventaservice.model.Venta;

import java.util.List;

public interface IVentaService {

    public Venta crearVenta(Venta venta);

    public List<VentaDTO> traerVentas();

    public VentaDTO traerVenta(Long id_venta);

    public String eliminarVenta(Long id_venta);
}
