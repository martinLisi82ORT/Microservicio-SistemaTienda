package com.example.productoservice.service;

import com.example.productoservice.model.Producto;

import java.util.List;

public interface IProductoService {

    public List<Producto> getProductos();

    public Producto crearProducto(Producto prod);

    public String deleteProducto(Long id);

    public Producto findProducto(Long id);

    public Producto editProducto(Long id, Producto prod);

}
