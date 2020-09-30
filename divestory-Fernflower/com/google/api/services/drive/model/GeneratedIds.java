package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.List;

public final class GeneratedIds extends GenericJson {
   @Key
   private List<String> ids;
   @Key
   private String kind;
   @Key
   private String space;

   public GeneratedIds clone() {
      return (GeneratedIds)super.clone();
   }

   public List<String> getIds() {
      return this.ids;
   }

   public String getKind() {
      return this.kind;
   }

   public String getSpace() {
      return this.space;
   }

   public GeneratedIds set(String var1, Object var2) {
      return (GeneratedIds)super.set(var1, var2);
   }

   public GeneratedIds setIds(List<String> var1) {
      this.ids = var1;
      return this;
   }

   public GeneratedIds setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public GeneratedIds setSpace(String var1) {
      this.space = var1;
      return this;
   }
}
