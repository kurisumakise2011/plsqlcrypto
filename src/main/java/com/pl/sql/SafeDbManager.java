package com.pl.sql;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class SafeDbManager {
   private final CryptoStringDelegator crypto = new CryptoStringDelegator(new CryptoImpl());

   public SafeDbManager() {
      try (InputStream a = getClass().getResourceAsStream("/a_key.bin");
           InputStream b = getClass().getResourceAsStream("/b_key.bin")) {
         crypto.expandKeys(IOUtils.toByteArray(a), IOUtils.toByteArray(b));
      } catch (Exception e) {
         throw new LoadingException("Could not expand keys", e);
      }

   }

   private Connection getConnection() throws SQLException {
      return DriverManager.getConnection(
            Main.properties.get("url"),
            Main.properties.get("user"),
            Main.properties.get("password"));
   }

   /**
    * @param sql  query
    * @param args if chypher then encrypt value
    */
   public void execute(String sql, Object... args) {
      try (Connection connection = getConnection()) {
         PreparedStatement pst = connection.prepareStatement(sql);
         for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Cypher) {
               Cypher cypher = (Cypher) args[i];
               byte[] crypted = crypto.encrypt(cypher.getVarchar());
               pst.setBytes(i + 1, crypted);
            } else {
               pst.setObject(i + 1, args[i]);
            }
         }
         pst.executeUpdate();
      } catch (SQLException e) {
         throw new DbRuntimeException(e);
      }
   }

   public Iterable<SecurityDocument> fetch(String sql) {
      try (Connection connection = getConnection()) {
         PreparedStatement pst = connection.prepareStatement(sql);
         ResultSet resultSet = pst.executeQuery();
         return () -> new Iterator<SecurityDocument>() {
            @Override
            public boolean hasNext() {
               try {
                  return resultSet.next();
               } catch (SQLException e) {
                  throw new DbRuntimeException(e);
               }
            }

            @Override
            public SecurityDocument next() {
               try {
                  byte[] crypted = resultSet.getBytes("text");
                  Cypher cypher = new Cypher(crypto.decrypt(crypted));
                  return new SecurityDocument()
                        .setId(resultSet.getLong("id"))
                        .setTitle(resultSet.getString("title"))
                        .setCreatedAt(resultSet.getTimestamp("created_at"))
                        .setDocument(cypher);
               } catch (SQLException e) {
                  throw new DbRuntimeException(e);
               }
            }
         };
      } catch (SQLException e) {
         throw new DbRuntimeException(e);
      }
   }

   public SignedDocument fetchDoc(String sql) {
      try (Connection connection = getConnection()) {
         PreparedStatement pst = connection.prepareStatement(sql);
         ResultSet resultSet = pst.executeQuery();

         if (!resultSet.next()) {
            return null;
         }
         return new SignedDocument()
               .setId(resultSet.getLong("id"))
               .setPqa(resultSet.getString("pqa"))
               .setPublicKey(resultSet.getString("public_key"))
               .setData(resultSet.getBytes("data"));
      } catch (SQLException e) {
         throw new DbRuntimeException(e);
      }
   }
}
