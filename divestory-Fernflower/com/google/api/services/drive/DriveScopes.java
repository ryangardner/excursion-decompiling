package com.google.api.services.drive;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DriveScopes {
   public static final String DRIVE = "https://www.googleapis.com/auth/drive";
   public static final String DRIVE_APPDATA = "https://www.googleapis.com/auth/drive.appdata";
   public static final String DRIVE_FILE = "https://www.googleapis.com/auth/drive.file";
   public static final String DRIVE_METADATA = "https://www.googleapis.com/auth/drive.metadata";
   public static final String DRIVE_METADATA_READONLY = "https://www.googleapis.com/auth/drive.metadata.readonly";
   public static final String DRIVE_PHOTOS_READONLY = "https://www.googleapis.com/auth/drive.photos.readonly";
   public static final String DRIVE_READONLY = "https://www.googleapis.com/auth/drive.readonly";
   public static final String DRIVE_SCRIPTS = "https://www.googleapis.com/auth/drive.scripts";

   private DriveScopes() {
   }

   public static Set<String> all() {
      HashSet var0 = new HashSet();
      var0.add("https://www.googleapis.com/auth/drive");
      var0.add("https://www.googleapis.com/auth/drive.appdata");
      var0.add("https://www.googleapis.com/auth/drive.file");
      var0.add("https://www.googleapis.com/auth/drive.metadata");
      var0.add("https://www.googleapis.com/auth/drive.metadata.readonly");
      var0.add("https://www.googleapis.com/auth/drive.photos.readonly");
      var0.add("https://www.googleapis.com/auth/drive.readonly");
      var0.add("https://www.googleapis.com/auth/drive.scripts");
      return Collections.unmodifiableSet(var0);
   }
}
