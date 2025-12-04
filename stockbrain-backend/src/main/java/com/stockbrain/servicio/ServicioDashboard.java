package com.stockbrain.servicio;

import com.stockbrain.modelo.dto.DashboardResponse;
import com.stockbrain.modelo.dto.ProductoVendidoDTO;
import com.stockbrain.modelo.dto.VentaDiaDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioDashboard {

    @PersistenceContext
    private final EntityManager em;

    private static final DateTimeFormatter DIA_FORMATTER =
            DateTimeFormatter.ofPattern("EEE", new Locale("es", "ES"));

    @Cacheable(value = "dashboard", key = "#tiendaId")
    public DashboardResponse obtenerDashboard(Long tiendaId) {

        Double ventasHoy = em.createQuery("""
                        SELECT COALESCE(SUM(v.cantidad * v.producto.precio), 0)
                        FROM EntidadVenta v
                        WHERE v.tienda.id = :tiendaId 
                          AND v.fecha = CURRENT_DATE
                        """, Double.class)
                .setParameter("tiendaId", tiendaId)
                .getSingleResult();

        Long totalProductos = em.createQuery("""
                        SELECT COUNT(p) 
                        FROM EntidadProducto p 
                        WHERE p.tienda.id = :tiendaId
                        """, Long.class)
                .setParameter("tiendaId", tiendaId)
                .getSingleResult();

        Long stockBajo = em.createQuery("""
                        SELECT COUNT(p) 
                        FROM EntidadProducto p 
                        WHERE p.tienda.id = :tiendaId 
                          AND p.stock < 10
                        """, Long.class)
                .setParameter("tiendaId", tiendaId)
                .getSingleResult();

        List<Object[]> raw7dias = em.createNativeQuery("""
                        SELECT v.fecha, COALESCE(SUM(v.cantidad), 0)
                        FROM ventas v
                        WHERE v.tienda_id = :tiendaId
                        AND v.fecha >= CURRENT_DATE - INTERVAL '6 days'
                        GROUP BY v.fecha
                        ORDER BY v.fecha
                        """)
                .setParameter("tiendaId", tiendaId)
                .getResultList();

        List<VentaDiaDTO> ventas7Dias = new ArrayList<>();

        LocalDate lunes = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        for (int i = 0; i < 7; i++) {
            LocalDate fecha = lunes.plusDays(i);
            String diaCorto = fecha.format(DIA_FORMATTER).toUpperCase().substring(0, 3);

            int cantidad = raw7dias.stream()
                    .filter(row -> ((java.sql.Date) row[0]).toLocalDate().equals(fecha))
                    .findFirst()
                    .map(row -> ((Number) row[1]).intValue())
                    .orElse(0);

            ventas7Dias.add(new VentaDiaDTO(diaCorto, cantidad));
        }

        List<ProductoVendidoDTO> top5Productos = em.createQuery("""
                        SELECT p.nombre, COALESCE(SUM(v.cantidad), 0L)
                        FROM EntidadVenta v
                        JOIN v.producto p
                        WHERE v.tienda.id = :tiendaId
                          AND v.fecha >= :fechaCorte
                        GROUP BY p.id, p.nombre
                        ORDER BY SUM(v.cantidad) DESC
                        """, Object[].class)
                .setParameter("tiendaId", tiendaId)
                .setParameter("fechaCorte", LocalDate.now().minusDays(30))
                .setMaxResults(5)
                .getResultList()
                .stream()
                .map(row -> new ProductoVendidoDTO((String) row[0], Math.toIntExact((Long) row[1])))
                .toList();

        return new DashboardResponse(ventasHoy, totalProductos, stockBajo, ventas7Dias, top5Productos);
    }
}