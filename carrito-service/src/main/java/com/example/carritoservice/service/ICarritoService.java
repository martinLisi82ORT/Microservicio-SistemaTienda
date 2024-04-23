package com.example.carritoservice.service;

import com.example.carritoservice.dto.CarritoDTO;
import com.example.carritoservice.model.Carrito;

import java.util.List;

public interface ICarritoService {

    public Carrito crearCarrito(Carrito carrito);

    public List<CarritoDTO> traerCarritos();

    public CarritoDTO traerCarritoDTO(Long id);

    public boolean existe(Long id);

    public Object[] agregarProducto(Long id_carrito, Long id_prod);

    public Object[] eliminarProducto(Long id_carrito, Long id_prod);

    public String eliminarCarrito(Long id_carrito);

}
