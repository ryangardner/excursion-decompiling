/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import org.apache.http.HeaderElement;
import org.apache.http.ParseException;

public interface Header {
    public HeaderElement[] getElements() throws ParseException;

    public String getName();

    public String getValue();
}

