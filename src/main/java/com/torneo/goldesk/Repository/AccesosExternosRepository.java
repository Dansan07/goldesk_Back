package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.AccesosExternos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccesosExternosRepository extends JpaRepository<AccesosExternos,Integer> {
    // Buscamos por el código y que esté activo
    Optional<AccesosExternos> findByCodigoAccesoAndActivoTrue(String codigoAcceso);
}
