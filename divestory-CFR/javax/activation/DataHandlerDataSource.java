/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataHandler;
import javax.activation.DataSource;

class DataHandlerDataSource
implements DataSource {
    DataHandler dataHandler = null;

    public DataHandlerDataSource(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    @Override
    public String getContentType() {
        return this.dataHandler.getContentType();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.dataHandler.getInputStream();
    }

    @Override
    public String getName() {
        return this.dataHandler.getName();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.dataHandler.getOutputStream();
    }
}

