/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.params;

import org.apache.http.params.HttpParams;

public abstract class AbstractHttpParams
implements HttpParams {
    protected AbstractHttpParams() {
    }

    @Override
    public boolean getBooleanParameter(String object, boolean bl) {
        if ((object = this.getParameter((String)object)) != null) return (Boolean)object;
        return bl;
    }

    @Override
    public double getDoubleParameter(String object, double d) {
        if ((object = this.getParameter((String)object)) != null) return (Double)object;
        return d;
    }

    @Override
    public int getIntParameter(String object, int n) {
        if ((object = this.getParameter((String)object)) != null) return (Integer)object;
        return n;
    }

    @Override
    public long getLongParameter(String object, long l) {
        if ((object = this.getParameter((String)object)) != null) return (Long)object;
        return l;
    }

    @Override
    public boolean isParameterFalse(String string2) {
        return this.getBooleanParameter(string2, false) ^ true;
    }

    @Override
    public boolean isParameterTrue(String string2) {
        return this.getBooleanParameter(string2, false);
    }

    @Override
    public HttpParams setBooleanParameter(String string2, boolean bl) {
        Boolean bl2 = bl ? Boolean.TRUE : Boolean.FALSE;
        this.setParameter(string2, bl2);
        return this;
    }

    @Override
    public HttpParams setDoubleParameter(String string2, double d) {
        this.setParameter(string2, new Double(d));
        return this;
    }

    @Override
    public HttpParams setIntParameter(String string2, int n) {
        this.setParameter(string2, new Integer(n));
        return this;
    }

    @Override
    public HttpParams setLongParameter(String string2, long l) {
        this.setParameter(string2, new Long(l));
        return this;
    }
}

