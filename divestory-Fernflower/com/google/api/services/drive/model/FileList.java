package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class FileList extends GenericJson {
   @Key
   private List<File> files;
   @Key
   private Boolean incompleteSearch;
   @Key
   private String kind;
   @Key
   private String nextPageToken;

   static {
      Data.nullOf(File.class);
   }

   public FileList clone() {
      return (FileList)super.clone();
   }

   public List<File> getFiles() {
      return this.files;
   }

   public Boolean getIncompleteSearch() {
      return this.incompleteSearch;
   }

   public String getKind() {
      return this.kind;
   }

   public String getNextPageToken() {
      return this.nextPageToken;
   }

   public FileList set(String var1, Object var2) {
      return (FileList)super.set(var1, var2);
   }

   public FileList setFiles(List<File> var1) {
      this.files = var1;
      return this;
   }

   public FileList setIncompleteSearch(Boolean var1) {
      this.incompleteSearch = var1;
      return this;
   }

   public FileList setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public FileList setNextPageToken(String var1) {
      this.nextPageToken = var1;
      return this;
   }
}
