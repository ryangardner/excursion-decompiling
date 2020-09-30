package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class User extends GenericJson {
   @Key
   private String displayName;
   @Key
   private String emailAddress;
   @Key
   private String kind;
   @Key
   private Boolean me;
   @Key
   private String permissionId;
   @Key
   private String photoLink;

   public User clone() {
      return (User)super.clone();
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getEmailAddress() {
      return this.emailAddress;
   }

   public String getKind() {
      return this.kind;
   }

   public Boolean getMe() {
      return this.me;
   }

   public String getPermissionId() {
      return this.permissionId;
   }

   public String getPhotoLink() {
      return this.photoLink;
   }

   public User set(String var1, Object var2) {
      return (User)super.set(var1, var2);
   }

   public User setDisplayName(String var1) {
      this.displayName = var1;
      return this;
   }

   public User setEmailAddress(String var1) {
      this.emailAddress = var1;
      return this;
   }

   public User setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public User setMe(Boolean var1) {
      this.me = var1;
      return this;
   }

   public User setPermissionId(String var1) {
      this.permissionId = var1;
      return this;
   }

   public User setPhotoLink(String var1) {
      this.photoLink = var1;
      return this;
   }
}
