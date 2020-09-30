package com.google.api.services.drive;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.util.Key;

public abstract class DriveRequest<T> extends AbstractGoogleJsonClientRequest<T> {
   @Key
   private String alt;
   @Key
   private String fields;
   @Key
   private String key;
   @Key("oauth_token")
   private String oauthToken;
   @Key
   private Boolean prettyPrint;
   @Key
   private String quotaUser;
   @Key
   private String userIp;

   public DriveRequest(Drive var1, String var2, String var3, Object var4, Class<T> var5) {
      super(var1, var2, var3, var4, var5);
   }

   public final Drive getAbstractGoogleClient() {
      return (Drive)super.getAbstractGoogleClient();
   }

   public String getAlt() {
      return this.alt;
   }

   public String getFields() {
      return this.fields;
   }

   public String getKey() {
      return this.key;
   }

   public String getOauthToken() {
      return this.oauthToken;
   }

   public Boolean getPrettyPrint() {
      return this.prettyPrint;
   }

   public String getQuotaUser() {
      return this.quotaUser;
   }

   public String getUserIp() {
      return this.userIp;
   }

   public DriveRequest<T> set(String var1, Object var2) {
      return (DriveRequest)super.set(var1, var2);
   }

   public DriveRequest<T> setAlt(String var1) {
      this.alt = var1;
      return this;
   }

   public DriveRequest<T> setDisableGZipContent(boolean var1) {
      return (DriveRequest)super.setDisableGZipContent(var1);
   }

   public DriveRequest<T> setFields(String var1) {
      this.fields = var1;
      return this;
   }

   public DriveRequest<T> setKey(String var1) {
      this.key = var1;
      return this;
   }

   public DriveRequest<T> setOauthToken(String var1) {
      this.oauthToken = var1;
      return this;
   }

   public DriveRequest<T> setPrettyPrint(Boolean var1) {
      this.prettyPrint = var1;
      return this;
   }

   public DriveRequest<T> setQuotaUser(String var1) {
      this.quotaUser = var1;
      return this;
   }

   public DriveRequest<T> setRequestHeaders(HttpHeaders var1) {
      return (DriveRequest)super.setRequestHeaders(var1);
   }

   public DriveRequest<T> setUserIp(String var1) {
      this.userIp = var1;
      return this;
   }
}
