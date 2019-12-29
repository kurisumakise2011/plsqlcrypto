package com.pl.sql;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public final class CryptoUtils {

   private CryptoUtils() {
   }

   private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

   public static byte[] hexToBinary(String s) {
      final int len = s.length();

      // "111" is not a valid hex encoding.
      if (len % 2 != 0)
         throw new IllegalArgumentException("hexBinary needs to be even-length: " + s);

      byte[] out = new byte[len / 2];

      for (int i = 0; i < len; i += 2) {
         int h = hexToBin(s.charAt(i));
         int l = hexToBin(s.charAt(i + 1));
         if (h == -1 || l == -1)
            throw new IllegalArgumentException("contains illegal character for hexBinary: " + s);

         out[i / 2] = (byte) (h * 16 + l);
      }

      return out;
   }

   private static int hexToBin(char ch) {
      if ('0' <= ch && ch <= '9') return ch - '0';
      if ('A' <= ch && ch <= 'F') return ch - 'A' + 10;
      if ('a' <= ch && ch <= 'f') return ch - 'a' + 10;
      return -1;
   }

   public static String binaryToHex(byte[] data) {
      StringBuilder r = new StringBuilder(data.length * 2);
      for (byte b : data) {
         r.append(hexCode[(b >> 4) & 0xF]);
         r.append(hexCode[(b & 0xF)]);
      }
      return r.toString();
   }

   public static String textToHex(String text) {
      return Hex.encodeHexString(text.getBytes(StandardCharsets.UTF_8));
   }

   public static String hexToText(String hex) {
      return new String(hexToBinary(hex), StandardCharsets.UTF_8);
   }

   public static byte[] intsToBytes(int[] res) {
      byte[] ut = new byte[res.length];
      for (int i = 0; i < res.length; i++) {
         ut[i] = (byte) res[i];
      }
      return ut;
   }
}
