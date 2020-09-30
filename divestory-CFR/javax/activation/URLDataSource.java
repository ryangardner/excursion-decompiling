/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.activation.DataSource;

public class URLDataSource
implements DataSource {
    private URL url = null;
    private URLConnection url_conn = null;

    public URLDataSource(URL uRL) {
        this.url = uRL;
    }

    @Override
    public String getContentType() {
        Object object;
        try {
            if (this.url_conn == null) {
                this.url_conn = this.url.openConnection();
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        object = (object = this.url_conn) != null ? ((URLConnection)object).getContentType() : null;
        Object object2 = object;
        if (object != null) return object2;
        return "application/octet-stream";
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.url.openStream();
    }

    @Override
    public String getName() {
        return this.url.getFile();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        URLConnection uRLConnection;
        this.url_conn = uRLConnection = this.url.openConnection();
        if (uRLConnection == null) return null;
        uRLConnection.setDoOutput(true);
        return this.url_conn.getOutputStream();
    }

    public URL getURL() {
        return this.url;
    }
}

