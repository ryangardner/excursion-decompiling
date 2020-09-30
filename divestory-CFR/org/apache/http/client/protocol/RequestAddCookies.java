/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.ProtocolException;
import org.apache.http.RequestLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.cookie.SetCookie2;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class RequestAddCookies
implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    @Override
    public void process(HttpRequest httpRequest, HttpContext object) throws HttpException, IOException {
        Object object6;
        Object object2;
        int n2;
        int n3;
        Object object3;
        Iterator<Header> iterator2;
        int n;
        Object object7;
        Object object4;
        Object object5;
        block19 : {
            if (httpRequest == null) throw new IllegalArgumentException("HTTP request may not be null");
            if (object == null) throw new IllegalArgumentException("HTTP context may not be null");
            if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase("CONNECT")) {
                return;
            }
            object6 = (CookieStore)object.getAttribute("http.cookie-store");
            if (object6 == null) {
                this.log.debug((Object)"Cookie store not specified in HTTP context");
                return;
            }
            object2 = (CookieSpecRegistry)object.getAttribute("http.cookiespec-registry");
            if (object2 == null) {
                this.log.debug((Object)"CookieSpec registry not specified in HTTP context");
                return;
            }
            object5 = (HttpHost)object.getAttribute("http.target_host");
            if (object5 == null) {
                this.log.debug((Object)"Target host not set in the context");
                return;
            }
            object4 = (HttpRoutedConnection)object.getAttribute("http.connection");
            if (object4 == null) {
                this.log.debug((Object)"HTTP connection not set in the context");
                return;
            }
            iterator2 = HttpClientParams.getCookiePolicy(httpRequest.getParams());
            if (this.log.isDebugEnabled()) {
                object7 = this.log;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("CookieSpec selected: ");
                ((StringBuilder)object3).append((String)((Object)iterator2));
                object7.debug((Object)((StringBuilder)object3).toString());
            }
            if (httpRequest instanceof HttpUriRequest) {
                object3 = ((HttpUriRequest)httpRequest).getURI();
            } else {
                object3 = new URI(httpRequest.getRequestLine().getUri());
            }
            object7 = ((HttpHost)object5).getHostName();
            n2 = ((HttpHost)object5).getPort();
            n3 = 0;
            n = n2;
            if (n2 < 0) {
                n = object4.getRoute().getHopCount() == 1 ? object4.getRemotePort() : (((String)(object5 = ((HttpHost)object5).getSchemeName())).equalsIgnoreCase("http") ? 80 : (((String)object5).equalsIgnoreCase("https") ? 443 : 0));
            }
            object3 = new CookieOrigin((String)object7, n, ((URI)object3).getPath(), object4.isSecure());
            object2 = ((CookieSpecRegistry)object2).getCookieSpec((String)((Object)iterator2), httpRequest.getParams());
            object4 = new ArrayList<Cookie>(object6.getCookies());
            object6 = new ArrayList();
            iterator2 = new Date();
            object5 = object4.iterator();
            break block19;
            catch (URISyntaxException uRISyntaxException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid request URI: ");
                ((StringBuilder)object).append(httpRequest.getRequestLine().getUri());
                throw new ProtocolException(((StringBuilder)object).toString(), uRISyntaxException);
            }
        }
        while (object5.hasNext()) {
            Log log;
            object4 = (Cookie)object5.next();
            if (!object4.isExpired((Date)((Object)iterator2))) {
                if (!object2.match((Cookie)object4, (CookieOrigin)object3)) continue;
                if (this.log.isDebugEnabled()) {
                    log = this.log;
                    object7 = new StringBuilder();
                    ((StringBuilder)object7).append("Cookie ");
                    ((StringBuilder)object7).append(object4);
                    ((StringBuilder)object7).append(" match ");
                    ((StringBuilder)object7).append(object3);
                    log.debug((Object)((StringBuilder)object7).toString());
                }
                object6.add(object4);
                continue;
            }
            if (!this.log.isDebugEnabled()) continue;
            log = this.log;
            object7 = new StringBuilder();
            ((StringBuilder)object7).append("Cookie ");
            ((StringBuilder)object7).append(object4);
            ((StringBuilder)object7).append(" expired");
            log.debug((Object)((StringBuilder)object7).toString());
        }
        if (!object6.isEmpty()) {
            iterator2 = object2.formatCookies((List<Cookie>)object6).iterator();
            while (iterator2.hasNext()) {
                httpRequest.addHeader(iterator2.next());
            }
        }
        if ((n2 = object2.getVersion()) > 0) {
            iterator2 = object6.iterator();
            n = n3;
            while (iterator2.hasNext()) {
                object6 = (Cookie)((Object)iterator2.next());
                if (n2 == object6.getVersion() && object6 instanceof SetCookie2) continue;
                n = 1;
            }
            if (n != 0 && (object6 = object2.getVersionHeader()) != null) {
                httpRequest.addHeader((Header)object6);
            }
        }
        object.setAttribute("http.cookie-spec", object2);
        object.setAttribute("http.cookie-origin", object3);
    }
}

