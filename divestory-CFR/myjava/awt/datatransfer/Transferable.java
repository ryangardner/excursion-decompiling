/*
 * Decompiled with CFR <Could not determine version>.
 */
package myjava.awt.datatransfer;

import java.io.IOException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public interface Transferable {
    public Object getTransferData(DataFlavor var1) throws UnsupportedFlavorException, IOException;

    public DataFlavor[] getTransferDataFlavors();

    public boolean isDataFlavorSupported(DataFlavor var1);
}

