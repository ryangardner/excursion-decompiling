package org.apache.harmony.awt.datatransfer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataSource implements DataProvider {
   protected final Transferable contents;
   private DataFlavor[] flavors;
   private List<String> nativeFormats;

   public DataSource(Transferable var1) {
      this.contents = var1;
   }

   private RawBitmap getImageBitmap(Image var1) {
      if (var1 instanceof BufferedImage) {
         BufferedImage var2 = (BufferedImage)var1;
         if (var2.getType() == 1) {
            return this.getImageBitmap32(var2);
         }
      }

      int var3 = var1.getWidth((ImageObserver)null);
      int var4 = var1.getHeight((ImageObserver)null);
      if (var3 > 0 && var4 > 0) {
         BufferedImage var5 = new BufferedImage(var3, var4, 1);
         Graphics var6 = var5.getGraphics();
         var6.drawImage(var1, 0, 0, (ImageObserver)null);
         var6.dispose();
         return this.getImageBitmap32(var5);
      } else {
         return null;
      }
   }

   private RawBitmap getImageBitmap32(BufferedImage var1) {
      int[] var2 = new int[var1.getWidth() * var1.getHeight()];
      DataBufferInt var3 = (DataBufferInt)var1.getRaster().getDataBuffer();
      int var4 = var3.getNumBanks();
      int[] var5 = var3.getOffsets();
      int var6 = 0;

      for(int var7 = 0; var6 < var4; ++var6) {
         int[] var8 = var3.getData(var6);
         System.arraycopy(var8, var5[var6], var2, var7, var8.length - var5[var6]);
         var7 += var8.length - var5[var6];
      }

      return new RawBitmap(var1.getWidth(), var1.getHeight(), var1.getWidth(), 32, 16711680, 65280, 255, var2);
   }

   private static List<String> getNativesForFlavors(DataFlavor[] var0) {
      ArrayList var1 = new ArrayList();
      SystemFlavorMap var2 = (SystemFlavorMap)SystemFlavorMap.getDefaultFlavorMap();

      for(int var3 = 0; var3 < var0.length; ++var3) {
         Iterator var4 = var2.getNativesForFlavor(var0[var3]).iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            if (!var1.contains(var5)) {
               var1.add(var5);
            }
         }
      }

      return var1;
   }

   private String getText(boolean var1) {
      DataFlavor[] var2 = this.contents.getTransferDataFlavors();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         DataFlavor var4 = var2[var3];
         if (var4.isFlavorTextType() && (!var1 || this.isHtmlFlavor(var4))) {
            try {
               if (String.class.isAssignableFrom(var4.getRepresentationClass())) {
                  return (String)this.contents.getTransferData(var4);
               }

               String var6 = this.getTextFromReader(var4.getReaderForText(this.contents));
               return var6;
            } catch (Exception var5) {
            }
         }
      }

      return null;
   }

   private String getTextFromReader(Reader var1) throws IOException {
      StringBuilder var2 = new StringBuilder();
      char[] var3 = new char[1024];

      while(true) {
         int var4 = var1.read(var3);
         if (var4 <= 0) {
            return var2.toString();
         }

         var2.append(var3, 0, var4);
      }
   }

   private boolean isHtmlFlavor(DataFlavor var1) {
      return "html".equalsIgnoreCase(var1.getSubType());
   }

   protected DataFlavor[] getDataFlavors() {
      if (this.flavors == null) {
         this.flavors = this.contents.getTransferDataFlavors();
      }

      return this.flavors;
   }

   public String[] getFileList() {
      try {
         List var1 = (List)this.contents.getTransferData(DataFlavor.javaFileListFlavor);
         String[] var3 = (String[])var1.toArray(new String[var1.size()]);
         return var3;
      } catch (Exception var2) {
         return null;
      }
   }

   public String getHTML() {
      return this.getText(true);
   }

   public String[] getNativeFormats() {
      return (String[])this.getNativeFormatsList().toArray(new String[0]);
   }

   public List<String> getNativeFormatsList() {
      if (this.nativeFormats == null) {
         this.nativeFormats = getNativesForFlavors(this.getDataFlavors());
      }

      return this.nativeFormats;
   }

   public RawBitmap getRawBitmap() {
      DataFlavor[] var1 = this.contents.getTransferDataFlavors();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         DataFlavor var3 = var1[var2];
         Class var4 = var3.getRepresentationClass();
         if (var4 != null && Image.class.isAssignableFrom(var4) && (var3.isMimeTypeEqual(DataFlavor.imageFlavor) || var3.isFlavorSerializedObjectType())) {
            try {
               RawBitmap var7 = this.getImageBitmap((Image)this.contents.getTransferData(var3));
               return var7;
            } finally {
               continue;
            }
         }
      }

      return null;
   }

   public byte[] getSerializedObject(Class<?> var1) {
      try {
         DataFlavor var2 = new DataFlavor(var1, (String)null);
         Serializable var8 = (Serializable)this.contents.getTransferData(var2);
         ByteArrayOutputStream var6 = new ByteArrayOutputStream();
         ObjectOutputStream var3 = new ObjectOutputStream(var6);
         var3.writeObject(var8);
         byte[] var7 = var6.toByteArray();
         return var7;
      } finally {
         ;
      }
   }

   public String getText() {
      return this.getText(false);
   }

   public String getURL() {
      String var5;
      try {
         var5 = ((URL)this.contents.getTransferData(urlFlavor)).toString();
         return var5;
      } catch (Exception var4) {
         try {
            var5 = ((URL)this.contents.getTransferData(uriFlavor)).toString();
            return var5;
         } catch (Exception var3) {
            try {
               URL var1 = new URL(this.getText());
               var5 = var1.toString();
               return var5;
            } catch (Exception var2) {
               return null;
            }
         }
      }
   }

   public boolean isNativeFormatAvailable(String var1) {
      return this.getNativeFormatsList().contains(var1);
   }
}
