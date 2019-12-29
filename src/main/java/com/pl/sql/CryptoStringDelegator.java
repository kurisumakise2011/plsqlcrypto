package com.pl.sql;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CryptoStringDelegator {
   private final Crypto crypto;

   public CryptoStringDelegator(Crypto crypto) {
      this.crypto = crypto;
   }

   public void expandKeys(byte[] keyA, byte[] keyB) {
      crypto.expandKeys(keyA, keyB);
   }

   public void expandKeys(Path toKeyA, Path toKeyB) {
      crypto.expandKeys(toKeyA, toKeyB);
   }

   public byte[] encrypt(String text) {
      return partitions(text, 16)
            .stream()
            .map(CryptoUtils::textToHex)
            .map(CryptoUtils::hexToBinary)
            .map(crypto::encrypt)
            .reduce(new byte[0], ArrayUtils::addAll);
   }

   private static List<String> partitions(String text, int size) {
      List<String> ret = new ArrayList<>((text.length() + size - 1) / size);
      for (int start = 0; start < text.length(); start += size) {
         ret.add(text.substring(start, Math.min(text.length(), start + size)));
      }

      int lastIndex = ret.size() - 1;
      String lastElement = ret.get(lastIndex);
      if (lastElement.length() < size) {
         ret.set(lastIndex, StringUtils.rightPad(lastElement, size));
      }
      return ret;
   }

   public String decrypt(byte[] bytes) {
      List<String> hexes = new ArrayList<>();
      int size = 16;
      for (int i = 0; i < bytes.length / size; i++) {
         if ((i + 1) * size < bytes.length) {
            byte[] decrypted = Arrays.copyOfRange(bytes, i * size, (i + 1) * size);
            hexes.add(CryptoUtils.binaryToHex(crypto.decrypt(decrypted)));
         } else {
            if (i * size < bytes.length) {
               byte[] decrypted = Arrays.copyOfRange(bytes, i * size, (bytes.length - i * size) + i * size);
               hexes.add(CryptoUtils.binaryToHex(crypto.decrypt(decrypted)));
            }
         }
      }
      StringBuilder sb = new StringBuilder();
      for (String hex : hexes) {
         sb.append(CryptoUtils.hexToText(hex));
      }
      return sb.toString().trim();
   }
}
