package com.stockbrain.controlador;

import com.stockbrain.modelo.dao.ITiendaDAO;
import com.stockbrain.modelo.entidad.EntidadProducto;
import com.stockbrain.modelo.entidad.EntidadTienda;
import com.stockbrain.servicio.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://192.168.1.133:8080/")
public class ControladorProducto {

    @Autowired
    private ServicioProducto servicioProducto;

    @Autowired
    private ITiendaDAO tiendaDAO;

    @Value("${app.upload.dir:uploads/productos}")
    private String uploadDir;

    @GetMapping
    public List<EntidadProducto> listarProductos() {
        return servicioProducto.listarTodosLosProductos();
    }

    @GetMapping("/tienda/{tiendaId}")
    public List<EntidadProducto> listarProductosPorTienda(@PathVariable Long tiendaId) {
        return servicioProducto.listarPorTienda(tiendaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadProducto> obtenerProductoPorId(@PathVariable Long id) {
        return servicioProducto.buscarProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<EntidadProducto> crearProductoConImagen(
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("stock") int stock,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam("tiendaId") Long tiendaId) {

        // Validar tienda
        EntidadTienda tienda = tiendaDAO.findById(tiendaId)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada: " + tiendaId));

        EntidadProducto producto = new EntidadProducto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setDescripcion(descripcion);
        producto.setTienda(tienda);

        // Subir imagen
        if (imagen != null && !imagen.isEmpty()) {
            String ruta = guardarImagen(imagen);
            producto.setImagenRuta(ruta);
        }

        EntidadProducto guardado = servicioProducto.guardarProducto(producto);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<EntidadProducto> actualizarProductoConImagen(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("stock") int stock,
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam(value = "tiendaId", required = false) Long tiendaId) {

        return servicioProducto.buscarProductoPorId(id)
                .map(producto -> {
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    producto.setStock(stock);
                    producto.setDescripcion(descripcion);

                    // Cambiar tienda solo si se envía y existe
                    if (tiendaId != null) {
                        EntidadTienda nuevaTienda = tiendaDAO.findById(tiendaId)
                                .orElseThrow(() -> new RuntimeException("Tienda no encontrada: " + tiendaId));
                        producto.setTienda(nuevaTienda);
                    }

                    // Reemplazar imagen
                    if (imagen != null && !imagen.isEmpty()) {
                        borrarImagenAnterior(producto.getImagenRuta());
                        String nuevaRuta = guardarImagen(imagen);
                        producto.setImagenRuta(nuevaRuta);
                    }

                    return ResponseEntity.ok(servicioProducto.guardarProducto(producto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarProducto(@PathVariable Long id) {
        return servicioProducto.buscarProductoPorId(id)
                .map(producto -> {
                    borrarImagenAnterior(producto.getImagenRuta());
                    servicioProducto.eliminarProductoPorId(id);
                    return ResponseEntity.<Void>noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // === MÉTODOS AUXILIARES ===
    private String guardarImagen(MultipartFile imagen) {
        try {
            validarImagen(imagen);
            String fileName = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, imagen.getBytes());
            return "/uploads/productos/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar imagen", e);
        }
    }

    private void borrarImagenAnterior(String ruta) {
        if (ruta != null && !ruta.isEmpty()) {
            try {
                String fileName = ruta.substring(ruta.lastIndexOf("/") + 1);
                Path path = Paths.get(uploadDir, fileName);
                Files.deleteIfExists(path);
            } catch (Exception e) {
                System.err.println("No se pudo borrar imagen: " + ruta);
            }
        }
    }

    private void validarImagen(MultipartFile imagen) {
        if (imagen.getSize() > 5 * 1024 * 1024) { // 5MB
            throw new IllegalArgumentException("La imagen no puede superar 5MB");
        }
        String contentType = imagen.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten imágenes");
        }
    }
}
