package com.pl.sql;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static Map<String, String> properties = new HashMap<>();

    static {
        try (InputStream is = Main.class.getResourceAsStream("/application.properties")){
            List<String> lines = IOUtils.readLines(is, "UTF-8");
            for (String line : lines) {
                String[] keyValuePair = line.split("=");
                properties.put(keyValuePair[0], keyValuePair[1]);
            }
        } catch (Exception e) {
            throw new LoadingException("Could not setup application properties !", e);
        }
    }

    public static void main(String[] args) {
        // дб менеджер, делает запросы в базу
        SafeDbManager manager = new SafeDbManager();

        manager.execute("DELETE FROM security_docs");

        // фасад который отвественный за формирование ЭП (ГОСТ 34.10-12)
        final DigitalSignatureGeneratorFacade digitalSignatureGeneratorFacade
              = new DigitalSignatureGeneratorFacadeImpl();


        byte[] bytes;
        try (InputStream is = Main.class.getResourceAsStream("/secret_file.txt")) {
            bytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            throw new LoadingException("Could not create digital signature", e);
        }

        // Получаем электронную подпись
        SecretResult secretResult = digitalSignatureGeneratorFacade.sign(bytes);


        // Вставляем документ с подписанной подпись в базу в таблицу signed_documents
        manager.execute("INSERT INTO signed_documents (data, public_key, pqa) VALUES(?, ?, ?)",
              bytes, secretResult.getPublicKey(), secretResult.getPqa());

        // Вставляем зашифрованный текст с помощью 34.13-2015
        manager.execute("INSERT INTO security_docs (created_at, title, text) VALUES(?, ?, ?)"
        , Timestamp.valueOf(LocalDateTime.now()), "Document 1",
              new Cypher("This document contains privacy and confidential information"));

        // Вставляем зашифрованный текст с помощью 34.13-2015
        manager.execute("INSERT INTO security_docs (created_at, title, text) VALUES(?, ?, ?)"
              , Timestamp.valueOf(LocalDateTime.now()), "Document 2",
              new Cypher("This document contains privacy and confidential information"));

        // Вытаскиваем декодированные документы
        Iterable<SecurityDocument> fetch = manager.fetch("SELECT * FROM security_docs");

        // Выводим их на экран убеждаемся что все ок расшифровалось
        for (SecurityDocument document : fetch) {
            System.out.println(document.toString());
        }

        // Вытаскиваем подписанный документ из базы
        SignedDocument signedDocument = manager.fetchDoc("SELECT * FROM signed_documents");
        // Проверяем что документы подлинный путем проверки подписи
        boolean verify = digitalSignatureGeneratorFacade.verify(secretResult.getSignature(), signedDocument);
        // Все окей, документ подлинный выводим подпись в консоль для примера
        System.out.println("Document is " + verify + "  with signature " + secretResult.getSignature());
    }
}
