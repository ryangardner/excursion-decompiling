package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class RevisionList extends GenericJson {
   @Key
   private String kind;
   @Key
   private String nextPageToken;
   @Key
   private List<Revision> revisions;

   static {
      Data.nullOf(Revision.class);
   }

   public RevisionList clone() {
      return (RevisionList)super.clone();
   }

   public String getKind() {
      return this.kind;
   }

   public String getNextPageToken() {
      return this.nextPageToken;
   }

   public List<Revision> getRevisions() {
      return this.revisions;
   }

   public RevisionList set(String var1, Object var2) {
      return (RevisionList)super.set(var1, var2);
   }

   public RevisionList setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public RevisionList setNextPageToken(String var1) {
      this.nextPageToken = var1;
      return this;
   }

   public RevisionList setRevisions(List<Revision> var1) {
      this.revisions = var1;
      return this;
   }
}
