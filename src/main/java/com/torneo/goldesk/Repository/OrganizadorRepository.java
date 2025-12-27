package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Organizador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface OrganizadorRepository extends JpaRepository<Organizador,String> {

    List<Organizador> findByActivoTrue();
    List<Organizador> findByActivoFalse();
    //verifica que el Email ingresado existe
    Optional<Organizador> findByEmailOrg(String email);

    //Mtodo para verificar si el código de invitado ya existe
    boolean existsByCodigoInvitado(String codigoInvitado);

    Optional<Organizador> findByCedulaOrg(String cedulaOrg);
}
