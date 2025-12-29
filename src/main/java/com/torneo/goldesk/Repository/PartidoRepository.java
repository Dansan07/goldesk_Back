package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Partido;
import com.torneo.goldesk.Entity.TorneoEquipo;
import com.torneo.goldesk.dto.tablaPosiciones.TablaPosicionesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartidoRepository extends JpaRepository<Partido,Integer> {

    @Query("SELECT new com.torneo.goldesk.dto.tablaPosiciones.TablaPosicionesDTO(" +
            "e.nombreEquipo, " +
            "SUM(CASE WHEN p.estado IN ('FINALIZADO', 'EN_CURSO') THEN 1L ELSE 0L END), " + // Nota el 1L para Long
            "SUM(CASE WHEN (p.local.equipo = e AND p.golesLocal > p.golesVisitante) OR (p.visitante.equipo = e AND p.golesVisitante > p.golesLocal) THEN 1L ELSE 0L END), " +
            "SUM(CASE WHEN p.golesLocal = p.golesVisitante AND p.estado IN ('FINALIZADO', 'EN_CURSO') THEN 1L ELSE 0L END), " +
            "SUM(CASE WHEN (p.local.equipo = e AND p.golesLocal < p.golesVisitante) OR (p.visitante.equipo = e AND p.golesVisitante < p.golesLocal) THEN 1L ELSE 0L END), " +
            "SUM(CASE WHEN p.local.equipo = e THEN CAST(p.golesLocal AS long) ELSE CAST(p.golesVisitante AS long) END), " +
            "SUM(CASE WHEN p.local.equipo = e THEN CAST(p.golesVisitante AS long) ELSE CAST(p.golesLocal AS long) END), " +
            "SUM(CASE WHEN p.local.equipo = e THEN CAST(p.golesLocal - p.golesVisitante AS long) ELSE CAST(p.golesVisitante - p.golesLocal AS long) END), " +
            "SUM(CASE " +
            "  WHEN (p.local.equipo = e AND p.golesLocal > p.golesVisitante) OR (p.visitante.equipo = e AND p.golesVisitante > p.golesLocal) THEN 3L " +
            "  WHEN p.golesLocal = p.golesVisitante AND p.estado IN ('FINALIZADO', 'EN_CURSO') THEN 1L ELSE 0L END), " +
            "(CASE WHEN SUM(CASE WHEN p.estado = 'EN_CURSO' THEN 1 ELSE 0 END) > 0 THEN true ELSE false END)" +
            ") " +
            "FROM Equipo e " +
            "JOIN TorneoEquipo te ON te.equipo = e " +
            "JOIN Partido p ON (p.local = te OR p.visitante = te) " +
            "WHERE te.torneo.idTorneo = :idTorneo AND p.estado IN ('FINALIZADO', 'EN_CURSO') " +
            "GROUP BY e.nombreEquipo " +
            "ORDER BY 9 DESC, 8 DESC")
    List<TablaPosicionesDTO> generarTablaPosiciones(@Param("idTorneo") Integer idTorneo);

    @Query("SELECT COUNT(p) > 0 FROM Partido p WHERE " +
            "((p.local = :local AND p.visitante = :visitante) OR " +
            "(p.local = :visitante AND p.visitante = :local)) " +
            "AND p.faseTorneo = :fase")
    boolean existeEnfrentamiento(@Param("local") TorneoEquipo local,
                                 @Param("visitante") TorneoEquipo visitante,
                                 @Param("fase") String fase);

    Optional<Partido> findByIdPartido(Integer idPartido);
}
