package com.example.pdf;

import com.example.examplefeature.TaskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PdfController {

    private final PdfService pdfService;
    private final TaskService taskService;

    public PdfController(PdfService pdfService, TaskService taskService) {
        this.pdfService = pdfService;
        this.taskService = taskService;
    }

    @GetMapping("/tasks/pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        List<String[]> rows = taskService.list(org.springframework.data.domain.Pageable.unpaged())
                .stream()
                .map(t -> new String[]{
                        t.getId() != null ? String.valueOf(t.getId()) : "-",
                        t.getDescription(),
                        t.getCreationDate().toString(),
                        t.getDueDate() != null ? t.getDueDate().toString() : "-"
                })
                .toList();

        String[] headers = {"ID", "Descrição", "Data de Criação", "Data de Vencimento"};

        byte[] pdf = pdfService.generatePdf(rows, "Relatório de Tarefas", headers);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tarefas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}