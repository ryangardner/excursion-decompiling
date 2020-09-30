package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class ChangeList extends GenericJson {
   @Key
   private List<Change> changes;
   @Key
   private String kind;
   @Key
   private String newStartPageToken;
   @Key
   private String nextPageToken;

   static {
      Data.nullOf(Change.class);
   }

   public ChangeList clone() {
      return (ChangeList)super.clone();
   }

   public List<Change> getChanges() {
      return this.changes;
   }

   public String getKind() {
      return this.kind;
   }

   public String getNewStartPageToken() {
      return this.newStartPageToken;
   }

   public String getNextPageToken() {
      return this.nextPageToken;
   }

   public ChangeList set(String var1, Object var2) {
      return (ChangeList)super.set(var1, var2);
   }

   public ChangeList setChanges(List<Change> var1) {
      this.changes = var1;
      return this;
   }

   public ChangeList setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public ChangeList setNewStartPageToken(String var1) {
      this.newStartPageToken = var1;
      return this;
   }

   public ChangeList setNextPageToken(String var1) {
      this.nextPageToken = var1;
      return this;
   }
}
