package com.pl.sql;

import java.sql.Timestamp;

public class SecurityDocument {
   private long id;
   private String title;
   private Timestamp createdAt;
   private Cypher document;

   public long getId() {
      return id;
   }

   public SecurityDocument setId(long id) {
      this.id = id;
      return this;
   }

   public String getTitle() {
      return title;
   }

   public SecurityDocument setTitle(String title) {
      this.title = title;
      return this;
   }

   public Timestamp getCreatedAt() {
      return createdAt;
   }

   public SecurityDocument setCreatedAt(Timestamp createdAt) {
      this.createdAt = createdAt;
      return this;
   }

   public Cypher getDocument() {
      return document;
   }

   public SecurityDocument setDocument(Cypher document) {
      this.document = document;
      return this;
   }

   @Override
   public String toString() {
      return "SecurityDocument{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", createdAt=" + createdAt +
            ", document=" + document +
            '}';
   }
}
