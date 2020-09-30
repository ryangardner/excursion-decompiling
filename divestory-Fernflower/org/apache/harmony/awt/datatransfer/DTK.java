package org.apache.harmony.awt.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.dnd.peer.DropTargetContextPeer;
import java.nio.charset.Charset;
import org.apache.harmony.awt.ContextStorage;
import org.apache.harmony.awt.internal.nls.Messages;
import org.apache.harmony.misc.SystemUtils;

public abstract class DTK {
   protected final DataTransferThread dataTransferThread;
   private NativeClipboard nativeClipboard = null;
   private NativeClipboard nativeSelection = null;
   protected SystemFlavorMap systemFlavorMap;

   protected DTK() {
      DataTransferThread var1 = new DataTransferThread(this);
      this.dataTransferThread = var1;
      var1.start();
   }

   private static DTK createDTK() {
      int var0 = SystemUtils.getOS();
      String var1;
      if (var0 != 1) {
         if (var0 != 2) {
            throw new RuntimeException(Messages.getString("awt.4E"));
         }

         var1 = "org.apache.harmony.awt.datatransfer.linux.LinuxDTK";
      } else {
         var1 = "org.apache.harmony.awt.datatransfer.windows.WinDTK";
      }

      try {
         DTK var3 = (DTK)Class.forName(var1).newInstance();
         return var3;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public static DTK getDTK() {
      Object var0 = ContextStorage.getContextLock();
      synchronized(var0){}

      Throwable var10000;
      boolean var10001;
      label266: {
         try {
            if (ContextStorage.shutdownPending()) {
               return null;
            }
         } catch (Throwable var32) {
            var10000 = var32;
            var10001 = false;
            break label266;
         }

         DTK var1;
         try {
            var1 = ContextStorage.getDTK();
         } catch (Throwable var31) {
            var10000 = var31;
            var10001 = false;
            break label266;
         }

         DTK var2 = var1;
         if (var1 == null) {
            try {
               var2 = createDTK();
               ContextStorage.setDTK(var2);
            } catch (Throwable var30) {
               var10000 = var30;
               var10001 = false;
               break label266;
            }
         }

         label249:
         try {
            return var2;
         } catch (Throwable var29) {
            var10000 = var29;
            var10001 = false;
            break label249;
         }
      }

      while(true) {
         Throwable var33 = var10000;

         try {
            throw var33;
         } catch (Throwable var28) {
            var10000 = var28;
            var10001 = false;
            continue;
         }
      }
   }

   protected void appendSystemFlavorMap(SystemFlavorMap var1, DataFlavor var2, String var3) {
      var1.addFlavorForUnencodedNative(var3, var2);
      var1.addUnencodedNativeForFlavor(var2, var3);
   }

   protected void appendSystemFlavorMap(SystemFlavorMap var1, String[] var2, String var3, String var4) {
      TextFlavor.addUnicodeClasses(var1, var4, var3);

      for(int var5 = 0; var5 < var2.length; ++var5) {
         if (var2[var5] != null && Charset.isSupported(var2[var5])) {
            TextFlavor.addCharsetClasses(var1, var4, var3, var2[var5]);
         }
      }

   }

   public abstract DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent var1);

   public abstract DropTargetContextPeer createDropTargetContextPeer(DropTargetContext var1);

   protected String[] getCharsets() {
      return new String[]{"UTF-16", "UTF-8", "unicode", "ISO-8859-1", "US-ASCII"};
   }

   public String getDefaultCharset() {
      return "unicode";
   }

   public NativeClipboard getNativeClipboard() {
      if (this.nativeClipboard == null) {
         this.nativeClipboard = this.newNativeClipboard();
      }

      return this.nativeClipboard;
   }

   public NativeClipboard getNativeSelection() {
      if (this.nativeSelection == null) {
         this.nativeSelection = this.newNativeSelection();
      }

      return this.nativeSelection;
   }

   public SystemFlavorMap getSystemFlavorMap() {
      synchronized(this){}

      SystemFlavorMap var1;
      try {
         var1 = this.systemFlavorMap;
      } finally {
         ;
      }

      return var1;
   }

   public abstract void initDragAndDrop();

   public void initSystemFlavorMap(SystemFlavorMap var1) {
      String[] var2 = this.getCharsets();
      this.appendSystemFlavorMap(var1, DataFlavor.stringFlavor, "text/plain");
      this.appendSystemFlavorMap(var1, var2, "plain", "text/plain");
      this.appendSystemFlavorMap(var1, var2, "html", "text/html");
      this.appendSystemFlavorMap(var1, DataProvider.urlFlavor, "application/x-java-url");
      this.appendSystemFlavorMap(var1, var2, "uri-list", "application/x-java-url");
      this.appendSystemFlavorMap(var1, DataFlavor.javaFileListFlavor, "application/x-java-file-list");
      this.appendSystemFlavorMap(var1, DataFlavor.imageFlavor, "image/x-java-image");
   }

   protected abstract NativeClipboard newNativeClipboard();

   protected abstract NativeClipboard newNativeSelection();

   public abstract void runEventLoop();

   public void setSystemFlavorMap(SystemFlavorMap var1) {
      synchronized(this){}

      try {
         this.systemFlavorMap = var1;
      } finally {
         ;
      }

   }
}
