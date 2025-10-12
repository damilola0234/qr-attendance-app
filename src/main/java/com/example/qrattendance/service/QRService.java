package com.example.qrattendance.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class QRService {
    public String generateQRCode(String text) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BitMatrix matrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, 200, 200);
        MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
        return Base64.getEncoder().encodeToString(stream.toByteArray());
    }
}