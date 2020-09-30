package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;
import java.util.Map;

public final class About extends GenericJson {
   @Key
   private Boolean appInstalled;
   @Key
   private Boolean canCreateTeamDrives;
   @Key
   private Map<String, List<String>> exportFormats;
   @Key
   private List<String> folderColorPalette;
   @Key
   private Map<String, List<String>> importFormats;
   @Key
   private String kind;
   @JsonString
   @Key
   private Map<String, Long> maxImportSizes;
   @JsonString
   @Key
   private Long maxUploadSize;
   @Key
   private About.StorageQuota storageQuota;
   @Key
   private List<About.TeamDriveThemes> teamDriveThemes;
   @Key
   private User user;

   static {
      Data.nullOf(About.TeamDriveThemes.class);
   }

   public About clone() {
      return (About)super.clone();
   }

   public Boolean getAppInstalled() {
      return this.appInstalled;
   }

   public Boolean getCanCreateTeamDrives() {
      return this.canCreateTeamDrives;
   }

   public Map<String, List<String>> getExportFormats() {
      return this.exportFormats;
   }

   public List<String> getFolderColorPalette() {
      return this.folderColorPalette;
   }

   public Map<String, List<String>> getImportFormats() {
      return this.importFormats;
   }

   public String getKind() {
      return this.kind;
   }

   public Map<String, Long> getMaxImportSizes() {
      return this.maxImportSizes;
   }

   public Long getMaxUploadSize() {
      return this.maxUploadSize;
   }

   public About.StorageQuota getStorageQuota() {
      return this.storageQuota;
   }

   public List<About.TeamDriveThemes> getTeamDriveThemes() {
      return this.teamDriveThemes;
   }

   public User getUser() {
      return this.user;
   }

   public About set(String var1, Object var2) {
      return (About)super.set(var1, var2);
   }

   public About setAppInstalled(Boolean var1) {
      this.appInstalled = var1;
      return this;
   }

   public About setCanCreateTeamDrives(Boolean var1) {
      this.canCreateTeamDrives = var1;
      return this;
   }

   public About setExportFormats(Map<String, List<String>> var1) {
      this.exportFormats = var1;
      return this;
   }

   public About setFolderColorPalette(List<String> var1) {
      this.folderColorPalette = var1;
      return this;
   }

   public About setImportFormats(Map<String, List<String>> var1) {
      this.importFormats = var1;
      return this;
   }

   public About setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public About setMaxImportSizes(Map<String, Long> var1) {
      this.maxImportSizes = var1;
      return this;
   }

   public About setMaxUploadSize(Long var1) {
      this.maxUploadSize = var1;
      return this;
   }

   public About setStorageQuota(About.StorageQuota var1) {
      this.storageQuota = var1;
      return this;
   }

   public About setTeamDriveThemes(List<About.TeamDriveThemes> var1) {
      this.teamDriveThemes = var1;
      return this;
   }

   public About setUser(User var1) {
      this.user = var1;
      return this;
   }

   public static final class StorageQuota extends GenericJson {
      @JsonString
      @Key
      private Long limit;
      @JsonString
      @Key
      private Long usage;
      @JsonString
      @Key
      private Long usageInDrive;
      @JsonString
      @Key
      private Long usageInDriveTrash;

      public About.StorageQuota clone() {
         return (About.StorageQuota)super.clone();
      }

      public Long getLimit() {
         return this.limit;
      }

      public Long getUsage() {
         return this.usage;
      }

      public Long getUsageInDrive() {
         return this.usageInDrive;
      }

      public Long getUsageInDriveTrash() {
         return this.usageInDriveTrash;
      }

      public About.StorageQuota set(String var1, Object var2) {
         return (About.StorageQuota)super.set(var1, var2);
      }

      public About.StorageQuota setLimit(Long var1) {
         this.limit = var1;
         return this;
      }

      public About.StorageQuota setUsage(Long var1) {
         this.usage = var1;
         return this;
      }

      public About.StorageQuota setUsageInDrive(Long var1) {
         this.usageInDrive = var1;
         return this;
      }

      public About.StorageQuota setUsageInDriveTrash(Long var1) {
         this.usageInDriveTrash = var1;
         return this;
      }
   }

   public static final class TeamDriveThemes extends GenericJson {
      @Key
      private String backgroundImageLink;
      @Key
      private String colorRgb;
      @Key
      private String id;

      public About.TeamDriveThemes clone() {
         return (About.TeamDriveThemes)super.clone();
      }

      public String getBackgroundImageLink() {
         return this.backgroundImageLink;
      }

      public String getColorRgb() {
         return this.colorRgb;
      }

      public String getId() {
         return this.id;
      }

      public About.TeamDriveThemes set(String var1, Object var2) {
         return (About.TeamDriveThemes)super.set(var1, var2);
      }

      public About.TeamDriveThemes setBackgroundImageLink(String var1) {
         this.backgroundImageLink = var1;
         return this;
      }

      public About.TeamDriveThemes setColorRgb(String var1) {
         this.colorRgb = var1;
         return this;
      }

      public About.TeamDriveThemes setId(String var1) {
         this.id = var1;
         return this;
      }
   }
}
