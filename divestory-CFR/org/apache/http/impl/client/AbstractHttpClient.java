/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionManagerFactory;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.auth.NegotiateSchemeFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.DefaultProxyAuthenticationHandler;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategyAdaptor;
import org.apache.http.impl.client.DefaultRequestDirector;
import org.apache.http.impl.client.DefaultTargetAuthenticationHandler;
import org.apache.http.impl.client.DefaultUserTokenHandler;
import org.apache.http.impl.conn.DefaultHttpRoutePlanner;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.impl.cookie.IgnoreSpecFactory;
import org.apache.http.impl.cookie.NetscapeDraftSpecFactory;
import org.apache.http.impl.cookie.RFC2109SpecFactory;
import org.apache.http.impl.cookie.RFC2965SpecFactory;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.DefaultedHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.util.EntityUtils;

public abstract class AbstractHttpClient
implements HttpClient {
    private ClientConnectionManager connManager;
    private CookieStore cookieStore;
    private CredentialsProvider credsProvider;
    private HttpParams defaultParams;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log = LogFactory.getLog(this.getClass());
    private BasicHttpProcessor mutableProcessor;
    private ImmutableHttpProcessor protocolProcessor;
    private AuthenticationHandler proxyAuthHandler;
    private RedirectStrategy redirectStrategy;
    private HttpRequestExecutor requestExec;
    private HttpRequestRetryHandler retryHandler;
    private ConnectionReuseStrategy reuseStrategy;
    private HttpRoutePlanner routePlanner;
    private AuthSchemeRegistry supportedAuthSchemes;
    private CookieSpecRegistry supportedCookieSpecs;
    private AuthenticationHandler targetAuthHandler;
    private UserTokenHandler userTokenHandler;

    protected AbstractHttpClient(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        this.defaultParams = httpParams;
        this.connManager = clientConnectionManager;
    }

    private static HttpHost determineTarget(HttpUriRequest object) throws ClientProtocolException {
        URI uRI = object.getURI();
        if (!uRI.isAbsolute()) {
            return null;
        }
        object = URIUtils.extractHost(uRI);
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("URI does not specify a valid host name: ");
        ((StringBuilder)object).append(uRI);
        throw new ClientProtocolException(((StringBuilder)object).toString());
    }

    private final HttpProcessor getProtocolProcessor() {
        synchronized (this) {
            int n;
            if (this.protocolProcessor != null) return this.protocolProcessor;
            HttpProcessor httpProcessor = this.getHttpProcessor();
            int n2 = httpProcessor.getRequestInterceptorCount();
            HttpRequestInterceptor[] arrhttpRequestInterceptor = new HttpRequestInterceptor[n2];
            int n3 = 0;
            for (n = 0; n < n2; ++n) {
                arrhttpRequestInterceptor[n] = httpProcessor.getRequestInterceptor(n);
            }
            n2 = httpProcessor.getResponseInterceptorCount();
            HttpResponseInterceptor[] arrhttpResponseInterceptor = new HttpResponseInterceptor[n2];
            for (n = n3; n < n2; ++n) {
                arrhttpResponseInterceptor[n] = httpProcessor.getResponseInterceptor(n);
            }
            httpProcessor = new ImmutableHttpProcessor(arrhttpRequestInterceptor, arrhttpResponseInterceptor);
            this.protocolProcessor = httpProcessor;
            return this.protocolProcessor;
        }
    }

    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor) {
        synchronized (this) {
            this.getHttpProcessor().addInterceptor(httpRequestInterceptor);
            this.protocolProcessor = null;
            return;
        }
    }

    public void addRequestInterceptor(HttpRequestInterceptor httpRequestInterceptor, int n) {
        synchronized (this) {
            this.getHttpProcessor().addInterceptor(httpRequestInterceptor, n);
            this.protocolProcessor = null;
            return;
        }
    }

    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor) {
        synchronized (this) {
            this.getHttpProcessor().addInterceptor(httpResponseInterceptor);
            this.protocolProcessor = null;
            return;
        }
    }

    public void addResponseInterceptor(HttpResponseInterceptor httpResponseInterceptor, int n) {
        synchronized (this) {
            this.getHttpProcessor().addInterceptor(httpResponseInterceptor, n);
            this.protocolProcessor = null;
            return;
        }
    }

    public void clearRequestInterceptors() {
        synchronized (this) {
            this.getHttpProcessor().clearRequestInterceptors();
            this.protocolProcessor = null;
            return;
        }
    }

    public void clearResponseInterceptors() {
        synchronized (this) {
            this.getHttpProcessor().clearResponseInterceptors();
            this.protocolProcessor = null;
            return;
        }
    }

    protected AuthSchemeRegistry createAuthSchemeRegistry() {
        AuthSchemeRegistry authSchemeRegistry = new AuthSchemeRegistry();
        authSchemeRegistry.register("Basic", new BasicSchemeFactory());
        authSchemeRegistry.register("Digest", new DigestSchemeFactory());
        authSchemeRegistry.register("NTLM", new NTLMSchemeFactory());
        authSchemeRegistry.register("negotiate", new NegotiateSchemeFactory());
        return authSchemeRegistry;
    }

    protected ClientConnectionManager createClientConnectionManager() {
        Object object;
        SchemeRegistry schemeRegistry = SchemeRegistryFactory.createDefault();
        HttpParams httpParams = this.getParams();
        String string2 = (String)httpParams.getParameter("http.connection-manager.factory-class-name");
        if (string2 != null) {
            try {
                object = (ClientConnectionManagerFactory)Class.forName(string2).newInstance();
            }
            catch (InstantiationException instantiationException) {
                throw new InstantiationError(instantiationException.getMessage());
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalAccessError(illegalAccessException.getMessage());
            }
            catch (ClassNotFoundException classNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid class name: ");
                stringBuilder.append(string2);
                throw new IllegalStateException(stringBuilder.toString());
            }
        } else {
            object = null;
        }
        if (object == null) return new SingleClientConnManager(schemeRegistry);
        return object.newInstance(httpParams, schemeRegistry);
    }

    @Deprecated
    protected RequestDirector createClientRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectHandler redirectHandler, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        return new DefaultRequestDirector(httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectHandler, authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
    }

    protected RequestDirector createClientRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectStrategy redirectStrategy, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        return new DefaultRequestDirector(this.log, httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectStrategy, authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
    }

    protected ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
        return new DefaultConnectionKeepAliveStrategy();
    }

    protected ConnectionReuseStrategy createConnectionReuseStrategy() {
        return new DefaultConnectionReuseStrategy();
    }

    protected CookieSpecRegistry createCookieSpecRegistry() {
        CookieSpecRegistry cookieSpecRegistry = new CookieSpecRegistry();
        cookieSpecRegistry.register("best-match", new BestMatchSpecFactory());
        cookieSpecRegistry.register("compatibility", new BrowserCompatSpecFactory());
        cookieSpecRegistry.register("netscape", new NetscapeDraftSpecFactory());
        cookieSpecRegistry.register("rfc2109", new RFC2109SpecFactory());
        cookieSpecRegistry.register("rfc2965", new RFC2965SpecFactory());
        cookieSpecRegistry.register("ignoreCookies", new IgnoreSpecFactory());
        return cookieSpecRegistry;
    }

    protected CookieStore createCookieStore() {
        return new BasicCookieStore();
    }

    protected CredentialsProvider createCredentialsProvider() {
        return new BasicCredentialsProvider();
    }

    protected HttpContext createHttpContext() {
        BasicHttpContext basicHttpContext = new BasicHttpContext();
        basicHttpContext.setAttribute("http.scheme-registry", this.getConnectionManager().getSchemeRegistry());
        basicHttpContext.setAttribute("http.authscheme-registry", this.getAuthSchemes());
        basicHttpContext.setAttribute("http.cookiespec-registry", this.getCookieSpecs());
        basicHttpContext.setAttribute("http.cookie-store", this.getCookieStore());
        basicHttpContext.setAttribute("http.auth.credentials-provider", this.getCredentialsProvider());
        return basicHttpContext;
    }

    protected abstract HttpParams createHttpParams();

    protected abstract BasicHttpProcessor createHttpProcessor();

    protected HttpRequestRetryHandler createHttpRequestRetryHandler() {
        return new DefaultHttpRequestRetryHandler();
    }

    protected HttpRoutePlanner createHttpRoutePlanner() {
        return new DefaultHttpRoutePlanner(this.getConnectionManager().getSchemeRegistry());
    }

    protected AuthenticationHandler createProxyAuthenticationHandler() {
        return new DefaultProxyAuthenticationHandler();
    }

    @Deprecated
    protected RedirectHandler createRedirectHandler() {
        return new DefaultRedirectHandler();
    }

    protected HttpRequestExecutor createRequestExecutor() {
        return new HttpRequestExecutor();
    }

    protected AuthenticationHandler createTargetAuthenticationHandler() {
        return new DefaultTargetAuthenticationHandler();
    }

    protected UserTokenHandler createUserTokenHandler() {
        return new DefaultUserTokenHandler();
    }

    protected HttpParams determineParams(HttpRequest httpRequest) {
        return new ClientParamsStack(null, this.getParams(), httpRequest.getParams(), null);
    }

    @Override
    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.execute(httpHost, httpRequest, responseHandler, null);
    }

    @Override
    public <T> T execute(HttpHost object, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        if (responseHandler == null) throw new IllegalArgumentException("Response handler must not be null.");
        object = this.execute((HttpHost)object, httpRequest, httpContext);
        try {
            httpRequest = responseHandler.handleResponse((HttpResponse)object);
        }
        catch (Throwable throwable) {
            object = object.getEntity();
            try {
                EntityUtils.consume((HttpEntity)object);
            }
            catch (Exception exception) {
                this.log.warn((Object)"Error consuming content after an exception.", (Throwable)exception);
            }
            if (throwable instanceof Error) throw (Error)throwable;
            if (throwable instanceof RuntimeException) throw (RuntimeException)throwable;
            if (!(throwable instanceof IOException)) throw new UndeclaredThrowableException(throwable);
            throw (IOException)throwable;
        }
        EntityUtils.consume(object.getEntity());
        return (T)httpRequest;
    }

    @Override
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        return this.execute(httpUriRequest, responseHandler, null);
    }

    @Override
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException, ClientProtocolException {
        return this.execute(AbstractHttpClient.determineTarget(httpUriRequest), httpUriRequest, responseHandler, httpContext);
    }

    @Override
    public final HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException, ClientProtocolException {
        return this.execute(httpHost, httpRequest, (HttpContext)null);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public final HttpResponse execute(HttpHost object, HttpRequest httpRequest, HttpContext object2) throws IOException, ClientProtocolException {
        Object object3;
        if (httpRequest == null) throw new IllegalArgumentException("Request must not be null.");
        synchronized (this) {
            object3 = this.createHttpContext();
            object2 = object2 == null ? object3 : new DefaultedHttpContext((HttpContext)object2, (HttpContext)object3);
            object3 = this.createClientRequestDirector(this.getRequestExecutor(), this.getConnectionManager(), this.getConnectionReuseStrategy(), this.getConnectionKeepAliveStrategy(), this.getRoutePlanner(), this.getProtocolProcessor(), this.getHttpRequestRetryHandler(), this.getRedirectStrategy(), this.getTargetAuthenticationHandler(), this.getProxyAuthenticationHandler(), this.getUserTokenHandler(), this.determineParams(httpRequest));
        }
        try {
            return object3.execute((HttpHost)object, httpRequest, (HttpContext)object2);
        }
        catch (HttpException httpException) {
            throw new ClientProtocolException(httpException);
        }
    }

    @Override
    public final HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException, ClientProtocolException {
        return this.execute(httpUriRequest, (HttpContext)null);
    }

    @Override
    public final HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        if (httpUriRequest == null) throw new IllegalArgumentException("Request must not be null.");
        return this.execute(AbstractHttpClient.determineTarget(httpUriRequest), (HttpRequest)httpUriRequest, httpContext);
    }

    public final AuthSchemeRegistry getAuthSchemes() {
        synchronized (this) {
            if (this.supportedAuthSchemes != null) return this.supportedAuthSchemes;
            this.supportedAuthSchemes = this.createAuthSchemeRegistry();
            return this.supportedAuthSchemes;
        }
    }

    public final ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        synchronized (this) {
            if (this.keepAliveStrategy != null) return this.keepAliveStrategy;
            this.keepAliveStrategy = this.createConnectionKeepAliveStrategy();
            return this.keepAliveStrategy;
        }
    }

    @Override
    public final ClientConnectionManager getConnectionManager() {
        synchronized (this) {
            if (this.connManager != null) return this.connManager;
            this.connManager = this.createClientConnectionManager();
            return this.connManager;
        }
    }

    public final ConnectionReuseStrategy getConnectionReuseStrategy() {
        synchronized (this) {
            if (this.reuseStrategy != null) return this.reuseStrategy;
            this.reuseStrategy = this.createConnectionReuseStrategy();
            return this.reuseStrategy;
        }
    }

    public final CookieSpecRegistry getCookieSpecs() {
        synchronized (this) {
            if (this.supportedCookieSpecs != null) return this.supportedCookieSpecs;
            this.supportedCookieSpecs = this.createCookieSpecRegistry();
            return this.supportedCookieSpecs;
        }
    }

    public final CookieStore getCookieStore() {
        synchronized (this) {
            if (this.cookieStore != null) return this.cookieStore;
            this.cookieStore = this.createCookieStore();
            return this.cookieStore;
        }
    }

    public final CredentialsProvider getCredentialsProvider() {
        synchronized (this) {
            if (this.credsProvider != null) return this.credsProvider;
            this.credsProvider = this.createCredentialsProvider();
            return this.credsProvider;
        }
    }

    protected final BasicHttpProcessor getHttpProcessor() {
        synchronized (this) {
            if (this.mutableProcessor != null) return this.mutableProcessor;
            this.mutableProcessor = this.createHttpProcessor();
            return this.mutableProcessor;
        }
    }

    public final HttpRequestRetryHandler getHttpRequestRetryHandler() {
        synchronized (this) {
            if (this.retryHandler != null) return this.retryHandler;
            this.retryHandler = this.createHttpRequestRetryHandler();
            return this.retryHandler;
        }
    }

    @Override
    public final HttpParams getParams() {
        synchronized (this) {
            if (this.defaultParams != null) return this.defaultParams;
            this.defaultParams = this.createHttpParams();
            return this.defaultParams;
        }
    }

    public final AuthenticationHandler getProxyAuthenticationHandler() {
        synchronized (this) {
            if (this.proxyAuthHandler != null) return this.proxyAuthHandler;
            this.proxyAuthHandler = this.createProxyAuthenticationHandler();
            return this.proxyAuthHandler;
        }
    }

    @Deprecated
    public final RedirectHandler getRedirectHandler() {
        synchronized (this) {
            return this.createRedirectHandler();
        }
    }

    public final RedirectStrategy getRedirectStrategy() {
        synchronized (this) {
            if (this.redirectStrategy != null) return this.redirectStrategy;
            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            this.redirectStrategy = redirectStrategy;
            return this.redirectStrategy;
        }
    }

    public final HttpRequestExecutor getRequestExecutor() {
        synchronized (this) {
            if (this.requestExec != null) return this.requestExec;
            this.requestExec = this.createRequestExecutor();
            return this.requestExec;
        }
    }

    public HttpRequestInterceptor getRequestInterceptor(int n) {
        synchronized (this) {
            return this.getHttpProcessor().getRequestInterceptor(n);
        }
    }

    public int getRequestInterceptorCount() {
        synchronized (this) {
            return this.getHttpProcessor().getRequestInterceptorCount();
        }
    }

    public HttpResponseInterceptor getResponseInterceptor(int n) {
        synchronized (this) {
            return this.getHttpProcessor().getResponseInterceptor(n);
        }
    }

    public int getResponseInterceptorCount() {
        synchronized (this) {
            return this.getHttpProcessor().getResponseInterceptorCount();
        }
    }

    public final HttpRoutePlanner getRoutePlanner() {
        synchronized (this) {
            if (this.routePlanner != null) return this.routePlanner;
            this.routePlanner = this.createHttpRoutePlanner();
            return this.routePlanner;
        }
    }

    public final AuthenticationHandler getTargetAuthenticationHandler() {
        synchronized (this) {
            if (this.targetAuthHandler != null) return this.targetAuthHandler;
            this.targetAuthHandler = this.createTargetAuthenticationHandler();
            return this.targetAuthHandler;
        }
    }

    public final UserTokenHandler getUserTokenHandler() {
        synchronized (this) {
            if (this.userTokenHandler != null) return this.userTokenHandler;
            this.userTokenHandler = this.createUserTokenHandler();
            return this.userTokenHandler;
        }
    }

    public void removeRequestInterceptorByClass(Class<? extends HttpRequestInterceptor> class_) {
        synchronized (this) {
            this.getHttpProcessor().removeRequestInterceptorByClass(class_);
            this.protocolProcessor = null;
            return;
        }
    }

    public void removeResponseInterceptorByClass(Class<? extends HttpResponseInterceptor> class_) {
        synchronized (this) {
            this.getHttpProcessor().removeResponseInterceptorByClass(class_);
            this.protocolProcessor = null;
            return;
        }
    }

    public void setAuthSchemes(AuthSchemeRegistry authSchemeRegistry) {
        synchronized (this) {
            this.supportedAuthSchemes = authSchemeRegistry;
            return;
        }
    }

    public void setCookieSpecs(CookieSpecRegistry cookieSpecRegistry) {
        synchronized (this) {
            this.supportedCookieSpecs = cookieSpecRegistry;
            return;
        }
    }

    public void setCookieStore(CookieStore cookieStore) {
        synchronized (this) {
            this.cookieStore = cookieStore;
            return;
        }
    }

    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        synchronized (this) {
            this.credsProvider = credentialsProvider;
            return;
        }
    }

    public void setHttpRequestRetryHandler(HttpRequestRetryHandler httpRequestRetryHandler) {
        synchronized (this) {
            this.retryHandler = httpRequestRetryHandler;
            return;
        }
    }

    public void setKeepAliveStrategy(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        synchronized (this) {
            this.keepAliveStrategy = connectionKeepAliveStrategy;
            return;
        }
    }

    public void setParams(HttpParams httpParams) {
        synchronized (this) {
            this.defaultParams = httpParams;
            return;
        }
    }

    public void setProxyAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        synchronized (this) {
            this.proxyAuthHandler = authenticationHandler;
            return;
        }
    }

    @Deprecated
    public void setRedirectHandler(RedirectHandler redirectHandler) {
        synchronized (this) {
            DefaultRedirectStrategyAdaptor defaultRedirectStrategyAdaptor = new DefaultRedirectStrategyAdaptor(redirectHandler);
            this.redirectStrategy = defaultRedirectStrategyAdaptor;
            return;
        }
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        synchronized (this) {
            this.redirectStrategy = redirectStrategy;
            return;
        }
    }

    public void setReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        synchronized (this) {
            this.reuseStrategy = connectionReuseStrategy;
            return;
        }
    }

    public void setRoutePlanner(HttpRoutePlanner httpRoutePlanner) {
        synchronized (this) {
            this.routePlanner = httpRoutePlanner;
            return;
        }
    }

    public void setTargetAuthenticationHandler(AuthenticationHandler authenticationHandler) {
        synchronized (this) {
            this.targetAuthHandler = authenticationHandler;
            return;
        }
    }

    public void setUserTokenHandler(UserTokenHandler userTokenHandler) {
        synchronized (this) {
            this.userTokenHandler = userTokenHandler;
            return;
        }
    }
}

