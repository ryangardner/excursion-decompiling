package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URL;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.Transferable;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public class DataHandler implements Transferable {
   private static final DataFlavor[] emptyFlavors = new DataFlavor[0];
   private static DataContentHandlerFactory factory;
   private CommandMap currentCommandMap = null;
   private DataContentHandler dataContentHandler;
   private DataSource dataSource = null;
   private DataContentHandler factoryDCH;
   private DataSource objDataSource = null;
   private Object object = null;
   private String objectMimeType = null;
   private DataContentHandlerFactory oldFactory;
   private String shortType;
   private DataFlavor[] transferFlavors;

   public DataHandler(Object var1, String var2) {
      this.transferFlavors = emptyFlavors;
      this.dataContentHandler = null;
      this.factoryDCH = null;
      this.oldFactory = null;
      this.shortType = null;
      this.object = var1;
      this.objectMimeType = var2;
      this.oldFactory = factory;
   }

   public DataHandler(URL var1) {
      this.transferFlavors = emptyFlavors;
      this.dataContentHandler = null;
      this.factoryDCH = null;
      this.oldFactory = null;
      this.shortType = null;
      this.dataSource = new URLDataSource(var1);
      this.oldFactory = factory;
   }

   public DataHandler(DataSource var1) {
      this.transferFlavors = emptyFlavors;
      this.dataContentHandler = null;
      this.factoryDCH = null;
      this.oldFactory = null;
      this.shortType = null;
      this.dataSource = var1;
      this.oldFactory = factory;
   }

   private String getBaseType() {
      // $FF: Couldn't be decompiled
   }

   private CommandMap getCommandMap() {
      synchronized(this){}

      CommandMap var1;
      try {
         if (this.currentCommandMap != null) {
            var1 = this.currentCommandMap;
            return var1;
         }

         var1 = CommandMap.getDefaultCommandMap();
      } finally {
         ;
      }

      return var1;
   }

   private DataContentHandler getDataContentHandler() {
      synchronized(this){}

      Throwable var10000;
      label915: {
         boolean var10001;
         try {
            if (factory != this.oldFactory) {
               this.oldFactory = factory;
               this.factoryDCH = null;
               this.dataContentHandler = null;
               this.transferFlavors = emptyFlavors;
            }
         } catch (Throwable var90) {
            var10000 = var90;
            var10001 = false;
            break label915;
         }

         DataContentHandler var95;
         try {
            if (this.dataContentHandler != null) {
               var95 = this.dataContentHandler;
               return var95;
            }
         } catch (Throwable var91) {
            var10000 = var91;
            var10001 = false;
            break label915;
         }

         String var1;
         try {
            var1 = this.getBaseType();
            if (this.factoryDCH == null && factory != null) {
               this.factoryDCH = factory.createDataContentHandler(var1);
            }
         } catch (Throwable var89) {
            var10000 = var89;
            var10001 = false;
            break label915;
         }

         try {
            if (this.factoryDCH != null) {
               this.dataContentHandler = this.factoryDCH;
            }
         } catch (Throwable var88) {
            var10000 = var88;
            var10001 = false;
            break label915;
         }

         label910: {
            label882:
            try {
               if (this.dataContentHandler == null) {
                  if (this.dataSource == null) {
                     break label882;
                  }

                  this.dataContentHandler = this.getCommandMap().createDataContentHandler(var1, this.dataSource);
               }
               break label910;
            } catch (Throwable var87) {
               var10000 = var87;
               var10001 = false;
               break label915;
            }

            try {
               this.dataContentHandler = this.getCommandMap().createDataContentHandler(var1);
            } catch (Throwable var86) {
               var10000 = var86;
               var10001 = false;
               break label915;
            }
         }

         label911: {
            try {
               if (this.dataSource != null) {
                  DataSourceDataContentHandler var92 = new DataSourceDataContentHandler(this.dataContentHandler, this.dataSource);
                  this.dataContentHandler = var92;
                  break label911;
               }
            } catch (Throwable var85) {
               var10000 = var85;
               var10001 = false;
               break label915;
            }

            try {
               ObjectDataContentHandler var93 = new ObjectDataContentHandler(this.dataContentHandler, this.object, this.objectMimeType);
               this.dataContentHandler = var93;
            } catch (Throwable var84) {
               var10000 = var84;
               var10001 = false;
               break label915;
            }
         }

         try {
            var95 = this.dataContentHandler;
         } catch (Throwable var83) {
            var10000 = var83;
            var10001 = false;
            break label915;
         }

         return var95;
      }

      Throwable var94 = var10000;
      throw var94;
   }

   public static void setDataContentHandlerFactory(DataContentHandlerFactory param0) {
      // $FF: Couldn't be decompiled
   }

   public CommandInfo[] getAllCommands() {
      return this.dataSource != null ? this.getCommandMap().getAllCommands(this.getBaseType(), this.dataSource) : this.getCommandMap().getAllCommands(this.getBaseType());
   }

   public Object getBean(CommandInfo var1) {
      Object var7;
      label30: {
         boolean var10001;
         ClassLoader var2;
         try {
            var2 = SecuritySupport.getContextClassLoader();
         } catch (ClassNotFoundException | IOException var6) {
            var10001 = false;
            break label30;
         }

         ClassLoader var3 = var2;
         if (var2 == null) {
            try {
               var3 = this.getClass().getClassLoader();
            } catch (ClassNotFoundException | IOException var5) {
               var10001 = false;
               break label30;
            }
         }

         try {
            var7 = var1.getCommandObject(this, var3);
            return var7;
         } catch (ClassNotFoundException | IOException var4) {
            var10001 = false;
         }
      }

      var7 = null;
      return var7;
   }

   public CommandInfo getCommand(String var1) {
      return this.dataSource != null ? this.getCommandMap().getCommand(this.getBaseType(), var1, this.dataSource) : this.getCommandMap().getCommand(this.getBaseType(), var1);
   }

   public Object getContent() throws IOException {
      Object var1 = this.object;
      return var1 != null ? var1 : this.getDataContentHandler().getContent(this.getDataSource());
   }

   public String getContentType() {
      DataSource var1 = this.dataSource;
      return var1 != null ? var1.getContentType() : this.objectMimeType;
   }

   public DataSource getDataSource() {
      DataSource var1 = this.dataSource;
      DataSource var2 = var1;
      if (var1 == null) {
         if (this.objDataSource == null) {
            this.objDataSource = new DataHandlerDataSource(this);
         }

         var2 = this.objDataSource;
      }

      return var2;
   }

   public InputStream getInputStream() throws IOException {
      DataSource var1 = this.dataSource;
      Object var4;
      if (var1 != null) {
         var4 = var1.getInputStream();
      } else {
         final DataContentHandler var2 = this.getDataContentHandler();
         StringBuilder var5;
         if (var2 == null) {
            var5 = new StringBuilder("no DCH for MIME type ");
            var5.append(this.getBaseType());
            throw new UnsupportedDataTypeException(var5.toString());
         }

         if (var2 instanceof ObjectDataContentHandler && ((ObjectDataContentHandler)var2).getDCH() == null) {
            var5 = new StringBuilder("no object DCH for MIME type ");
            var5.append(this.getBaseType());
            throw new UnsupportedDataTypeException(var5.toString());
         }

         final PipedOutputStream var3 = new PipedOutputStream();
         var4 = new PipedInputStream(var3);
         (new Thread(new Runnable() {
            public void run() {
               try {
                  var2.writeTo(DataHandler.this.object, DataHandler.this.objectMimeType, var3);
               } catch (IOException var8) {
               } finally {
                  try {
                     var3.close();
                  } catch (IOException var7) {
                  }

               }

            }
         }, "DataHandler.getInputStream")).start();
      }

      return (InputStream)var4;
   }

   public String getName() {
      DataSource var1 = this.dataSource;
      return var1 != null ? var1.getName() : null;
   }

   public OutputStream getOutputStream() throws IOException {
      DataSource var1 = this.dataSource;
      return var1 != null ? var1.getOutputStream() : null;
   }

   public CommandInfo[] getPreferredCommands() {
      return this.dataSource != null ? this.getCommandMap().getPreferredCommands(this.getBaseType(), this.dataSource) : this.getCommandMap().getPreferredCommands(this.getBaseType());
   }

   public Object getTransferData(DataFlavor var1) throws UnsupportedFlavorException, IOException {
      return this.getDataContentHandler().getTransferData(var1, this.dataSource);
   }

   public DataFlavor[] getTransferDataFlavors() {
      synchronized(this){}

      DataFlavor[] var1;
      try {
         if (factory != this.oldFactory) {
            this.transferFlavors = emptyFlavors;
         }

         if (this.transferFlavors == emptyFlavors) {
            this.transferFlavors = this.getDataContentHandler().getTransferDataFlavors();
         }

         var1 = this.transferFlavors;
      } finally {
         ;
      }

      return var1;
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

   public void setCommandMap(CommandMap var1) {
      synchronized(this){}

      Throwable var10000;
      label74: {
         boolean var10001;
         label73: {
            try {
               if (var1 != this.currentCommandMap) {
                  break label73;
               }
            } catch (Throwable var7) {
               var10000 = var7;
               var10001 = false;
               break label74;
            }

            if (var1 != null) {
               return;
            }
         }

         label68:
         try {
            this.transferFlavors = emptyFlavors;
            this.dataContentHandler = null;
            this.currentCommandMap = var1;
            return;
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label68;
         }
      }

      Throwable var8 = var10000;
      throw var8;
   }

   public void writeTo(OutputStream var1) throws IOException {
      DataSource var2 = this.dataSource;
      if (var2 == null) {
         this.getDataContentHandler().writeTo(this.object, this.objectMimeType, var1);
      } else {
         byte[] var3 = new byte[8192];
         InputStream var12 = var2.getInputStream();

         Throwable var10000;
         while(true) {
            boolean var10001;
            int var4;
            try {
               var4 = var12.read(var3);
            } catch (Throwable var10) {
               var10000 = var10;
               var10001 = false;
               break;
            }

            if (var4 <= 0) {
               var12.close();
               return;
            }

            try {
               var1.write(var3, 0, var4);
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break;
            }
         }

         Throwable var11 = var10000;
         var12.close();
         throw var11;
      }
   }
}
