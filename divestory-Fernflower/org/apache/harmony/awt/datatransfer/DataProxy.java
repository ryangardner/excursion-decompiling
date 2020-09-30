package org.apache.harmony.awt.datatransfer;

import java.awt.Image;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferUShort;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import org.apache.harmony.awt.internal.nls.Messages;

public final class DataProxy implements Transferable {
   public static final Class[] charsetTextClasses = new Class[]{byte[].class, ByteBuffer.class, InputStream.class};
   public static final Class[] unicodeTextClasses = new Class[]{String.class, Reader.class, CharBuffer.class, char[].class};
   private final DataProvider data;
   private final SystemFlavorMap flavorMap;

   public DataProxy(DataProvider var1) {
      this.data = var1;
      this.flavorMap = (SystemFlavorMap)SystemFlavorMap.getDefaultFlavorMap();
   }

   private BufferedImage createBufferedImage(RawBitmap var1) {
      if (var1 != null && var1.buffer != null && var1.width > 0 && var1.height > 0) {
         int var2;
         int var3;
         int var4;
         Object var6;
         WritableRaster var8;
         if (var1.bits == 32 && var1.buffer instanceof int[]) {
            if (!this.isRGB(var1) && !this.isBGR(var1)) {
               return null;
            }

            var2 = var1.rMask;
            var3 = var1.gMask;
            var4 = var1.bMask;
            int[] var10 = (int[])var1.buffer;
            var6 = new DirectColorModel(24, var1.rMask, var1.gMask, var1.bMask);
            var8 = Raster.createPackedRaster(new DataBufferInt(var10, var10.length), var1.width, var1.height, var1.stride, new int[]{var2, var3, var4}, (Point)null);
         } else if (var1.bits == 24 && var1.buffer instanceof byte[]) {
            int[] var11;
            if (this.isRGB(var1)) {
               var11 = new int[]{0, 1, 2};
            } else {
               if (!this.isBGR(var1)) {
                  return null;
               }

               var11 = new int[]{2, 1, 0};
            }

            byte[] var7 = (byte[])var1.buffer;
            ComponentColorModel var9 = new ComponentColorModel(ColorSpace.getInstance(1000), new int[]{8, 8, 8}, false, false, 1, 0);
            var8 = Raster.createInterleavedRaster(new DataBufferByte(var7, var7.length), var1.width, var1.height, var1.stride, 3, var11, (Point)null);
            var6 = var9;
         } else if ((var1.bits == 16 || var1.bits == 15) && var1.buffer instanceof short[]) {
            var4 = var1.rMask;
            var3 = var1.gMask;
            var2 = var1.bMask;
            short[] var5 = (short[])var1.buffer;
            var6 = new DirectColorModel(var1.bits, var1.rMask, var1.gMask, var1.bMask);
            var8 = Raster.createPackedRaster(new DataBufferUShort(var5, var5.length), var1.width, var1.height, var1.stride, new int[]{var4, var3, var2}, (Point)null);
         } else {
            var8 = null;
            var6 = var8;
         }

         if (var6 != null && var8 != null) {
            return new BufferedImage((ColorModel)var6, var8, false, (Hashtable)null);
         }
      }

      return null;
   }

   private String getCharset(DataFlavor var1) {
      return var1.getParameter("charset");
   }

   private Object getFileList(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if (this.data.isNativeFormatAvailable("application/x-java-file-list")) {
         String[] var2 = this.data.getFileList();
         if (var2 != null) {
            return Arrays.asList(var2);
         } else {
            throw new IOException(Messages.getString("awt.4F"));
         }
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   private Object getHTML(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if (this.data.isNativeFormatAvailable("text/html")) {
         String var2 = this.data.getHTML();
         if (var2 != null) {
            return this.getTextRepresentation(var2, var1);
         } else {
            throw new IOException(Messages.getString("awt.4F"));
         }
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   private Image getImage(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if (this.data.isNativeFormatAvailable("image/x-java-image")) {
         RawBitmap var2 = this.data.getRawBitmap();
         if (var2 != null) {
            return this.createBufferedImage(var2);
         } else {
            throw new IOException(Messages.getString("awt.4F"));
         }
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   private Object getPlainText(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if (this.data.isNativeFormatAvailable("text/plain")) {
         String var2 = this.data.getText();
         if (var2 != null) {
            return this.getTextRepresentation(var2, var1);
         } else {
            throw new IOException(Messages.getString("awt.4F"));
         }
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   private Object getSerializedObject(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      String var2 = SystemFlavorMap.encodeDataFlavor(var1);
      if (var2 != null && this.data.isNativeFormatAvailable(var2)) {
         byte[] var4 = this.data.getSerializedObject(var1.getRepresentationClass());
         if (var4 != null) {
            ByteArrayInputStream var5 = new ByteArrayInputStream(var4);

            try {
               ObjectInputStream var7 = new ObjectInputStream(var5);
               Object var6 = var7.readObject();
               return var6;
            } catch (ClassNotFoundException var3) {
               throw new IOException(var3.getMessage());
            }
         } else {
            throw new IOException(Messages.getString("awt.4F"));
         }
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   private Object getTextRepresentation(String var1, DataFlavor var2) throws UnsupportedFlavorException, IOException {
      if (var2.getRepresentationClass() == String.class) {
         return var1;
      } else if (var2.isRepresentationClassReader()) {
         return new StringReader(var1);
      } else if (var2.isRepresentationClassCharBuffer()) {
         return CharBuffer.wrap(var1);
      } else if (var2.getRepresentationClass() == char[].class) {
         char[] var4 = new char[var1.length()];
         var1.getChars(0, var1.length(), var4, 0);
         return var4;
      } else {
         String var3 = this.getCharset(var2);
         if (var2.getRepresentationClass() == byte[].class) {
            return var1.getBytes(var3);
         } else if (var2.isRepresentationClassByteBuffer()) {
            return ByteBuffer.wrap(var1.getBytes(var3));
         } else if (var2.isRepresentationClassInputStream()) {
            return new ByteArrayInputStream(var1.getBytes(var3));
         } else {
            throw new UnsupportedFlavorException(var2);
         }
      }
   }

   private Object getURL(DataFlavor var1) throws IOException, UnsupportedFlavorException {
      if (this.data.isNativeFormatAvailable("application/x-java-url")) {
         String var2 = this.data.getURL();
         if (var2 != null) {
            URL var3 = new URL(var2);
            if (var1.getRepresentationClass().isAssignableFrom(URL.class)) {
               return var3;
            } else if (var1.isFlavorTextType()) {
               return this.getTextRepresentation(var3.toString(), var1);
            } else {
               throw new UnsupportedFlavorException(var1);
            }
         } else {
            throw new IOException(Messages.getString("awt.4F"));
         }
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   private boolean isBGR(RawBitmap var1) {
      return var1.rMask == 255 && var1.gMask == 65280 && var1.bMask == 16711680;
   }

   private boolean isRGB(RawBitmap var1) {
      return var1.rMask == 16711680 && var1.gMask == 65280 && var1.bMask == 255;
   }

   public DataProvider getDataProvider() {
      return this.data;
   }

   public Object getTransferData(DataFlavor var1) throws UnsupportedFlavorException, IOException {
      StringBuilder var2 = new StringBuilder(String.valueOf(var1.getPrimaryType()));
      var2.append("/");
      var2.append(var1.getSubType());
      String var3 = var2.toString();
      if (var1.isFlavorTextType()) {
         if (var3.equalsIgnoreCase("text/html")) {
            return this.getHTML(var1);
         } else {
            return var3.equalsIgnoreCase("text/uri-list") ? this.getURL(var1) : this.getPlainText(var1);
         }
      } else if (var1.isFlavorJavaFileListType()) {
         return this.getFileList(var1);
      } else if (var1.isFlavorSerializedObjectType()) {
         return this.getSerializedObject(var1);
      } else if (var1.equals(DataProvider.urlFlavor)) {
         return this.getURL(var1);
      } else if (var3.equalsIgnoreCase("image/x-java-image") && Image.class.isAssignableFrom(var1.getRepresentationClass())) {
         return this.getImage(var1);
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   public DataFlavor[] getTransferDataFlavors() {
      ArrayList var1 = new ArrayList();
      String[] var2 = this.data.getNativeFormats();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         Iterator var4 = this.flavorMap.getFlavorsForNative(var2[var3]).iterator();

         while(var4.hasNext()) {
            DataFlavor var5 = (DataFlavor)var4.next();
            if (!var1.contains(var5)) {
               var1.add(var5);
            }
         }
      }

      return (DataFlavor[])var1.toArray(new DataFlavor[var1.size()]);
   }

   public boolean isDataFlavorSupported(DataFlavor var1) {
      DataFlavor[] var2 = this.getTransferDataFlavors();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         if (var2[var3].equals(var1)) {
            return true;
         }
      }

      return false;
   }
}
