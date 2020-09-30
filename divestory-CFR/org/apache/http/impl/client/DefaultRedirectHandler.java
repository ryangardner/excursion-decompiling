/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.client;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Deprecated
public class DefaultRedirectHandler
implements RedirectHandler {
    private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public URI getLocationURI(HttpResponse object, HttpContext object2) throws ProtocolException {
        Comparable<StringBuilder> comparable;
        if (object == null) throw new IllegalArgumentException("HTTP response may not be null");
        Object object3 = object.getFirstHeader("location");
        if (object3 == null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Received redirect response ");
            ((StringBuilder)object2).append(object.getStatusLine());
            ((StringBuilder)object2).append(" but no location header");
            throw new ProtocolException(((StringBuilder)object2).toString());
        }
        Object object4 = object3.getValue();
        if (this.log.isDebugEnabled()) {
            object3 = this.log;
            comparable = new StringBuilder();
            comparable.append("Redirect requested to location '");
            comparable.append((String)object4);
            comparable.append("'");
            object3.debug((Object)comparable.toString());
        }
        try {
            object3 = new URI((String)object4);
        }
        catch (URISyntaxException uRISyntaxException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid redirect URI: ");
            ((StringBuilder)object).append((String)object4);
            throw new ProtocolException(((StringBuilder)object).toString(), uRISyntaxException);
        }
        object4 = object.getParams();
        object = object3;
        if (!((URI)object3).isAbsolute()) {
            if (object4.isParameterTrue("http.protocol.reject-relative-redirect")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Relative redirect location '");
                ((StringBuilder)object).append(object3);
                ((StringBuilder)object).append("' not allowed");
                throw new ProtocolException(((StringBuilder)object).toString());
            }
            HttpHost httpHost = (HttpHost)object2.getAttribute("http.target_host");
            if (httpHost == null) throw new IllegalStateException("Target host not available in the HTTP context");
            object = (HttpRequest)object2.getAttribute("http.request");
            try {
                comparable = new URI(object.getRequestLine().getUri());
                object = URIUtils.resolve(URIUtils.rewriteURI((URI)comparable, httpHost, true), (URI)object3);
            }
            catch (URISyntaxException uRISyntaxException) {
                throw new ProtocolException(uRISyntaxException.getMessage(), uRISyntaxException);
            }
        }
        if (!object4.isParameterFalse("http.protocol.allow-circular-redirects")) return object;
        object3 = object4 = (RedirectLocations)object2.getAttribute(REDIRECT_LOCATIONS);
        if (object4 == null) {
            object3 = new RedirectLocations();
            object2.setAttribute(REDIRECT_LOCATIONS, object3);
        }
        if (((URI)object).getFragment() != null) {
            try {
                object2 = new HttpHost(((URI)object).getHost(), ((URI)object).getPort(), ((URI)object).getScheme());
                object2 = URIUtils.rewriteURI((URI)object, (HttpHost)object2, true);
            }
            catch (URISyntaxException uRISyntaxException) {
                throw new ProtocolException(uRISyntaxException.getMessage(), uRISyntaxException);
            }
        } else {
            object2 = object;
        }
        if (!((RedirectLocations)object3).contains((URI)object2)) {
            ((RedirectLocations)object3).add((URI)object2);
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Circular redirect to '");
        ((StringBuilder)object).append(object2);
        ((StringBuilder)object).append("'");
        throw new CircularRedirectException(((StringBuilder)object).toString());
    }

    @Override
    public boolean isRedirectRequested(HttpResponse object, HttpContext httpContext) {
        if (object == null) throw new IllegalArgumentException("HTTP response may not be null");
        int n = object.getStatusLine().getStatusCode();
        boolean bl = false;
        if (n != 307) {
            switch (n) {
                default: {
                    return false;
                }
                case 303: {
                    return true;
                }
                case 301: 
                case 302: 
            }
        }
        if (((String)(object = ((HttpRequest)httpContext.getAttribute("http.request")).getRequestLine().getMethod())).equalsIgnoreCase("GET")) return true;
        if (!((String)object).equalsIgnoreCase("HEAD")) return bl;
        return true;
    }
}

