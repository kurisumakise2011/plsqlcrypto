package com.pl.sql;

import java.math.BigInteger;

import static java.lang.StrictMath.pow;

public class DigitalSignatureGeneratorFacadeImpl implements DigitalSignatureGeneratorFacade {
   /**
    * Gost 34.10-2012
    * @param data документы в байтаха
    * @return подпись с открытым ключом
    */
   @Override
   public SecretResult sign(byte[] data) {
      LinearCongruentialGenerator g = new LinearCongruentialGenerator(
            0x3DFC46F1,
            97781173,
            0xD,
            (long) pow(2, 32)
      );
      Generator generator = new Generator(g);
      BigInteger[] primes = generator.generatePrimes1024();
      BigInteger p = primes[0];
      BigInteger q = primes[1];
      BigInteger a = generator.generateA(p, q);

      // гост генератор эп
      GostSignature ds = new GostSignature(p, q, a);
      BigInteger x = ds.getRandomPrivateKey(128);
      BigInteger y = ds.getPublicKey(x);

      // гост 34.11-2012 функция кеширования
      HashFunction hashFunction = new HashFunction();
      byte[] bytes = hashFunction.getHash(data);

      return new SecretResult(ds.sign(CryptoUtils.binaryToHex(bytes), x),
            p.toString(16) + "\n" + q.toString(16) + "\n" + a.toString(16),
            y.toString(16));
   }

   @Override
   public boolean verify(String signature, SignedDocument document) {
      String[] params = document.getPqa().split("\n");
      BigInteger p = new BigInteger(params[0], 16);
      BigInteger q = new BigInteger(params[1], 16);
      BigInteger a = new BigInteger(params[2], 16);
      BigInteger y = new BigInteger(document.getPublicKey(), 16);
      GostSignature ds = new GostSignature(p, q, a);
      HashFunction hashFunction = new HashFunction();
      String hash = CryptoUtils.binaryToHex(hashFunction.getHash(document.getData()));
      return ds.verify(hash, signature, y);
   }
}
