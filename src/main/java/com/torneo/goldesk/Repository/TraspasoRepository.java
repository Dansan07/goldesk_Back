package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Traspaso;
import com.torneo.goldesk.dto.traspaso.TraspasoResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TraspasoRepository extends JpaRepository<Traspaso, Integer> {

    Optional<Traspaso> findByIdTraspaso(Integer idTraspaso);

    // Para ver las que YA respondió un organizador específico
    List<Traspaso> findByEstadoAndOrganizador_CedulaOrg(String estado, String cedulaOrg);

    // Para ver las PENDIENTES de los torneos que gestiona ese organizador
    // (Asumiendo que el Organizador tiene una relación con sus Torneos)
    @Query("SELECT t FROM Traspaso t WHERE t.estado = :estado AND t.torneoEquipoSolicita.torneo.organizador.cedulaOrg = :cedulaOrg")
    List<Traspaso> findPendientesPorOrganizador(@Param("estado") String estado, @Param("cedulaOrg") String cedulaOrg);

    //verificar si existe una solicitud para no duplicarla
    boolean existsByJugador_IdJugadorAndEstado(Integer idJugador, String estado);

    // Para ver el historial de un jugador específico
    List<Traspaso> findByJugador_IdJugador(Integer idJugador);


}
