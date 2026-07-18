package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.PagoTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoTarjetaRepository extends JpaRepository<PagoTarjeta, Integer> {

}
