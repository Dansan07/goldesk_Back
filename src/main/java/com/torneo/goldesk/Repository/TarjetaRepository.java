package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Tarjeta;
import com.torneo.goldesk.dto.registroPagos.tarjetas.TarjetaTorneoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta,Integer> {

    Optional<Tarjeta> findByIdTarjeta(Integer idTarjeta);

    List<Tarjeta> findByParticipacionJugador_IdParticipacionAndTipoTarjeta(Integer idParticipacion, String tipoTarjeta);

    List<Tarjeta> findByParticipacionJugador_TorneoEquipoJugador_TorneoEquipo_Torneo_IdTorneo(Integer idTorneo);

    @Query(value = "SELECT * FROM obtener_tarjetas_torneo(:idTorneo, :estadoPago)", nativeQuery = true)
    List<TarjetaTorneoResponse.Vista> obtenerTarjetasTorneo(
            @Param("idTorneo") Integer idTorneo,
            @Param("estadoPago") String estadoPago
    );
}
