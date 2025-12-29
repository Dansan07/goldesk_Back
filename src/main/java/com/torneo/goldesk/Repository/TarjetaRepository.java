package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Tarjeta;
import com.torneo.goldesk.dto.tarjeta.TarjetaResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta,Integer> {

    @Query("SELECT new com.torneo.goldesk.dto.tarjeta.TarjetaResponseDTO(" +
            "t.idTarjeta, t.tipoTarjeta, t.jugador.jugador.nombreJugador, t.valorTarjeta, t.motivoTarjeta) " +
            "FROM Tarjeta t WHERE t.partido.idPartido = :idPartido " +
            "AND t.jugador.idTorneoEquipoJugador = :idTEJ")
    List<TarjetaResponseDTO> buscarTarjetasJugadorEnPartido(
            @Param("idPartido") Integer idPartido,
            @Param("idTEJ") Integer idTEJ);

    Optional<Tarjeta> findByIdTarjeta(Integer idTarjeta);
}
