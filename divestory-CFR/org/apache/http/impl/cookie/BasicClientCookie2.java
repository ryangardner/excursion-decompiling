/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.impl.cookie;

import java.io.Serializable;
import java.util.Date;
import org.apache.http.cookie.SetCookie2;
import org.apache.http.impl.cookie.BasicClientCookie;

public class BasicClientCookie2
extends BasicClientCookie
implements SetCookie2,
Serializable {
    private static final long serialVersionUID = -7744598295706617057L;
    private String commentURL;
    private boolean discard;
    private int[] ports;

    public BasicClientCookie2(String string2, String string3) {
        super(string2, string3);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        BasicClientCookie2 basicClientCookie2 = (BasicClientCookie2)super.clone();
        int[] arrn = this.ports;
        if (arrn == null) return basicClientCookie2;
        basicClientCookie2.ports = (int[])arrn.clone();
        return basicClientCookie2;
    }

    @Override
    public String getCommentURL() {
        return this.commentURL;
    }

    @Override
    public int[] getPorts() {
        return this.ports;
    }

    @Override
    public boolean isExpired(Date date) {
        if (this.discard) return true;
        if (super.isExpired(date)) return true;
        return false;
    }

    @Override
    public boolean isPersistent() {
        if (this.discard) return false;
        if (!super.isPersistent()) return false;
        return true;
    }

    @Override
    public void setCommentURL(String string2) {
        this.commentURL = string2;
    }

    @Override
    public void setDiscard(boolean bl) {
        this.discard = bl;
    }

    @Override
    public void setPorts(int[] arrn) {
        this.ports = arrn;
    }
}

