package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class Reply extends GenericJson {
   @Key
   private String action;
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

   public Reply clone() {
      return (Reply)super.clone();
   }

   public String getAction() {
      return this.action;
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

   public Reply set(String var1, Object var2) {
      return (Reply)super.set(var1, var2);
   }

   public Reply setAction(String var1) {
      this.action = var1;
      return this;
   }

   public Reply setAuthor(User var1) {
      this.author = var1;
      return this;
   }

   public Reply setContent(String var1) {
      this.content = var1;
      return this;
   }

   public Reply setCreatedTime(DateTime var1) {
      this.createdTime = var1;
      return this;
   }

   public Reply setDeleted(Boolean var1) {
      this.deleted = var1;
      return this;
   }

   public Reply setHtmlContent(String var1) {
      this.htmlContent = var1;
      return this;
   }

   public Reply setId(String var1) {
      this.id = var1;
      return this;
   }

   public Reply setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public Reply setModifiedTime(DateTime var1) {
      this.modifiedTime = var1;
      return this;
   }
}
