package com.example.productoservice.controller;

import com.example.productoservice.model.Producto;
import com.example.productoservice.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tienda/producto")
public class ProductoController {

    @Autowired
    private IProductoService prodService;

    @Value("${server.port}")
    private int serverPort;

    @PostMapping("/crear")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto prod) {
        return new ResponseEntity<>(prodService.crearProducto(prod), HttpStatus.CREATED);
    }


    @GetMapping("/traer")
    public ResponseEntity<List<Producto>> traerProductos() {



        return new ResponseEntity<>(prodService.getProductos(), HttpStatus.OK);
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<Producto> traerProducto(@PathVariable Long id) {
        System.out.println("------ Puerto: " + serverPort + " ------");
        return new ResponseEntity<>(prodService.findProducto(id), HttpStatus.OK);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        // prodService.deleteProducto(id);
        return new ResponseEntity<>(prodService.deleteProducto(id), HttpStatus.OK);
    }

    @PutMapping("/editar/{id_original}")
    public ResponseEntity<Producto> editProducto(@PathVariable Long id_original,
                                                 @RequestBody Producto productoEditar) {
        return new ResponseEntity<>(prodService.editProducto(id_original, productoEditar), HttpStatus.OK);
    }


}
