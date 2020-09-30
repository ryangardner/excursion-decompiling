/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.activation.UnsupportedDataTypeException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

class DataSourceDataContentHandler
implements DataContentHandler {
    private DataContentHandler dch = null;
    private DataSource ds = null;
    private DataFlavor[] transferFlavors = null;

    public DataSourceDataContentHandler(DataContentHandler dataContentHandler, DataSource dataSource) {
        this.ds = dataSource;
        this.dch = dataContentHandler;
    }

    @Override
    public Object getContent(DataSource dataSource) throws IOException {
        DataContentHandler dataContentHandler = this.dch;
        if (dataContentHandler == null) return dataSource.getInputStream();
        return dataContentHandler.getContent(dataSource);
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws UnsupportedFlavorException, IOException {
        DataContentHandler dataContentHandler = this.dch;
        if (dataContentHandler != null) {
            return dataContentHandler.getTransferData(dataFlavor, dataSource);
        }
        if (!dataFlavor.equals(this.getTransferDataFlavors()[0])) throw new UnsupportedFlavorException(dataFlavor);
        return dataSource.getInputStream();
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        if (this.transferFlavors != null) return this.transferFlavors;
        DataFlavor[] arrdataFlavor = this.dch;
        if (arrdataFlavor != null) {
            this.transferFlavors = arrdataFlavor.getTransferDataFlavors();
            return this.transferFlavors;
        }
        arrdataFlavor = new DataFlavor[1];
        this.transferFlavors = arrdataFlavor;
        arrdataFlavor[0] = new ActivationDataFlavor(this.ds.getContentType(), this.ds.getContentType());
        return this.transferFlavors;
    }

    @Override
    public void writeTo(Object object, String string2, OutputStream outputStream2) throws IOException {
        DataContentHandler dataContentHandler = this.dch;
        if (dataContentHandler != null) {
            dataContentHandler.writeTo(object, string2, outputStream2);
            return;
        }
        object = new StringBuilder("no DCH for content type ");
        ((StringBuilder)object).append(this.ds.getContentType());
        throw new UnsupportedDataTypeException(((StringBuilder)object).toString());
    }
}

