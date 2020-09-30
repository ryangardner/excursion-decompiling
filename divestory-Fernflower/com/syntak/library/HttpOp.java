package com.syntak.library;

import android.os.Process;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpOp {
   private static final String CRLF = "\r\n";
   public static final String HTTP_ERROR = "HTTP ERROR";
   public static final String HTTP_ERROR_CONNECTION = "HTTP Error Connection!";
   public static final String HTTP_ERROR_SOURCE_FILE = "HTTP Error Source File Not Found!";
   public static final String HTTP_ERROR_TARGET_FILE = "HTTP Error Target File Cannot Open!";
   public static final String HTTP_OK = "HTTP OK";
   public static final String HTTP_URL = "HTTP_URL";
   private static final int TASK_DOWNLOAD = 2;
   private static final int TASK_PHP = 0;
   private static final int TASK_UPLOAD = 1;
   public static final String TRANSMISSION_CHECK = "TRANSMISSION_CHECK";
   private static final String tag_folder_switch = "folder_switch";
   private static final String tag_uploaded_file = "uploaded_file";

   public static String Connection_IO(URLConnection var0, String var1) {
      String var10;
      IOException var10000;
      label40: {
         BufferedReader var11;
         boolean var10001;
         try {
            var0.setDoInput(true);
            var0.setDoOutput(true);
            var0.setUseCaches(false);
            var0.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            DataOutputStream var2 = new DataOutputStream(var0.getOutputStream());
            DataOutputStream var3 = new DataOutputStream(var2);
            OutputStreamWriter var13 = new OutputStreamWriter(var3, "UTF-8");
            BufferedWriter var4 = new BufferedWriter(var13);
            var4.write(var1);
            var4.close();
            var4.close();
            InputStream var9 = var0.getInputStream();
            InputStreamReader var15 = new InputStreamReader(var9, "UTF-8");
            var11 = new BufferedReader(var15);
         } catch (IOException var8) {
            var10000 = var8;
            var10001 = false;
            break label40;
         }

         var10 = "";

         while(true) {
            String var16;
            try {
               var16 = var11.readLine();
            } catch (IOException var6) {
               var10000 = var6;
               var10001 = false;
               break;
            }

            if (var16 == null) {
               try {
                  var11.close();
                  return var10;
               } catch (IOException var5) {
                  var10000 = var5;
                  var10001 = false;
                  break;
               }
            }

            try {
               StringBuilder var14 = new StringBuilder();
               var14.append(var10);
               var14.append(var16);
               var10 = var14.toString();
            } catch (IOException var7) {
               var10000 = var7;
               var10001 = false;
               break;
            }
         }
      }

      IOException var12 = var10000;
      var12.printStackTrace();
      var10 = "HTTP Error Connection!";
      return var10;
   }

   public static String HttpClientGet(String var0) {
      DefaultHttpClient var1 = new DefaultHttpClient();
      HttpGet var3 = new HttpGet(var0);

      try {
         var0 = EntityUtils.toString(var1.execute(var3).getEntity());
      } catch (IOException var2) {
         var0 = null;
      }

      return var0;
   }

   public static String HttpDeleteFile(String var0) {
      HttpURLConnection var8;
      String var12;
      label40: {
         label39: {
            IOException var1;
            label38: {
               ProtocolException var9;
               label37: {
                  MalformedURLException var10;
                  label36: {
                     try {
                        URL var11 = new URL(var0);
                        var8 = (HttpURLConnection)var11.openConnection();
                     } catch (MalformedURLException var5) {
                        var10 = var5;
                        var8 = null;
                        break label36;
                     } catch (ProtocolException var6) {
                        var9 = var6;
                        var8 = null;
                        break label37;
                     } catch (IOException var7) {
                        var1 = var7;
                        var8 = null;
                        break label38;
                     }

                     try {
                        var8.setDoOutput(true);
                        var8.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        var8.setRequestMethod("DELETE");
                        var8.connect();
                        break label39;
                     } catch (MalformedURLException var2) {
                        var10 = var2;
                     } catch (ProtocolException var3) {
                        var9 = var3;
                        break label37;
                     } catch (IOException var4) {
                        var1 = var4;
                        break label38;
                     }
                  }

                  var12 = var10.getMessage();
                  break label40;
               }

               var12 = var9.getMessage();
               break label40;
            }

            var12 = var1.getMessage();
            break label40;
         }

         var12 = "HTTP OK";
      }

      if (var8 != null) {
         var8.disconnect();
      }

      return var12;
   }

   public static String HttpDownloadFile(String var0, String var1) {
      Object var2 = null;
      InputStream var3 = null;

      HttpURLConnection var17;
      label66: {
         label65: {
            IOException var18;
            label64: {
               FileNotFoundException var19;
               label70: {
                  File var4;
                  try {
                     var4 = new File(var1);
                     URL var20 = new URL(var0);
                     var17 = (HttpURLConnection)var20.openConnection();
                  } catch (FileNotFoundException var15) {
                     var19 = var15;
                     var17 = (HttpURLConnection)var2;
                     break label70;
                  } catch (IOException var16) {
                     var18 = var16;
                     var17 = var3;
                     break label64;
                  }

                  FileNotFoundException var23;
                  label59: {
                     IOException var10000;
                     label58: {
                        int var5;
                        boolean var10001;
                        FileOutputStream var21;
                        byte[] var22;
                        try {
                           var17.getResponseCode();
                           var17.connect();
                           var5 = var17.getContentLength();
                           var3 = var17.getInputStream();
                           var21 = new FileOutputStream(var4);
                           var5 = Math.min(var5, 1048576);
                           var22 = new byte[var5];
                        } catch (FileNotFoundException var13) {
                           var23 = var13;
                           var10001 = false;
                           break label59;
                        } catch (IOException var14) {
                           var10000 = var14;
                           var10001 = false;
                           break label58;
                        }

                        while(true) {
                           int var6;
                           try {
                              var6 = var3.read(var22, 0, var5);
                           } catch (FileNotFoundException var9) {
                              var23 = var9;
                              var10001 = false;
                              break label59;
                           } catch (IOException var10) {
                              var10000 = var10;
                              var10001 = false;
                              break;
                           }

                           if (var6 <= 0) {
                              try {
                                 var21.flush();
                                 var21.close();
                                 var3.close();
                                 var17.disconnect();
                                 break label65;
                              } catch (FileNotFoundException var7) {
                                 var23 = var7;
                                 var10001 = false;
                                 break label59;
                              } catch (IOException var8) {
                                 var10000 = var8;
                                 var10001 = false;
                                 break;
                              }
                           }

                           try {
                              var21.write(var22, 0, var6);
                           } catch (FileNotFoundException var11) {
                              var23 = var11;
                              var10001 = false;
                              break label59;
                           } catch (IOException var12) {
                              var10000 = var12;
                              var10001 = false;
                              break;
                           }
                        }
                     }

                     var18 = var10000;
                     break label64;
                  }

                  var19 = var23;
               }

               var1 = var19.getMessage();
               break label66;
            }

            var1 = var18.getMessage();
            break label66;
         }

         var1 = "HTTP OK";
      }

      if (var17 != null) {
         var17.disconnect();
      }

      return var1;
   }

   public static String HttpDownloadFileCipher(String param0, String param1, String param2, String param3) {
      // $FF: Couldn't be decompiled
   }

   public static long HttpGetLastModified(String var0) {
      long var1 = 0L;

      HttpURLConnection var10;
      label34: {
         long var4;
         label33: {
            IOException var3;
            label32: {
               FileNotFoundException var11;
               label31: {
                  try {
                     URL var12 = new URL(var0);
                     var10 = (HttpURLConnection)var12.openConnection();
                  } catch (FileNotFoundException var8) {
                     var11 = var8;
                     var10 = null;
                     break label31;
                  } catch (IOException var9) {
                     var3 = var9;
                     var10 = null;
                     break label32;
                  }

                  try {
                     var4 = var10.getLastModified();
                     break label33;
                  } catch (FileNotFoundException var6) {
                     var11 = var6;
                  } catch (IOException var7) {
                     var3 = var7;
                     break label32;
                  }
               }

               var11.getMessage();
               break label34;
            }

            var3.getMessage();
            break label34;
         }

         var1 = var4;
      }

      if (var10 != null) {
         var10.disconnect();
      }

      return var1;
   }

   public static String HttpPost(String var0, String var1, int var2) {
      IOException var10000;
      label30: {
         boolean var10001;
         URLConnection var7;
         try {
            URL var3 = new URL(var0);
            var7 = var3.openConnection();
         } catch (IOException var6) {
            var10000 = var6;
            var10001 = false;
            break label30;
         }

         if (var2 >= 0) {
            try {
               var7.setConnectTimeout(var2);
               var7.setReadTimeout(var2);
            } catch (IOException var5) {
               var10000 = var5;
               var10001 = false;
               break label30;
            }
         }

         try {
            var0 = Connection_IO(var7, var1);
            return var0;
         } catch (IOException var4) {
            var10000 = var4;
            var10001 = false;
         }
      }

      IOException var8 = var10000;
      var8.printStackTrace();
      var0 = "HTTP Error Connection!";
      return var0;
   }

   public static String[] HttpSocketPhp(String var0, String var1, int var2, String var3) {
      BufferedWriter var4 = null;
      String[] var5 = new String[]{null, null, null, null};

      Socket var6;
      label48: {
         Socket var13;
         IOException var15;
         label52: {
            try {
               var6 = new Socket(var0, var2);
            } catch (IOException var12) {
               var15 = var12;
               var13 = var4;
               break label52;
            }

            IOException var10000;
            label43: {
               BufferedReader var14;
               StringBuilder var16;
               boolean var10001;
               try {
                  OutputStreamWriter var7 = new OutputStreamWriter(var6.getOutputStream());
                  var4 = new BufferedWriter(var7);
                  StringBuilder var18 = new StringBuilder();
                  var18.append("GET ");
                  var18.append(var1);
                  var18.append("?");
                  var18.append(var3);
                  var18.append(" HTTP/1.1");
                  var18.append("\r\n");
                  var4.write(var18.toString());
                  var16 = new StringBuilder();
                  var16.append("Host: ");
                  var16.append(var0);
                  var16.append("\r\n");
                  var4.write(var16.toString());
                  var4.write("\r\n");
                  var4.flush();
                  InputStreamReader var17 = new InputStreamReader(var6.getInputStream());
                  var14 = new BufferedReader(var17);
               } catch (IOException var11) {
                  var10000 = var11;
                  var10001 = false;
                  break label43;
               }

               while(true) {
                  try {
                     var3 = var14.readLine();
                  } catch (IOException var9) {
                     var10000 = var9;
                     var10001 = false;
                     break;
                  }

                  if (var3 == null) {
                     try {
                        var14.close();
                        break label48;
                     } catch (IOException var8) {
                        var10000 = var8;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     var16 = new StringBuilder();
                     var16.append(var5[0]);
                     var16.append(var3);
                     var5[0] = var16.toString();
                  } catch (IOException var10) {
                     var10000 = var10;
                     var10001 = false;
                     break;
                  }
               }
            }

            var15 = var10000;
            var13 = var6;
         }

         var15.printStackTrace();
         var5[0] = "HTTP Error Connection!";
         var6 = var13;
      }

      if (var6 != null) {
         var5[1] = var6.getLocalAddress().toString();
         var5[2] = String.valueOf(var6.getLocalPort());
         var5[3] = var6.getInetAddress().getHostAddress();
      }

      return var5;
   }

   public static String HttpUploadFile(String var0, String var1, String var2) {
      return HttpUploadFile(var0, var1, var2, "tag", "uploaded_file", "folder", "../file/");
   }

   public static String HttpUploadFile(String var0, String var1, String var2, String var3, String var4, String var5, String var6) {
      File var7 = new File(var1);
      if (!var7.isFile()) {
         return "HTTP Error Source File Not Found!";
      } else {
         label86: {
            StringBuilder var28;
            MalformedURLException var33;
            label87: {
               Exception var10000;
               label67: {
                  int var8;
                  int var9;
                  boolean var10001;
                  StringBuilder var22;
                  FileInputStream var23;
                  byte[] var24;
                  DataOutputStream var30;
                  HttpURLConnection var32;
                  try {
                     var23 = new FileInputStream(var7);
                     URL var31 = new URL(var0);
                     var32 = (HttpURLConnection)var31.openConnection();
                     var32.setDoInput(true);
                     var32.setDoOutput(true);
                     var32.setUseCaches(false);
                     var32.setRequestMethod("POST");
                     var32.setRequestProperty("Connection", "Keep-Alive");
                     var32.setRequestProperty("ENCTYPE", "multipart/form-data");
                     var22 = new StringBuilder();
                     var22.append("multipart/form-data;boundary=");
                     var22.append("*****");
                     var32.setRequestProperty("Content-Type", var22.toString());
                     var32.setRequestProperty(var3, var4);
                     var32.setRequestProperty(var5, var6);
                     var30 = new DataOutputStream(var32.getOutputStream());
                     var22 = new StringBuilder();
                     var22.append("--");
                     var22.append("*****");
                     var22.append("\r\n");
                     var30.writeBytes(var22.toString());
                     var22 = new StringBuilder();
                     var22.append("Content-Disposition: form-data; name=\"");
                     var22.append(var4);
                     var22.append("\";filename=\"");
                     var22.append(var2);
                     var22.append('"');
                     var22.append("\r\n");
                     var30.writeBytes(var22.toString());
                     var30.writeBytes("\r\n");
                     var8 = Math.min(var23.available(), 1048576);
                     var24 = new byte[var8];
                     var9 = var23.read(var24, 0, var8);
                  } catch (MalformedURLException var20) {
                     var33 = var20;
                     var10001 = false;
                     break label87;
                  } catch (Exception var21) {
                     var10000 = var21;
                     var10001 = false;
                     break label67;
                  }

                  label66:
                  while(true) {
                     if (var9 <= 0) {
                        DataInputStream var25;
                        try {
                           var30.writeBytes("\r\n");
                           var22 = new StringBuilder();
                           var22.append("--");
                           var22.append("*****");
                           var22.append("--");
                           var22.append("\r\n");
                           var30.writeBytes(var22.toString());
                           var8 = var32.getResponseCode();
                           var0 = var32.getResponseMessage();
                           var28 = new StringBuilder();
                           var28.append("HTTP Response is : ");
                           var28.append(var0);
                           var28.append(": ");
                           var28.append(var8);
                           Log.i("uploadFile", var28.toString());
                           var23.close();
                           var30.flush();
                           var30.close();
                           var25 = new DataInputStream(var32.getInputStream());
                        } catch (MalformedURLException var16) {
                           var33 = var16;
                           var10001 = false;
                           break label87;
                        } catch (Exception var17) {
                           var10000 = var17;
                           var10001 = false;
                           break;
                        }

                        while(true) {
                           try {
                              var3 = var25.readLine();
                           } catch (MalformedURLException var12) {
                              var33 = var12;
                              var10001 = false;
                              break label87;
                           } catch (Exception var13) {
                              var10000 = var13;
                              var10001 = false;
                              break label66;
                           }

                           if (var3 == null) {
                              try {
                                 var25.close();
                                 break label86;
                              } catch (MalformedURLException var10) {
                                 var33 = var10;
                                 var10001 = false;
                                 break label87;
                              } catch (Exception var11) {
                                 var10000 = var11;
                                 var10001 = false;
                                 break label66;
                              }
                           }

                           try {
                              var28 = new StringBuilder();
                              var28.append(var0);
                              var28.append(var3);
                              var0 = var28.toString();
                           } catch (MalformedURLException var14) {
                              var33 = var14;
                              var10001 = false;
                              break label87;
                           } catch (Exception var15) {
                              var10000 = var15;
                              var10001 = false;
                              break label66;
                           }
                        }
                     }

                     try {
                        var30.write(var24, 0, var8);
                        var8 = Math.min(var23.available(), 1048576);
                        var9 = var23.read(var24, 0, var8);
                     } catch (MalformedURLException var18) {
                        var33 = var18;
                        var10001 = false;
                        break label87;
                     } catch (Exception var19) {
                        var10000 = var19;
                        var10001 = false;
                        break;
                     }
                  }
               }

               Exception var29 = var10000;
               var0 = var29.getMessage();
               StringBuilder var26 = new StringBuilder();
               var26.append("Exception : ");
               var26.append(var0);
               Log.e("Upload file ", var26.toString(), var29);
               return var0;
            }

            MalformedURLException var27 = var33;
            var0 = var27.getMessage();
            var28 = new StringBuilder();
            var28.append("error: ");
            var28.append(var0);
            Log.e("Upload file ", var28.toString(), var27);
            return var0;
         }

         var0 = "HTTP OK";
         return var0;
      }
   }

   public static long getUrlLastModifiedTime(String var0) {
      try {
         var0 = var0.replaceAll(" ", "%20");
         URL var1 = new URL(var0);
         var0 = var1.openConnection().getHeaderField("Last-Modified");
         SimpleDateFormat var5 = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
         long var2 = var5.parse(var0).getTime();
         return var2;
      } catch (IOException | ParseException | MalformedURLException var4) {
         return 0L;
      }
   }

   public static class AsyncHttpDownloadFile {
      String source;
      String target;

      public AsyncHttpDownloadFile(String... var1) {
         this.source = var1[0];
         this.target = var1[1];
         (new HttpOp.AsyncHttpDownloadFile.background_thread()).start();
      }

      public String OnBackgroundResponse(String var1) {
         return var1;
      }

      class background_thread extends Thread {
         public void run() {
            String var1 = HttpOp.HttpDownloadFile(AsyncHttpDownloadFile.this.source, AsyncHttpDownloadFile.this.target);
            AsyncHttpDownloadFile.this.OnBackgroundResponse(var1.trim());
         }
      }
   }

   public static class AsyncHttpDownloadFileCipher {
      long downloaded = 0L;
      String id = null;
      String map_file;
      String source;
      String target;
      String timestamp;

      public AsyncHttpDownloadFileCipher(String... var1) {
         Process.setThreadPriority(11);
         this.source = var1[0];
         this.target = var1[1];
         this.map_file = var1[2];
         if (var1.length > 3 && var1[3] != null) {
            this.timestamp = var1[3];
         }

         if (var1.length > 4 && var1[4] != null) {
            this.downloaded = Long.parseLong(var1[4]);
         }

         if (var1.length > 5 && var1[5] != null) {
            this.id = var1[5];
         }

         (new HttpOp.AsyncHttpDownloadFileCipher.background_thread()).start();
      }

      public String OnBackgroundResponse(String var1) {
         return var1;
      }

      public String OnBackgroundResponse(String var1, String var2) {
         return var2;
      }

      public void OnDownloaded(int var1, byte[] var2) {
      }

      public void OnDownloaded(String var1, int var2, byte[] var3) {
      }

      class background_thread extends Thread {
         public void run() {
            // $FF: Couldn't be decompiled
         }
      }
   }

   public static class HttpPostAsync {
      HashMap<String, String> hashMap;
      String request = "";
      String url;

      public HttpPostAsync(HashMap<String, String> var1) {
         this.hashMap = var1;
         this.url = (String)var1.get("HTTP_URL");
         Iterator var2 = var1.keySet().iterator();
         int var3 = 0;

         while(var2.hasNext()) {
            String var4 = (String)var2.next();
            if (!"HTTP_URL".equals(var4)) {
               StringBuilder var5;
               if (var3 > 0) {
                  var5 = new StringBuilder();
                  var5.append(this.request);
                  var5.append("&");
                  this.request = var5.toString();
               }

               if ((String)var1.get(var4) != null) {
                  var5 = new StringBuilder();
                  var5.append(this.request);
                  var5.append(var4);
                  var5.append("=");
                  var5.append((String)var1.get(var4));
                  this.request = var5.toString();
               } else {
                  var5 = new StringBuilder();
                  var5.append(this.request);
                  var5.append(var4);
                  var5.append("=");
                  this.request = var5.toString();
               }

               ++var3;
            }
         }

         (new HttpOp.HttpPostAsync.background_thread()).start();
      }

      public String OnBackgroundResponse(String var1) {
         return var1;
      }

      class background_thread extends Thread {
         public void run() {
            String var1 = HttpOp.HttpPost(HttpPostAsync.this.url, HttpPostAsync.this.request, 0);
            HttpPostAsync.this.OnBackgroundResponse(var1.trim());
         }
      }
   }

   public static class HttpUploadFileAsync {
      String php;
      String source;
      String target;

      public HttpUploadFileAsync(String... var1) {
         this.php = var1[0];
         this.source = var1[1];
         this.target = var1[2];
         (new HttpOp.HttpUploadFileAsync.background_thread()).start();
      }

      public String OnBackgroundResponse(String var1) {
         return var1;
      }

      class background_thread extends Thread {
         public void run() {
            String var1 = HttpOp.HttpUploadFile(HttpUploadFileAsync.this.php, HttpUploadFileAsync.this.source, HttpUploadFileAsync.this.target);
            HttpUploadFileAsync.this.OnBackgroundResponse(var1.trim());
         }
      }
   }
}
