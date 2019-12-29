package com.pl.sql;

public final class Cypher {
   private final String varchar;

   public Cypher(String varchar) {
      this.varchar = varchar;
   }

   public String getVarchar() {
      return varchar;
   }

   @Override
   public String toString() {
      return "Cypher{" +
            "varchar='" + varchar + '\'' +
            '}';
   }
}
