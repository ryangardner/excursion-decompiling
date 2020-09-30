package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class ReplyList extends GenericJson {
   @Key
   private String kind;
   @Key
   private String nextPageToken;
   @Key
   private List<Reply> replies;

   static {
      Data.nullOf(Reply.class);
   }

   public ReplyList clone() {
      return (ReplyList)super.clone();
   }

   public String getKind() {
      return this.kind;
   }

   public String getNextPageToken() {
      return this.nextPageToken;
   }

   public List<Reply> getReplies() {
      return this.replies;
   }

   public ReplyList set(String var1, Object var2) {
      return (ReplyList)super.set(var1, var2);
   }

   public ReplyList setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public ReplyList setNextPageToken(String var1) {
      this.nextPageToken = var1;
      return this;
   }

   public ReplyList setReplies(List<Reply> var1) {
      this.replies = var1;
      return this;
   }
}
