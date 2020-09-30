package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DataSnapshot implements DataProvider {
   private final String[] fileList;
   private final String html;
   private final String[] nativeFormats;
   private final RawBitmap rawBitmap;
   private final Map<Class<?>, byte[]> serializedObjects;
   private final String text;
   private final String url;

   public DataSnapshot(DataProvider var1) {
      this.nativeFormats = var1.getNativeFormats();
      this.text = var1.getText();
      this.fileList = var1.getFileList();
      this.url = var1.getURL();
      this.html = var1.getHTML();
      this.rawBitmap = var1.getRawBitmap();
      this.serializedObjects = Collections.synchronizedMap(new HashMap());
      int var2 = 0;

      while(true) {
         String[] var3 = this.nativeFormats;
         if (var2 >= var3.length) {
            return;
         }

         DataFlavor var4 = null;

         label25: {
            DataFlavor var7;
            try {
               var7 = SystemFlavorMap.decodeDataFlavor(var3[var2]);
            } catch (ClassNotFoundException var5) {
               break label25;
            }

            var4 = var7;
         }

         if (var4 != null) {
            Class var8 = var4.getRepresentationClass();
            byte[] var6 = var1.getSerializedObject(var8);
            if (var6 != null) {
               this.serializedObjects.put(var8, var6);
            }
         }

         ++var2;
      }
   }

   public String[] getFileList() {
      return this.fileList;
   }

   public String getHTML() {
      return this.html;
   }

   public String[] getNativeFormats() {
      return this.nativeFormats;
   }

   public RawBitmap getRawBitmap() {
      return this.rawBitmap;
   }

   public short[] getRawBitmapBuffer16() {
      RawBitmap var1 = this.rawBitmap;
      short[] var2;
      if (var1 != null && var1.buffer instanceof short[]) {
         var2 = (short[])this.rawBitmap.buffer;
      } else {
         var2 = null;
      }

      return var2;
   }

   public int[] getRawBitmapBuffer32() {
      RawBitmap var1 = this.rawBitmap;
      int[] var2;
      if (var1 != null && var1.buffer instanceof int[]) {
         var2 = (int[])this.rawBitmap.buffer;
      } else {
         var2 = null;
      }

      return var2;
   }

   public byte[] getRawBitmapBuffer8() {
      RawBitmap var1 = this.rawBitmap;
      byte[] var2;
      if (var1 != null && var1.buffer instanceof byte[]) {
         var2 = (byte[])this.rawBitmap.buffer;
      } else {
         var2 = null;
      }

      return var2;
   }

   public int[] getRawBitmapHeader() {
      RawBitmap var1 = this.rawBitmap;
      int[] var2;
      if (var1 != null) {
         var2 = var1.getHeader();
      } else {
         var2 = null;
      }

      return var2;
   }

   public byte[] getSerializedObject(Class<?> var1) {
      return (byte[])this.serializedObjects.get(var1);
   }

   public byte[] getSerializedObject(String var1) {
      try {
         byte[] var3 = this.getSerializedObject(SystemFlavorMap.decodeDataFlavor(var1).getRepresentationClass());
         return var3;
      } catch (Exception var2) {
         return null;
      }
   }

   public String getText() {
      return this.text;
   }

   public String getURL() {
      return this.url;
   }

   public boolean isNativeFormatAvailable(String var1) {
      if (var1 == null) {
         return false;
      } else if (var1.equals("text/plain")) {
         return this.text != null;
      } else if (var1.equals("application/x-java-file-list")) {
         return this.fileList != null;
      } else if (var1.equals("application/x-java-url")) {
         return this.url != null;
      } else if (var1.equals("text/html")) {
         return this.html != null;
      } else if (var1.equals("image/x-java-image")) {
         return this.rawBitmap != null;
      } else {
         try {
            DataFlavor var4 = SystemFlavorMap.decodeDataFlavor(var1);
            boolean var2 = this.serializedObjects.containsKey(var4.getRepresentationClass());
            return var2;
         } catch (Exception var3) {
            return false;
         }
      }
   }
}
