package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class Revision extends GenericJson {
   @Key
   private String id;
   @Key
   private Boolean keepForever;
   @Key
   private String kind;
   @Key
   private User lastModifyingUser;
   @Key
   private String md5Checksum;
   @Key
   private String mimeType;
   @Key
   private DateTime modifiedTime;
   @Key
   private String originalFilename;
   @Key
   private Boolean publishAuto;
   @Key
   private Boolean published;
   @Key
   private Boolean publishedOutsideDomain;
   @JsonString
   @Key
   private Long size;

   public Revision clone() {
      return (Revision)super.clone();
   }

   public String getId() {
      return this.id;
   }

   public Boolean getKeepForever() {
      return this.keepForever;
   }

   public String getKind() {
      return this.kind;
   }

   public User getLastModifyingUser() {
      return this.lastModifyingUser;
   }

   public String getMd5Checksum() {
      return this.md5Checksum;
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public DateTime getModifiedTime() {
      return this.modifiedTime;
   }

   public String getOriginalFilename() {
      return this.originalFilename;
   }

   public Boolean getPublishAuto() {
      return this.publishAuto;
   }

   public Boolean getPublished() {
      return this.published;
   }

   public Boolean getPublishedOutsideDomain() {
      return this.publishedOutsideDomain;
   }

   public Long getSize() {
      return this.size;
   }

   public Revision set(String var1, Object var2) {
      return (Revision)super.set(var1, var2);
   }

   public Revision setId(String var1) {
      this.id = var1;
      return this;
   }

   public Revision setKeepForever(Boolean var1) {
      this.keepForever = var1;
      return this;
   }

   public Revision setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public Revision setLastModifyingUser(User var1) {
      this.lastModifyingUser = var1;
      return this;
   }

   public Revision setMd5Checksum(String var1) {
      this.md5Checksum = var1;
      return this;
   }

   public Revision setMimeType(String var1) {
      this.mimeType = var1;
      return this;
   }

   public Revision setModifiedTime(DateTime var1) {
      this.modifiedTime = var1;
      return this;
   }

   public Revision setOriginalFilename(String var1) {
      this.originalFilename = var1;
      return this;
   }

   public Revision setPublishAuto(Boolean var1) {
      this.publishAuto = var1;
      return this;
   }

   public Revision setPublished(Boolean var1) {
      this.published = var1;
      return this;
   }

   public Revision setPublishedOutsideDomain(Boolean var1) {
      this.publishedOutsideDomain = var1;
      return this;
   }

   public Revision setSize(Long var1) {
      this.size = var1;
      return this;
   }
}
