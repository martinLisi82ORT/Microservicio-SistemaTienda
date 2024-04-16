package com.example.carritoservice.service;

import com.example.carritoservice.dto.CarritoDTO;
import com.example.carritoservice.model.Carrito;

import java.util.List;

public interface ICarritoService {

    public Carrito crearCarrito(Carrito carrito);

    public Carrito traerCarrito(Long id);

    public List<Carrito> traerCarritos();

    public CarritoDTO agregarProducto(Long id_carrito, Long id_prod);

    public CarritoDTO eliminarProducto(Long id_carrito, Long id_prod);

    public String eliminarCarrito(Long id_carrito);

    public CarritoDTO traerCarritoDTO(Long id);
}
