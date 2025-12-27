package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Entity.Traspaso;
import com.torneo.goldesk.Service.PdfGeneratorService;
import com.torneo.goldesk.Service.TraspasoService;
import com.torneo.goldesk.dto.traspaso.TraspasoCreateDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/traspaso")
public class TraspasoController {

    private final TraspasoService traspasoService;
    private final PdfGeneratorService pdfGeneratorService;


    public TraspasoController(TraspasoService traspasoService, PdfGeneratorService pdfGeneratorService) {
        this.traspasoService = traspasoService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    // 1. Iniciar el trámite (Crea el registro en la DB)
    @PostMapping("/crear")
    public ResponseEntity<byte[]> crearSolicitud(@RequestBody TraspasoCreateDTO dto) {
        byte[] pdfConsten = traspasoService.crearSolicitud(dto);

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        headers.setContentDispositionFormData("attachment","Solicitud_Traspaso.pdf");
        return new ResponseEntity<>(pdfConsten, headers, HttpStatus.CREATED);
    }

    // 2. Generar y descargar el PDF para firmar
    @GetMapping("/descargar-carta/{idTraspaso}")
    public ResponseEntity<byte[]> descargarPDF(@PathVariable Integer idTraspaso) {
        try {
            byte[] pdfContents = pdfGeneratorService.generarPdfTraspaso(idTraspaso);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // El attachment fuerza la descarga en el navegador
            headers.setContentDispositionFormData("attachment", "Solicitud_Traspaso_N° " + idTraspaso + ".pdf");

            return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 3. Obtener solicitudes por estado (Para la vista del Organizador)
    @GetMapping("/mis-solicitudes")
    public ResponseEntity<List<Traspaso>> listarMisSolicitudes(
            @RequestParam String estado,
            @RequestParam String cedulaOrg) {
        return ResponseEntity.ok(traspasoService.listarPorEstadoYOrganizador(estado, cedulaOrg));
    }

    // 4. Finalizar el proceso (Aprobar/Rechazar)
    @PatchMapping("/responder/{idTraspaso}")
    public ResponseEntity<String> responder(
            @PathVariable Integer idTraspaso,
            @RequestParam String nuevoEstado,
            @RequestParam String observaciones,
            @RequestParam String cedulaOrg) {
        return ResponseEntity.ok(traspasoService.responderSolicitud(idTraspaso, nuevoEstado, observaciones, cedulaOrg));
    }

}
