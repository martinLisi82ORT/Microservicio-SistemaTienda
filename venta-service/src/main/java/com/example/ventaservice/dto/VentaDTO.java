package com.example.ventaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO {

    private Long id_venta;
    private LocalDate fecha;
    private Long carrito_id;

    private CarritoDTO carritoDto;

}
