package com.pl.sql;

import java.nio.file.Path;

public interface Crypto {
   /**
    * Функция для расчета ключей
    *
    * @param keyA ключ в битовом значении
    * @param keyB ключ в битовом значении
    */
   void expandKeys(byte[] keyA, byte[] keyB);

   /**
    * Функция для расчета ключей
    *
    * @param toKeyA путь к ключу A
    * @param toKeyB путь к ключу B
    */
   void expandKeys(Path toKeyA, Path toKeyB);

   /**
    * Шифрует данный массив байтов
    *
    * @param bytes массив к зашифровке
    * @return зашифрованный массив
    */
   byte[] encrypt(byte[] bytes);

   /**
    * Расшифрует данный массив байтов
    * @param bytes
    * @return
    */
   byte[] decrypt(byte[] bytes);
}
