package com.example.ventaservice.controller;

import com.example.ventaservice.dto.VentaDTO;
import com.example.ventaservice.model.Venta;
import com.example.ventaservice.service.IVentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tienda/venta")
public class VentaController {

    @Autowired
    private IVentaService ventaServ;

    @PostMapping("/crear")
    public ResponseEntity<String> crearVenta(@Valid @RequestBody Venta venta, BindingResult result) {
        String msj = "";
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                msj = error.getDefaultMessage();
            }
        } else {
            msj = ventaServ.crearVenta(venta);
        }
        return new ResponseEntity<>(msj, HttpStatus.CREATED);
    }

    @GetMapping("/traer")
    public ResponseEntity<List<VentaDTO>> traerVentas() {
        return new ResponseEntity<>(ventaServ.traerVentas(), HttpStatus.OK);
    }

    @GetMapping("/traer/{id_venta}")
    public ResponseEntity<VentaDTO> traerVenta(@PathVariable Long id_venta) {
        return new ResponseEntity<>(ventaServ.traerVenta(id_venta), HttpStatus.OK);

    }

    @PutMapping("/editar/{id_venta}")
    public ResponseEntity<VentaDTO> editarVenta(@PathVariable Long id_venta, @RequestBody Venta venta) {

        return new ResponseEntity<>(ventaServ.editarVenta(id_venta, venta), HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id_venta}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Long id_venta) {
        return new ResponseEntity<>(ventaServ.eliminarVenta(id_venta), HttpStatus.OK);
    }

}
