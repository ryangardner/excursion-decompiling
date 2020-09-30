/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.params;

import java.util.Collection;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionManagerFactory;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

public class ClientParamBean
extends HttpAbstractParamBean {
    public ClientParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setAllowCircularRedirects(boolean bl) {
        this.params.setBooleanParameter("http.protocol.allow-circular-redirects", bl);
    }

    @Deprecated
    public void setConnectionManagerFactory(ClientConnectionManagerFactory clientConnectionManagerFactory) {
        this.params.setParameter("http.connection-manager.factory-object", clientConnectionManagerFactory);
    }

    public void setConnectionManagerFactoryClassName(String string2) {
        this.params.setParameter("http.connection-manager.factory-class-name", string2);
    }

    public void setCookiePolicy(String string2) {
        this.params.setParameter("http.protocol.cookie-policy", string2);
    }

    public void setDefaultHeaders(Collection<Header> collection) {
        this.params.setParameter("http.default-headers", collection);
    }

    public void setDefaultHost(HttpHost httpHost) {
        this.params.setParameter("http.default-host", httpHost);
    }

    public void setHandleAuthentication(boolean bl) {
        this.params.setBooleanParameter("http.protocol.handle-authentication", bl);
    }

    public void setHandleRedirects(boolean bl) {
        this.params.setBooleanParameter("http.protocol.handle-redirects", bl);
    }

    public void setMaxRedirects(int n) {
        this.params.setIntParameter("http.protocol.max-redirects", n);
    }

    public void setRejectRelativeRedirect(boolean bl) {
        this.params.setBooleanParameter("http.protocol.reject-relative-redirect", bl);
    }

    public void setVirtualHost(HttpHost httpHost) {
        this.params.setParameter("http.virtual-host", httpHost);
    }
}

