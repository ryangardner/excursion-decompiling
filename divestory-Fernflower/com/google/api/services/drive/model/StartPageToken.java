package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class StartPageToken extends GenericJson {
   @Key
   private String kind;
   @Key
   private String startPageToken;

   public StartPageToken clone() {
      return (StartPageToken)super.clone();
   }

   public String getKind() {
      return this.kind;
   }

   public String getStartPageToken() {
      return this.startPageToken;
   }

   public StartPageToken set(String var1, Object var2) {
      return (StartPageToken)super.set(var1, var2);
   }

   public StartPageToken setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public StartPageToken setStartPageToken(String var1) {
      this.startPageToken = var1;
      return this;
   }
}
