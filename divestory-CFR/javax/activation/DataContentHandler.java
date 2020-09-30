/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import javax.activation.DataSource;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

public interface DataContentHandler {
    public Object getContent(DataSource var1) throws IOException;

    public Object getTransferData(DataFlavor var1, DataSource var2) throws UnsupportedFlavorException, IOException;

    public DataFlavor[] getTransferDataFlavors();

    public void writeTo(Object var1, String var2, OutputStream var3) throws IOException;
}

