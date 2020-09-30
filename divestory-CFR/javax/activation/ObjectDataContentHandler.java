/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataContentHandler;
import javax.activation.DataSource;
import javax.activation.UnsupportedDataTypeException;
import myjava.awt.datatransfer.DataFlavor;
import myjava.awt.datatransfer.UnsupportedFlavorException;

class ObjectDataContentHandler
implements DataContentHandler {
    private DataContentHandler dch = null;
    private String mimeType;
    private Object obj;
    private DataFlavor[] transferFlavors = null;

    public ObjectDataContentHandler(DataContentHandler dataContentHandler, Object object, String string2) {
        this.obj = object;
        this.mimeType = string2;
        this.dch = dataContentHandler;
    }

    @Override
    public Object getContent(DataSource dataSource) {
        return this.obj;
    }

    public DataContentHandler getDCH() {
        return this.dch;
    }

    @Override
    public Object getTransferData(DataFlavor dataFlavor, DataSource dataSource) throws UnsupportedFlavorException, IOException {
        DataContentHandler dataContentHandler = this.dch;
        if (dataContentHandler != null) {
            return dataContentHandler.getTransferData(dataFlavor, dataSource);
        }
        if (!dataFlavor.equals(this.getTransferDataFlavors()[0])) throw new UnsupportedFlavorException(dataFlavor);
        return this.obj;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        synchronized (this) {
            if (this.transferFlavors != null) return this.transferFlavors;
            if (this.dch != null) {
                this.transferFlavors = this.dch.getTransferDataFlavors();
                return this.transferFlavors;
            } else {
                DataFlavor[] arrdataFlavor = new DataFlavor[1];
                this.transferFlavors = arrdataFlavor;
                arrdataFlavor[0] = new ActivationDataFlavor(this.obj.getClass(), this.mimeType, this.mimeType);
            }
            return this.transferFlavors;
        }
    }

    @Override
    public void writeTo(Object object, String object2, OutputStream outputStream2) throws IOException {
        DataContentHandler dataContentHandler = this.dch;
        if (dataContentHandler != null) {
            dataContentHandler.writeTo(object, (String)object2, outputStream2);
            return;
        }
        if (object instanceof byte[]) {
            outputStream2.write((byte[])object);
            return;
        }
        if (object instanceof String) {
            object2 = new OutputStreamWriter(outputStream2);
            ((Writer)object2).write((String)object);
            ((OutputStreamWriter)object2).flush();
            return;
        }
        object = new StringBuilder("no object DCH for MIME type ");
        ((StringBuilder)object).append(this.mimeType);
        throw new UnsupportedDataTypeException(((StringBuilder)object).toString());
    }
}

