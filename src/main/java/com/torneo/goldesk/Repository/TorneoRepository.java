package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TorneoRepository extends JpaRepository<Torneo, Integer> {

    List<Torneo> findByActivoTrue();
    List<Torneo> findByActivoFalse();

    // Busca torneos de un organizador, pero solo los que el torneo esté activo
    List<Torneo> findByOrganizador_CedulaOrgAndActivoTrue(String cedulaOrg);

    Optional<Torneo> findByIdTorneo(Integer idTorneo);
}
