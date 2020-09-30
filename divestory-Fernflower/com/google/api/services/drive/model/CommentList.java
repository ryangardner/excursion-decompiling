package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class CommentList extends GenericJson {
   @Key
   private List<Comment> comments;
   @Key
   private String kind;
   @Key
   private String nextPageToken;

   static {
      Data.nullOf(Comment.class);
   }

   public CommentList clone() {
      return (CommentList)super.clone();
   }

   public List<Comment> getComments() {
      return this.comments;
   }

   public String getKind() {
      return this.kind;
   }

   public String getNextPageToken() {
      return this.nextPageToken;
   }

   public CommentList set(String var1, Object var2) {
      return (CommentList)super.set(var1, var2);
   }

   public CommentList setComments(List<Comment> var1) {
      this.comments = var1;
      return this;
   }

   public CommentList setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public CommentList setNextPageToken(String var1) {
      this.nextPageToken = var1;
      return this;
   }
}
