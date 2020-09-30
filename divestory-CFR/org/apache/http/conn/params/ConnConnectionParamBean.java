/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.conn.params;

import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

public class ConnConnectionParamBean
extends HttpAbstractParamBean {
    public ConnConnectionParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setMaxStatusLineGarbage(int n) {
        this.params.setIntParameter("http.connection.max-status-line-garbage", n);
    }
}

