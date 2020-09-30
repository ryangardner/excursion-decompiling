/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import java.io.Serializable;
import org.apache.http.ProtocolVersion;

public final class HttpVersion
extends ProtocolVersion
implements Serializable {
    public static final String HTTP = "HTTP";
    public static final HttpVersion HTTP_0_9 = new HttpVersion(0, 9);
    public static final HttpVersion HTTP_1_0 = new HttpVersion(1, 0);
    public static final HttpVersion HTTP_1_1 = new HttpVersion(1, 1);
    private static final long serialVersionUID = -5856653513894415344L;

    public HttpVersion(int n, int n2) {
        super(HTTP, n, n2);
    }

    @Override
    public ProtocolVersion forVersion(int n, int n2) {
        if (n == this.major && n2 == this.minor) {
            return this;
        }
        if (n == 1) {
            if (n2 == 0) {
                return HTTP_1_0;
            }
            if (n2 == 1) {
                return HTTP_1_1;
            }
        }
        if (n != 0) return new HttpVersion(n, n2);
        if (n2 != 9) return new HttpVersion(n, n2);
        return HTTP_0_9;
    }
}

