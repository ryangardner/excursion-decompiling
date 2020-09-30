package com.sun.mail.smtp;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class DigestMD5 {
   private static char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private String clientResponse;
   private PrintStream debugout;
   private MessageDigest md5;
   private String uri;

   public DigestMD5(PrintStream var1) {
      this.debugout = var1;
      if (var1 != null) {
         var1.println("DEBUG DIGEST-MD5: Loaded");
      }

   }

   private static String toHex(byte[] var0) {
      char[] var1 = new char[var0.length * 2];
      int var2 = 0;

      for(int var3 = 0; var2 < var0.length; ++var2) {
         int var4 = var0[var2] & 255;
         int var5 = var3 + 1;
         char[] var6 = digits;
         var1[var3] = (char)var6[var4 >> 4];
         var3 = var5 + 1;
         var1[var5] = (char)var6[var4 & 15];
      }

      return new String(var1);
   }

   private Hashtable tokenize(String var1) throws IOException {
      Hashtable var2 = new Hashtable();
      byte[] var7 = var1.getBytes();
      StreamTokenizer var3 = new StreamTokenizer(new InputStreamReader(new BASE64DecoderStream(new ByteArrayInputStream(var7, 4, var7.length - 4))));
      var3.ordinaryChars(48, 57);
      var3.wordChars(48, 57);

      while(true) {
         var1 = null;

         while(true) {
            int var4 = var3.nextToken();
            if (var4 == -1) {
               return var2;
            }

            if (var4 != -3) {
               if (var4 == 34) {
                  break;
               }
            } else {
               if (var1 != null) {
                  break;
               }

               var1 = var3.sval;
            }
         }

         PrintStream var5 = this.debugout;
         StringBuilder var6;
         if (var5 != null) {
            var6 = new StringBuilder("DEBUG DIGEST-MD5: Received => ");
            var6.append(var1);
            var6.append("='");
            var6.append(var3.sval);
            var6.append("'");
            var5.println(var6.toString());
         }

         if (var2.containsKey(var1)) {
            var6 = new StringBuilder();
            var6.append(var2.get(var1));
            var6.append(",");
            var6.append(var3.sval);
            var2.put(var1, var6.toString());
         } else {
            var2.put(var1, var3.sval);
         }
      }
   }

   public byte[] authClient(String var1, String var2, String var3, String var4, String var5) throws IOException {
      ByteArrayOutputStream var6 = new ByteArrayOutputStream();
      BASE64EncoderStream var7 = new BASE64EncoderStream(var6, Integer.MAX_VALUE);

      SecureRandom var8;
      StringBuilder var13;
      try {
         var8 = new SecureRandom();
         this.md5 = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException var12) {
         PrintStream var15 = this.debugout;
         if (var15 != null) {
            var13 = new StringBuilder("DEBUG DIGEST-MD5: ");
            var13.append(var12);
            var15.println(var13.toString());
         }

         throw new IOException(var12.toString());
      }

      StringBuffer var9 = new StringBuffer();
      StringBuilder var10 = new StringBuilder("smtp/");
      var10.append(var1);
      this.uri = var10.toString();
      byte[] var21 = new byte[32];
      PrintStream var11 = this.debugout;
      if (var11 != null) {
         var11.println("DEBUG DIGEST-MD5: Begin authentication ...");
      }

      Hashtable var22 = this.tokenize(var5);
      var5 = var4;
      if (var4 == null) {
         var4 = (String)var22.get("realm");
         if (var4 != null) {
            var1 = (new StringTokenizer(var4, ",")).nextToken();
         }

         var5 = var1;
      }

      var1 = (String)var22.get("nonce");
      var8.nextBytes(var21);
      var7.write(var21);
      var7.flush();
      var4 = var6.toString();
      var6.reset();
      MessageDigest var19 = this.md5;
      var10 = new StringBuilder(String.valueOf(var2));
      var10.append(":");
      var10.append(var5);
      var10.append(":");
      var10.append(var3);
      var19.update(var19.digest(ASCIIUtility.getBytes(var10.toString())));
      MessageDigest var16 = this.md5;
      StringBuilder var20 = new StringBuilder(":");
      var20.append(var1);
      var20.append(":");
      var20.append(var4);
      var16.update(ASCIIUtility.getBytes(var20.toString()));
      StringBuilder var18 = new StringBuilder(String.valueOf(toHex(this.md5.digest())));
      var18.append(":");
      var18.append(var1);
      var18.append(":");
      var18.append("00000001");
      var18.append(":");
      var18.append(var4);
      var18.append(":");
      var18.append("auth");
      var18.append(":");
      this.clientResponse = var18.toString();
      var16 = this.md5;
      var20 = new StringBuilder("AUTHENTICATE:");
      var20.append(this.uri);
      var16.update(ASCIIUtility.getBytes(var20.toString()));
      var19 = this.md5;
      var18 = new StringBuilder(String.valueOf(this.clientResponse));
      var18.append(toHex(this.md5.digest()));
      var19.update(ASCIIUtility.getBytes(var18.toString()));
      var18 = new StringBuilder("username=\"");
      var18.append(var2);
      var18.append("\"");
      var9.append(var18.toString());
      StringBuilder var14 = new StringBuilder(",realm=\"");
      var14.append(var5);
      var14.append("\"");
      var9.append(var14.toString());
      var14 = new StringBuilder(",qop=");
      var14.append("auth");
      var9.append(var14.toString());
      var14 = new StringBuilder(",nc=");
      var14.append("00000001");
      var9.append(var14.toString());
      var14 = new StringBuilder(",nonce=\"");
      var14.append(var1);
      var14.append("\"");
      var9.append(var14.toString());
      var13 = new StringBuilder(",cnonce=\"");
      var13.append(var4);
      var13.append("\"");
      var9.append(var13.toString());
      var13 = new StringBuilder(",digest-uri=\"");
      var13.append(this.uri);
      var13.append("\"");
      var9.append(var13.toString());
      var13 = new StringBuilder(",response=");
      var13.append(toHex(this.md5.digest()));
      var9.append(var13.toString());
      PrintStream var17 = this.debugout;
      if (var17 != null) {
         var14 = new StringBuilder("DEBUG DIGEST-MD5: Response => ");
         var14.append(var9.toString());
         var17.println(var14.toString());
      }

      var7.write(ASCIIUtility.getBytes(var9.toString()));
      var7.flush();
      return var6.toByteArray();
   }

   public boolean authServer(String var1) throws IOException {
      Hashtable var4 = this.tokenize(var1);
      MessageDigest var2 = this.md5;
      StringBuilder var3 = new StringBuilder(":");
      var3.append(this.uri);
      var2.update(ASCIIUtility.getBytes(var3.toString()));
      var2 = this.md5;
      var3 = new StringBuilder(String.valueOf(this.clientResponse));
      var3.append(toHex(this.md5.digest()));
      var2.update(ASCIIUtility.getBytes(var3.toString()));
      String var6 = toHex(this.md5.digest());
      if (!var6.equals((String)var4.get("rspauth"))) {
         PrintStream var5 = this.debugout;
         if (var5 != null) {
            var3 = new StringBuilder("DEBUG DIGEST-MD5: Expected => rspauth=");
            var3.append(var6);
            var5.println(var3.toString());
         }

         return false;
      } else {
         return true;
      }
   }
}
