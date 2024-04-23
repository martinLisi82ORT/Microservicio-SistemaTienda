package com.example.ventaservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venta;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha no puede ser Null")
    private LocalDate fecha;
    @NotNull(message = "Id Carrito no puede ser Null")
    @Min(value = 0, message = "Id Carrito no puede ser inferior a cero")
    private Long carrito_id;

}
