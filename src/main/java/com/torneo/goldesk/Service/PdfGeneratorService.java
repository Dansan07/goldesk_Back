package com.torneo.goldesk.Service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.torneo.goldesk.Entity.Traspaso;
import com.torneo.goldesk.Repository.TraspasoRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneratorService {

    private final TraspasoRepository traspasoRepository;

    public PdfGeneratorService(TraspasoRepository traspasoRepository) {
        this.traspasoRepository = traspasoRepository;
    }

    public byte[] generarPdfTraspaso(Integer idTraspaso) {
        Traspaso solicitud = traspasoRepository.findById(idTraspaso)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50); // Márgenes

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            // --- EVENTO PARA MARCA DE AGUA ---
            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    try {
                        String urlImagen = solicitud.getOrganizador().getUrlLogoOrg();

                        if (urlImagen != null && !urlImagen.isEmpty()) {
                            // Image.getInstance(String url) descarga la imagen automáticamente
                            Image logo = Image.getInstance(urlImagen);

                            // Centrado en una página A4 (aprox 595x842 puntos)
                            logo.setAbsolutePosition(150, 300);
                            logo.scaleToFit(300, 300);

                            PdfContentByte canvas = writer.getDirectContentUnder();
                            PdfGState gs = new PdfGState();
                            gs.setFillOpacity(0.12f); // Un poco más visible (12%)
                            canvas.setGState(gs);
                            canvas.addImage(logo);
                        }
                    } catch (Exception e) {
                        // Si la URL está rota o no hay internet, el PDF sale sin marca de agua pero NO falla
                        System.err.println("Error cargando marca de agua desde URL: " + e.getMessage());
                    }
                }
            });

            // Fuentes con Estilo
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.DARK_GRAY);
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.WHITE);
            Font fontDatos = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font fontNegrita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            // --- CABECERA ---
            String nombreTorneo= solicitud.getTorneoEquipoSolicita().getTorneo().getNombreTorneo().toUpperCase();
            Paragraph titulo = new Paragraph(nombreTorneo, fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Paragraph subtitulo = new Paragraph("Formulario Oficial de Transferencia de Jugador",
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Color.GRAY));
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitulo);
            document.add(new Paragraph("\n"));

            // --- TABLA DE INFORMACIÓN ---
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            // Celda de Título de Tabla
            PdfPCell cellHeader = new PdfPCell(new Phrase("DETALLES DEL TRASPASO #" + solicitud.getIdTraspaso(), fontSubtitulo));
            cellHeader.setBackgroundColor(new Color(41, 128, 185)); // Azul elegante
            cellHeader.setColspan(2);
            cellHeader.setPadding(8);
            cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellHeader);

            // Filas de la Tabla
            String nombreJugador = solicitud.getJugador().getNombreJugador() + " " + solicitud.getJugador().getApellidosJugador();
            String documentoJugador = solicitud.getJugador().getCedulaJug();
            String torneo;
            String equipoActual = solicitud.getTorneoEquipoActual().getNombrePersonalizado()==null?
                    solicitud.getTorneoEquipoActual().getEquipo().getNombreEquipo():solicitud.getTorneoEquipoActual().getNombrePersonalizado();
            String equipoSolicita = solicitud.getTorneoEquipoSolicita().getNombrePersonalizado()==null?
                    solicitud.getTorneoEquipoSolicita().getEquipo().getNombreEquipo():solicitud.getTorneoEquipoSolicita().getNombrePersonalizado();
            String fechaSolicitud = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            agregarFilaTabla(table, "JUGADOR:", nombreJugador, fontNegrita, fontDatos);
            agregarFilaTabla(table, "DOCUMENTO:", documentoJugador, fontNegrita, fontDatos);
            agregarFilaTabla(table, "TORNEO:", nombreTorneo, fontNegrita, fontDatos);
            agregarFilaTabla(table, "EQUIPO ORIGEN:", equipoActual, fontNegrita, fontDatos);
            agregarFilaTabla(table, "EQUIPO DESTINO:", equipoSolicita, fontNegrita, fontDatos);
            agregarFilaTabla(table, "FECHA SOLICITUD:", fechaSolicitud, fontNegrita, fontDatos);

            document.add(table);
            document.add(new Paragraph("\n"));

            // --- CUERPO LEGAL ---
            Paragraph cuerpo = new Paragraph();
            cuerpo.setAlignment(Element.ALIGN_JUSTIFIED);
            cuerpo.add(new Phrase("ASUNTO/MOTIVO: ", fontNegrita));
            cuerpo.add(new Phrase(solicitud.getAsunto() + "\n\n", fontDatos));
            cuerpo.add(new Phrase("El jugador abajo firmante manifiesta su voluntad de desvincularse del equipo "+(new Phrase(equipoActual, fontNegrita))+
                    " para integrarse al equipo "+(new Phrase(equipoSolicita, fontNegrita))+", aceptando los reglamentos internos del torneo y confirmando que " +
                    "no posee deudas pendientes o sanciones vigentes que impidan este movimiento.", fontDatos));
            document.add(cuerpo);

            // --- SECCIÓN DE FIRMAS ---
            document.add(new Paragraph("\n\n\n\n"));
            PdfPTable tablaFirmas = new PdfPTable(3);
            tablaFirmas.setWidthPercentage(100);
            tablaFirmas.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            tablaFirmas.addCell(crearCeldaFirma("FIRMA JUGADOR"));
            tablaFirmas.addCell(crearCeldaFirma("DELEGADO SOLICITANTE"));
            tablaFirmas.addCell(crearCeldaFirma("ORGANIZADOR"));

            document.add(tablaFirmas);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Fallo al crear PDF: " + e.getMessage());
        }
        return out.toByteArray();
    }

    // Metodo auxiliar para las celdas de firma
    private PdfPCell crearCeldaFirma(String texto) {
        Paragraph p = new Paragraph("__________________________\n\n" + texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9));
        PdfPCell cell = new PdfPCell(p);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setFixedHeight(80f); // Espacio para la firma física
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    // Metodo auxiliar para no repetir código de celdas
    private void agregarFilaTabla(PdfPTable table, String label, String value, Font f1, Font f2) {
        PdfPCell c1 = new PdfPCell(new Phrase(label, f1));
        c1.setPadding(5);
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase(value, f2));
        c2.setPadding(5);
        table.addCell(c2);
    }
}
