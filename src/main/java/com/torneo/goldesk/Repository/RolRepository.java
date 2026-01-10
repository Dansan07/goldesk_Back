package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol,Integer> {

    Optional<Rol> findByIdRol(Integer idRol);

    Optional<Rol> findByTipoRol(String nombreRol);
}
