package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

class DataSourceDataContentHandler implements DataContentHandler {
   private DataContentHandler dch = null;
   private DataSource ds = null;
   private DataFlavor[] transferFlavors = null;

   public DataSourceDataContentHandler(DataContentHandler var1, DataSource var2) {
      this.ds = var2;
      this.dch = var1;
   }

   public Object getContent(DataSource var1) throws IOException {
      DataContentHandler var2 = this.dch;
      return var2 != null ? var2.getContent(var1) : var1.getInputStream();
   }

   public Object getTransferData(DataFlavor var1, DataSource var2) throws UnsupportedFlavorException, IOException {
      DataContentHandler var3 = this.dch;
      if (var3 != null) {
         return var3.getTransferData(var1, var2);
      } else if (var1.equals(this.getTransferDataFlavors()[0])) {
         return var2.getInputStream();
      } else {
         throw new UnsupportedFlavorException(var1);
      }
   }

   public DataFlavor[] getTransferDataFlavors() {
      if (this.transferFlavors == null) {
         DataContentHandler var1 = this.dch;
         if (var1 != null) {
            this.transferFlavors = var1.getTransferDataFlavors();
         } else {
            DataFlavor[] var2 = new DataFlavor[1];
            this.transferFlavors = var2;
            var2[0] = new ActivationDataFlavor(this.ds.getContentType(), this.ds.getContentType());
         }
      }

      return this.transferFlavors;
   }

   public void writeTo(Object var1, String var2, OutputStream var3) throws IOException {
      DataContentHandler var4 = this.dch;
      if (var4 != null) {
         var4.writeTo(var1, var2, var3);
      } else {
         StringBuilder var5 = new StringBuilder("no DCH for content type ");
         var5.append(this.ds.getContentType());
         throw new UnsupportedDataTypeException(var5.toString());
      }
   }
}
