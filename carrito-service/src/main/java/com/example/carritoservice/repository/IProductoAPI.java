package com.example.carritoservice.repository;

import com.example.carritoservice.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="producto-service")
public interface IProductoAPI {

    @GetMapping("/api/tienda/producto/traer/{id}")
    public ProductoDTO traerProducto(@PathVariable("id") Long id);

}
