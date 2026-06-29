package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TorneoRepository extends JpaRepository<Torneo, Integer> {

    List<Torneo> findByActivoTrue();
    List<Torneo> findByActivoFalse();

    // Busca torneos de un organizador, pero solo los que el torneo esté activo
    List<Torneo> findByOrganizador_CedulaOrgAndActivoTrue(String cedulaOrg);

    List<Torneo> findByOrganizador_CedulaOrg(String cedulaOrg);

    // Spring interpreta "FindDistinct" como SELECT DISTINCT
    @Query("""
    SELECT DISTINCT t.categoriaTorneo
    FROM Torneo t
    WHERE t.organizador.cedulaOrg = :cedulaOrg
""")
    List<String> findCategorias(@Param("cedulaOrg") String cedulaOrg);

    Optional<Torneo> findByIdTorneo(Integer idTorneo);

    Boolean existsByOrganizador_CedulaOrgAndNombreTorneo(String cedulaOrg, String nombreTorneo);
}
