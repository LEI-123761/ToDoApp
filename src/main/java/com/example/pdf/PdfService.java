package com.example.pdf;

import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import java.awt.Color;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    /**
     * Gera um PDF com uma tabela
     */
    public byte[] generatePdf(List<String[]> rows, String title, String[] headers) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Título
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph pTitle = new Paragraph(title, titleFont);
            pTitle.setAlignment(Element.ALIGN_CENTER);
            pTitle.setSpacingAfter(20);
            document.add(pTitle);

            // Tabela
            PdfPTable table = new PdfPTable(headers.length);
            table.setWidthPercentage(100);

            Font headerFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }

            Font contentFont = new Font(Font.HELVETICA, 12);
            for (String[] row : rows) {
                for (String cellData : row) {
                    PdfPCell cell = new PdfPCell(new Phrase(cellData, contentFont));
                    cell.setPadding(5);
                    table.addCell(cell);
                }
            }

            document.add(table);
            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }
    }
}