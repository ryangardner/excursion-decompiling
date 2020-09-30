package myjava.awt.datatransfer;

import java.io.IOException;

public interface Transferable {
   Object getTransferData(DataFlavor var1) throws UnsupportedFlavorException, IOException;

   DataFlavor[] getTransferDataFlavors();

   boolean isDataFlavorSupported(DataFlavor var1);
}
