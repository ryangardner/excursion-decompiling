/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.params;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class SyncBasicHttpParams
extends BasicHttpParams {
    private static final long serialVersionUID = 5387834869062660642L;

    @Override
    public void clear() {
        synchronized (this) {
            super.clear();
            return;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        synchronized (this) {
            return super.clone();
        }
    }

    @Override
    public Object getParameter(String object) {
        synchronized (this) {
            return super.getParameter((String)object);
        }
    }

    @Override
    public boolean isParameterSet(String string2) {
        synchronized (this) {
            return super.isParameterSet(string2);
        }
    }

    @Override
    public boolean isParameterSetLocally(String string2) {
        synchronized (this) {
            return super.isParameterSetLocally(string2);
        }
    }

    @Override
    public boolean removeParameter(String string2) {
        synchronized (this) {
            return super.removeParameter(string2);
        }
    }

    @Override
    public HttpParams setParameter(String object, Object object2) {
        synchronized (this) {
            return super.setParameter((String)object, object2);
        }
    }

    @Override
    public void setParameters(String[] arrstring, Object object) {
        synchronized (this) {
            super.setParameters(arrstring, object);
            return;
        }
    }
}

