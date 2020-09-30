package com.sun.activation.registries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MailcapFile {
   private static boolean addReverse;
   private Map fallback_hash = new HashMap();
   private Map native_commands = new HashMap();
   private Map type_hash = new HashMap();

   static {
      label19:
      try {
         addReverse = Boolean.getBoolean("javax.activation.addreverse");
      } finally {
         break label19;
      }

   }

   public MailcapFile() {
      if (LogSupport.isLoggable()) {
         LogSupport.log("new MailcapFile: default");
      }

   }

   public MailcapFile(InputStream var1) throws IOException {
      if (LogSupport.isLoggable()) {
         LogSupport.log("new MailcapFile: InputStream");
      }

      this.parse(new BufferedReader(new InputStreamReader(var1, "iso-8859-1")));
   }

   public MailcapFile(String var1) throws IOException {
      StringBuilder var2;
      if (LogSupport.isLoggable()) {
         var2 = new StringBuilder("new MailcapFile: file ");
         var2.append(var1);
         LogSupport.log(var2.toString());
      }

      var2 = null;
      boolean var12 = false;

      FileReader var3;
      try {
         var12 = true;
         var3 = new FileReader(var1);
         var12 = false;
      } finally {
         if (var12) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (IOException var13) {
               }
            }

         }
      }

      try {
         BufferedReader var17 = new BufferedReader(var3);
         this.parse(var17);
      } finally {
         ;
      }

      try {
         var3.close();
      } catch (IOException var14) {
      }

   }

   private Map mergeResults(Map var1, Map var2) {
      Iterator var3 = var2.keySet().iterator();
      HashMap var4 = new HashMap(var1);

      while(var3.hasNext()) {
         String var8 = (String)var3.next();
         List var5 = (List)var4.get(var8);
         if (var5 == null) {
            var4.put(var8, var2.get(var8));
         } else {
            List var6 = (List)var2.get(var8);
            ArrayList var7 = new ArrayList(var5);
            var7.addAll(var6);
            var4.put(var8, var7);
         }
      }

      return var4;
   }

   private void parse(Reader param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   protected static void reportParseError(int var0, int var1, int var2, int var3, String var4) throws MailcapParseException {
      StringBuilder var5;
      if (LogSupport.isLoggable()) {
         var5 = new StringBuilder("PARSE ERROR: Encountered a ");
         var5.append(MailcapTokenizer.nameForToken(var3));
         var5.append(" token (");
         var5.append(var4);
         var5.append(") while expecting a ");
         var5.append(MailcapTokenizer.nameForToken(var0));
         var5.append(", a ");
         var5.append(MailcapTokenizer.nameForToken(var1));
         var5.append(", or a ");
         var5.append(MailcapTokenizer.nameForToken(var2));
         var5.append(" token.");
         LogSupport.log(var5.toString());
      }

      var5 = new StringBuilder("Encountered a ");
      var5.append(MailcapTokenizer.nameForToken(var3));
      var5.append(" token (");
      var5.append(var4);
      var5.append(") while expecting a ");
      var5.append(MailcapTokenizer.nameForToken(var0));
      var5.append(", a ");
      var5.append(MailcapTokenizer.nameForToken(var1));
      var5.append(", or a ");
      var5.append(MailcapTokenizer.nameForToken(var2));
      var5.append(" token.");
      throw new MailcapParseException(var5.toString());
   }

   protected static void reportParseError(int var0, int var1, int var2, String var3) throws MailcapParseException {
      StringBuilder var4 = new StringBuilder("Encountered a ");
      var4.append(MailcapTokenizer.nameForToken(var2));
      var4.append(" token (");
      var4.append(var3);
      var4.append(") while expecting a ");
      var4.append(MailcapTokenizer.nameForToken(var0));
      var4.append(" or a ");
      var4.append(MailcapTokenizer.nameForToken(var1));
      var4.append(" token.");
      throw new MailcapParseException(var4.toString());
   }

   protected static void reportParseError(int var0, int var1, String var2) throws MailcapParseException {
      StringBuilder var3 = new StringBuilder("Encountered a ");
      var3.append(MailcapTokenizer.nameForToken(var1));
      var3.append(" token (");
      var3.append(var2);
      var3.append(") while expecting a ");
      var3.append(MailcapTokenizer.nameForToken(var0));
      var3.append(" token.");
      throw new MailcapParseException(var3.toString());
   }

   public void appendToMailcap(String var1) {
      if (LogSupport.isLoggable()) {
         StringBuilder var2 = new StringBuilder("appendToMailcap: ");
         var2.append(var1);
         LogSupport.log(var2.toString());
      }

      try {
         StringReader var4 = new StringReader(var1);
         this.parse(var4);
      } catch (IOException var3) {
      }

   }

   public Map getMailcapFallbackList(String var1) {
      Map var2 = (Map)this.fallback_hash.get(var1);
      int var3 = var1.indexOf(47) + 1;
      Map var4 = var2;
      if (!var1.substring(var3).equals("*")) {
         StringBuilder var5 = new StringBuilder(String.valueOf(var1.substring(0, var3)));
         var5.append("*");
         var1 = var5.toString();
         Map var6 = (Map)this.fallback_hash.get(var1);
         var4 = var2;
         if (var6 != null) {
            if (var2 != null) {
               var4 = this.mergeResults(var2, var6);
            } else {
               var4 = var6;
            }
         }
      }

      return var4;
   }

   public Map getMailcapList(String var1) {
      Map var2 = (Map)this.type_hash.get(var1);
      int var3 = var1.indexOf(47) + 1;
      Map var4 = var2;
      if (!var1.substring(var3).equals("*")) {
         StringBuilder var5 = new StringBuilder(String.valueOf(var1.substring(0, var3)));
         var5.append("*");
         var1 = var5.toString();
         Map var6 = (Map)this.type_hash.get(var1);
         var4 = var2;
         if (var6 != null) {
            if (var2 != null) {
               var4 = this.mergeResults(var2, var6);
            } else {
               var4 = var6;
            }
         }
      }

      return var4;
   }

   public String[] getMimeTypes() {
      HashSet var1 = new HashSet(this.type_hash.keySet());
      var1.addAll(this.fallback_hash.keySet());
      var1.addAll(this.native_commands.keySet());
      return (String[])var1.toArray(new String[var1.size()]);
   }

   public String[] getNativeCommands(String var1) {
      String[] var2 = (String[])null;
      List var3 = (List)this.native_commands.get(var1.toLowerCase(Locale.ENGLISH));
      String[] var4 = var2;
      if (var3 != null) {
         var4 = (String[])var3.toArray(new String[var3.size()]);
      }

      return var4;
   }

   protected void parseLine(String var1) throws MailcapParseException, IOException {
      MailcapTokenizer var2 = new MailcapTokenizer(var1);
      var2.setIsAutoquoting(false);
      StringBuilder var3;
      if (LogSupport.isLoggable()) {
         var3 = new StringBuilder("parse: ");
         var3.append(var1);
         LogSupport.log(var3.toString());
      }

      int var4 = var2.nextToken();
      if (var4 != 2) {
         reportParseError(2, var4, var2.getCurrentTokenValue());
      }

      String var5 = var2.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
      var4 = var2.nextToken();
      if (var4 != 47 && var4 != 59) {
         reportParseError(47, 59, var4, var2.getCurrentTokenValue());
      }

      String var16;
      if (var4 == 47) {
         var4 = var2.nextToken();
         if (var4 != 2) {
            reportParseError(2, var4, var2.getCurrentTokenValue());
         }

         var16 = var2.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
         var4 = var2.nextToken();
      } else {
         var16 = "*";
      }

      StringBuilder var21 = new StringBuilder(String.valueOf(var5));
      var21.append("/");
      var21.append(var16);
      String var6 = var21.toString();
      if (LogSupport.isLoggable()) {
         var3 = new StringBuilder("  Type: ");
         var3.append(var6);
         LogSupport.log(var3.toString());
      }

      LinkedHashMap var22 = new LinkedHashMap();
      if (var4 != 59) {
         reportParseError(59, var4, var2.getCurrentTokenValue());
      }

      var2.setIsAutoquoting(true);
      int var7 = var2.nextToken();
      var2.setIsAutoquoting(false);
      if (var7 != 2 && var7 != 59) {
         reportParseError(2, 59, var7, var2.getCurrentTokenValue());
      }

      List var18;
      if (var7 == 2) {
         var18 = (List)this.native_commands.get(var6);
         if (var18 == null) {
            ArrayList var20 = new ArrayList();
            var20.add(var1);
            this.native_commands.put(var6, var20);
         } else {
            var18.add(var1);
         }
      }

      var4 = var7;
      if (var7 != 59) {
         var4 = var2.nextToken();
      }

      if (var4 == 59) {
         boolean var24 = false;

         int var9;
         String var10;
         StringBuilder var12;
         boolean var27;
         do {
            var7 = var2.nextToken();
            if (var7 != 2) {
               reportParseError(2, var7, var2.getCurrentTokenValue());
            }

            var1 = var2.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
            int var8 = var2.nextToken();
            if (var8 != 61 && var8 != 59 && var8 != 5) {
               reportParseError(61, 59, 5, var8, var2.getCurrentTokenValue());
            }

            var27 = var24;
            var9 = var8;
            if (var8 == 61) {
               var2.setIsAutoquoting(true);
               var7 = var2.nextToken();
               var2.setIsAutoquoting(false);
               if (var7 != 2) {
                  reportParseError(2, var7, var2.getCurrentTokenValue());
               }

               var10 = var2.getCurrentTokenValue();
               var27 = var24;
               if (var1.startsWith("x-java-")) {
                  String var11 = var1.substring(7);
                  if (var11.equals("fallback-entry") && var10.equalsIgnoreCase("true")) {
                     var27 = true;
                  } else {
                     if (LogSupport.isLoggable()) {
                        var12 = new StringBuilder("    Command: ");
                        var12.append(var11);
                        var12.append(", Class: ");
                        var12.append(var10);
                        LogSupport.log(var12.toString());
                     }

                     var18 = (List)var22.get(var11);
                     Object var13 = var18;
                     if (var18 == null) {
                        var13 = new ArrayList();
                        var22.put(var11, var13);
                     }

                     if (addReverse) {
                        ((List)var13).add(0, var10);
                        var27 = var24;
                     } else {
                        ((List)var13).add(var10);
                        var27 = var24;
                     }
                  }
               }

               var9 = var2.nextToken();
            }

            var24 = var27;
         } while(var9 == 59);

         Map var17;
         if (var27) {
            var17 = this.fallback_hash;
         } else {
            var17 = this.type_hash;
         }

         Map var23 = (Map)var17.get(var6);
         if (var23 == null) {
            var17.put(var6, var22);
         } else {
            if (LogSupport.isLoggable()) {
               var12 = new StringBuilder("Merging commands for type ");
               var12.append(var6);
               LogSupport.log(var12.toString());
            }

            Iterator var19 = var23.keySet().iterator();

            label124:
            while(true) {
               List var14;
               List var25;
               do {
                  if (!var19.hasNext()) {
                     var19 = var22.keySet().iterator();

                     while(var19.hasNext()) {
                        String var15 = (String)var19.next();
                        if (!var23.containsKey(var15)) {
                           var23.put(var15, (List)var22.get(var15));
                        }
                     }
                     break label124;
                  }

                  var6 = (String)var19.next();
                  var14 = (List)var23.get(var6);
                  var25 = (List)var22.get(var6);
               } while(var25 == null);

               Iterator var26 = var25.iterator();

               while(var26.hasNext()) {
                  var10 = (String)var26.next();
                  if (!var14.contains(var10)) {
                     if (addReverse) {
                        var14.add(0, var10);
                     } else {
                        var14.add(var10);
                     }
                  }
               }
            }
         }
      } else if (var4 != 5) {
         reportParseError(5, 59, var4, var2.getCurrentTokenValue());
      }

   }
}
