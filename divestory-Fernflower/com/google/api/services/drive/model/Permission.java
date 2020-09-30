package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import java.util.List;

public final class Permission extends GenericJson {
   @Key
   private Boolean allowFileDiscovery;
   @Key
   private Boolean deleted;
   @Key
   private String displayName;
   @Key
   private String domain;
   @Key
   private String emailAddress;
   @Key
   private DateTime expirationTime;
   @Key
   private String id;
   @Key
   private String kind;
   @Key
   private String photoLink;
   @Key
   private String role;
   @Key
   private List<Permission.TeamDrivePermissionDetails> teamDrivePermissionDetails;
   @Key
   private String type;

   static {
      Data.nullOf(Permission.TeamDrivePermissionDetails.class);
   }

   public Permission clone() {
      return (Permission)super.clone();
   }

   public Boolean getAllowFileDiscovery() {
      return this.allowFileDiscovery;
   }

   public Boolean getDeleted() {
      return this.deleted;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getDomain() {
      return this.domain;
   }

   public String getEmailAddress() {
      return this.emailAddress;
   }

   public DateTime getExpirationTime() {
      return this.expirationTime;
   }

   public String getId() {
      return this.id;
   }

   public String getKind() {
      return this.kind;
   }

   public String getPhotoLink() {
      return this.photoLink;
   }

   public String getRole() {
      return this.role;
   }

   public List<Permission.TeamDrivePermissionDetails> getTeamDrivePermissionDetails() {
      return this.teamDrivePermissionDetails;
   }

   public String getType() {
      return this.type;
   }

   public Permission set(String var1, Object var2) {
      return (Permission)super.set(var1, var2);
   }

   public Permission setAllowFileDiscovery(Boolean var1) {
      this.allowFileDiscovery = var1;
      return this;
   }

   public Permission setDeleted(Boolean var1) {
      this.deleted = var1;
      return this;
   }

   public Permission setDisplayName(String var1) {
      this.displayName = var1;
      return this;
   }

   public Permission setDomain(String var1) {
      this.domain = var1;
      return this;
   }

   public Permission setEmailAddress(String var1) {
      this.emailAddress = var1;
      return this;
   }

   public Permission setExpirationTime(DateTime var1) {
      this.expirationTime = var1;
      return this;
   }

   public Permission setId(String var1) {
      this.id = var1;
      return this;
   }

   public Permission setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public Permission setPhotoLink(String var1) {
      this.photoLink = var1;
      return this;
   }

   public Permission setRole(String var1) {
      this.role = var1;
      return this;
   }

   public Permission setTeamDrivePermissionDetails(List<Permission.TeamDrivePermissionDetails> var1) {
      this.teamDrivePermissionDetails = var1;
      return this;
   }

   public Permission setType(String var1) {
      this.type = var1;
      return this;
   }

   public static final class TeamDrivePermissionDetails extends GenericJson {
      @Key
      private Boolean inherited;
      @Key
      private String inheritedFrom;
      @Key
      private String role;
      @Key
      private String teamDrivePermissionType;

      public Permission.TeamDrivePermissionDetails clone() {
         return (Permission.TeamDrivePermissionDetails)super.clone();
      }

      public Boolean getInherited() {
         return this.inherited;
      }

      public String getInheritedFrom() {
         return this.inheritedFrom;
      }

      public String getRole() {
         return this.role;
      }

      public String getTeamDrivePermissionType() {
         return this.teamDrivePermissionType;
      }

      public Permission.TeamDrivePermissionDetails set(String var1, Object var2) {
         return (Permission.TeamDrivePermissionDetails)super.set(var1, var2);
      }

      public Permission.TeamDrivePermissionDetails setInherited(Boolean var1) {
         this.inherited = var1;
         return this;
      }

      public Permission.TeamDrivePermissionDetails setInheritedFrom(String var1) {
         this.inheritedFrom = var1;
         return this;
      }

      public Permission.TeamDrivePermissionDetails setRole(String var1) {
         this.role = var1;
         return this;
      }

      public Permission.TeamDrivePermissionDetails setTeamDrivePermissionType(String var1) {
         this.teamDrivePermissionType = var1;
         return this;
      }
   }
}
