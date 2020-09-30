/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.params;

import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
public class ConnManagerParamBean
extends HttpAbstractParamBean {
    public ConnManagerParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    @Deprecated
    public void setConnectionsPerRoute(ConnPerRouteBean connPerRouteBean) {
        this.params.setParameter("http.conn-manager.max-per-route", connPerRouteBean);
    }

    @Deprecated
    public void setMaxTotalConnections(int n) {
        this.params.setIntParameter("http.conn-manager.max-total", n);
    }

    public void setTimeout(long l) {
        this.params.setLongParameter("http.conn-manager.timeout", l);
    }
}

