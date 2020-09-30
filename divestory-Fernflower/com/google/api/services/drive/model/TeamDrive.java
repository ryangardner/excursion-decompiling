package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class TeamDrive extends GenericJson {
   @Key
   private TeamDrive.BackgroundImageFile backgroundImageFile;
   @Key
   private String backgroundImageLink;
   @Key
   private TeamDrive.Capabilities capabilities;
   @Key
   private String colorRgb;
   @Key
   private DateTime createdTime;
   @Key
   private String id;
   @Key
   private String kind;
   @Key
   private String name;
   @Key
   private String themeId;

   public TeamDrive clone() {
      return (TeamDrive)super.clone();
   }

   public TeamDrive.BackgroundImageFile getBackgroundImageFile() {
      return this.backgroundImageFile;
   }

   public String getBackgroundImageLink() {
      return this.backgroundImageLink;
   }

   public TeamDrive.Capabilities getCapabilities() {
      return this.capabilities;
   }

   public String getColorRgb() {
      return this.colorRgb;
   }

   public DateTime getCreatedTime() {
      return this.createdTime;
   }

   public String getId() {
      return this.id;
   }

   public String getKind() {
      return this.kind;
   }

   public String getName() {
      return this.name;
   }

   public String getThemeId() {
      return this.themeId;
   }

   public TeamDrive set(String var1, Object var2) {
      return (TeamDrive)super.set(var1, var2);
   }

   public TeamDrive setBackgroundImageFile(TeamDrive.BackgroundImageFile var1) {
      this.backgroundImageFile = var1;
      return this;
   }

   public TeamDrive setBackgroundImageLink(String var1) {
      this.backgroundImageLink = var1;
      return this;
   }

   public TeamDrive setCapabilities(TeamDrive.Capabilities var1) {
      this.capabilities = var1;
      return this;
   }

   public TeamDrive setColorRgb(String var1) {
      this.colorRgb = var1;
      return this;
   }

   public TeamDrive setCreatedTime(DateTime var1) {
      this.createdTime = var1;
      return this;
   }

   public TeamDrive setId(String var1) {
      this.id = var1;
      return this;
   }

   public TeamDrive setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public TeamDrive setName(String var1) {
      this.name = var1;
      return this;
   }

   public TeamDrive setThemeId(String var1) {
      this.themeId = var1;
      return this;
   }

   public static final class BackgroundImageFile extends GenericJson {
      @Key
      private String id;
      @Key
      private Float width;
      @Key
      private Float xCoordinate;
      @Key
      private Float yCoordinate;

      public TeamDrive.BackgroundImageFile clone() {
         return (TeamDrive.BackgroundImageFile)super.clone();
      }

      public String getId() {
         return this.id;
      }

      public Float getWidth() {
         return this.width;
      }

      public Float getXCoordinate() {
         return this.xCoordinate;
      }

      public Float getYCoordinate() {
         return this.yCoordinate;
      }

      public TeamDrive.BackgroundImageFile set(String var1, Object var2) {
         return (TeamDrive.BackgroundImageFile)super.set(var1, var2);
      }

      public TeamDrive.BackgroundImageFile setId(String var1) {
         this.id = var1;
         return this;
      }

      public TeamDrive.BackgroundImageFile setWidth(Float var1) {
         this.width = var1;
         return this;
      }

      public TeamDrive.BackgroundImageFile setXCoordinate(Float var1) {
         this.xCoordinate = var1;
         return this;
      }

      public TeamDrive.BackgroundImageFile setYCoordinate(Float var1) {
         this.yCoordinate = var1;
         return this;
      }
   }

   public static final class Capabilities extends GenericJson {
      @Key
      private Boolean canAddChildren;
      @Key
      private Boolean canChangeTeamDriveBackground;
      @Key
      private Boolean canComment;
      @Key
      private Boolean canCopy;
      @Key
      private Boolean canDeleteTeamDrive;
      @Key
      private Boolean canDownload;
      @Key
      private Boolean canEdit;
      @Key
      private Boolean canListChildren;
      @Key
      private Boolean canManageMembers;
      @Key
      private Boolean canReadRevisions;
      @Key
      private Boolean canRemoveChildren;
      @Key
      private Boolean canRename;
      @Key
      private Boolean canRenameTeamDrive;
      @Key
      private Boolean canShare;

      public TeamDrive.Capabilities clone() {
         return (TeamDrive.Capabilities)super.clone();
      }

      public Boolean getCanAddChildren() {
         return this.canAddChildren;
      }

      public Boolean getCanChangeTeamDriveBackground() {
         return this.canChangeTeamDriveBackground;
      }

      public Boolean getCanComment() {
         return this.canComment;
      }

      public Boolean getCanCopy() {
         return this.canCopy;
      }

      public Boolean getCanDeleteTeamDrive() {
         return this.canDeleteTeamDrive;
      }

      public Boolean getCanDownload() {
         return this.canDownload;
      }

      public Boolean getCanEdit() {
         return this.canEdit;
      }

      public Boolean getCanListChildren() {
         return this.canListChildren;
      }

      public Boolean getCanManageMembers() {
         return this.canManageMembers;
      }

      public Boolean getCanReadRevisions() {
         return this.canReadRevisions;
      }

      public Boolean getCanRemoveChildren() {
         return this.canRemoveChildren;
      }

      public Boolean getCanRename() {
         return this.canRename;
      }

      public Boolean getCanRenameTeamDrive() {
         return this.canRenameTeamDrive;
      }

      public Boolean getCanShare() {
         return this.canShare;
      }

      public TeamDrive.Capabilities set(String var1, Object var2) {
         return (TeamDrive.Capabilities)super.set(var1, var2);
      }

      public TeamDrive.Capabilities setCanAddChildren(Boolean var1) {
         this.canAddChildren = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanChangeTeamDriveBackground(Boolean var1) {
         this.canChangeTeamDriveBackground = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanComment(Boolean var1) {
         this.canComment = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanCopy(Boolean var1) {
         this.canCopy = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanDeleteTeamDrive(Boolean var1) {
         this.canDeleteTeamDrive = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanDownload(Boolean var1) {
         this.canDownload = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanEdit(Boolean var1) {
         this.canEdit = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanListChildren(Boolean var1) {
         this.canListChildren = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanManageMembers(Boolean var1) {
         this.canManageMembers = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanReadRevisions(Boolean var1) {
         this.canReadRevisions = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanRemoveChildren(Boolean var1) {
         this.canRemoveChildren = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanRename(Boolean var1) {
         this.canRename = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanRenameTeamDrive(Boolean var1) {
         this.canRenameTeamDrive = var1;
         return this;
      }

      public TeamDrive.Capabilities setCanShare(Boolean var1) {
         this.canShare = var1;
         return this;
      }
   }
}
