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
    public Object[] crearProducto(Producto prod) {
        Object[] listaObj = new Object[2];
        listaObj[0] = "Producto creado correctamente";
        listaObj[1] = prodRepo.save(prod);
        return listaObj;
    }

    @Override
    public List<Producto> getProductos() {
        return prodRepo.findAll();
    }

    @Override
    public Producto findProducto(Long id) {
        return prodRepo.findById(id).orElse(null);
    }

    @Override
    public int traerStock(Long id) {
        Producto prod = prodRepo.findById(id).orElse(null);
        return prod.getStock();
    }

    @Override
    public String deleteProducto(Long id) {
        String mjs = "Producto no encontrado";
        Producto prodElim = this.findProducto(id);
        if (prodElim != null) {
            prodRepo.deleteById(id);
            mjs = "El producto id " + id + " fue eliminado";
        }
        return mjs;
    }

    @Override
    public Producto editProducto(Long id, Producto prod) {
        Producto producto = new Producto();
        Producto productoEditar = this.findProducto(id);
        if (productoEditar != null) {
            productoEditar.setCodigo(prod.getCodigo());
            productoEditar.setNombre(prod.getNombre());
            productoEditar.setMarca(prod.getMarca());
            productoEditar.setStock(prod.getStock());
            productoEditar.setPrecio(prod.getPrecio());
            this.crearProducto(productoEditar);
            producto = this.findProducto(id);
        }
        return producto;
    }

    @Override
    public String restarStock(Long id) {
        String msj = "Producto no encontrado";
        Producto productoEditar = this.findProducto(id);
        if (productoEditar != null) {
            productoEditar.setStock(productoEditar.getStock() - 1);
            msj = "Stock editado";
            this.crearProducto(productoEditar);
        }
        return msj;
    }

    @Override
    public String sumarStock(Long id) {
        String msj = "Producto no encontrado";
        Producto productoEditar = this.findProducto(id);
        if (productoEditar != null) {
            productoEditar.setStock(productoEditar.getStock() + 1);
            msj = "Stock editado";
            this.crearProducto(productoEditar);
        }
        return msj;
    }

}
