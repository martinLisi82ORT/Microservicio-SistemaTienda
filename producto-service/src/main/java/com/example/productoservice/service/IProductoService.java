package com.example.productoservice.service;

import com.example.productoservice.model.Producto;

import java.util.List;

public interface IProductoService {

    public Object[] crearProducto(Producto prod);

    public List<Producto> getProductos();

    public int traerStock(Long id);

    public Producto findProducto(Long id);

    public Producto editProducto(Long id, Producto prod);

    public String restarStock(Long id);

    public String sumarStock(Long id);

    public String deleteProducto(Long id);
}

