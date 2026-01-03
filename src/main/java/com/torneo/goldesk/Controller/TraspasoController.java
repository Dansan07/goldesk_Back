package com.torneo.goldesk.Controller;

import com.torneo.goldesk.Service.PdfGeneratorService;
import com.torneo.goldesk.Service.TraspasoService;
import com.torneo.goldesk.dto.traspaso.TraspasoCreateDTO;
import com.torneo.goldesk.dto.traspaso.TraspasoResponseDTO;
import com.torneo.goldesk.dto.traspaso.TraspasoUpdateDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PatchMapping("/{id}/subir-firmado")
    public ResponseEntity<String> subirDocumentoFirmado(
            @PathVariable Integer id,
            @RequestParam("archivo") MultipartFile archivo) {
        try {
            traspasoService.guardarDocumentoFirmado(id, archivo);
            return ResponseEntity.ok("Documento firmado subido exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al subir el archivo: " + e.getMessage());
        }
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORGANIZADOR')")
    public ResponseEntity<List<TraspasoResponseDTO>> listarMisSolicitudes(
            @RequestParam String estado) {
        return ResponseEntity.ok(traspasoService.listarPorEstadoYOrganizador(estado));
    }

    // 4. Finalizar el proceso (Aprobar)
    @PatchMapping("/aprobar/{idTraspaso}")
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    public ResponseEntity<?> aprobarSolicitud(@PathVariable Integer idTraspaso) {
        return ResponseEntity.ok(traspasoService.aprobarSolicitud(idTraspaso));
    }

    // 4. Finalizar el proceso (Aprobar)
    @PatchMapping("/rechazar")
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    public ResponseEntity<?> rechazarSolicitud(@RequestBody TraspasoUpdateDTO dto) {
        return ResponseEntity.ok(traspasoService.rechazarSolicitud(dto));
    }

}
