package com.pl.sql;

public class SecretDocument {
   private final byte[] data;

   public SecretDocument(byte[] data) {
      this.data = data;
   }

   public byte[] getData() {
      return data;
   }
}
