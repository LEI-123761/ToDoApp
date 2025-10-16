package com.example.QRCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QRCodeService {

    /**
     * Generates a QR Code PNG image as byte array.
     * @param text The text or URL to encode
     * @param width Width in pixels
     * @param height Height in pixels
     * @return byte[] PNG image
     */
    public byte[] generateQRCode(String text, int width, int height) throws IOException, WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
            return baos.toByteArray();
        }
    }
}