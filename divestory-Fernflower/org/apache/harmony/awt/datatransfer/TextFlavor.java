package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class TextFlavor {
   public static final Class[] charsetTextClasses = new Class[]{InputStream.class, ByteBuffer.class, byte[].class};
   public static final Class[] unicodeTextClasses = new Class[]{String.class, Reader.class, CharBuffer.class, char[].class};

   public static void addCharsetClasses(SystemFlavorMap var0, String var1, String var2, String var3) {
      for(int var4 = 0; var4 < charsetTextClasses.length; ++var4) {
         StringBuilder var5 = new StringBuilder("text/");
         var5.append(var2);
         String var8 = var5.toString();
         StringBuilder var6 = new StringBuilder(";class=\"");
         var6.append(charsetTextClasses[var4].getName());
         var6.append("\"");
         var6.append(";charset=\"");
         var6.append(var3);
         var6.append("\"");
         String var10 = var6.toString();
         StringBuilder var7 = new StringBuilder(String.valueOf(var8));
         var7.append(var10);
         DataFlavor var9 = new DataFlavor(var7.toString(), var8);
         var0.addFlavorForUnencodedNative(var1, var9);
         var0.addUnencodedNativeForFlavor(var9, var1);
      }

   }

   public static void addUnicodeClasses(SystemFlavorMap var0, String var1, String var2) {
      for(int var3 = 0; var3 < unicodeTextClasses.length; ++var3) {
         StringBuilder var4 = new StringBuilder("text/");
         var4.append(var2);
         String var8 = var4.toString();
         StringBuilder var5 = new StringBuilder(";class=\"");
         var5.append(unicodeTextClasses[var3].getName());
         var5.append("\"");
         String var7 = var5.toString();
         StringBuilder var6 = new StringBuilder(String.valueOf(var8));
         var6.append(var7);
         DataFlavor var9 = new DataFlavor(var6.toString(), var8);
         var0.addFlavorForUnencodedNative(var1, var9);
         var0.addUnencodedNativeForFlavor(var9, var1);
      }

   }
}
