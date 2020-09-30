/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.entity;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;

public class UrlEncodedFormEntity
extends StringEntity {
    public UrlEncodedFormEntity(List<? extends NameValuePair> list) throws UnsupportedEncodingException {
        this(list, "ISO-8859-1");
    }

    public UrlEncodedFormEntity(List<? extends NameValuePair> object, String string2) throws UnsupportedEncodingException {
        super(URLEncodedUtils.format(object, string2), string2);
        object = new StringBuilder();
        ((StringBuilder)object).append("application/x-www-form-urlencoded; charset=");
        if (string2 == null) {
            string2 = "ISO-8859-1";
        }
        ((StringBuilder)object).append(string2);
        this.setContentType(((StringBuilder)object).toString());
    }
}

