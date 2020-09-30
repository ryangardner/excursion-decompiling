/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.SetCookie;

public class BasicClientCookie
implements SetCookie,
ClientCookie,
Cloneable,
Serializable {
    private static final long serialVersionUID = -3869795591041535538L;
    private Map<String, String> attribs;
    private String cookieComment;
    private String cookieDomain;
    private Date cookieExpiryDate;
    private String cookiePath;
    private int cookieVersion;
    private boolean isSecure;
    private final String name;
    private String value;

    public BasicClientCookie(String string2, String string3) {
        if (string2 == null) throw new IllegalArgumentException("Name may not be null");
        this.name = string2;
        this.attribs = new HashMap<String, String>();
        this.value = string3;
    }

    public Object clone() throws CloneNotSupportedException {
        BasicClientCookie basicClientCookie = (BasicClientCookie)super.clone();
        basicClientCookie.attribs = new HashMap<String, String>(this.attribs);
        return basicClientCookie;
    }

    @Override
    public boolean containsAttribute(String string2) {
        if (this.attribs.get(string2) == null) return false;
        return true;
    }

    @Override
    public String getAttribute(String string2) {
        return this.attribs.get(string2);
    }

    @Override
    public String getComment() {
        return this.cookieComment;
    }

    @Override
    public String getCommentURL() {
        return null;
    }

    @Override
    public String getDomain() {
        return this.cookieDomain;
    }

    @Override
    public Date getExpiryDate() {
        return this.cookieExpiryDate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPath() {
        return this.cookiePath;
    }

    @Override
    public int[] getPorts() {
        return null;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public int getVersion() {
        return this.cookieVersion;
    }

    @Override
    public boolean isExpired(Date date) {
        if (date == null) throw new IllegalArgumentException("Date may not be null");
        Date date2 = this.cookieExpiryDate;
        if (date2 == null) return false;
        if (date2.getTime() > date.getTime()) return false;
        return true;
    }

    @Override
    public boolean isPersistent() {
        if (this.cookieExpiryDate == null) return false;
        return true;
    }

    @Override
    public boolean isSecure() {
        return this.isSecure;
    }

    public void setAttribute(String string2, String string3) {
        this.attribs.put(string2, string3);
    }

    @Override
    public void setComment(String string2) {
        this.cookieComment = string2;
    }

    @Override
    public void setDomain(String string2) {
        if (string2 != null) {
            this.cookieDomain = string2.toLowerCase(Locale.ENGLISH);
            return;
        }
        this.cookieDomain = null;
    }

    @Override
    public void setExpiryDate(Date date) {
        this.cookieExpiryDate = date;
    }

    @Override
    public void setPath(String string2) {
        this.cookiePath = string2;
    }

    @Override
    public void setSecure(boolean bl) {
        this.isSecure = bl;
    }

    @Override
    public void setValue(String string2) {
        this.value = string2;
    }

    @Override
    public void setVersion(int n) {
        this.cookieVersion = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[version: ");
        stringBuilder.append(Integer.toString(this.cookieVersion));
        stringBuilder.append("]");
        stringBuilder.append("[name: ");
        stringBuilder.append(this.name);
        stringBuilder.append("]");
        stringBuilder.append("[value: ");
        stringBuilder.append(this.value);
        stringBuilder.append("]");
        stringBuilder.append("[domain: ");
        stringBuilder.append(this.cookieDomain);
        stringBuilder.append("]");
        stringBuilder.append("[path: ");
        stringBuilder.append(this.cookiePath);
        stringBuilder.append("]");
        stringBuilder.append("[expiry: ");
        stringBuilder.append(this.cookieExpiryDate);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

