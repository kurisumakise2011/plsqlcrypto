package com.pl.sql;

public class SecretResult {
   private String signature;
   private String pqa;
   private String publicKey;

   public SecretResult(String signature, String pqa, String publicKey) {
      this.signature = signature;
      this.pqa = pqa;
      this.publicKey = publicKey;
   }

   public String getSignature() {
      return signature;
   }

   public String getPqa() {
      return pqa;
   }

   public String getPublicKey() {
      return publicKey;
   }
}
