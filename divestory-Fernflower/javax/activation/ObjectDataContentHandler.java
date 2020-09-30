package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

class ObjectDataContentHandler implements DataContentHandler {
   private DataContentHandler dch = null;
   private String mimeType;
   private Object obj;
   private DataFlavor[] transferFlavors = null;

   public ObjectDataContentHandler(DataContentHandler var1, Object var2, String var3) {
      this.obj = var2;
      this.mimeType = var3;
      this.dch = var1;
   }

   public Object getContent(DataSource var1) {
      return this.obj;
   }

   public DataContentHandler getDCH() {
      return this.dch;
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws UnsupportedFlavorException, IOException {
      DataContentHandler var3 = this.dch;
      if (var3 != null) {
         return var3.getTransferData(var1, var2);
      } else if (var1.equals(this.getTransferDataFlavors()[0])) {
         return this.obj;
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   public DataFlavor[] getTransferDataFlavors() {
      synchronized(this){}

      Throwable var10000;
      label141: {
         boolean var10001;
         DataFlavor[] var1;
         label148: {
            label138:
            try {
               if (this.transferFlavors == null) {
                  if (this.dch == null) {
                     break label138;
                  }

                  this.transferFlavors = this.dch.getTransferDataFlavors();
               }
               break label148;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label141;
            }

            try {
               var1 = new DataFlavor[1];
               this.transferFlavors = var1;
               var1[0] = new ActivationDataFlavor(this.obj.getClass(), this.mimeType, this.mimeType);
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label141;
            }
         }

         label129:
         try {
            var1 = this.transferFlavors;
            return var1;
         } catch (Throwable var11) {
            var10000 = var11;
            var10001 = false;
            break label129;
         }
      }

      Throwable var14 = var10000;
      throw var14;
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      DataContentHandler var4 = this.dch;
      if (var4 != null) {
         var4.writeTo(var1, var2, var3);
      } else if (var1 instanceof byte[]) {
         var3.write((byte[])var1);
      } else {
         if (!(var1 instanceof String)) {
            StringBuilder var5 = new StringBuilder("no object DCH for MIME type ");
            var5.append(this.mimeType);
            throw new UnsupportedDataTypeException(var5.toString());
         }

         OutputStreamWriter var6 = new OutputStreamWriter(var3);
         var6.write((String)var1);
         var6.flush();
      }

   }
}
