package com.torneo.goldesk.Service;

import com.torneo.goldesk.Entity.PagoTarjeta;
import com.torneo.goldesk.Entity.Tarjeta;
import com.torneo.goldesk.Repository.PagoTarjetaRepository;
import com.torneo.goldesk.Repository.TarjetaRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PagoTarjetaService {

    private final PagoTarjetaRepository pagoTarjetaRepository;
    private final TarjetaRepository tarjetaRepository;

    public PagoTarjetaService(PagoTarjetaRepository pagoTarjetaRepository, TarjetaRepository tarjetaRepository) {
        this.pagoTarjetaRepository = pagoTarjetaRepository;
        this.tarjetaRepository = tarjetaRepository;
    }

    public void registarPagoTarjeta(Map<String, Object> map){
        Tarjeta tarjeta = tarjetaRepository.findByIdTarjeta((Integer) map.get("idTarjeta"))
                .orElseThrow(() -> new RuntimeException("No se encontró la Tarjeta"));

        PagoTarjeta pagoTarjeta = new PagoTarjeta();
        pagoTarjeta.setTarjeta(tarjeta);
        pagoTarjeta.setMontoPagado((Double) map.get("monto"));
        String observacion = (String) map.get("observacion");
        pagoTarjeta.setObservacion(observacion == null?"":observacion);
        pagoTarjetaRepository.save(pagoTarjeta);
    }

    public void obtenerAbonosEquipos(){

    }
}
