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
    public ResponseEntity<?> crearSolicitud(@RequestBody TraspasoCreateDTO dto) {
        try {
            byte[] pdfContent = traspasoService.crearSolicitud(dto);

            // Si todo sale bien, devolvemos el PDF
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Solicitud_Traspaso.pdf\"")
                    .body(pdfContent);

        } catch (RuntimeException e) {
            // Si el Service lanza la excepción de "Ya existe una solicitud",
            // aquí la capturamos para enviar un mensaje claro al Front.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage());
        }
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
    public ResponseEntity<?> listarMisSolicitudes(
            @RequestParam String estado) {
        try {
            return ResponseEntity.ok(traspasoService.listarPorEstadoYOrganizador(estado));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. Finalizar el proceso (Aprobar)
    @PatchMapping("/aprobar/{idTraspaso}")
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    public ResponseEntity<?> aprobarSolicitud(@PathVariable Integer idTraspaso) {
        try {
            return ResponseEntity.ok(traspasoService.aprobarSolicitud(idTraspaso));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. Finalizar el proceso (rechazar)
    @PatchMapping("/rechazar")
    @PreAuthorize("hasRole('ROLE_ORGANIZADOR')")
    public ResponseEntity<?> rechazarSolicitud(@RequestBody TraspasoUpdateDTO dto) {
        try {
            return ResponseEntity.ok(traspasoService.rechazarSolicitud(dto));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
