package com.torneo.goldesk.Service;

import com.torneo.goldesk.Repository.JugadorRepositoty;
import org.springframework.stereotype.Service;

@Service
public class JugadorService {

    private final JugadorRepositoty jugadorRepositoty;


    public JugadorService(JugadorRepositoty jugadorRepositoty) {
        this.jugadorRepositoty = jugadorRepositoty;
    }
}
