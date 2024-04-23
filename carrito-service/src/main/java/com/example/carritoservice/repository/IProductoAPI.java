package com.example.carritoservice.repository;

import com.example.carritoservice.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "producto-service")
public interface IProductoAPI {

    @GetMapping("/api/tienda/producto/traer/{id}")
    public ProductoDTO traerProducto(@PathVariable("id") Long id);

    @GetMapping("/api/tienda/producto/traerStock/{id}")
    public Integer traerStock(@PathVariable("id") Long id);

    @PutMapping("/api/tienda/producto/restarStock/{id}")
    public void restarStock(@PathVariable("id") Long id);

    @PutMapping("/api/tienda/producto/sumarStock/{id}")
    public void sumarStock(@PathVariable("id") Long id);
}
