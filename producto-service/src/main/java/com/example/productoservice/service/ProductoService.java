package com.example.productoservice.service;

import com.example.productoservice.model.Producto;
import com.example.productoservice.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    private IProductoRepository prodRepo;

    @Override
    public List<Producto> getProductos() {
        return prodRepo.findAll();
    }

    @Override
    public Producto findProducto(Long id) {
        return prodRepo.findById(id).orElse(null);
    }

    @Override
    public Producto crearProducto(Producto prod) {
        return prodRepo.save(prod);
    }

    @Override
    public String deleteProducto(Long id) {
        prodRepo.deleteById(id);
        return "El producto id " + id + " fue eliminado";
    }

    @Override
    public Producto editProducto(Long id, Producto prod) {
        Producto productoEditar = this.findProducto(id);
        productoEditar.setCodigo(prod.getCodigo());
        productoEditar.setNombre(prod.getNombre());
        productoEditar.setMarca(prod.getMarca());
        productoEditar.setPrecio(prod.getPrecio());

        return this.crearProducto(productoEditar);
    }
}
