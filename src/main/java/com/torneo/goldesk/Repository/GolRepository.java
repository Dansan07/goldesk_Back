package com.torneo.goldesk.Repository;

import com.torneo.goldesk.Entity.Gol;
import com.torneo.goldesk.dto.gol.GolResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GolRepository extends JpaRepository<Gol,Integer> {

    Optional<Gol> findByIdGol(Integer idGol);

    // Busca los goles usando directamente la llave foránea de la tabla intermedia
    List<GolResponseDTO> findByParticipacionJugador_IdParticipacion(Integer idParticipacion);
}
