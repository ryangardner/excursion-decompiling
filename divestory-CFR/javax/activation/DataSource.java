/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.activation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface DataSource {
    public String getContentType();

    public InputStream getInputStream() throws IOException;

    public String getName();

    public OutputStream getOutputStream() throws IOException;
}

