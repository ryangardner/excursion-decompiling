/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;

public interface HttpResponse
extends HttpMessage {
    public HttpEntity getEntity();

    public Locale getLocale();

    public StatusLine getStatusLine();

    public void setEntity(HttpEntity var1);

    public void setLocale(Locale var1);

    public void setReasonPhrase(String var1) throws IllegalStateException;

    public void setStatusCode(int var1) throws IllegalStateException;

    public void setStatusLine(ProtocolVersion var1, int var2);

    public void setStatusLine(ProtocolVersion var1, int var2, String var3);

    public void setStatusLine(StatusLine var1);
}
