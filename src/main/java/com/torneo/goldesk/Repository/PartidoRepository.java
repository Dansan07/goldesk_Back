package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Partido;
import com.torneo.goldesk.Entity.TorneoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoRepository extends JpaRepository<Partido,Integer> {

    @Query("SELECT COUNT(p) > 0 FROM Partido p WHERE " +
            "((p.local = :local AND p.visitante = :visitante) OR " +
            "(p.local = :visitante AND p.visitante = :local)) " +
            "AND p.faseTorneo = :fase")
    boolean existeEnfrentamiento(@Param("local") TorneoEquipo local,
                                 @Param("visitante") TorneoEquipo visitante,
                                 @Param("fase") String fase);
}
