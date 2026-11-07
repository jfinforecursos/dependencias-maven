package com.example.service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Usuario;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class UsuarioPdfService {

    public ByteArrayInputStream generarReporteUsuarios(List<Usuario> usuarios) {
        Document document = new Document(PageSize.A4.rotate(), 20, 20, 30, 30);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Tipografías
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(33, 53, 71));
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.DARK_GRAY);
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
            Font fontData = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);

            // Título
            Paragraph titulo = new Paragraph("Reporte de Usuarios - Sistema de Inventario", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(5);
            document.add(titulo);

            // Metadatos (Fecha y Total)
            String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            Paragraph meta = new Paragraph("Generado el: " + fechaActual + "   |   Total de usuarios: " + (usuarios != null ? usuarios.size() : 0), fontSubtitulo);
            meta.setAlignment(Element.ALIGN_CENTER);
            meta.setSpacingAfter(15);
            document.add(meta);

            // Tabla de Usuarios
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.0f, 3.0f, 3.5f, 2.2f, 3.5f, 1.8f, 1.8f});

            // Encabezados
            String[] headers = {"ID", "Nombre Completo", "Email", "Teléfono", "Dirección", "Rol", "Estado"};
            Color colorHeaderBg = new Color(41, 128, 185);

            for (String headerText : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(headerText, fontHeader));
                cell.setBackgroundColor(colorHeaderBg);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(6);
                table.addCell(cell);
            }

            // Filas de datos
            Color zebraLight = new Color(245, 247, 250);
            boolean alternate = false;

            if (usuarios != null) {
                for (Usuario u : usuarios) {
                    Color bg = alternate ? zebraLight : Color.WHITE;
                    alternate = !alternate;

                    table.addCell(crearCelda(String.valueOf(u.getId() != null ? u.getId() : "-"), fontData, bg, Element.ALIGN_CENTER));
                    
                    String nombreCompleto = ((u.getNombre() != null ? u.getNombre() : "") + " " + (u.getApellido() != null ? u.getApellido() : "")).trim();
                    table.addCell(crearCelda(nombreCompleto.isEmpty() ? "-" : nombreCompleto, fontData, bg, Element.ALIGN_LEFT));
                    table.addCell(crearCelda(u.getEmail() != null ? u.getEmail() : "-", fontData, bg, Element.ALIGN_LEFT));
                    table.addCell(crearCelda(u.getTelefono() != null ? u.getTelefono() : "-", fontData, bg, Element.ALIGN_CENTER));
                    table.addCell(crearCelda(u.getDireccion() != null ? u.getDireccion() : "-", fontData, bg, Element.ALIGN_LEFT));
                    table.addCell(crearCelda(u.getRol() != null ? u.getRol() : "-", fontData, bg, Element.ALIGN_CENTER));
                    table.addCell(crearCelda(u.getEstado() != null ? u.getEstado() : "-", fontData, bg, Element.ALIGN_CENTER));
                }
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private PdfPCell crearCelda(String contenido, Font font, Color background, int alineacion) {
        PdfPCell cell = new PdfPCell(new Phrase(contenido, font));
        cell.setBackgroundColor(background);
        cell.setHorizontalAlignment(alineacion);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        return cell;
    }
}

