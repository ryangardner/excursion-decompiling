package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;
import java.util.Map;

public final class Channel extends GenericJson {
   @Key
   private String address;
   @JsonString
   @Key
   private Long expiration;
   @Key
   private String id;
   @Key
   private String kind;
   @Key
   private Map<String, String> params;
   @Key
   private Boolean payload;
   @Key
   private String resourceId;
   @Key
   private String resourceUri;
   @Key
   private String token;
   @Key
   private String type;

   public Channel clone() {
      return (Channel)super.clone();
   }

   public String getAddress() {
      return this.address;
   }

   public Long getExpiration() {
      return this.expiration;
   }

   public String getId() {
      return this.id;
   }

   public String getKind() {
      return this.kind;
   }

   public Map<String, String> getParams() {
      return this.params;
   }

   public Boolean getPayload() {
      return this.payload;
   }

   public String getResourceId() {
      return this.resourceId;
   }

   public String getResourceUri() {
      return this.resourceUri;
   }

   public String getToken() {
      return this.token;
   }

   public String getType() {
      return this.type;
   }

   public Channel set(String var1, Object var2) {
      return (Channel)super.set(var1, var2);
   }

   public Channel setAddress(String var1) {
      this.address = var1;
      return this;
   }

   public Channel setExpiration(Long var1) {
      this.expiration = var1;
      return this;
   }

   public Channel setId(String var1) {
      this.id = var1;
      return this;
   }

   public Channel setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public Channel setParams(Map<String, String> var1) {
      this.params = var1;
      return this;
   }

   public Channel setPayload(Boolean var1) {
      this.payload = var1;
      return this;
   }

   public Channel setResourceId(String var1) {
      this.resourceId = var1;
      return this;
   }

   public Channel setResourceUri(String var1) {
      this.resourceUri = var1;
      return this;
   }

   public Channel setToken(String var1) {
      this.token = var1;
      return this;
   }

   public Channel setType(String var1) {
      this.type = var1;
      return this;
   }
}
