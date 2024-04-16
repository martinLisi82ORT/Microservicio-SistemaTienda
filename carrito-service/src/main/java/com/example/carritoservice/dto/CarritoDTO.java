package com.example.carritoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarritoDTO {

    private Long id;
    private Double sumaTotal;

    private List<ProductoDTO> listaProductos;


}
