package com.example.carritoservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {

    private Long codigo;
    private String nombre;
    private String marca;
    private Double precio;


}
