package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Tarjeta;
import com.torneo.goldesk.dto.tarjeta.TarjetaResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta,Integer> {

    Optional<Tarjeta> findByIdTarjeta(Integer idTarjeta);

    List<Tarjeta> findByParticipacionJugador_IdParticipacionAndTipoTarjeta(Integer idParticipacion, String tipoTarjeta);

    List<Tarjeta> findByParticipacionJugador_TorneoEquipoJugador_TorneoEquipo_Torneo_IdTorneo(Integer idTorneo);
}
