package com.example.qr_code;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.core.io.InputStreamResource;

public class QrCodeGenerator {

    public static InputStreamResource getQRCode(Info info) {

        int width = 300;
        int height = 300;

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(
                    info.toString(),
                    BarcodeFormat.QR_CODE,
                    width,
                    height,
                    createHintMap()
            );

            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            byte[] imageBytes = convertImageToByteArray(image);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

            return new InputStreamResource(inputStream);
        } catch (WriterException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate and save QR code", e);
        }
    }
    private static byte[] convertImageToByteArray(BufferedImage image) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert image to byte array", e);
        }
    }

    private static java.util.Map<EncodeHintType, Object> createHintMap() {
        java.util.Map<EncodeHintType, Object> hintMap = new java.util.HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        return hintMap;
    }
}
