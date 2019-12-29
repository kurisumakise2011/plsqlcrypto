package com.pl.sql;

public class DbRuntimeException extends RuntimeException {
   public DbRuntimeException() {
      super();
   }

   public DbRuntimeException(String message) {
      super(message);
   }

   public DbRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }

   public DbRuntimeException(Throwable cause) {
      super(cause);
   }
}
