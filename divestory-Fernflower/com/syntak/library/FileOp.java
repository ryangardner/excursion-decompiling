package com.syntak.library;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipOutputStream;

public class FileOp {
   static final String EXT_M3U8 = "m3u8";
   public static final String MIME_SQLITE = "application/x-sqlite3";

   public static String[] BufferedReaderToArray(BufferedReader var0) throws IOException {
      ArrayList var1 = new ArrayList();

      while(true) {
         String var2 = var0.readLine();
         if (var2 == null) {
            var0.close();
            return (String[])var1.toArray(new String[var1.size()]);
         }

         var1.add(var2);
      }
   }

   public static void DownloadFileFromWeb(String var0, String var1) {
      try {
         URL var2 = new URL(var0);
         FileInputStream var5 = (FileInputStream)var2.getContent();
         FileOutputStream var4 = new FileOutputStream(var1);
         copyStream(var5, var4);
      } catch (Exception var3) {
      }

   }

   public static String[] FileToArray(String var0) throws IOException {
      return BufferedReaderToArray(new BufferedReader(new FileReader(var0)));
   }

   public static boolean IsHostavailable(String var0) {
      try {
         InetAddress.getByName(var0);
         return true;
      } catch (UnknownHostException var1) {
         var1.printStackTrace();
         return false;
      }
   }

   public static Drawable LoadImageFromWeb(String var0) {
      try {
         URL var1 = new URL(var0);
         Drawable var3 = Drawable.createFromStream(var1.openStream(), "src name");
         return var3;
      } catch (Exception var2) {
         return null;
      }
   }

   public static boolean NewIsHostavailable(String var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("ping ");
      var1.append(var0);
      var1.append(" -c 1");
      var0 = var1.toString();
      Runtime var6 = Runtime.getRuntime();
      boolean var2 = false;

      try {
         Process var5 = var6.exec(var0);

         try {
            var5.waitFor();
         } catch (InterruptedException var3) {
            var3.printStackTrace();
            return false;
         }

         if (var5.exitValue() != 0) {
            return var2;
         }
      } catch (IOException var4) {
         var4.printStackTrace();
         return false;
      }

      var2 = true;
      return var2;
   }

   public static void SortFilesInfo(ArrayList<FileOp.FileInfo> var0) {
      Collections.sort(var0, new Comparator<FileOp.FileInfo>() {
         public int compare(FileOp.FileInfo var1, FileOp.FileInfo var2) {
            return var1.file_type != var2.file_type ? var1.file_type - var2.file_type : var1.name.compareToIgnoreCase(var2.name);
         }
      });
   }

   public static String[] UrlFileToArray(String var0) throws IOException {
      return BufferedReaderToArray(new BufferedReader(new InputStreamReader((InputStream)(new URL(var0)).getContent())));
   }

   public static String[][] UrlFileToArray2D(String var0) throws IOException {
      String[] var3 = UrlFileToArray(var0);
      String[][] var1 = new String[var3.length][];

      for(int var2 = 0; var2 < var3.length; ++var2) {
         var1[var2] = var3[var2].split(",");
      }

      return var1;
   }

   public static boolean appendTextFile(String var0, String var1) {
      File var2 = new File(var0);

      try {
         if (!var2.exists()) {
            var2.createNewFile();
         }

         FileOutputStream var5 = new FileOutputStream(var2, true);
         OutputStreamWriter var6 = new OutputStreamWriter(var5);
         BufferedWriter var3 = new BufferedWriter(var6);
         var3.write(var1);
         var3.newLine();
         var3.flush();
         var3.close();
         var5.close();
         return true;
      } catch (IOException | FileNotFoundException var4) {
         return false;
      }
   }

   public static void buildFolderChain(String var0) {
      if (var0 != null) {
         int var1 = var0.indexOf("/", 1);
         int var3;
         if (var1 > 0) {
            do {
               String var2 = var0.substring(0, var1);
               if (!checkDirExist(var2)) {
                  makeDir(var2);
               }

               var3 = var0.indexOf("/", var1 + 1);
               if (var3 <= 0) {
                  break;
               }

               var1 = var3;
            } while(var3 <= var0.length());
         }

         if (!checkDirExist(var0)) {
            makeDir(var0);
         }

      }
   }

   public static boolean checkDirExist(String var0) {
      File var2 = new File(var0);
      boolean var1;
      if (var2.exists() && var2.isDirectory()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean checkFileExist(String var0) {
      boolean var1 = false;
      if (var0 == null) {
         return false;
      } else {
         File var3 = new File(var0);
         boolean var2 = var1;
         if (var3.exists()) {
            var2 = var1;
            if (var3.isFile()) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public static boolean checkUriExist(Uri var0) {
      return checkFileExist(var0.getPath());
   }

   public static String combinePath(String var0, String var1) {
      if (var0 == null) {
         return var1;
      } else if (var1 == null) {
         return var0;
      } else {
         StringBuilder var2;
         if (var0.lastIndexOf("/") == var0.length() - 1) {
            if (var1.indexOf("/") == 0) {
               var1 = var1.substring(1);
               var2 = new StringBuilder();
               var2.append(var0);
               var2.append(var1);
               var0 = var2.toString();
            } else {
               var2 = new StringBuilder();
               var2.append(var0);
               var2.append(var1);
               var0 = var2.toString();
            }
         } else if (var1.indexOf("/") == 0) {
            var2 = new StringBuilder();
            var2.append(var0);
            var2.append(var1);
            var0 = var2.toString();
         } else {
            var2 = new StringBuilder();
            var2.append(var0);
            var2.append("/");
            var2.append(var1);
            var0 = var2.toString();
         }

         return var0;
      }
   }

   public static String combinePath(String... var0) {
      int var1 = var0.length;
      int var2 = 0;
      String var3 = null;

      String var7;
      for(int var4 = 0; var2 < var1; var3 = var7) {
         String var5 = var0[var2];
         int var6 = var4;
         var7 = var3;
         if (var5 != null) {
            if (var4 == 0) {
               var3 = var5;
            } else {
               StringBuilder var9;
               if (var3.lastIndexOf("/") == var3.length() - 1) {
                  if (var5.indexOf("/") == 0) {
                     var7 = var5.substring(1);
                     StringBuilder var8 = new StringBuilder();
                     var8.append(var3);
                     var8.append(var7);
                     var3 = var8.toString();
                  } else {
                     var9 = new StringBuilder();
                     var9.append(var3);
                     var9.append(var5);
                     var3 = var9.toString();
                  }
               } else if (var5.indexOf("/") == 0) {
                  var9 = new StringBuilder();
                  var9.append(var3);
                  var9.append(var5);
                  var3 = var9.toString();
               } else {
                  var9 = new StringBuilder();
                  var9.append(var3);
                  var9.append("/");
                  var9.append(var5);
                  var3 = var9.toString();
               }
            }

            var6 = var4 + 1;
            var7 = var3;
         }

         ++var2;
         var4 = var6;
      }

      return var3;
   }

   public static void copyFile(String var0, String var1) {
      try {
         FileInputStream var2 = new FileInputStream(var0);
         FileOutputStream var4 = new FileOutputStream(var1);
         copyStream(var2, var4);
         var4.flush();
         var4.close();
         var2.close();
      } catch (IOException var3) {
      }

   }

   public static void copyStream(InputStream var0, OutputStream var1) throws IOException {
      byte[] var2 = new byte[1024];

      while(true) {
         int var3 = var0.read(var2);
         if (var3 <= 0) {
            return;
         }

         var1.write(var2, 0, var3);
      }
   }

   public static String decriptFile(String var0, String var1, String var2, int var3) {
      byte[] var4 = new byte[1048576];
      byte[] var13 = CipherOp.readMapFile(var2);

      Exception var10000;
      label46: {
         FileInputStream var5;
         FileOutputStream var11;
         boolean var10001;
         try {
            var5 = new FileInputStream(var0);
            var11 = new FileOutputStream(var1);
         } catch (Exception var10) {
            var10000 = var10;
            var10001 = false;
            break label46;
         }

         if (var3 > 0) {
            try {
               var5.read(var4, 0, var3);
            } catch (Exception var9) {
               var10000 = var9;
               var10001 = false;
               break label46;
            }
         }

         while(true) {
            try {
               var3 = var5.read(var4, 0, 1048576);
            } catch (Exception var7) {
               var10000 = var7;
               var10001 = false;
               break;
            }

            if (var3 <= 0) {
               try {
                  var11.flush();
                  var11.close();
                  var5.close();
                  return var1;
               } catch (Exception var6) {
                  var10000 = var6;
                  var10001 = false;
                  break;
               }
            }

            try {
               var11.write(CipherOp.mappingData(var13, var4, var3), 0, var3);
            } catch (Exception var8) {
               var10000 = var8;
               var10001 = false;
               break;
            }
         }
      }

      Exception var12 = var10000;
      var12.printStackTrace();
      return null;
   }

   public static void deleteFile(String var0) {
      if (checkFileExist(var0)) {
         (new File(var0)).delete();
      }
   }

   public static boolean dumpByteArrayToTextFile(byte[] var0, String var1, int var2) {
      boolean var3 = false;
      boolean var4 = var3;
      if (var0 != null) {
         var4 = var3;
         if (var0.length != 0) {
            var4 = var3;
            if (var1 != null) {
               if (var1.length() == 0) {
                  var4 = var3;
               } else {
                  int var5 = var2;
                  if (var2 == 0) {
                     var5 = 10;
                  }

                  File var6 = new File(var1);

                  label134: {
                     label127: {
                        boolean var10001;
                        try {
                           if (var6.exists()) {
                              deleteFile(var1);
                           }
                        } catch (IOException var23) {
                           var10001 = false;
                           break label127;
                        }

                        FileOutputStream var7;
                        BufferedWriter var8;
                        try {
                           var6.createNewFile();
                           var7 = new FileOutputStream(var6, true);
                           OutputStreamWriter var24 = new OutputStreamWriter(var7);
                           var8 = new BufferedWriter(var24);
                           var8.write(var1);
                           var8.newLine();
                           var8.write("      ");
                        } catch (IOException var22) {
                           var10001 = false;
                           break label127;
                        }

                        for(var2 = 0; var2 < var5; ++var2) {
                           try {
                              var8.write(String.format(Locale.ENGLISH, "%2d ", var2));
                           } catch (IOException var21) {
                              var10001 = false;
                              break label127;
                           }
                        }

                        try {
                           var8.newLine();
                           var8.write("      ");
                        } catch (IOException var20) {
                           var10001 = false;
                           break label127;
                        }

                        for(var2 = 0; var2 < var5; ++var2) {
                           try {
                              var8.write("---");
                           } catch (IOException var19) {
                              var10001 = false;
                              break label127;
                           }
                        }

                        try {
                           var8.newLine();
                        } catch (IOException var18) {
                           var10001 = false;
                           break label127;
                        }

                        int var9 = 0;
                        int var10 = 0;
                        var2 = 0;
                        int var11 = 0;

                        while(true) {
                           try {
                              if (var9 >= var0.length) {
                                 break;
                              }
                           } catch (IOException var17) {
                              var10001 = false;
                              break label127;
                           }

                           if (var10 % var5 == 0) {
                              try {
                                 var8.write(String.format(Locale.ENGLISH, "%04d| ", var2));
                              } catch (IOException var16) {
                                 var10001 = false;
                                 break label127;
                              }
                           }

                           try {
                              var8.write(String.format("%02x ", var0[var11]));
                           } catch (IOException var15) {
                              var10001 = false;
                              break label127;
                           }

                           int var12 = var11 + 1;
                           if (var10 % var5 == var5 - 1) {
                              try {
                                 var8.newLine();
                              } catch (IOException var14) {
                                 var10001 = false;
                                 break label127;
                              }

                              var11 = var2 + 1;
                              var2 = 0;
                           } else {
                              ++var10;
                              var11 = var2;
                              var2 = var10;
                           }

                           ++var9;
                           var10 = var2;
                           var2 = var11;
                           var11 = var12;
                        }

                        try {
                           var8.flush();
                           var8.close();
                           var7.close();
                           break label134;
                        } catch (IOException var13) {
                           var10001 = false;
                        }
                     }

                     var4 = var3;
                     return var4;
                  }

                  var4 = true;
               }
            }
         }
      }

      return var4;
   }

   public static void emptyFolder(String var0) {
      File var3 = new File(var0);
      if (var3.isDirectory()) {
         String[] var1 = var3.list();

         for(int var2 = 0; var2 < var1.length; ++var2) {
            (new File(var3, var1[var2])).delete();
         }
      }

   }

   public static String getAnyFilePathInFolder(String var0) {
      File[] var4 = (new File(var0)).listFiles();
      if (var4 != null && var4.length > 0) {
         int var1 = var4.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            File var3 = var4[var2];
            if (!var3.isDirectory()) {
               return var3.getPath();
            }
         }
      }

      return null;
   }

   public static String getFileExtension(String var0) {
      return var0.contains(".") ? var0.substring(var0.lastIndexOf(46) + 1) : null;
   }

   public static Date getFileModifiedDate(String var0) {
      if (var0 == null) {
         return null;
      } else {
         File var1 = new File(var0);
         return var1.exists() ? new Date(var1.lastModified()) : null;
      }
   }

   public static long getFileModifiedTime(String var0) {
      long var1 = 0L;
      if (var0 == null) {
         return 0L;
      } else {
         File var3 = new File(var0);
         if (var3.exists()) {
            var1 = var3.lastModified();
         }

         return var1;
      }
   }

   public static long getFileSize(String var0) {
      long var1 = 0L;
      if (var0 == null) {
         return 0L;
      } else {
         File var3 = new File(var0);
         if (var3.exists()) {
            var1 = var3.length();
         }

         return var1;
      }
   }

   public static String getFilenameFromPath(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.lastIndexOf(File.separator);
         String var2 = var0;
         if (var1 > 0) {
            var2 = var0.substring(var1 + 1);
         }

         return var2;
      }
   }

   public static String[] getFilenames(String var0) {
      return (new File(var0)).list();
   }

   public static File[] getFiles(String var0) {
      return (new File(var0)).listFiles();
   }

   public static String[] getFilesByPrimary(String var0, String var1) {
      File[] var5 = (new File(var0)).listFiles();
      ArrayList var2 = new ArrayList();
      if (var5 == null) {
         return null;
      } else {
         for(int var3 = 0; var3 < var5.length; ++var3) {
            if (var5[var3].isFile()) {
               String var4 = var5[var3].getName();
               if (var1.equals(getPrimaryFilename(var4))) {
                  var2.add(var4);
               }
            }
         }

         if (var2.size() > 0) {
            return (String[])var2.toArray(new String[var2.size()]);
         } else {
            return null;
         }
      }
   }

   public static ArrayList<FileOp.FileInfo> getFilesInfo(String var0) {
      ArrayList var1 = new ArrayList();
      File[] var6 = (new File(var0)).listFiles();
      int var2 = var6.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         File var4 = var6[var3];
         FileOp.FileInfo var5 = new FileOp.FileInfo();
         var5.path = var4.getPath();
         var5.name = var4.getName();
         var5.file = var4;
         var5.time_last_modified = var4.lastModified();
         if (var4.isDirectory()) {
            var5.file_type = 0;
            File[] var7 = var4.listFiles();
            if (var7 != null) {
               var5.num_childs = var7.length;
            }
         } else {
            var5.file_type = 1;
            var5.length = var4.length();
            var5.checkable = true;
         }

         var1.add(var5);
      }

      SortFilesInfo(var1);
      return var1;
   }

   public static String getFirstFolderChainOfNextLevel(String var0) {
      File[] var2 = (new File(var0)).listFiles();
      int var1 = 0;

      while(true) {
         if (var1 >= var2.length) {
            var0 = null;
            break;
         }

         if (var2[var1].isDirectory()) {
            var0 = var2[var1].getPath();
            break;
         }

         ++var1;
      }

      return var0;
   }

   public static String getFolderChainFromPath(String var0) {
      Object var1 = null;
      if (var0 == null) {
         return null;
      } else {
         int var2 = var0.lastIndexOf(File.separator);
         if (var2 > 0) {
            var0 = var0.substring(0, var2);
         } else {
            var0 = (String)var1;
            if (var2 == 0) {
               var0 = "/";
            }
         }

         return var0;
      }
   }

   public static String getFolderFromPath(String var0) {
      Object var1 = null;
      if (var0 == null) {
         return null;
      } else {
         int var2 = var0.lastIndexOf(File.separator);
         if (var2 > 0) {
            int var3 = var2 - 1;
            String var4 = var0.substring(0, var3);
            var2 = var4.lastIndexOf(File.separator);
            var0 = (String)var1;
            if (var2 >= 0) {
               var0 = var4.substring(var2, var3);
            }
         } else {
            var0 = (String)var1;
            if (var2 == 0) {
               var0 = "/";
            }
         }

         return var0;
      }
   }

   public static String getImagePathFromID(Context var0, long var1) {
      Cursor var3 = var0.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_data"}, "_id = ?", new String[]{String.valueOf(var1)}, (String)null);
      int var4 = var3.getColumnIndex("_data");
      var3.moveToFirst();
      String var5 = null;

      while(!var3.isAfterLast()) {
         var5 = var3.getString(var4);
         var3.moveToNext();
      }

      var3.close();
      return var5;
   }

   public static String getImagePathFromThumbPath(Context var0, String var1) {
      if (var1 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("%");
         var2.append(var1);
         var1 = var2.toString();
         Cursor var6 = var0.getContentResolver().query(Thumbnails.EXTERNAL_CONTENT_URI, new String[]{"image_id"}, "_data LIKE ?", new String[]{var1}, (String)null);
         if (var6 != null) {
            int var3 = var6.getColumnIndex("image_id");
            var6.moveToFirst();
            long var4 = -1L;

            while(!var6.isAfterLast()) {
               var4 = Long.parseLong(var6.getString(var3));
               var6.moveToNext();
            }

            if (var4 != -1L) {
               getImagePathFromID(var0, var4);
            }
         }

         var6.close();
      }

      return null;
   }

   public static int getListsFromM3U8(String var0, ArrayList<String> var1, ArrayList<String> var2) {
      String[] var5 = var0.split(" ");
      int var3 = 0;

      int var4;
      for(var4 = 0; var3 < var5.length; ++var3) {
         if (!var5[var3].substring(0, 1).equals("#")) {
            if ("m3u8".equals(getFileExtension(var5[var3]).toLowerCase())) {
               var1.add(var5[var3]);
               ++var4;
            } else {
               var2.add(var5[var3]);
            }
         }
      }

      return var4;
   }

   public static String getPathAfterFolder(String var0, String var1) {
      Object var2 = null;
      String var3 = (String)var2;
      if (var0 != null) {
         if (var1 == null) {
            var3 = (String)var2;
         } else {
            var3 = (String)var2;
            if (var0.contains(var1)) {
               var3 = var0.substring(var0.indexOf(var1) + var1.length());
            }
         }
      }

      return var3;
   }

   public static String getPathFromURI(Context var0, Uri var1) {
      String var2 = var1.getScheme();
      if (var2 != null && var2.equals("content")) {
         Cursor var27 = null;

         Cursor var24;
         String var26;
         label264: {
            Throwable var10000;
            label265: {
               boolean var10001;
               try {
                  var24 = var0.getContentResolver().query(var1, new String[]{"_data"}, (String)null, (String[])null, (String)null);
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break label265;
               }

               var27 = var24;

               int var3;
               try {
                  var3 = var24.getColumnIndexOrThrow("_data");
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label265;
               }

               var27 = var24;

               try {
                  var24.moveToFirst();
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break label265;
               }

               var27 = var24;

               label244:
               try {
                  var26 = var24.getString(var3);
                  break label264;
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break label244;
               }
            }

            Throwable var25 = var10000;
            if (var27 != null) {
               var27.close();
            }

            throw var25;
         }

         if (var24 != null) {
            var24.close();
         }

         return var26;
      } else {
         return var1.getPath();
      }
   }

   public static String[] getPathsFromURIs(Context var0, ArrayList<Uri> var1) {
      int var2 = var1.size();
      String[] var3 = new String[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = getPathFromURI(var0, (Uri)var1.get(var4));
      }

      return var3;
   }

   public static String getPrimaryFilename(String var0) {
      String var1 = var0;
      if (var0.contains(".")) {
         var1 = var0.substring(0, var0.lastIndexOf(46));
      }

      return var1;
   }

   public static String getPrimaryFilenameFromPath(String var0) {
      return getPrimaryFilename(getFilenameFromPath(var0));
   }

   public static String getThumbPathFromID(Context var0, long var1) {
      Cursor var3 = var0.getContentResolver().query(Thumbnails.EXTERNAL_CONTENT_URI, new String[]{"_data"}, "image_id = ?", new String[]{String.valueOf(var1)}, (String)null);
      int var4 = var3.getColumnIndex("_data");
      var3.moveToFirst();
      String var5 = null;

      while(!var3.isAfterLast()) {
         var5 = var3.getString(var4);
         var3.moveToNext();
      }

      var3.close();
      return var5;
   }

   public static List<String> get_List_of_Filepath_with_Extension(String var0, String var1) {
      ArrayList var2 = new ArrayList();
      File[] var3 = (new File(var0)).listFiles();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File var6 = var3[var5];
         var0 = var6.getPath();
         if (var6.isFile() && var0.endsWith(var1)) {
            var2.add(var0);
         }
      }

      return var2;
   }

   public static boolean isExternalStorageAvailable() {
      boolean var0;
      if (Environment.getExternalStorageState().equals("mounted")) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean makeDir(String var0) {
      return (new File(var0)).mkdir();
   }

   public static void moveFile(String var0, String var1) {
      copyFile(var0, var1);
      (new File(var0)).delete();
   }

   public static String readTextFile(String var0) {
      File var1 = new File(var0);
      StringBuilder var8 = new StringBuilder();

      boolean var10001;
      BufferedReader var2;
      try {
         FileReader var3 = new FileReader(var1);
         var2 = new BufferedReader(var3);
      } catch (IOException var7) {
         var10001 = false;
         return var8.toString();
      }

      while(true) {
         String var9;
         try {
            var9 = var2.readLine();
         } catch (IOException var5) {
            var10001 = false;
            break;
         }

         if (var9 == null) {
            try {
               var2.close();
            } catch (IOException var4) {
               var10001 = false;
            }
            break;
         }

         try {
            var8.append(var9);
            var8.append('\n');
         } catch (IOException var6) {
            var10001 = false;
            break;
         }
      }

      return var8.toString();
   }

   public static String removeLastSlash(String var0) {
      String var1 = var0;
      if (var0.lastIndexOf("/") == var0.length() - 1) {
         var1 = var0.substring(0, var0.length() - 1);
      }

      return var1;
   }

   public static void rename(String var0, String var1, String var2) {
      (new File(var0, var1)).renameTo(new File(var0, var2));
   }

   public static boolean setFileLastModified(String var0, long var1) {
      if (var0 == null) {
         return false;
      } else {
         File var3 = new File(var0);
         if (var3.exists() && var3.isFile()) {
            var3.setLastModified(var1);
         }

         return true;
      }
   }

   public static void writeDataOutputStreamShorts(DataOutputStream var0, short[] var1, int var2) throws IOException {
      for(int var3 = 0; var3 < var2; ++var3) {
         var0.writeShort(var1[var3]);
      }

   }

   public static String zip(String var0, String var1) {
      String var2 = getFilenameFromPath(var0);
      StringBuilder var3 = new StringBuilder();
      var3.append(var1);
      var3.append(getPrimaryFilename(var2));
      var3.append(".zip");
      var1 = var3.toString();
      byte[] var15 = new byte[1048576];

      Exception var10000;
      label34: {
         ZipOutputStream var5;
         BufferedInputStream var11;
         boolean var10001;
         try {
            FileInputStream var13 = new FileInputStream(var0);
            var11 = new BufferedInputStream(var13);
            FileOutputStream var4 = new FileOutputStream(var1);
            BufferedOutputStream var14 = new BufferedOutputStream(var4);
            var5 = new ZipOutputStream(var14);
         } catch (Exception var10) {
            var10000 = var10;
            var10001 = false;
            break label34;
         }

         while(true) {
            int var6;
            try {
               var6 = var11.read(var15, 0, 1048576);
            } catch (Exception var8) {
               var10000 = var8;
               var10001 = false;
               break;
            }

            if (var6 <= 0) {
               try {
                  var5.flush();
                  var5.close();
                  var11.close();
                  return var1;
               } catch (Exception var7) {
                  var10000 = var7;
                  var10001 = false;
                  break;
               }
            }

            try {
               var5.write(var15, 0, var6);
            } catch (Exception var9) {
               var10000 = var9;
               var10001 = false;
               break;
            }
         }
      }

      Exception var12 = var10000;
      var12.printStackTrace();
      return null;
   }

   public static class AsyncCopyFile extends AsyncTask<String, Void, Void> {
      protected Void doInBackground(String... var1) {
         FileOp.copyFile(var1[0], var1[1]);
         this.onBackgroundResponse();
         return null;
      }

      public void onBackgroundResponse() {
      }

      public void onPost() {
      }

      protected void onPostExecute() {
         this.onPost();
      }
   }

   public static class AsyncMappingFile extends AsyncTask<String, Void, Void> {
      protected Void doInBackground(String... var1) {
         String var2 = var1[0];
         String var3 = var1[1];
         String var4 = var1[2];
         String var5 = var1[3];
         String var15 = var1[4];
         int var6;
         if (var15 != null) {
            var6 = var15.length();
         } else {
            var6 = 0;
         }

         byte[] var7 = new byte[1048576];
         byte[] var18 = CipherOp.readMapFile(var5);

         label56: {
            Exception var10000;
            label60: {
               FileInputStream var8;
               FileOutputStream var17;
               boolean var10001;
               try {
                  var8 = new FileInputStream(var3);
                  var17 = new FileOutputStream(var4);
               } catch (Exception var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label60;
               }

               if (var6 > 0) {
                  if (var2 == "encription") {
                     try {
                        var17.write(var15.getBytes(), 0, var15.length());
                     } catch (Exception var13) {
                        var10000 = var13;
                        var10001 = false;
                        break label60;
                     }
                  } else {
                     try {
                        var8.read(var7, 0, var6);
                     } catch (Exception var12) {
                        var10000 = var12;
                        var10001 = false;
                        break label60;
                     }
                  }
               }

               while(true) {
                  try {
                     var6 = var8.read(var7, 0, 1048576);
                  } catch (Exception var10) {
                     var10000 = var10;
                     var10001 = false;
                     break;
                  }

                  if (var6 <= 0) {
                     try {
                        var17.flush();
                        var17.close();
                        var8.close();
                        break label56;
                     } catch (Exception var9) {
                        var10000 = var9;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     var17.write(CipherOp.mappingData(var18, var7, var6), 0, var6);
                     this.onRead(var6);
                  } catch (Exception var11) {
                     var10000 = var11;
                     var10001 = false;
                     break;
                  }
               }
            }

            Exception var16 = var10000;
            var16.printStackTrace();
            return null;
         }

         this.onBackgroundResult(true);
         return null;
      }

      public void onBackgroundResult(boolean var1) {
      }

      public void onPost() {
      }

      protected void onPostExecute() {
         this.onPost();
      }

      public void onRead(int var1) {
      }
   }

   public static class DownloadCachedUrlImage extends AsyncTask<String, Void, String> {
      Bitmap bitmap;
      boolean flag_local_existed = false;
      boolean flag_max_priority = false;
      boolean flag_to_download = false;
      String local;
      String url;

      public DownloadCachedUrlImage(String var1, String var2) {
         this.url = var1;
         this.local = var2;
         if (FileOp.checkFileExist(var2)) {
            Bitmap var3 = MediaOp.getBitmapFromPath(var2);
            this.bitmap = var3;
            if (var3 == null) {
               this.flag_to_download = true;
            } else {
               this.flag_local_existed = true;
            }
         } else {
            this.flag_to_download = true;
         }

      }

      public void OnDownloaded(String var1) {
      }

      protected String doInBackground(String... var1) {
         if (this.flag_max_priority) {
            Thread.currentThread().setPriority(10);
         }

         long var2 = HttpOp.HttpGetLastModified(this.url);
         if (this.flag_local_existed && (new File(this.local)).lastModified() != var2) {
            this.flag_to_download = true;
         }

         if (this.flag_to_download) {
            if ("HTTP OK".equals(HttpOp.HttpDownloadFile(this.url, this.local))) {
               FileOp.setFileLastModified(this.local, var2);
            } else {
               this.local = null;
            }
         }

         return this.local;
      }

      protected void onPostExecute(String var1) {
         this.OnDownloaded(var1);
      }

      public FileOp.DownloadCachedUrlImage setMaxPriority() {
         this.flag_max_priority = true;
         return this;
      }
   }

   public static class FileInfo {
      public static final int TYPE_FILE = 1;
      public static final int TYPE_FOLDER = 0;
      public static final int TYPE_PARENT = 2;
      public static final int TYPE_ROOT = 3;
      public boolean checkable = false;
      public File file;
      public int file_type;
      public boolean isChecked = false;
      public long length;
      public String mime_type;
      public String name = null;
      public int num_childs = 0;
      public String path = null;
      public long time_last_modified = 0L;
      private ViewGroup viewGroup = null;
   }

   public static class FileList {
      private static ArrayList<FileOp.FileInfo> files_info;
      private static FileOp.FileList.FileListAdapter listAdapter;
      private static ListView listView;
      private static String parent_folder;
      private Context context;
      private boolean flag_show_index = false;
      private int index = 0;
      boolean multiple_selection = false;
      ArrayList<String> preSelectedPaths = null;

      public FileList(Context var1, ListView var2, String var3, boolean var4) {
         this.context = var1;
         listView = var2;
         this.multiple_selection = var4;
         if (files_info == null) {
            files_info = new ArrayList();
         }

         FileOp.FileList.FileListAdapter var5 = new FileOp.FileList.FileListAdapter();
         listAdapter = var5;
         listView.setAdapter(var5);
         fillFilesInfo(var3);
      }

      public static void clearList() {
         files_info.clear();
         files_info.trimToSize();
         notifyDataSetChanged();
      }

      public static void fillFilesInfo(String var0) {
         files_info = null;
         files_info = FileOp.getFilesInfo(var0);
         parent_folder = (new File(var0)).getParent();
         notifyDataSetChanged();
      }

      public static FileOp.FileInfo getItem(int var0) {
         return (FileOp.FileInfo)files_info.get(var0);
      }

      public static int getListSize() {
         return files_info.size();
      }

      public static String getParentFolder() {
         return parent_folder;
      }

      private void logChecked(View var1) {
         FileOp.FileInfo var2 = (FileOp.FileInfo)var1.getTag();
         var2.isChecked = ((CheckBox)var1).isChecked();
         if (var2.isChecked) {
            this.onCheckedChanged(1);
         } else {
            this.onCheckedChanged(-1);
         }

      }

      public static void notifyDataSetChanged() {
         listAdapter.notifyDataSetChanged();
      }

      private void show_row_view(FileOp.FileInfo var1) {
         ImageView var2 = (ImageView)var1.viewGroup.findViewById(R.id.icon);
         TextView var3 = (TextView)var1.viewGroup.findViewById(R.id.name);
         TextView var4 = (TextView)var1.viewGroup.findViewById(R.id.time_last_modifed);
         TextView var5 = (TextView)var1.viewGroup.findViewById(R.id.size);
         CheckBox var6 = (CheckBox)var1.viewGroup.findViewById(R.id.checkBox);
         RelativeLayout var7 = (RelativeLayout)var1.viewGroup.findViewById(R.id.row_file_list);
         var2.setVisibility(0);
         var3.setVisibility(0);
         var4.setVisibility(0);
         var5.setVisibility(0);
         int var8 = var1.file_type;
         StringBuilder var9;
         if (var8 != 0) {
            if (var8 != 1) {
               if (var8 != 2) {
                  if (var8 == 3) {
                     var2.setImageDrawable(this.context.getResources().getDrawable(R.mipmap.up));
                     var3.setText("/");
                     var4.setVisibility(8);
                     var5.setVisibility(8);
                  }
               } else {
                  var3.setText("..");
                  var2.setImageDrawable(this.context.getResources().getDrawable(R.mipmap.up));
                  var4.setVisibility(8);
                  var5.setVisibility(8);
               }
            } else {
               var2.setImageDrawable(this.context.getResources().getDrawable(R.mipmap.file));
               var3.setText(var1.name);
               var4.setText(TimeOp.MsToHourMinuteYearMonthDayVariable(var1.time_last_modified));
               String var10;
               if (var1.length > 1000000L) {
                  var9 = new StringBuilder();
                  var9.append(String.valueOf(var1.length / 1000000L));
                  var9.append(" MB");
                  var10 = var9.toString();
               } else if (var1.length > 1000L) {
                  var9 = new StringBuilder();
                  var9.append(String.valueOf(var1.length / 1000L));
                  var9.append(" KB");
                  var10 = var9.toString();
               } else {
                  var9 = new StringBuilder();
                  var9.append(String.valueOf(var1.length));
                  var9.append(" Bytes");
                  var10 = var9.toString();
               }

               var5.setText(var10);
            }
         } else {
            var3.setText(var1.name);
            var4.setText(TimeOp.MsToHourMinuteYearMonthDayVariable(var1.time_last_modified));
            var2.setImageDrawable(this.context.getResources().getDrawable(R.mipmap.folder_blue));
            var9 = new StringBuilder();
            var9.append(String.valueOf(var1.num_childs));
            var9.append(" items");
            var5.setText(var9.toString());
         }

         if (var1.checkable && this.multiple_selection) {
            ArrayList var11 = this.preSelectedPaths;
            if (var11 != null && var11.contains(var1.path)) {
               var6.setChecked(true);
               var6.setEnabled(false);
            } else {
               var6.setChecked(false);
               var6.setEnabled(true);
               var6.setTag(var1);
               var6.setOnClickListener(new OnClickListener() {
                  public void onClick(View var1) {
                     FileList.this.logChecked(var1);
                  }
               });
            }

            var6.setVisibility(0);
         } else {
            var6.setVisibility(8);
         }

         var7.setTag(var1);
         var7.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               FileList.this.onRowClicked((FileOp.FileInfo)var1.getTag());
            }
         });
      }

      public void onCheckedChanged(int var1) {
      }

      public void onRowClicked(FileOp.FileInfo var1) {
      }

      public class FileListAdapter extends BaseAdapter {
         LayoutInflater inflater;

         public FileListAdapter() {
            this.inflater = (LayoutInflater)FileList.this.context.getSystemService("layout_inflater");
         }

         public int getCount() {
            return FileOp.FileList.files_info.size();
         }

         public Object getItem(int var1) {
            return FileOp.FileList.files_info.get(var1);
         }

         public long getItemId(int var1) {
            return 0L;
         }

         public View getView(int var1, View var2, ViewGroup var3) {
            FileOp.FileInfo var4 = (FileOp.FileInfo)FileOp.FileList.files_info.get(var1);
            if (var4.viewGroup == null) {
               var4.viewGroup = (ViewGroup)this.inflater.inflate(R.layout.file_list, (ViewGroup)null);
               FileList.this.show_row_view(var4);
            }

            return var4.viewGroup;
         }
      }
   }

   public static class ReadTextFromWebFile {
      public ReadTextFromWebFile(final String var1) {
         (new Thread(new Runnable() {
            public void run() {
               try {
                  URL var1x = new URL(var1);
                  HttpURLConnection var2 = (HttpURLConnection)var1x.openConnection();
                  var2.getResponseCode();
                  var2.connect();
                  int var3 = var2.getContentLength();
                  byte[] var6 = new byte[var3 + 1];
                  InputStream var4 = var2.getInputStream();
                  var4.read(var6, 0, var3);
                  var4.close();
                  var2.disconnect();
                  String var7 = var6.toString();
                  ReadTextFromWebFile.this.OnTextRead(var7);
               } catch (IOException var5) {
                  Log.d("WebFile", var5.toString());
               }

            }
         })).start();
      }

      public void OnTextRead(String var1) {
      }
   }
}
