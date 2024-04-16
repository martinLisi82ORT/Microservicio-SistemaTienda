package com.example.ventaservice.controller;

import com.example.ventaservice.dto.VentaDTO;
import com.example.ventaservice.model.Venta;
import com.example.ventaservice.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tienda/venta")
public class VentaController {

    @Autowired
    private IVentaService ventaServ;

    @PostMapping("/crear")
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        return new ResponseEntity<>(ventaServ.crearVenta(venta), HttpStatus.CREATED);
    }

    @GetMapping("/traer")
    public ResponseEntity<List<VentaDTO>> traerVentas() {
        return new ResponseEntity<>(ventaServ.traerVentas(), HttpStatus.OK);
    }

    @GetMapping("/traer/{id_venta}")
    public ResponseEntity<VentaDTO> traerVenta(@PathVariable Long id_venta) {
        return new ResponseEntity<>(ventaServ.traerVenta(id_venta), HttpStatus.OK);

    }

    @DeleteMapping("eliminar/{id_venta}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Long id_venta) {
        return new ResponseEntity<>(ventaServ.eliminarVenta(id_venta), HttpStatus.OK);
    }

}
