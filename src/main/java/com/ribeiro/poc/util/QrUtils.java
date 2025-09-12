package com.ribeiro.poc.util;

public final   class QrUtils {

    public static String qrPngBase64(String text, int size) {
        try {
            var writer = new com.google.zxing.qrcode.QRCodeWriter();
            var matrix = writer.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, size, size);
            var image = com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage(matrix);
            var out = new java.io.ByteArrayOutputStream();
            javax.imageio.ImageIO.write(image, "png", out);
            return java.util.Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (Exception e) { throw new IllegalStateException(e); }
    }
}
