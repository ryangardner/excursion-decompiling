/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.methods;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

public class HttpOptions
extends HttpRequestBase {
    public static final String METHOD_NAME = "OPTIONS";

    public HttpOptions() {
    }

    public HttpOptions(String string2) {
        this.setURI(URI.create(string2));
    }

    public HttpOptions(URI uRI) {
        this.setURI(uRI);
    }

    public Set<String> getAllowedMethods(HttpResponse object) {
        if (object == null) throw new IllegalArgumentException("HTTP response may not be null");
        object = object.headerIterator("Allow");
        HashSet<String> hashSet = new HashSet<String>();
        block0 : while (object.hasNext()) {
            HeaderElement[] arrheaderElement = object.nextHeader().getElements();
            int n = arrheaderElement.length;
            int n2 = 0;
            do {
                if (n2 >= n) continue block0;
                hashSet.add(arrheaderElement[n2].getName());
                ++n2;
            } while (true);
            break;
        }
        return hashSet;
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}

