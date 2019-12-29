package com.pl.sql;

public class SignedDocument {
   private long id;
   private byte[] data;
   private String publicKey;
   private String pqa;

   public long getId() {
      return id;
   }

   public SignedDocument setId(long id) {
      this.id = id;
      return this;
   }

   public byte[] getData() {
      return data;
   }

   public SignedDocument setData(byte[] data) {
      this.data = data;
      return this;
   }

   public String getPublicKey() {
      return publicKey;
   }

   public SignedDocument setPublicKey(String publicKey) {
      this.publicKey = publicKey;
      return this;
   }

   public String getPqa() {
      return pqa;
   }

   public SignedDocument setPqa(String pqa) {
      this.pqa = pqa;
      return this;
   }

   @Override
   public String toString() {
      return "SignedDocument{" +
            "id=" + id +
            ", publicKey='" + publicKey + '\'' +
            ", pqa='" + pqa + '\'' +
            '}';
   }
}
