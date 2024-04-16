package com.example.ventaservice.repository;

import com.example.ventaservice.dto.CarritoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "carrito-service")
public interface ICarritoAPI {

    @GetMapping("/api/tienda/carrito/traer")
    public List<CarritoDTO> traerCarritos();

    @GetMapping("/api/tienda/carrito/traer/{id}")
    public CarritoDTO traerCarrito(@PathVariable Long id);

    @GetMapping("/api/tienda/carrito/traerCarritoDTO/{id_carrito}")
    public CarritoDTO traerCarritoDTO(@PathVariable("id_carrito") Long id_carrito);
}
