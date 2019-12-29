package com.pl.sql;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.pl.sql.Dictionaries.PI_LEGEND;
import static com.pl.sql.Dictionaries.REVERSE_PI_LEGEND;

/**
 * Блочное шифрование 34.13─2015
 */
public class CryptoImpl implements Crypto {
   private static final int BLOCK_SIZE = 16;

   /**
    * Вектор линейного преобразования алгоритма
    */
   private static final byte[] TRANSFORMATION_VECTOR = {1,
         (byte) 148, 32, (byte) 133, 16, (byte) 194, (byte) 192, 1,
         (byte) 251, 1, (byte) 192, (byte) 194, 16, (byte) 133, 32, (byte) 148
   };

   /**
    * Массив для хранения констант
    */
   private final byte[][] constants = new byte[32][16];

   /**
    * Массив содерщажий ключ
    */
   private final byte[][] key = new byte[10][64];


   /**
    * Функция для расчета ключей
    *
    * @param keyA ключ в битовом значении
    * @param keyB ключ в битовом значении
    */
   @Override
   public void expandKeys(byte[] keyA, byte[] keyB) {
      int i;

      byte[][] i1 = new byte[2][];
      byte[][] i2;
      resolveConstants();
      key[0] = keyA;
      key[1] = keyB;
      i1[0] = keyA;
      i1[1] = keyB;
      for (i = 0; i < 4; i++) {
         i2 = transformFeistelCell(i1[0], i1[1], constants[8 * i]);
         i1 = transformFeistelCell(i2[0], i2[1], constants[1 + 8 * i]);
         i2 = transformFeistelCell(i1[0], i1[1], constants[2 + 8 * i]);
         i1 = transformFeistelCell(i2[0], i2[1], constants[3 + 8 * i]);
         i2 = transformFeistelCell(i1[0], i1[1], constants[4 + 8 * i]);
         i1 = transformFeistelCell(i2[0], i2[1], constants[5 + 8 * i]);
         i2 = transformFeistelCell(i1[0], i1[1], constants[6 + 8 * i]);
         i1 = transformFeistelCell(i2[0], i2[1], constants[7 + 8 * i]);

         key[2 * i + 2] = i1[0];
         key[2 * i + 3] = i1[1];
      }
   }

   @Override
   public void expandKeys(Path toKeyA, Path toKeyB) {
      try {
         expandKeys(Files.readAllBytes(toKeyA), Files.readAllBytes(toKeyB));
      } catch (IOException e) {
         throw new ExpandingKeyException("Cannot read bytes from file", e);
      }
   }

   /**
    * Фунция для поиска и расчета констант и сохранения их в глобальный массив
    */
   private void resolveConstants() {
      int i;
      byte[][] iterations = new byte[32][16];
      for (i = 0; i < 32; i++) {
         for (int j = 0; j < BLOCK_SIZE; j++)
            iterations[i][j] = 0;
         iterations[i][0] = (byte) (i + 1);
      }
      for (i = 0; i < 32; i++) {
         constants[i] = resolveVectorL(iterations[i]);
      }
   }

   /**
    * Функция для вычисления вектора L
    * @param bytes
    * @return
    */
   private byte[] resolveVectorL(byte[] bytes) {
      int i;
      byte[] internal = bytes;
      for (i = 0; i < 16; i++) {
         internal = rotateAndResolveBytes(internal);
      }
      return internal;
   }


   /**
    * Шифрует данный массив байтов
    *
    * @param bytes массив к зашифровке
    * @return зашифрованный массив
    */
   @Override
   public byte[] encrypt(byte[] bytes) {
      int i;
      byte[] out;
      out = bytes;
      for (i = 0; i < 9; i++) {
         out = functionX(key[i], out);
         out = functionS(out);
         out = resolveVectorL(out);
      }
      out = functionX(out, key[9]);
      return out;
   }

   /**
    * Расшифрует данный массив байтов
    * @param bytes
    * @return
    */
   @Override
   public byte[] decrypt(byte[] bytes) {
      int i;
      byte[] out;
      out = bytes;

      out = functionX(out, key[9]);
      for (i = 8; i >= 0; i--) {
         out = reverseVectorL(out);
         out = reverseFunctionS(out);
         out = functionX(key[i], out);
      }
      return out;
   }

   /**
    * Функция x
    * @param a
    * @param b
    * @return
    */
   private byte[] functionX(byte[] a, byte[] b) {
      int i;
      byte[] c = new byte[BLOCK_SIZE];
      for (i = 0; i < BLOCK_SIZE; i++)
         c[i] = (byte) (a[i] ^ b[i]);
      return c;
   }


   /**
    * Функция s
    * @param bytes
    * @return
    */
   private byte[] functionS(byte[] bytes) {
      int i;
      byte[] out = new byte[bytes.length];
      for (i = 0; i < BLOCK_SIZE; i++) {
         int data = bytes[i];
         if (data < 0) {
            data = data + 256;
         }
         out[i] = PI_LEGEND[data];
      }
      return out;
   }

   /**
    * Обратная функция S
    *
    * @param bytes
    * @return
    */
   private byte[] reverseFunctionS(byte[] bytes) {
      int i;
      byte[] out_data = new byte[bytes.length];
      for (i = 0; i < BLOCK_SIZE; i++) {
         int data = bytes[i];
         if (data < 0) {
            data = data + 256;
         }
         out_data[i] = REVERSE_PI_LEGEND[data];
      }
      return out_data;
   }

   /**
    * Обратная r функция
    * @param state
    * @return
    */
   private byte[] reverseRotationFunction(byte[] state) {
      int i;
      byte a0;
      a0 = state[15];
      byte[] internal = new byte[16];
      for (i = 1; i < 16; i++) {
         internal[i] = state[i - 1];
         a0 ^= multiplyInGaulField(internal[i], TRANSFORMATION_VECTOR[i]);
      }
      internal[0] = a0;
      return internal;
   }

   /**
    * Функция обратного вектора L
    * @param bytes
    * @return
    */
   private byte[] reverseVectorL(byte[] bytes) {
      int i;
      byte[] out;
      byte[] internal;
      internal = bytes;
      for (i = 0; i < 16; i++)
         internal = reverseRotationFunction(internal);
      out = internal;
      return out;
   }

   /**
    * Функция r сдвигает данные и реализует уравнение, представленное для расчета l - вектора
    */
   private byte[] rotateAndResolveBytes(byte[] state) {
      int i;
      byte a15 = 0;
      byte[] internal = new byte[16];
      for (i = 15; i >= 0; i--) {
         if (i == 0)
            internal[15] = state[i];
         else
            internal[i - 1] = state[i];
         a15 ^= multiplyInGaulField(state[i], TRANSFORMATION_VECTOR[i]);
      }
      internal[15] = a15;
      return internal;
   }

   /**
    * Функция, выполняющая преобразования ячейки Фейстеля
    * @param intoFirstKey
    * @param intoSecondKey
    * @param iterConst
    * @return
    */
   private byte[][] transformFeistelCell(byte[] intoFirstKey,
                                         byte[] intoSecondKey,
                                         byte[] iterConst) {
      byte[] internal;
      internal = functionX(intoFirstKey, iterConst);
      internal = functionS(internal);
      internal = resolveVectorL(internal);
      byte[] outKey = functionX(internal, intoSecondKey);
      byte[][] key = new byte[2][];
      key[0] = outKey;
      key[1] = intoFirstKey;
      return key;
   }


   /**
    * Функция умножения в поле Гаула
    * Используется полином  x^8+x^7+x^6+x+1
    * @param a
    * @param b
    * @return
    */
   private byte multiplyInGaulField(byte a, byte b) {
      byte c = 0;
      byte hi_bit;
      int i;
      for (i = 0; i < 8; i++) {
         if ((b & 1) == 1)
            c ^= a;
         hi_bit = (byte) (a & 0x80);
         a <<= 1;
         if (hi_bit < 0)
            a ^= 0xc3;
         b >>= 1;
      }
      return c;
   }
}