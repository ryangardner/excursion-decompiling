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
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.auth.NegotiateSchemeFactory;
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

public abstract class AbstractHttpClient implements HttpClient {
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

   protected AbstractHttpClient(ClientConnectionManager var1, HttpParams var2) {
      this.defaultParams = var2;
      this.connManager = var1;
   }

   private static HttpHost determineTarget(HttpUriRequest var0) throws ClientProtocolException {
      URI var1 = var0.getURI();
      HttpHost var2;
      if (var1.isAbsolute()) {
         var2 = URIUtils.extractHost(var1);
         if (var2 == null) {
            StringBuilder var3 = new StringBuilder();
            var3.append("URI does not specify a valid host name: ");
            var3.append(var1);
            throw new ClientProtocolException(var3.toString());
         }
      } else {
         var2 = null;
      }

      return var2;
   }

   private final HttpProcessor getProtocolProcessor() {
      synchronized(this){}

      Throwable var10000;
      label414: {
         boolean var10001;
         label419: {
            BasicHttpProcessor var1;
            int var2;
            HttpRequestInterceptor[] var3;
            try {
               if (this.protocolProcessor != null) {
                  break label419;
               }

               var1 = this.getHttpProcessor();
               var2 = var1.getRequestInterceptorCount();
               var3 = new HttpRequestInterceptor[var2];
            } catch (Throwable var48) {
               var10000 = var48;
               var10001 = false;
               break label414;
            }

            byte var4 = 0;

            int var5;
            for(var5 = 0; var5 < var2; ++var5) {
               try {
                  var3[var5] = var1.getRequestInterceptor(var5);
               } catch (Throwable var47) {
                  var10000 = var47;
                  var10001 = false;
                  break label414;
               }
            }

            HttpResponseInterceptor[] var6;
            try {
               var2 = var1.getResponseInterceptorCount();
               var6 = new HttpResponseInterceptor[var2];
            } catch (Throwable var46) {
               var10000 = var46;
               var10001 = false;
               break label414;
            }

            var5 = var4;

            while(true) {
               if (var5 >= var2) {
                  try {
                     ImmutableHttpProcessor var49 = new ImmutableHttpProcessor(var3, var6);
                     this.protocolProcessor = var49;
                     break;
                  } catch (Throwable var44) {
                     var10000 = var44;
                     var10001 = false;
                     break label414;
                  }
               }

               try {
                  var6[var5] = var1.getResponseInterceptor(var5);
               } catch (Throwable var45) {
                  var10000 = var45;
                  var10001 = false;
                  break label414;
               }

               ++var5;
            }
         }

         label389:
         try {
            ImmutableHttpProcessor var51 = this.protocolProcessor;
            return var51;
         } catch (Throwable var43) {
            var10000 = var43;
            var10001 = false;
            break label389;
         }
      }

      Throwable var50 = var10000;
      throw var50;
   }

   public void addRequestInterceptor(HttpRequestInterceptor var1) {
      synchronized(this){}

      try {
         this.getHttpProcessor().addInterceptor(var1);
         this.protocolProcessor = null;
      } finally {
         ;
      }

   }

   public void addRequestInterceptor(HttpRequestInterceptor var1, int var2) {
      synchronized(this){}

      try {
         this.getHttpProcessor().addInterceptor(var1, var2);
         this.protocolProcessor = null;
      } finally {
         ;
      }

   }

   public void addResponseInterceptor(HttpResponseInterceptor var1) {
      synchronized(this){}

      try {
         this.getHttpProcessor().addInterceptor(var1);
         this.protocolProcessor = null;
      } finally {
         ;
      }

   }

   public void addResponseInterceptor(HttpResponseInterceptor var1, int var2) {
      synchronized(this){}

      try {
         this.getHttpProcessor().addInterceptor(var1, var2);
         this.protocolProcessor = null;
      } finally {
         ;
      }

   }

   public void clearRequestInterceptors() {
      synchronized(this){}

      try {
         this.getHttpProcessor().clearRequestInterceptors();
         this.protocolProcessor = null;
      } finally {
         ;
      }

   }

   public void clearResponseInterceptors() {
      synchronized(this){}

      try {
         this.getHttpProcessor().clearResponseInterceptors();
         this.protocolProcessor = null;
      } finally {
         ;
      }

   }

   protected AuthSchemeRegistry createAuthSchemeRegistry() {
      AuthSchemeRegistry var1 = new AuthSchemeRegistry();
      var1.register("Basic", new BasicSchemeFactory());
      var1.register("Digest", new DigestSchemeFactory());
      var1.register("NTLM", new NTLMSchemeFactory());
      var1.register("negotiate", new NegotiateSchemeFactory());
      return var1;
   }

   protected ClientConnectionManager createClientConnectionManager() {
      SchemeRegistry var1 = SchemeRegistryFactory.createDefault();
      HttpParams var2 = this.getParams();
      String var3 = (String)var2.getParameter("http.connection-manager.factory-class-name");
      ClientConnectionManagerFactory var9;
      if (var3 != null) {
         try {
            var9 = (ClientConnectionManagerFactory)Class.forName(var3).newInstance();
         } catch (ClassNotFoundException var5) {
            StringBuilder var4 = new StringBuilder();
            var4.append("Invalid class name: ");
            var4.append(var3);
            throw new IllegalStateException(var4.toString());
         } catch (IllegalAccessException var6) {
            throw new IllegalAccessError(var6.getMessage());
         } catch (InstantiationException var7) {
            throw new InstantiationError(var7.getMessage());
         }
      } else {
         var9 = null;
      }

      Object var8;
      if (var9 != null) {
         var8 = var9.newInstance(var2, var1);
      } else {
         var8 = new SingleClientConnManager(var1);
      }

      return (ClientConnectionManager)var8;
   }

   @Deprecated
   protected RequestDirector createClientRequestDirector(HttpRequestExecutor var1, ClientConnectionManager var2, ConnectionReuseStrategy var3, ConnectionKeepAliveStrategy var4, HttpRoutePlanner var5, HttpProcessor var6, HttpRequestRetryHandler var7, RedirectHandler var8, AuthenticationHandler var9, AuthenticationHandler var10, UserTokenHandler var11, HttpParams var12) {
      return new DefaultRequestDirector(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   protected RequestDirector createClientRequestDirector(HttpRequestExecutor var1, ClientConnectionManager var2, ConnectionReuseStrategy var3, ConnectionKeepAliveStrategy var4, HttpRoutePlanner var5, HttpProcessor var6, HttpRequestRetryHandler var7, RedirectStrategy var8, AuthenticationHandler var9, AuthenticationHandler var10, UserTokenHandler var11, HttpParams var12) {
      return new DefaultRequestDirector(this.log, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   protected ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
      return new DefaultConnectionKeepAliveStrategy();
   }

   protected ConnectionReuseStrategy createConnectionReuseStrategy() {
      return new DefaultConnectionReuseStrategy();
   }

   protected CookieSpecRegistry createCookieSpecRegistry() {
      CookieSpecRegistry var1 = new CookieSpecRegistry();
      var1.register("best-match", new BestMatchSpecFactory());
      var1.register("compatibility", new BrowserCompatSpecFactory());
      var1.register("netscape", new NetscapeDraftSpecFactory());
      var1.register("rfc2109", new RFC2109SpecFactory());
      var1.register("rfc2965", new RFC2965SpecFactory());
      var1.register("ignoreCookies", new IgnoreSpecFactory());
      return var1;
   }

   protected CookieStore createCookieStore() {
      return new BasicCookieStore();
   }

   protected CredentialsProvider createCredentialsProvider() {
      return new BasicCredentialsProvider();
   }

   protected HttpContext createHttpContext() {
      BasicHttpContext var1 = new BasicHttpContext();
      var1.setAttribute("http.scheme-registry", this.getConnectionManager().getSchemeRegistry());
      var1.setAttribute("http.authscheme-registry", this.getAuthSchemes());
      var1.setAttribute("http.cookiespec-registry", this.getCookieSpecs());
      var1.setAttribute("http.cookie-store", this.getCookieStore());
      var1.setAttribute("http.auth.credentials-provider", this.getCredentialsProvider());
      return var1;
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

   protected HttpParams determineParams(HttpRequest var1) {
      return new ClientParamsStack((HttpParams)null, this.getParams(), var1.getParams(), (HttpParams)null);
   }

   public <T> T execute(HttpHost var1, HttpRequest var2, ResponseHandler<? extends T> var3) throws IOException, ClientProtocolException {
      return this.execute(var1, var2, var3, (HttpContext)null);
   }

   public <T> T execute(HttpHost var1, HttpRequest var2, ResponseHandler<? extends T> var3, HttpContext var4) throws IOException, ClientProtocolException {
      if (var3 != null) {
         HttpResponse var9 = this.execute(var1, var2, var4);

         Object var11;
         try {
            var11 = var3.handleResponse(var9);
         } catch (Throwable var8) {
            HttpEntity var10 = var9.getEntity();

            try {
               EntityUtils.consume(var10);
            } catch (Exception var7) {
               this.log.warn("Error consuming content after an exception.", var7);
            }

            if (!(var8 instanceof Error)) {
               if (!(var8 instanceof RuntimeException)) {
                  if (var8 instanceof IOException) {
                     throw (IOException)var8;
                  }

                  throw new UndeclaredThrowableException(var8);
               }

               throw (RuntimeException)var8;
            }

            throw (Error)var8;
         }

         EntityUtils.consume(var9.getEntity());
         return var11;
      } else {
         throw new IllegalArgumentException("Response handler must not be null.");
      }
   }

   public <T> T execute(HttpUriRequest var1, ResponseHandler<? extends T> var2) throws IOException, ClientProtocolException {
      return this.execute((HttpUriRequest)var1, (ResponseHandler)var2, (HttpContext)null);
   }

   public <T> T execute(HttpUriRequest var1, ResponseHandler<? extends T> var2, HttpContext var3) throws IOException, ClientProtocolException {
      return this.execute(determineTarget(var1), var1, var2, var3);
   }

   public final HttpResponse execute(HttpHost var1, HttpRequest var2) throws IOException, ClientProtocolException {
      return this.execute(var1, var2, (HttpContext)null);
   }

   public final HttpResponse execute(HttpHost var1, HttpRequest var2, HttpContext var3) throws IOException, ClientProtocolException {
      if (var2 != null) {
         synchronized(this){}

         Object var32;
         RequestDirector var33;
         label226: {
            Throwable var10000;
            boolean var10001;
            label227: {
               HttpContext var4;
               try {
                  var4 = this.createHttpContext();
               } catch (Throwable var29) {
                  var10000 = var29;
                  var10001 = false;
                  break label227;
               }

               if (var3 == null) {
                  var32 = var4;
               } else {
                  try {
                     var32 = new DefaultedHttpContext(var3, var4);
                  } catch (Throwable var28) {
                     var10000 = var28;
                     var10001 = false;
                     break label227;
                  }
               }

               label212:
               try {
                  var33 = this.createClientRequestDirector(this.getRequestExecutor(), this.getConnectionManager(), this.getConnectionReuseStrategy(), this.getConnectionKeepAliveStrategy(), this.getRoutePlanner(), this.getProtocolProcessor(), this.getHttpRequestRetryHandler(), this.getRedirectStrategy(), this.getTargetAuthenticationHandler(), this.getProxyAuthenticationHandler(), this.getUserTokenHandler(), this.determineParams(var2));
                  break label226;
               } catch (Throwable var27) {
                  var10000 = var27;
                  var10001 = false;
                  break label212;
               }
            }

            while(true) {
               Throwable var30 = var10000;

               try {
                  throw var30;
               } catch (Throwable var26) {
                  var10000 = var26;
                  var10001 = false;
                  continue;
               }
            }
         }

         try {
            HttpResponse var31 = var33.execute(var1, var2, (HttpContext)var32);
            return var31;
         } catch (HttpException var25) {
            throw new ClientProtocolException(var25);
         }
      } else {
         throw new IllegalArgumentException("Request must not be null.");
      }
   }

   public final HttpResponse execute(HttpUriRequest var1) throws IOException, ClientProtocolException {
      return this.execute(var1, (HttpContext)null);
   }

   public final HttpResponse execute(HttpUriRequest var1, HttpContext var2) throws IOException, ClientProtocolException {
      if (var1 != null) {
         return this.execute((HttpHost)determineTarget(var1), (HttpRequest)var1, (HttpContext)var2);
      } else {
         throw new IllegalArgumentException("Request must not be null.");
      }
   }

   public final AuthSchemeRegistry getAuthSchemes() {
      synchronized(this){}

      AuthSchemeRegistry var1;
      try {
         if (this.supportedAuthSchemes == null) {
            this.supportedAuthSchemes = this.createAuthSchemeRegistry();
         }

         var1 = this.supportedAuthSchemes;
      } finally {
         ;
      }

      return var1;
   }

   public final ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
      synchronized(this){}

      ConnectionKeepAliveStrategy var1;
      try {
         if (this.keepAliveStrategy == null) {
            this.keepAliveStrategy = this.createConnectionKeepAliveStrategy();
         }

         var1 = this.keepAliveStrategy;
      } finally {
         ;
      }

      return var1;
   }

   public final ClientConnectionManager getConnectionManager() {
      synchronized(this){}

      ClientConnectionManager var1;
      try {
         if (this.connManager == null) {
            this.connManager = this.createClientConnectionManager();
         }

         var1 = this.connManager;
      } finally {
         ;
      }

      return var1;
   }

   public final ConnectionReuseStrategy getConnectionReuseStrategy() {
      synchronized(this){}

      ConnectionReuseStrategy var1;
      try {
         if (this.reuseStrategy == null) {
            this.reuseStrategy = this.createConnectionReuseStrategy();
         }

         var1 = this.reuseStrategy;
      } finally {
         ;
      }

      return var1;
   }

   public final CookieSpecRegistry getCookieSpecs() {
      synchronized(this){}

      CookieSpecRegistry var1;
      try {
         if (this.supportedCookieSpecs == null) {
            this.supportedCookieSpecs = this.createCookieSpecRegistry();
         }

         var1 = this.supportedCookieSpecs;
      } finally {
         ;
      }

      return var1;
   }

   public final CookieStore getCookieStore() {
      synchronized(this){}

      CookieStore var1;
      try {
         if (this.cookieStore == null) {
            this.cookieStore = this.createCookieStore();
         }

         var1 = this.cookieStore;
      } finally {
         ;
      }

      return var1;
   }

   public final CredentialsProvider getCredentialsProvider() {
      synchronized(this){}

      CredentialsProvider var1;
      try {
         if (this.credsProvider == null) {
            this.credsProvider = this.createCredentialsProvider();
         }

         var1 = this.credsProvider;
      } finally {
         ;
      }

      return var1;
   }

   protected final BasicHttpProcessor getHttpProcessor() {
      synchronized(this){}

      BasicHttpProcessor var1;
      try {
         if (this.mutableProcessor == null) {
            this.mutableProcessor = this.createHttpProcessor();
         }

         var1 = this.mutableProcessor;
      } finally {
         ;
      }

      return var1;
   }

   public final HttpRequestRetryHandler getHttpRequestRetryHandler() {
      synchronized(this){}

      HttpRequestRetryHandler var1;
      try {
         if (this.retryHandler == null) {
            this.retryHandler = this.createHttpRequestRetryHandler();
         }

         var1 = this.retryHandler;
      } finally {
         ;
      }

      return var1;
   }

   public final HttpParams getParams() {
      synchronized(this){}

      HttpParams var1;
      try {
         if (this.defaultParams == null) {
            this.defaultParams = this.createHttpParams();
         }

         var1 = this.defaultParams;
      } finally {
         ;
      }

      return var1;
   }

   public final AuthenticationHandler getProxyAuthenticationHandler() {
      synchronized(this){}

      AuthenticationHandler var1;
      try {
         if (this.proxyAuthHandler == null) {
            this.proxyAuthHandler = this.createProxyAuthenticationHandler();
         }

         var1 = this.proxyAuthHandler;
      } finally {
         ;
      }

      return var1;
   }

   @Deprecated
   public final RedirectHandler getRedirectHandler() {
      synchronized(this){}

      RedirectHandler var1;
      try {
         var1 = this.createRedirectHandler();
      } finally {
         ;
      }

      return var1;
   }

   public final RedirectStrategy getRedirectStrategy() {
      synchronized(this){}

      RedirectStrategy var4;
      try {
         if (this.redirectStrategy == null) {
            DefaultRedirectStrategy var1 = new DefaultRedirectStrategy();
            this.redirectStrategy = var1;
         }

         var4 = this.redirectStrategy;
      } finally {
         ;
      }

      return var4;
   }

   public final HttpRequestExecutor getRequestExecutor() {
      synchronized(this){}

      HttpRequestExecutor var1;
      try {
         if (this.requestExec == null) {
            this.requestExec = this.createRequestExecutor();
         }

         var1 = this.requestExec;
      } finally {
         ;
      }

      return var1;
   }

   public HttpRequestInterceptor getRequestInterceptor(int var1) {
      synchronized(this){}

      HttpRequestInterceptor var2;
      try {
         var2 = this.getHttpProcessor().getRequestInterceptor(var1);
      } finally {
         ;
      }

      return var2;
   }

   public int getRequestInterceptorCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.getHttpProcessor().getRequestInterceptorCount();
      } finally {
         ;
      }

      return var1;
   }

   public HttpResponseInterceptor getResponseInterceptor(int var1) {
      synchronized(this){}

      HttpResponseInterceptor var2;
      try {
         var2 = this.getHttpProcessor().getResponseInterceptor(var1);
      } finally {
         ;
      }

      return var2;
   }

   public int getResponseInterceptorCount() {
      synchronized(this){}

      int var1;
      try {
         var1 = this.getHttpProcessor().getResponseInterceptorCount();
      } finally {
         ;
      }

      return var1;
   }

   public final HttpRoutePlanner getRoutePlanner() {
      synchronized(this){}

      HttpRoutePlanner var1;
      try {
         if (this.routePlanner == null) {
            this.routePlanner = this.createHttpRoutePlanner();
         }

         var1 = this.routePlanner;
      } finally {
         ;
      }

      return var1;
   }

   public final AuthenticationHandler getTargetAuthenticationHandler() {
      synchronized(this){}

      AuthenticationHandler var1;
      try {
         if (this.targetAuthHandler == null) {
            this.targetAuthHandler = this.createTargetAuthenticationHandler();
         }

         var1 = this.targetAuthHandler;
      } finally {
         ;
      }

      return var1;
   }

   public final UserTokenHandler getUserTokenHandler() {
      synchronized(this){}

      UserTokenHandler var1;
      try {
         if (this.userTokenHandler == null) {
            this.userTokenHandler = this.createUserTokenHandler();
         }

         var1 = this.userTokenHandler;
      } finally {
         ;
      }

      return var1;
   }

   public void removeRequestInterceptorByClass(Class<? extends HttpRequestInterceptor> var1) {
      synchronized(this){}

      try {
         this.getHttpProcessor().removeRequestInterceptorByClass(var1);
         this.protocolProcessor = null;
      } finally {
         ;
      }

   }

   public void removeResponseInterceptorByClass(Class<? extends HttpResponseInterceptor> var1) {
      synchronized(this){}

      try {
         this.getHttpProcessor().removeResponseInterceptorByClass(var1);
         this.protocolProcessor = null;
      } finally {
         ;
      }

   }

   public void setAuthSchemes(AuthSchemeRegistry var1) {
      synchronized(this){}

      try {
         this.supportedAuthSchemes = var1;
      } finally {
         ;
      }

   }

   public void setCookieSpecs(CookieSpecRegistry var1) {
      synchronized(this){}

      try {
         this.supportedCookieSpecs = var1;
      } finally {
         ;
      }

   }

   public void setCookieStore(CookieStore var1) {
      synchronized(this){}

      try {
         this.cookieStore = var1;
      } finally {
         ;
      }

   }

   public void setCredentialsProvider(CredentialsProvider var1) {
      synchronized(this){}

      try {
         this.credsProvider = var1;
      } finally {
         ;
      }

   }

   public void setHttpRequestRetryHandler(HttpRequestRetryHandler var1) {
      synchronized(this){}

      try {
         this.retryHandler = var1;
      } finally {
         ;
      }

   }

   public void setKeepAliveStrategy(ConnectionKeepAliveStrategy var1) {
      synchronized(this){}

      try {
         this.keepAliveStrategy = var1;
      } finally {
         ;
      }

   }

   public void setParams(HttpParams var1) {
      synchronized(this){}

      try {
         this.defaultParams = var1;
      } finally {
         ;
      }

   }

   public void setProxyAuthenticationHandler(AuthenticationHandler var1) {
      synchronized(this){}

      try {
         this.proxyAuthHandler = var1;
      } finally {
         ;
      }

   }

   @Deprecated
   public void setRedirectHandler(RedirectHandler var1) {
      synchronized(this){}

      try {
         DefaultRedirectStrategyAdaptor var2 = new DefaultRedirectStrategyAdaptor(var1);
         this.redirectStrategy = var2;
      } finally {
         ;
      }

   }

   public void setRedirectStrategy(RedirectStrategy var1) {
      synchronized(this){}

      try {
         this.redirectStrategy = var1;
      } finally {
         ;
      }

   }

   public void setReuseStrategy(ConnectionReuseStrategy var1) {
      synchronized(this){}

      try {
         this.reuseStrategy = var1;
      } finally {
         ;
      }

   }

   public void setRoutePlanner(HttpRoutePlanner var1) {
      synchronized(this){}

      try {
         this.routePlanner = var1;
      } finally {
         ;
      }

   }

   public void setTargetAuthenticationHandler(AuthenticationHandler var1) {
      synchronized(this){}

      try {
         this.targetAuthHandler = var1;
      } finally {
         ;
      }

   }

   public void setUserTokenHandler(UserTokenHandler var1) {
      synchronized(this){}

      try {
         this.userTokenHandler = var1;
      } finally {
         ;
      }

   }
}
