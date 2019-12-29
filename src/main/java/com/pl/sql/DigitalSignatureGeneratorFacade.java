package com.pl.sql;

public interface DigitalSignatureGeneratorFacade {

   SecretResult sign(byte[] data);

   boolean verify(String signature, SignedDocument document);

}
