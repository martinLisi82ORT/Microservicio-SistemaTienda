package com.example.carritoservice.service;

import com.example.carritoservice.dto.CarritoDTO;
import com.example.carritoservice.dto.ProductoDTO;
import com.example.carritoservice.model.Carrito;
import com.example.carritoservice.repository.ICarritoRepository;
import com.example.carritoservice.repository.IProductoAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarritoService implements ICarritoService {

    @Autowired
    private ICarritoRepository carritoRepo;

    @Autowired
    private IProductoAPI prodAPI;

    @Override
    public Carrito crearCarrito(Carrito carrito) {
        carrito.setSumaTotal((double) 0);
        return carritoRepo.save(carrito);
    }

    @Override
    public String eliminarCarrito(Long id_carrito) {
        carritoRepo.deleteById(id_carrito);
        return "El carrito id " + id_carrito + " fue eliminado";
    }

    @Override
    public List<Carrito> traerCarritos() {
        return carritoRepo.findAll();
    }

    @Override
    @CircuitBreaker(name = "producto-service", fallbackMethod = "fallbackGetCarritoDTO")
    @Retry(name = "producto-service")
    public CarritoDTO traerCarritoDTO(Long id) {
        Carrito carrito = carritoRepo.findById(id).orElse(null);
        List<ProductoDTO> listaProdDto = new ArrayList<>();

        for (Long id_prod : carrito.getListaProductos()) {
            ProductoDTO prodDto = prodAPI.traerProducto(id_prod);
            listaProdDto.add(prodDto);
        }

        // Simula Exception para probar CircuitBreaker
        //createException();

        return new CarritoDTO(carrito.getId(), carrito.getSumaTotal(), listaProdDto);
    }

    @Override
    public Carrito traerCarrito(Long id) {
        return carritoRepo.findById(id).orElse(null);
    }

    @Override
    @CircuitBreaker(name = "producto-service", fallbackMethod = "fallbackGetCarritoDTO")
    @Retry(name = "producto-service")
    public CarritoDTO agregarProducto(Long id_carrito, Long id_prod) {

        // Simula Exception para probar CircuitBreaker
        //createException();


        // Buscar Carrito
        Carrito carrito = carritoRepo.findById(id_carrito).orElse(null);

        // Buscar ProductoDTO
        ProductoDTO prodDo = prodAPI.traerProducto(id_prod);

        // Actualiza lista Carrito
        List<Long> lista = carrito.getListaProductos();
        lista.add(prodDo.getCodigo());
        carrito.setListaProductos(lista);

        // Actualiza SumaTotal Carrito
        double total = carrito.getSumaTotal();
        carrito.setSumaTotal(total += prodDo.getPrecio());
        carritoRepo.save(carrito);

        // Crea lista de ProductoDTO
        List<ProductoDTO> listaProd = new ArrayList<>();
        for (Long prod : lista) {
            listaProd.add(prodAPI.traerProducto(prod));
        }

        return new CarritoDTO(carrito.getId(), total, listaProd);
    }

    @Override
    public CarritoDTO eliminarProducto(Long id_carrito, Long id_prod) {
        // Buscar Carrito
        Carrito carrito = carritoRepo.findById(id_carrito).orElse(null);

        // Buscar ProductoDTO
        ProductoDTO prodDo = prodAPI.traerProducto(id_prod);

        // Crea CarritoDTO
        CarritoDTO carritoDto = new CarritoDTO();

        if (carrito.getListaProductos().contains(prodDo.getCodigo())) {
            Double total = carrito.getSumaTotal();
            total -= prodDo.getPrecio();
            carrito.setSumaTotal(total);
            List<Long> lista = carrito.getListaProductos();
            lista.remove(prodDo.getCodigo());
            carritoRepo.save(carrito);

            // Crea lista de ProductoDTO
            List<ProductoDTO> listaProd = new ArrayList<>();
            for (Long prod : lista) {
                listaProd.add(prodAPI.traerProducto(prod));
            }

            carritoDto.setId(carrito.getId());
            carritoDto.setSumaTotal(total);
            carritoDto.setListaProductos(listaProd);
        }
        return carritoDto;
    }

    private CarritoDTO fallbackGetCarritoDTO(Throwable throwable) {

        return new CarritoDTO(0L, (double) 0, null);
    }

    private void createException() {
        throw new IllegalArgumentException("Prueba resilience y y Circuit Breaker");
    }

}
