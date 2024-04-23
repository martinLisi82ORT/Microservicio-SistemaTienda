package com.example.carritoservice.controller;

import com.example.carritoservice.dto.CarritoDTO;
import com.example.carritoservice.model.Carrito;
import com.example.carritoservice.service.ICarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tienda/carrito")
public class CarritoController {

    @Autowired
    private ICarritoService carrServ;

    @GetMapping("/traer")
    public ResponseEntity<List<CarritoDTO>> traerCarritos() {
        return new ResponseEntity<>(carrServ.traerCarritos(), HttpStatus.OK);
    }

    @GetMapping("traer/{id_carrito}")
    public ResponseEntity<CarritoDTO> traerCarritoDTO(@PathVariable Long id_carrito) {
        return new ResponseEntity<>(carrServ.traerCarritoDTO(id_carrito), HttpStatus.OK);
    }

    @GetMapping("existe/{id_carrito}")
    public ResponseEntity<Boolean> existe(@PathVariable Long id_carrito) {
        return new ResponseEntity<>(carrServ.existe(id_carrito), HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        return new ResponseEntity<>(carrServ.crearCarrito(carrito), HttpStatus.CREATED);
    }

    @PutMapping("/agregar")
    public ResponseEntity<Object[]> agregarProducto(@RequestParam Long id_carrito, @RequestParam Long id_producto) {
        return new ResponseEntity<>(carrServ.agregarProducto(id_carrito, id_producto), HttpStatus.OK);
    }

    @PutMapping("/quitar")
    public ResponseEntity<Object[]> eliminarProducto(@RequestParam Long id_carrito, @RequestParam Long id_producto) {
        return new ResponseEntity<>(carrServ.eliminarProducto(id_carrito, id_producto), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id_carrito}")
    public ResponseEntity<String> eliminarCarrito(@PathVariable Long id_carrito) {
        return new ResponseEntity<>(carrServ.eliminarCarrito(id_carrito), HttpStatus.OK);
    }


}
