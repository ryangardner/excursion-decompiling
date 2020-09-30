package com.syntak.library;

import android.content.Context;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GoogleDriveRestOp {
   private static final String[] SCOPES = new String[]{"https://www.googleapis.com/auth/drive.metadata.readonly"};
   String app_name;
   Context context;
   private Drive driveService = null;
   private GoogleAccountCredential mCredential;

   public GoogleDriveRestOp(Context var1, String var2) {
      this.context = var1;
      this.app_name = var2;
      this.mCredential = GoogleAccountCredential.usingOAuth2(var1, Arrays.asList(SCOPES)).setBackOff(new ExponentialBackOff());
      this.driveService = (new Drive.Builder(AndroidHttp.newCompatibleTransport(), JacksonFactory.getDefaultInstance(), this.mCredential)).setApplicationName(var2).build();
   }

   public static boolean isGooglePlayServicesAvailable(Context var0) {
      boolean var1;
      if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(var0) == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void downloadFile(String var1, String var2) {
      try {
         FileOutputStream var3 = new FileOutputStream(var2);
         this.driveService.files().get(var1).executeMediaAndDownloadTo(var3);
         var3.flush();
         var3.close();
      } catch (IOException var4) {
      }

   }

   public List<File> getFileListWithAppProperty(String var1, String var2) {
      List var3 = null;
      String var4 = null;

      String var22;
      List var24;
      do {
         List var5 = var3;

         label114: {
            label122: {
               Drive.Files.List var6;
               boolean var10001;
               try {
                  var6 = this.driveService.files().list();
               } catch (IOException var20) {
                  var10001 = false;
                  break label122;
               }

               Drive.Files.List var7 = var6;
               if (var1 != null) {
                  var7 = var6;
                  if (var2 != null) {
                     var5 = var3;

                     StringBuilder var23;
                     try {
                        var23 = new StringBuilder;
                     } catch (IOException var19) {
                        var10001 = false;
                        break label122;
                     }

                     var5 = var3;

                     try {
                        var23.<init>();
                     } catch (IOException var18) {
                        var10001 = false;
                        break label122;
                     }

                     var5 = var3;

                     try {
                        var23.append("appProperties has { key='");
                     } catch (IOException var17) {
                        var10001 = false;
                        break label122;
                     }

                     var5 = var3;

                     try {
                        var23.append(var1);
                     } catch (IOException var16) {
                        var10001 = false;
                        break label122;
                     }

                     var5 = var3;

                     try {
                        var23.append("' and value='");
                     } catch (IOException var15) {
                        var10001 = false;
                        break label122;
                     }

                     var5 = var3;

                     try {
                        var23.append(var2);
                     } catch (IOException var14) {
                        var10001 = false;
                        break label122;
                     }

                     var5 = var3;

                     try {
                        var23.append("' }");
                     } catch (IOException var13) {
                        var10001 = false;
                        break label122;
                     }

                     var5 = var3;

                     try {
                        var7 = var6.setQ(var23.toString());
                     } catch (IOException var12) {
                        var10001 = false;
                        break label122;
                     }
                  }
               }

               var5 = var3;

               FileList var21;
               try {
                  var21 = (FileList)var7.setSpaces("drive").setFields("nextPageToken, files(id, name, modifiedTime)").setPageToken(var4).execute();
               } catch (IOException var11) {
                  var10001 = false;
                  break label122;
               }

               if (var3 == null) {
                  var5 = var3;

                  try {
                     var24 = var21.getFiles();
                  } catch (IOException var10) {
                     var10001 = false;
                     break label122;
                  }
               } else {
                  var5 = var3;

                  try {
                     var3.addAll(var21.getFiles());
                  } catch (IOException var9) {
                     var10001 = false;
                     break label122;
                  }

                  var24 = var3;
               }

               var5 = var24;

               try {
                  var22 = var21.getNextPageToken();
                  break label114;
               } catch (IOException var8) {
                  var10001 = false;
               }
            }

            var24 = var5;
            break;
         }

         var3 = var24;
         var4 = var22;
      } while(var22 != null);

      return var24;
   }

   public boolean isDriveServiceAvailable() {
      boolean var1;
      if (this.driveService != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String searchFileWithAppProperty(String var1, String var2) {
      return null;
   }

   public void stop() {
      this.driveService = null;
   }

   public void uploadFileWithAppProperty(String var1, String var2, String var3) {
      String var4 = FileOp.getFilenameFromPath(var1);
      String var5 = MediaOp.getMimeType(var1);
      File var6 = new File();
      var6.setName(var4);
      FileContent var9 = new FileContent(var5, new java.io.File(var1));

      try {
         Drive.Files.Create var10 = this.driveService.files().create(var6, var9).setFields("id");
         StringBuilder var8 = new StringBuilder();
         var8.append("appProperties, {");
         var8.append(var2);
         var8.append(",");
         var8.append(var3);
         var8.append("}");
         File var11 = (File)var10.setFields(var8.toString()).execute();
      } catch (IOException var7) {
      }

   }
}
