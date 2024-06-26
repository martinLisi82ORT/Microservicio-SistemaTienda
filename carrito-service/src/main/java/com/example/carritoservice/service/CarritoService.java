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
    public List<CarritoDTO> traerCarritos() {
        List<CarritoDTO> listaDto = new ArrayList<>();
        List<Carrito> listaCarritos = carritoRepo.findAll();
        for (Carrito carr : listaCarritos) {
            CarritoDTO carritoDto = this.traerCarritoDTO(carr.getId());
            listaDto.add(carritoDto);
        }
        return listaDto;
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
    public boolean existe(Long id) {
        return carritoRepo.existsById(id);
    }

    @Override
    @CircuitBreaker(name = "producto-service", fallbackMethod = "fallbackGetCarritoDTO")
    @Retry(name = "producto-service")
    public Object[] agregarProducto(Long id_carrito, Long id_prod) {
        Object[] listaObj = new Object[2];

        // Simula Exception para probar CircuitBreaker
        //createException();

        // Buscar Carrito
        Carrito carrito = carritoRepo.findById(id_carrito).orElse(null);

        // Buscar ProductoDTO
        ProductoDTO prodDo = prodAPI.traerProducto(id_prod);

        if (carrito != null && prodDo != null) {
            actualizarlStock(id_prod, carrito, prodDo, listaObj);
        } else {
            listaObj[0] = "Error";
            listaObj[1] = "Carrito o Producto Null";
        }

        return listaObj;
    }

    // Resta el stock de Producto y modifica listaObj
    private void actualizarlStock(Long id_prod, Carrito carrito, ProductoDTO prodDo, Object[] listaObj) {
        if (prodAPI.traerStock(id_prod) > 0) {
            // Actualiza stock Producto
            prodAPI.restarStock(id_prod);

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

            listaObj[0] = "Producto agregado correctamente";
            listaObj[1] = new CarritoDTO(carrito.getId(), total, listaProd);
        } else {
            listaObj[0] = "Error";
            listaObj[1] = "Stock insuficiente";
        }
    }

    @Override
    @CircuitBreaker(name = "producto-service", fallbackMethod = "fallbackGetCarritoDTO")
    @Retry(name = "producto-service")
    public Object[] eliminarProducto(Long id_carrito, Long id_prod) {
        Object[] listaObj = new Object[2];

        // Buscar Carrito
        Carrito carrito = carritoRepo.findById(id_carrito).orElse(null);

        // Buscar ProductoDTO
        ProductoDTO prodDo = prodAPI.traerProducto(id_prod);

        if (carrito != null && prodDo != null) {
            this.quitarProducto(carrito, prodDo, listaObj);
        } else {
            listaObj[0] = "Error";
            listaObj[1] = "Carrito o Producto Null";
        }
        return listaObj;
    }

    // Suma stock en Producto y modifica listaObj
    private void quitarProducto(Carrito carrito, ProductoDTO prodDo, Object[] listaObj) {
        // Crea CarritoDTO
        CarritoDTO carritoDto = new CarritoDTO();

        if (carrito.getListaProductos().contains(prodDo.getCodigo())) {
            prodAPI.sumarStock(prodDo.getCodigo());
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

            listaObj[0] = "Producto eliminado correctamente";
            listaObj[1] = carritoDto;
        } else {
            listaObj[0] = "Error";
            listaObj[1] = "Producto no encontrado en Carrito";
        }
    }

    @Override
    public String eliminarCarrito(Long id_carrito) {
        String msj = "Carrito no encontrado";
        if (carritoRepo.findById(id_carrito).orElse(null) != null) {
            carritoRepo.deleteById(id_carrito);
            msj = "El carrito id " + id_carrito + " fue eliminado";
        }
        return msj;
    }

    private CarritoDTO fallbackGetCarritoDTO(Throwable throwable) {

        return new CarritoDTO(0L, (double) 0, null);
    }

    private void createException() {
        throw new IllegalArgumentException("Prueba resilience y y Circuit Breaker");
    }

}
