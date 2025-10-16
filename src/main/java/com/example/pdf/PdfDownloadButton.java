package com.example.pdf;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PdfDownloadButton extends VerticalLayout {

    public PdfDownloadButton() {
        // Link direto para o endpoint REST que retorna o PDF
        Anchor downloadLink = new Anchor("/tasks/pdf", "Download Task List PDF ⬇️"); // Modified text for clarity
        downloadLink.getElement().setAttribute("download", true);

        // Optional: Remove default padding/spacing of VerticalLayout if used in a tighter space like a toolbar
        setPadding(false);
        setSpacing(false);
        setMargin(false);

        add(downloadLink);
    }
}