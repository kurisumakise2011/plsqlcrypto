package com.pl.sql;

public class ExpandingKeyException extends RuntimeException {
   public ExpandingKeyException() {
      super();
   }

   public ExpandingKeyException(String message) {
      super(message);
   }

   public ExpandingKeyException(String message, Throwable cause) {
      super(message, cause);
   }

   public ExpandingKeyException(Throwable cause) {
      super(cause);
   }
}
