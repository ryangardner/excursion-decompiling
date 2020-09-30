/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import org.apache.http.NameValuePair;

public interface HeaderElement {
    public String getName();

    public NameValuePair getParameter(int var1);

    public NameValuePair getParameterByName(String var1);

    public int getParameterCount();

    public NameValuePair[] getParameters();

    public String getValue();
}

