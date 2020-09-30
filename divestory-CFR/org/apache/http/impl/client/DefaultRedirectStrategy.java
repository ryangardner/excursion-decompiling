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
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class DefaultRedirectStrategy
implements RedirectStrategy {
    public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    private final Log log = LogFactory.getLog(this.getClass());

    protected URI createLocationURI(String string2) throws ProtocolException {
        try {
            return new URI(string2);
        }
        catch (URISyntaxException uRISyntaxException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid redirect URI: ");
            stringBuilder.append(string2);
            throw new ProtocolException(stringBuilder.toString(), uRISyntaxException);
        }
    }

    public URI getLocationURI(HttpRequest object, HttpResponse object2, HttpContext object3) throws ProtocolException {
        Object object4;
        if (object2 == null) throw new IllegalArgumentException("HTTP response may not be null");
        Object object5 = object2.getFirstHeader("location");
        if (object5 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Received redirect response ");
            ((StringBuilder)object).append(object2.getStatusLine());
            ((StringBuilder)object).append(" but no location header");
            throw new ProtocolException(((StringBuilder)object).toString());
        }
        Object object6 = object5.getValue();
        if (this.log.isDebugEnabled()) {
            object5 = this.log;
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("Redirect requested to location '");
            ((StringBuilder)object4).append((String)object6);
            ((StringBuilder)object4).append("'");
            object5.debug((Object)((StringBuilder)object4).toString());
        }
        object5 = this.createLocationURI((String)object6);
        object4 = object2.getParams();
        object2 = object5;
        if (!((URI)object5).isAbsolute()) {
            if (object4.isParameterTrue("http.protocol.reject-relative-redirect")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Relative redirect location '");
                ((StringBuilder)object).append(object5);
                ((StringBuilder)object).append("' not allowed");
                throw new ProtocolException(((StringBuilder)object).toString());
            }
            object2 = (HttpHost)object3.getAttribute("http.target_host");
            if (object2 == null) throw new IllegalStateException("Target host not available in the HTTP context");
            try {
                object6 = new URI(object.getRequestLine().getUri());
                object2 = URIUtils.resolve(URIUtils.rewriteURI((URI)object6, (HttpHost)object2, true), (URI)object5);
            }
            catch (URISyntaxException uRISyntaxException) {
                throw new ProtocolException(uRISyntaxException.getMessage(), uRISyntaxException);
            }
        }
        if (!object4.isParameterFalse("http.protocol.allow-circular-redirects")) return object2;
        object = object5 = (RedirectLocations)object3.getAttribute(REDIRECT_LOCATIONS);
        if (object5 == null) {
            object = new RedirectLocations();
            object3.setAttribute(REDIRECT_LOCATIONS, object);
        }
        if (((URI)object2).getFragment() != null) {
            try {
                object3 = new HttpHost(((URI)object2).getHost(), ((URI)object2).getPort(), ((URI)object2).getScheme());
                object3 = URIUtils.rewriteURI((URI)object2, (HttpHost)object3, true);
            }
            catch (URISyntaxException uRISyntaxException) {
                throw new ProtocolException(uRISyntaxException.getMessage(), uRISyntaxException);
            }
        } else {
            object3 = object2;
        }
        if (!((RedirectLocations)object).contains((URI)object3)) {
            ((RedirectLocations)object).add((URI)object3);
            return object2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Circular redirect to '");
        ((StringBuilder)object).append(object3);
        ((StringBuilder)object).append("'");
        throw new CircularRedirectException(((StringBuilder)object).toString());
    }

    @Override
    public HttpUriRequest getRedirect(HttpRequest httpRequest, HttpResponse object, HttpContext httpContext) throws ProtocolException {
        object = this.getLocationURI(httpRequest, (HttpResponse)object, httpContext);
        if (!httpRequest.getRequestLine().getMethod().equalsIgnoreCase("HEAD")) return new HttpGet((URI)object);
        return new HttpHead((URI)object);
    }

    @Override
    public boolean isRedirected(HttpRequest object, HttpResponse object2, HttpContext httpContext) throws ProtocolException {
        if (object2 == null) throw new IllegalArgumentException("HTTP response may not be null");
        int n = object2.getStatusLine().getStatusCode();
        object = object.getRequestLine().getMethod();
        object2 = object2.getFirstHeader("location");
        boolean bl = false;
        boolean bl2 = false;
        if (n != 307) {
            switch (n) {
                default: {
                    return false;
                }
                case 303: {
                    return true;
                }
                case 302: {
                    if (!((String)object).equalsIgnoreCase("GET")) {
                        bl = bl2;
                        if (!((String)object).equalsIgnoreCase("HEAD")) return bl;
                    }
                    bl = bl2;
                    if (object2 == null) return bl;
                    return true;
                }
                case 301: 
            }
        }
        if (((String)object).equalsIgnoreCase("GET")) return true;
        if (!((String)object).equalsIgnoreCase("HEAD")) return bl;
        return true;
    }
}

