/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.params;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.HttpParams;

public class BasicHttpParams
extends AbstractHttpParams
implements Serializable,
Cloneable {
    private static final long serialVersionUID = -7086398485908701455L;
    private final HashMap parameters = new HashMap();

    public void clear() {
        this.parameters.clear();
    }

    public Object clone() throws CloneNotSupportedException {
        BasicHttpParams basicHttpParams = (BasicHttpParams)Object.super.clone();
        this.copyParams(basicHttpParams);
        return basicHttpParams;
    }

    @Override
    public HttpParams copy() {
        try {
            return (HttpParams)this.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new UnsupportedOperationException("Cloning not supported");
        }
    }

    protected void copyParams(HttpParams httpParams) {
        Iterator iterator2 = this.parameters.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry entry = iterator2.next();
            if (!(entry.getKey() instanceof String)) continue;
            httpParams.setParameter((String)entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Object getParameter(String string2) {
        return this.parameters.get(string2);
    }

    public boolean isParameterSet(String string2) {
        if (this.getParameter(string2) == null) return false;
        return true;
    }

    public boolean isParameterSetLocally(String string2) {
        if (this.parameters.get(string2) == null) return false;
        return true;
    }

    @Override
    public boolean removeParameter(String string2) {
        if (!this.parameters.containsKey(string2)) return false;
        this.parameters.remove(string2);
        return true;
    }

    @Override
    public HttpParams setParameter(String string2, Object object) {
        this.parameters.put(string2, object);
        return this;
    }

    public void setParameters(String[] arrstring, Object object) {
        int n = 0;
        while (n < arrstring.length) {
            this.setParameter(arrstring[n], object);
            ++n;
        }
    }
}

