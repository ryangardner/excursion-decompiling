package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;

public final class Comment extends GenericJson {
   @Key
   private String anchor;
   @Key
   private User author;
   @Key
   private String content;
   @Key
   private DateTime createdTime;
   @Key
   private Boolean deleted;
   @Key
   private String htmlContent;
   @Key
   private String id;
   @Key
   private String kind;
   @Key
   private DateTime modifiedTime;
   @Key
   private Comment.QuotedFileContent quotedFileContent;
   @Key
   private List<Reply> replies;
   @Key
   private Boolean resolved;

   public Comment clone() {
      return (Comment)super.clone();
   }

   public String getAnchor() {
      return this.anchor;
   }

   public User getAuthor() {
      return this.author;
   }

   public String getContent() {
      return this.content;
   }

   public DateTime getCreatedTime() {
      return this.createdTime;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public String getHtmlContent() {
      return this.htmlContent;
   }

   public String getId() {
      return this.id;
   }

   public String getKind() {
      return this.kind;
   }

   public DateTime getModifiedTime() {
      return this.modifiedTime;
   }

   public Comment.QuotedFileContent getQuotedFileContent() {
      return this.quotedFileContent;
   }

   public List<Reply> getReplies() {
      return this.replies;
   }

   public Boolean getResolved() {
      return this.resolved;
   }

   public Comment set(String var1, Object var2) {
      return (Comment)super.set(var1, var2);
   }

   public Comment setAnchor(String var1) {
      this.anchor = var1;
      return this;
   }

   public Comment setAuthor(User var1) {
      this.author = var1;
      return this;
   }

   public Comment setContent(String var1) {
      this.content = var1;
      return this;
   }

   public Comment setCreatedTime(DateTime var1) {
      this.createdTime = var1;
      return this;
   }

   public Comment setDeleted(Boolean var1) {
      this.deleted = var1;
      return this;
   }

   public Comment setHtmlContent(String var1) {
      this.htmlContent = var1;
      return this;
   }

   public Comment setId(String var1) {
      this.id = var1;
      return this;
   }

   public Comment setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public Comment setModifiedTime(DateTime var1) {
      this.modifiedTime = var1;
      return this;
   }

   public Comment setQuotedFileContent(Comment.QuotedFileContent var1) {
      this.quotedFileContent = var1;
      return this;
   }

   public Comment setReplies(List<Reply> var1) {
      this.replies = var1;
      return this;
   }

   public Comment setResolved(Boolean var1) {
      this.resolved = var1;
      return this;
   }

   public static final class QuotedFileContent extends GenericJson {
      @Key
      private String mimeType;
      @Key
      private String value;

      public Comment.QuotedFileContent clone() {
         return (Comment.QuotedFileContent)super.clone();
      }

      public String getMimeType() {
         return this.mimeType;
      }

      public String getValue() {
         return this.value;
      }

      public Comment.QuotedFileContent set(String var1, Object var2) {
         return (Comment.QuotedFileContent)super.set(var1, var2);
      }

      public Comment.QuotedFileContent setMimeType(String var1) {
         this.mimeType = var1;
         return this;
      }

      public Comment.QuotedFileContent setValue(String var1) {
         this.value = var1;
         return this;
      }
   }
}
