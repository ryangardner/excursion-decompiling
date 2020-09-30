/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.params;

import org.apache.http.HttpVersion;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HttpProtocolParamBean
extends HttpAbstractParamBean {
    public HttpProtocolParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setContentCharset(String string2) {
        HttpProtocolParams.setContentCharset(this.params, string2);
    }

    public void setHttpElementCharset(String string2) {
        HttpProtocolParams.setHttpElementCharset(this.params, string2);
    }

    public void setUseExpectContinue(boolean bl) {
        HttpProtocolParams.setUseExpectContinue(this.params, bl);
    }

    public void setUserAgent(String string2) {
        HttpProtocolParams.setUserAgent(this.params, string2);
    }

    public void setVersion(HttpVersion httpVersion) {
        HttpProtocolParams.setVersion(this.params, httpVersion);
    }
}

