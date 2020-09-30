/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.NonRepeatableRequestException;
import org.apache.http.client.RedirectException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.BasicRouteDirector;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultRedirectStrategyAdaptor;
import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.client.RoutedRequest;
import org.apache.http.impl.client.TunnelRefusedException;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.EntityUtils;

public class DefaultRequestDirector
implements RequestDirector {
    protected final ClientConnectionManager connManager;
    private int execCount;
    protected final HttpProcessor httpProcessor;
    protected final ConnectionKeepAliveStrategy keepAliveStrategy;
    private final Log log;
    protected ManagedClientConnection managedConn;
    private int maxRedirects;
    protected final HttpParams params;
    protected final AuthenticationHandler proxyAuthHandler;
    protected final AuthState proxyAuthState;
    private int redirectCount;
    @Deprecated
    protected final RedirectHandler redirectHandler = null;
    protected final RedirectStrategy redirectStrategy;
    protected final HttpRequestExecutor requestExec;
    protected final HttpRequestRetryHandler retryHandler;
    protected final ConnectionReuseStrategy reuseStrategy;
    protected final HttpRoutePlanner routePlanner;
    protected final AuthenticationHandler targetAuthHandler;
    protected final AuthState targetAuthState;
    protected final UserTokenHandler userTokenHandler;
    private HttpHost virtualHost;

    public DefaultRequestDirector(Log log, HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectStrategy redirectStrategy, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        if (log == null) throw new IllegalArgumentException("Log may not be null.");
        if (httpRequestExecutor == null) throw new IllegalArgumentException("Request executor may not be null.");
        if (clientConnectionManager == null) throw new IllegalArgumentException("Client connection manager may not be null.");
        if (connectionReuseStrategy == null) throw new IllegalArgumentException("Connection reuse strategy may not be null.");
        if (connectionKeepAliveStrategy == null) throw new IllegalArgumentException("Connection keep alive strategy may not be null.");
        if (httpRoutePlanner == null) throw new IllegalArgumentException("Route planner may not be null.");
        if (httpProcessor == null) throw new IllegalArgumentException("HTTP protocol processor may not be null.");
        if (httpRequestRetryHandler == null) throw new IllegalArgumentException("HTTP request retry handler may not be null.");
        if (redirectStrategy == null) throw new IllegalArgumentException("Redirect strategy may not be null.");
        if (authenticationHandler == null) throw new IllegalArgumentException("Target authentication handler may not be null.");
        if (authenticationHandler2 == null) throw new IllegalArgumentException("Proxy authentication handler may not be null.");
        if (userTokenHandler == null) throw new IllegalArgumentException("User token handler may not be null.");
        if (httpParams == null) throw new IllegalArgumentException("HTTP parameters may not be null");
        this.log = log;
        this.requestExec = httpRequestExecutor;
        this.connManager = clientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
        this.routePlanner = httpRoutePlanner;
        this.httpProcessor = httpProcessor;
        this.retryHandler = httpRequestRetryHandler;
        this.redirectStrategy = redirectStrategy;
        this.targetAuthHandler = authenticationHandler;
        this.proxyAuthHandler = authenticationHandler2;
        this.userTokenHandler = userTokenHandler;
        this.params = httpParams;
        this.managedConn = null;
        this.execCount = 0;
        this.redirectCount = 0;
        this.maxRedirects = httpParams.getIntParameter("http.protocol.max-redirects", 100);
        this.targetAuthState = new AuthState();
        this.proxyAuthState = new AuthState();
    }

    @Deprecated
    public DefaultRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectHandler redirectHandler, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        this(LogFactory.getLog(DefaultRequestDirector.class), httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, new DefaultRedirectStrategyAdaptor(redirectHandler), authenticationHandler, authenticationHandler2, userTokenHandler, httpParams);
    }

    private void abortConnection() {
        ManagedClientConnection managedClientConnection;
        block4 : {
            managedClientConnection = this.managedConn;
            if (managedClientConnection == null) return;
            this.managedConn = null;
            try {
                managedClientConnection.abortConnection();
            }
            catch (IOException iOException) {
                if (!this.log.isDebugEnabled()) break block4;
                this.log.debug((Object)iOException.getMessage(), (Throwable)iOException);
            }
        }
        try {
            managedClientConnection.releaseConnection();
            return;
        }
        catch (IOException iOException) {
            this.log.debug((Object)"Error releasing connection", (Throwable)iOException);
        }
    }

    private void invalidateAuthIfSuccessful(AuthState authState) {
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme == null) return;
        if (!authScheme.isConnectionBased()) return;
        if (!authScheme.isComplete()) return;
        if (authState.getCredentials() == null) return;
        authState.invalidate();
    }

    private void processChallenges(Map<String, Header> object, AuthState object2, AuthenticationHandler authenticationHandler, HttpResponse httpResponse, HttpContext httpContext) throws MalformedChallengeException, AuthenticationException {
        AuthScheme authScheme;
        AuthScheme authScheme2 = authScheme = ((AuthState)object2).getAuthScheme();
        if (authScheme == null) {
            authScheme2 = authenticationHandler.selectScheme((Map<String, Header>)object, httpResponse, httpContext);
            ((AuthState)object2).setAuthScheme(authScheme2);
        }
        if ((object = object.get(((String)(object2 = authScheme2.getSchemeName())).toLowerCase(Locale.ENGLISH))) != null) {
            authScheme2.processChallenge((Header)object);
            this.log.debug((Object)"Authorization challenge processed");
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append((String)object2);
        ((StringBuilder)object).append(" authorization challenge expected, but not found");
        throw new AuthenticationException(((StringBuilder)object).toString());
    }

    private void tryConnect(RoutedRequest routedRequest, HttpContext httpContext) throws HttpException, IOException {
        HttpRoute httpRoute = routedRequest.getRoute();
        int n = 0;
        do {
            ++n;
            try {
                if (!this.managedConn.isOpen()) {
                    this.managedConn.open(httpRoute, httpContext, this.params);
                } else {
                    this.managedConn.setSocketTimeout(HttpConnectionParams.getSoTimeout(this.params));
                }
                this.establishRoute(httpRoute, httpContext);
                return;
            }
            catch (IOException iOException) {
                try {
                    this.managedConn.close();
                }
                catch (IOException iOException2) {
                    // empty catch block
                }
                if (!this.retryHandler.retryRequest(iOException, n, httpContext)) throw iOException;
                if (this.log.isInfoEnabled()) {
                    Log log = this.log;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("I/O exception (");
                    stringBuilder.append(iOException.getClass().getName());
                    stringBuilder.append(") caught when connecting to the target host: ");
                    stringBuilder.append(iOException.getMessage());
                    log.info((Object)stringBuilder.toString());
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug((Object)iOException.getMessage(), (Throwable)iOException);
                }
                this.log.info((Object)"Retrying connect");
                continue;
            }
            break;
        } while (true);
    }

    private HttpResponse tryExecute(RoutedRequest object, HttpContext httpContext) throws HttpException, IOException {
        RequestWrapper requestWrapper = ((RoutedRequest)object).getRequest();
        HttpRoute httpRoute = ((RoutedRequest)object).getRoute();
        Object var5_6 = null;
        object = null;
        do {
            Object object2;
            ++this.execCount;
            requestWrapper.incrementExecCount();
            if (!requestWrapper.isRepeatable()) {
                this.log.debug((Object)"Cannot retry non-repeatable request");
                if (object == null) throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
                throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.  The cause lists the reason the original request failed.", (Throwable)object);
            }
            try {
                if (!this.managedConn.isOpen()) {
                    if (httpRoute.isTunnelled()) {
                        this.log.debug((Object)"Proxied connection. Need to start over.");
                        return var5_6;
                    }
                    this.log.debug((Object)"Reopening the direct connection.");
                    this.managedConn.open(httpRoute, httpContext, this.params);
                }
                if (!this.log.isDebugEnabled()) return this.requestExec.execute(requestWrapper, this.managedConn, httpContext);
                object2 = this.log;
                object = new StringBuilder();
                ((StringBuilder)object).append("Attempt ");
                ((StringBuilder)object).append(this.execCount);
                ((StringBuilder)object).append(" to execute request");
                object2.debug((Object)((StringBuilder)object).toString());
                return this.requestExec.execute(requestWrapper, this.managedConn, httpContext);
            }
            catch (IOException iOException) {
                this.log.debug((Object)"Closing the connection.");
                try {
                    this.managedConn.close();
                }
                catch (IOException iOException2) {
                    // empty catch block
                }
                if (!this.retryHandler.retryRequest(iOException, requestWrapper.getExecCount(), httpContext)) throw iOException;
                if (this.log.isInfoEnabled()) {
                    Log log = this.log;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("I/O exception (");
                    ((StringBuilder)object2).append(iOException.getClass().getName());
                    ((StringBuilder)object2).append(") caught when processing request: ");
                    ((StringBuilder)object2).append(iOException.getMessage());
                    log.info((Object)((StringBuilder)object2).toString());
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug((Object)iOException.getMessage(), (Throwable)iOException);
                }
                this.log.info((Object)"Retrying request");
                continue;
            }
            break;
        } while (true);
    }

    private void updateAuthState(AuthState authState, HttpHost object, CredentialsProvider object2) {
        int n;
        if (!authState.isValid()) {
            return;
        }
        Object object3 = ((HttpHost)object).getHostName();
        int n2 = n = ((HttpHost)object).getPort();
        if (n < 0) {
            n2 = this.connManager.getSchemeRegistry().getScheme((HttpHost)object).getDefaultPort();
        }
        AuthScheme authScheme = authState.getAuthScheme();
        object3 = new AuthScope((String)object3, n2, authScheme.getRealm(), authScheme.getSchemeName());
        if (this.log.isDebugEnabled()) {
            object = this.log;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Authentication scope: ");
            stringBuilder.append(object3);
            object.debug((Object)stringBuilder.toString());
        }
        if ((object = authState.getCredentials()) == null) {
            object = object2 = object2.getCredentials((AuthScope)object3);
            if (this.log.isDebugEnabled()) {
                if (object2 != null) {
                    this.log.debug((Object)"Found credentials");
                    object = object2;
                } else {
                    this.log.debug((Object)"Credentials not found");
                    object = object2;
                }
            }
        } else if (authScheme.isComplete()) {
            this.log.debug((Object)"Authentication failed");
            object = null;
        }
        authState.setAuthScope((AuthScope)object3);
        authState.setCredentials((Credentials)object);
    }

    private RequestWrapper wrapRequest(HttpRequest httpRequest) throws ProtocolException {
        if (!(httpRequest instanceof HttpEntityEnclosingRequest)) return new RequestWrapper(httpRequest);
        return new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httpRequest);
    }

    protected HttpRequest createConnectRequest(HttpRoute object, HttpContext object2) {
        int n;
        object2 = ((HttpRoute)object).getTargetHost();
        object = ((HttpHost)object2).getHostName();
        int n2 = n = ((HttpHost)object2).getPort();
        if (n < 0) {
            n2 = this.connManager.getSchemeRegistry().getScheme(((HttpHost)object2).getSchemeName()).getDefaultPort();
        }
        object2 = new StringBuilder(((String)object).length() + 6);
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(':');
        ((StringBuilder)object2).append(Integer.toString(n2));
        return new BasicHttpRequest("CONNECT", ((StringBuilder)object2).toString(), HttpProtocolParams.getVersion(this.params));
    }

    protected boolean createTunnelToProxy(HttpRoute httpRoute, int n, HttpContext httpContext) throws HttpException, IOException {
        throw new HttpException("Proxy chains are not supported.");
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    protected boolean createTunnelToTarget(HttpRoute object, HttpContext httpContext) throws HttpException, IOException {
        HttpHost httpHost = ((HttpRoute)object).getProxyHost();
        HttpHost httpHost2 = ((HttpRoute)object).getTargetHost();
        HttpMessage httpMessage = null;
        boolean bl = false;
        while (!bl) {
            if (!this.managedConn.isOpen()) {
                this.managedConn.open((HttpRoute)object, httpContext, this.params);
            }
            httpMessage = this.createConnectRequest((HttpRoute)object, httpContext);
            httpMessage.setParams(this.params);
            httpContext.setAttribute("http.target_host", httpHost2);
            httpContext.setAttribute("http.proxy_host", httpHost);
            httpContext.setAttribute("http.connection", this.managedConn);
            httpContext.setAttribute("http.auth.target-scope", this.targetAuthState);
            httpContext.setAttribute("http.auth.proxy-scope", this.proxyAuthState);
            httpContext.setAttribute("http.request", httpMessage);
            this.requestExec.preProcess((HttpRequest)httpMessage, this.httpProcessor, httpContext);
            httpMessage = this.requestExec.execute((HttpRequest)httpMessage, this.managedConn, httpContext);
            httpMessage.setParams(this.params);
            this.requestExec.postProcess((HttpResponse)httpMessage, this.httpProcessor, httpContext);
            if (httpMessage.getStatusLine().getStatusCode() < 200) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected response to CONNECT request: ");
                ((StringBuilder)object).append(httpMessage.getStatusLine());
                throw new HttpException(((StringBuilder)object).toString());
            }
            CredentialsProvider credentialsProvider = (CredentialsProvider)httpContext.getAttribute("http.auth.credentials-provider");
            if (credentialsProvider != null && HttpClientParams.isAuthenticating(this.params)) {
                if (this.proxyAuthHandler.isAuthenticationRequested((HttpResponse)httpMessage, httpContext)) {
                    block15 : {
                        AuthenticationHandler authenticationHandler;
                        block16 : {
                            this.log.debug((Object)"Proxy requested authentication");
                            Map<String, Header> map = this.proxyAuthHandler.getChallenges((HttpResponse)httpMessage, httpContext);
                            AuthState authState = this.proxyAuthState;
                            authenticationHandler = this.proxyAuthHandler;
                            try {
                                this.processChallenges(map, authState, authenticationHandler, (HttpResponse)httpMessage, httpContext);
                                break block15;
                            }
                            catch (AuthenticationException authenticationException) {
                                break block16;
                            }
                            catch (AuthenticationException authenticationException) {
                                // empty catch block
                            }
                        }
                        if (this.log.isWarnEnabled()) {
                            httpContext = this.log;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Authentication error: ");
                            ((StringBuilder)object).append(((Throwable)((Object)authenticationHandler)).getMessage());
                            httpContext.warn((Object)((StringBuilder)object).toString());
                            break;
                        }
                    }
                    this.updateAuthState(this.proxyAuthState, httpHost, credentialsProvider);
                    if (this.proxyAuthState.getCredentials() != null) {
                        if (this.reuseStrategy.keepAlive((HttpResponse)httpMessage, httpContext)) {
                            this.log.debug((Object)"Connection kept alive");
                            EntityUtils.consume(httpMessage.getEntity());
                        } else {
                            this.managedConn.close();
                        }
                        bl = false;
                        continue;
                    }
                    bl = true;
                    continue;
                }
                this.proxyAuthState.setAuthScope(null);
            }
            bl = true;
        }
        if (httpMessage.getStatusLine().getStatusCode() <= 299) {
            this.managedConn.markReusable();
            return false;
        }
        object = httpMessage.getEntity();
        if (object != null) {
            httpMessage.setEntity(new BufferedHttpEntity((HttpEntity)object));
        }
        this.managedConn.close();
        object = new StringBuilder();
        ((StringBuilder)object).append("CONNECT refused by proxy: ");
        ((StringBuilder)object).append(httpMessage.getStatusLine());
        throw new TunnelRefusedException(((StringBuilder)object).toString(), (HttpResponse)httpMessage);
    }

    protected HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        HttpHost httpHost2 = httpHost;
        if (httpHost == null) {
            httpHost2 = (HttpHost)httpRequest.getParams().getParameter("http.default-host");
        }
        if (httpHost2 == null) throw new IllegalStateException("Target host must not be null, or set in parameters.");
        return this.routePlanner.determineRoute(httpHost2, httpRequest, httpContext);
    }

    /*
     * Unable to fully structure code
     */
    protected void establishRoute(HttpRoute var1_1, HttpContext var2_2) throws HttpException, IOException {
        var3_3 = new BasicRouteDirector();
        block8 : do {
            var4_4 = this.managedConn.getRoute();
            var5_5 = var3_3.nextStep((RouteInfo)var1_1, var4_4);
            switch (var5_5) {
                default: {
                    var1_1 = new StringBuilder();
                    var1_1.append("Unknown step indicator ");
                    var1_1.append(var5_5);
                    var1_1.append(" from RouteDirector.");
                    throw new IllegalStateException(var1_1.toString());
                }
                case 5: {
                    this.managedConn.layerProtocol((HttpContext)var2_2, this.params);
                    ** GOTO lbl31
                }
                case 4: {
                    var6_6 = var4_4.getHopCount() - 1;
                    var7_7 = this.createTunnelToProxy((HttpRoute)var1_1, var6_6, (HttpContext)var2_2);
                    this.log.debug((Object)"Tunnel to proxy created.");
                    this.managedConn.tunnelProxy(var1_1.getHopTarget(var6_6), var7_7, this.params);
                    ** GOTO lbl31
                }
                case 3: {
                    var7_7 = this.createTunnelToTarget((HttpRoute)var1_1, (HttpContext)var2_2);
                    this.log.debug((Object)"Tunnel to target created.");
                    this.managedConn.tunnelTarget(var7_7, this.params);
                    ** GOTO lbl31
                }
                case 1: 
                case 2: {
                    this.managedConn.open((HttpRoute)var1_1, (HttpContext)var2_2, this.params);
                }
lbl31: // 5 sources:
                case 0: {
                    if (var5_5 > 0) continue block8;
                    return;
                }
                case -1: 
            }
            break;
        } while (true);
        var2_2 = new StringBuilder();
        var2_2.append("Unable to establish route: planned = ");
        var2_2.append(var1_1);
        var2_2.append("; current = ");
        var2_2.append(var4_4);
        throw new HttpException(var2_2.toString());
    }

    /*
     * Exception decompiling
     */
    @Override
    public HttpResponse execute(HttpHost var1_1, HttpRequest var2_7, HttpContext var3_9) throws HttpException, IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[WHILELOOP]], but top level block is 0[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    protected RoutedRequest handleResponse(RoutedRequest object, HttpResponse object2, HttpContext object3) throws HttpException, IOException {
        Object object4;
        CredentialsProvider credentialsProvider;
        block13 : {
            HttpRoute httpRoute = ((RoutedRequest)object).getRoute();
            Object object5 = ((RoutedRequest)object).getRequest();
            object4 = ((AbstractHttpMessage)object5).getParams();
            if (HttpClientParams.isRedirecting((HttpParams)object4) && this.redirectStrategy.isRedirected((HttpRequest)object5, (HttpResponse)object2, (HttpContext)object3)) {
                int n = this.redirectCount;
                if (n >= this.maxRedirects) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Maximum redirects (");
                    ((StringBuilder)object).append(this.maxRedirects);
                    ((StringBuilder)object).append(") exceeded");
                    throw new RedirectException(((StringBuilder)object).toString());
                }
                this.redirectCount = n + 1;
                this.virtualHost = null;
                object2 = this.redirectStrategy.getRedirect((HttpRequest)object5, (HttpResponse)object2, (HttpContext)object3);
                object2.setHeaders(((RequestWrapper)object5).getOriginal().getAllHeaders());
                object = object2.getURI();
                if (((URI)object).getHost() == null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Redirect URI does not specify a valid host name: ");
                    ((StringBuilder)object2).append(object);
                    throw new ProtocolException(((StringBuilder)object2).toString());
                }
                object5 = new HttpHost(((URI)object).getHost(), ((URI)object).getPort(), ((URI)object).getScheme());
                this.targetAuthState.setAuthScope(null);
                this.proxyAuthState.setAuthScope(null);
                if (!httpRoute.getTargetHost().equals(object5)) {
                    this.targetAuthState.invalidate();
                    AuthScheme authScheme = this.proxyAuthState.getAuthScheme();
                    if (authScheme != null && authScheme.isConnectionBased()) {
                        this.proxyAuthState.invalidate();
                    }
                }
                object2 = this.wrapRequest((HttpRequest)object2);
                ((AbstractHttpMessage)object2).setParams((HttpParams)object4);
                object3 = this.determineRoute((HttpHost)object5, (HttpRequest)object2, (HttpContext)object3);
                object2 = new RoutedRequest((RequestWrapper)object2, (HttpRoute)object3);
                if (!this.log.isDebugEnabled()) return object2;
                object4 = this.log;
                object5 = new StringBuilder();
                ((StringBuilder)object5).append("Redirecting to '");
                ((StringBuilder)object5).append(object);
                ((StringBuilder)object5).append("' via ");
                ((StringBuilder)object5).append(object3);
                object4.debug((Object)((StringBuilder)object5).toString());
                return object2;
            }
            credentialsProvider = (CredentialsProvider)object3.getAttribute("http.auth.credentials-provider");
            if (credentialsProvider == null) return null;
            if (!HttpClientParams.isAuthenticating((HttpParams)object4)) return null;
            if (this.targetAuthHandler.isAuthenticationRequested((HttpResponse)object2, (HttpContext)object3)) {
                block12 : {
                    object4 = object5 = (HttpHost)object3.getAttribute("http.target_host");
                    if (object5 == null) {
                        object4 = httpRoute.getTargetHost();
                    }
                    this.log.debug((Object)"Target requested authentication");
                    object5 = this.targetAuthHandler.getChallenges((HttpResponse)object2, (HttpContext)object3);
                    try {
                        this.processChallenges((Map<String, Header>)object5, this.targetAuthState, this.targetAuthHandler, (HttpResponse)object2, (HttpContext)object3);
                    }
                    catch (AuthenticationException authenticationException) {
                        if (!this.log.isWarnEnabled()) break block12;
                        object = this.log;
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Authentication error: ");
                        ((StringBuilder)object3).append(authenticationException.getMessage());
                        object.warn((Object)((StringBuilder)object3).toString());
                        return null;
                    }
                }
                this.updateAuthState(this.targetAuthState, (HttpHost)object4, credentialsProvider);
                if (this.targetAuthState.getCredentials() == null) return null;
                return object;
            }
            this.targetAuthState.setAuthScope(null);
            if (!this.proxyAuthHandler.isAuthenticationRequested((HttpResponse)object2, (HttpContext)object3)) {
                this.proxyAuthState.setAuthScope(null);
                return null;
            }
            object4 = httpRoute.getProxyHost();
            this.log.debug((Object)"Proxy requested authentication");
            object5 = this.proxyAuthHandler.getChallenges((HttpResponse)object2, (HttpContext)object3);
            try {
                this.processChallenges((Map<String, Header>)object5, this.proxyAuthState, this.proxyAuthHandler, (HttpResponse)object2, (HttpContext)object3);
            }
            catch (AuthenticationException authenticationException) {
                if (!this.log.isWarnEnabled()) break block13;
                object3 = this.log;
                object = new StringBuilder();
                ((StringBuilder)object).append("Authentication error: ");
                ((StringBuilder)object).append(authenticationException.getMessage());
                object3.warn((Object)((StringBuilder)object).toString());
                return null;
            }
        }
        this.updateAuthState(this.proxyAuthState, (HttpHost)object4, credentialsProvider);
        if (this.proxyAuthState.getCredentials() == null) return null;
        return object;
    }

    protected void releaseConnection() {
        try {
            this.managedConn.releaseConnection();
        }
        catch (IOException iOException) {
            this.log.debug((Object)"IOException releasing connection", (Throwable)iOException);
        }
        this.managedConn = null;
    }

    protected void rewriteRequestURI(RequestWrapper requestWrapper, HttpRoute httpRoute) throws ProtocolException {
        try {
            URI uRI = requestWrapper.getURI();
            if (httpRoute.getProxyHost() != null && !httpRoute.isTunnelled()) {
                if (uRI.isAbsolute()) return;
                requestWrapper.setURI(URIUtils.rewriteURI(uRI, httpRoute.getTargetHost()));
                return;
            }
            if (!uRI.isAbsolute()) return;
            requestWrapper.setURI(URIUtils.rewriteURI(uRI, null));
            return;
        }
        catch (URISyntaxException uRISyntaxException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid URI: ");
            stringBuilder.append(requestWrapper.getRequestLine().getUri());
            throw new ProtocolException(stringBuilder.toString(), uRISyntaxException);
        }
    }
}

