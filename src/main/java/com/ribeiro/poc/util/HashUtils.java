package com.ribeiro.poc.util;

import java.security.MessageDigest;
import java.util.HexFormat;

public final class HashUtils {

    private HashUtils() {}

    public static String sha256Hex(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(data));
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao calcular SHA-256", e);
        }
    }
}
