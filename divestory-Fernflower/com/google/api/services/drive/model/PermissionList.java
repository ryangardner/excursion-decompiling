package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class PermissionList extends GenericJson {
   @Key
   private String kind;
   @Key
   private String nextPageToken;
   @Key
   private List<Permission> permissions;

   static {
      Data.nullOf(Permission.class);
   }

   public PermissionList clone() {
      return (PermissionList)super.clone();
   }

   public String getKind() {
      return this.kind;
   }

   public String getNextPageToken() {
      return this.nextPageToken;
   }

   public List<Permission> getPermissions() {
      return this.permissions;
   }

   public PermissionList set(String var1, Object var2) {
      return (PermissionList)super.set(var1, var2);
   }

   public PermissionList setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public PermissionList setNextPageToken(String var1) {
      this.nextPageToken = var1;
      return this;
   }

   public PermissionList setPermissions(List<Permission> var1) {
      this.permissions = var1;
      return this;
   }
}
