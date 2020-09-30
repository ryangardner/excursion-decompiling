package com.google.api.services.drive.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Data;
import com.google.api.client.util.Key;
import java.util.List;

public final class TeamDriveList extends GenericJson {
   @Key
   private String kind;
   @Key
   private String nextPageToken;
   @Key
   private List<TeamDrive> teamDrives;

   static {
      Data.nullOf(TeamDrive.class);
   }

   public TeamDriveList clone() {
      return (TeamDriveList)super.clone();
   }

   public String getKind() {
      return this.kind;
   }

   public String getNextPageToken() {
      return this.nextPageToken;
   }

   public List<TeamDrive> getTeamDrives() {
      return this.teamDrives;
   }

   public TeamDriveList set(String var1, Object var2) {
      return (TeamDriveList)super.set(var1, var2);
   }

   public TeamDriveList setKind(String var1) {
      this.kind = var1;
      return this;
   }

   public TeamDriveList setNextPageToken(String var1) {
      this.nextPageToken = var1;
      return this;
   }

   public TeamDriveList setTeamDrives(List<TeamDrive> var1) {
      this.teamDrives = var1;
      return this;
   }
}
