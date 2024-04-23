package com.example.productoservice.controller;

import com.example.productoservice.model.Producto;
import com.example.productoservice.service.IProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
    public ResponseEntity<Object[]> crearProducto(@Valid @RequestBody Producto prod, BindingResult result) {
        Object[] listObj = new Object[2];
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                listObj[0] = "Error en carga de Producto";
                listObj[1] = error.getDefaultMessage();
            }
        } else {
            listObj = prodService.crearProducto(prod);
        }

        return new ResponseEntity<>(listObj, HttpStatus.CREATED);
    }

    @GetMapping("/traer")
    public ResponseEntity<List<Producto>> traerProductos() {
        return new ResponseEntity<>(prodService.getProductos(), HttpStatus.OK);
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<Producto> traerProducto(@PathVariable Long id) {
        //System.out.println("------ Puerto: " + serverPort + " ------");
        return new ResponseEntity<>(prodService.findProducto(id), HttpStatus.OK);
    }

    @GetMapping("/traerStock/{id}")
    public ResponseEntity<Integer> traerStock(@PathVariable Long id) {
        return new ResponseEntity<>(prodService.traerStock(id), HttpStatus.OK);
    }

    @PutMapping("/editar/{id_original}")
    public ResponseEntity<Producto> editProducto(@PathVariable Long id_original,
                                                 @RequestBody Producto productoEditar) {
        return new ResponseEntity<>(prodService.editProducto(id_original, productoEditar), HttpStatus.OK);
    }

    @PutMapping("/restarStock/{id}")
    public ResponseEntity<String> restarStock(@PathVariable Long id) {
        return new ResponseEntity<>(prodService.restarStock(id), HttpStatus.OK);
    }

    @PutMapping("/sumarStock/{id}")
    public ResponseEntity<String> sumarStock(@PathVariable Long id) {
        return new ResponseEntity<>(prodService.sumarStock(id), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Long id) {
        return new ResponseEntity<>(prodService.deleteProducto(id), HttpStatus.OK);
    }

}
