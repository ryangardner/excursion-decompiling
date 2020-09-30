/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

public abstract class AbstractAuthenticationHandler
implements AuthenticationHandler {
    private static final List<String> DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList("negotiate", "NTLM", "Digest", "Basic"));
    private final Log log = LogFactory.getLog(this.getClass());

    protected List<String> getAuthPreferences() {
        return DEFAULT_SCHEME_PRIORITY;
    }

    protected List<String> getAuthPreferences(HttpResponse httpResponse, HttpContext httpContext) {
        return this.getAuthPreferences();
    }

    protected Map<String, Header> parseChallenges(Header[] arrheader) throws MalformedChallengeException {
        HashMap<String, Header> hashMap = new HashMap<String, Header>(arrheader.length);
        int n = arrheader.length;
        int n2 = 0;
        while (n2 < n) {
            CharArrayBuffer charArrayBuffer;
            int n3;
            Object object;
            int n4;
            Header header = arrheader[n2];
            if (header instanceof FormattedHeader) {
                object = (FormattedHeader)header;
                charArrayBuffer = object.getBuffer();
                n4 = object.getValuePos();
            } else {
                object = header.getValue();
                if (object == null) throw new MalformedChallengeException("Header value is null");
                charArrayBuffer = new CharArrayBuffer(((String)object).length());
                charArrayBuffer.append((String)object);
                n4 = 0;
            }
            while (n4 < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(n4))) {
                ++n4;
            }
            for (n3 = n4; n3 < charArrayBuffer.length() && !HTTP.isWhitespace(charArrayBuffer.charAt(n3)); ++n3) {
            }
            hashMap.put(charArrayBuffer.substring(n4, n3).toLowerCase(Locale.ENGLISH), header);
            ++n2;
        }
        return hashMap;
    }

    @Override
    public AuthScheme selectScheme(Map<String, Header> map, HttpResponse object, HttpContext object2) throws AuthenticationException {
        Object object3;
        Object object4;
        AuthSchemeRegistry authSchemeRegistry = (AuthSchemeRegistry)object2.getAttribute("http.authscheme-registry");
        if (authSchemeRegistry == null) throw new IllegalStateException("AuthScheme registry not set in HTTP context");
        object2 = object4 = this.getAuthPreferences((HttpResponse)object, (HttpContext)object2);
        if (object4 == null) {
            object2 = DEFAULT_SCHEME_PRIORITY;
        }
        if (this.log.isDebugEnabled()) {
            object3 = this.log;
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Authentication schemes in the order of preference: ");
            ((StringBuilder)object4).append(object2);
            object3.debug((Object)((StringBuilder)object4).toString());
        }
        object4 = null;
        object3 = object2.iterator();
        do {
            Object object5;
            object2 = object4;
            if (!object3.hasNext()) break;
            String string2 = (String)object3.next();
            if (map.get(string2.toLowerCase(Locale.ENGLISH)) != null) {
                if (this.log.isDebugEnabled()) {
                    object2 = this.log;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append(string2);
                    ((StringBuilder)object5).append(" authentication scheme selected");
                    object2.debug((Object)((StringBuilder)object5).toString());
                }
                try {
                    object2 = authSchemeRegistry.getAuthScheme(string2, object.getParams());
                    break;
                }
                catch (IllegalStateException illegalStateException) {
                    if (!this.log.isWarnEnabled()) continue;
                    object2 = this.log;
                    object5 = new StringBuilder();
                    ((StringBuilder)object5).append("Authentication scheme ");
                    ((StringBuilder)object5).append(string2);
                    ((StringBuilder)object5).append(" not supported");
                    object2.warn((Object)((StringBuilder)object5).toString());
                    continue;
                }
            }
            if (!this.log.isDebugEnabled()) continue;
            object5 = this.log;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Challenge for ");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(" authentication scheme not available");
            object5.debug((Object)((StringBuilder)object2).toString());
        } while (true);
        if (object2 != null) {
            return object2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to respond to any of these challenges: ");
        ((StringBuilder)object).append(map);
        throw new AuthenticationException(((StringBuilder)object).toString());
    }
}

