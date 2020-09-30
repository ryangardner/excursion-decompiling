/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.auth.params;

import org.apache.http.auth.params.AuthParams;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

public class AuthParamBean
extends HttpAbstractParamBean {
    public AuthParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setCredentialCharset(String string2) {
        AuthParams.setCredentialCharset(this.params, string2);
    }
}

