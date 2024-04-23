package com.example.productoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @NotNull(message = "Nombre no puede ser Null")
    @NotBlank(message = "Nombre no puede estar vacio")
    private String nombre;
    @NotNull(message = "Marca no puede ser Null")
    @NotBlank(message = "Marca no puede estar vacio")
    private String marca;
    @NotNull(message = "Stock no puede ser Null")
    @Min(value = 0, message = "Stock no puede ser inferior a cero")
    private int stock;
    @NotNull(message = "Precio no puede ser Null")
    @Min(value = 0, message = "Precio no puede ser inferior a cero")
    private Double precio;


}
