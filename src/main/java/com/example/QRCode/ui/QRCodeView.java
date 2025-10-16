package com.example.QRCode.ui;

import com.example.QRCode.QRCodeService;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "qrcode", layout = com.example.base.ui.MainLayout.class)
public class QRCodeView extends VerticalLayout {

    @Autowired
    public QRCodeView(QRCodeService qrCodeService) {
        String qrText = "https://meusistema.com/tasks/123";

        Image qrImage = new Image(
                event -> {
                    try (var os = event.getOutputStream()) {
                        byte[] imageBytes = qrCodeService.generateQRCode(qrText, 250, 250);
                        // opcionalmente: event.setContentType("image/png"); — mas pode ser deduzido
                        os.write(imageBytes);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                "QR Code da Task"
        );

        add(qrImage);
    }
}