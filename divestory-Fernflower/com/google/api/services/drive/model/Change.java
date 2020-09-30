package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public final class Change extends GenericJson {
   @Key
   private File file;
   @Key
   private String fileId;
   @Key
   private String kind;
   @Key
   private Boolean removed;
   @Key
   private TeamDrive teamDrive;
   @Key
   private String teamDriveId;
   @Key
   private DateTime time;
   @Key
   private String type;

   public Change clone() {
      return (Change)super.clone();
   }

   public File getFile() {
      return this.file;
   }

   public String getFileId() {
      return this.fileId;
   }

   public String getKind() {
      return this.kind;
   }

   public Boolean getRemoved() {
      return this.removed;
   }

   public TeamDrive getTeamDrive() {
      return this.teamDrive;
   }

   public String getTeamDriveId() {
      return this.teamDriveId;
   }

   public DateTime getTime() {
      return this.time;
   }

   public String getType() {
      return this.type;
   }

   public Change set(String var1, Object var2) {
      return (Change)super.set(var1, var2);
   }

   public Change setFile(File var1) {
      this.file = var1;
      return this;
   }

   public Change setFileId(String var1) {
      this.fileId = var1;
      return this;
   }

   public Change setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public Change setRemoved(Boolean var1) {
      this.removed = var1;
      return this;
   }

   public Change setTeamDrive(TeamDrive var1) {
      this.teamDrive = var1;
      return this;
   }

   public Change setTeamDriveId(String var1) {
      this.teamDriveId = var1;
      return this;
   }

   public Change setTime(DateTime var1) {
      this.time = var1;
      return this;
   }

   public Change setType(String var1) {
      this.type = var1;
      return this;
   }
}
