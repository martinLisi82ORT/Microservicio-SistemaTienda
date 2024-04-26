# Microservicio Sistema de Tienda - API RESTful con Spring Boot

Microservicio programado en **Java** mediante **Spring Cloud**. Se utilizaron los siguientes patrones de diseño: **Service Registry** (Eureka Server), **Load Balancing**, **Circuit Breaker** (Resilence4J), **API Gateway** y **Config Server**. La arquitectura de Cliente/Servidor mediante protocolo HTTP se desarrolló con **Spring Boot** y la misma está configurada con una Base de Datos **MySQL**.

Arquitectura de Software de Microservicios que permite el manejo de **Productos**, **Carritos de Compras** y **Ventas** de una Tienda de electrodomésticos online :computer:. La aplicación permite realizar un CRUD completo (GET, POST, DELETE y PUT) de todas sus entidades con validación de datos (datos vacíos, nulos, numéricos y fecha) mediante la annotation _@Valid_.

- El Microservicio de **Producto** gestiona la información de los mismos guardando los siguientes datos: _código_, _marca_, _nombre_, _precio_ y _stock_.

- El Microservicio de **Carrito de Compras** maneja el carrito de compras de los usuarios. Se crea vacío y guarda un _Id_, un _listado de Productos_ y la _suma total_ de los mismos. El  Servicio de **Carrito** permite agregar y quitar **Productos**, actualizando la suma total y también el stock de estos **Productos** en su Base de Datos.

- El Microservicio de **Venta** se encarga de registrar cada venta mediante un _Id_, una _fecha_ y la asignación de un **Carrito de Compras** creado anteriormente. La **Venta** permite conocer el monto de la misma (al consultar el **Carrito de Compras**) y la _lista de Productos_ (consultando el servicio de **Carrito de Compras** que a su vez consume el servicio de **Productos**).

Todo el proceso valida que el **Producto** exista en la Base de Datos y que su stock sea suficiente para poder ser agregado al **Carrito de Compras**, avisando al Usuario en caso contrario.

Todos los Microservicios se registran en un servidor Eureka (**Service Registry**) permitiendo la comunicación entre los mismos. Mediante **Spring Cloud Load Balancer** se implementa un balanceo de carga para distribuir de manera equitativa y eficiente las solicitudes entrantes entre múltiples instancias de un servidor. Mediante **Circuit Breaker**, a través de _Resilience4J_, se controlan y manejan posibles fallas en la comunicación entre microservicios. Mediante **API Gateway** se permite un acceso único para el usuario como principal punto de acceso. Finalmente, mediante **Config Server** se implementa un Servidor Centralizado de Configuraciones para todos los Microservicios.
Cuenta también con los respectivos archivos _Dockerfile_ y el archivo _Docker Compose_ (docker-compose.yml) para poder construir y ejecutar un contenedor mediante **Docker**.

**URL base:** 
>`http://localhost:${PORT}/api/tienda`



---

### :page_facing_up: Documentacion:
[Postman Documentation] (https://documenter.getpostman.com/view/32556955/2sA3Bt39wV)

---
### Herramientas utilizadas:
Java | SpringBoot | MySQL | Docker | Netbeans 

<div align="center">
<img width="60" height="30" src="https://elblogdecodigo.files.wordpress.com/2014/12/java_logo.png" />

<img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot" />

<img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" />

<img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" />

<img src="https://img.shields.io/badge/apache%20netbeans-1B6AC6?style=for-the-badge&logo=apache%20netbeans%20IDE&logoColor=white" />
</div

