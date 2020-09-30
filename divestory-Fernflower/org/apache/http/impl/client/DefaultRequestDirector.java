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
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
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
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.EntityUtils;

public class DefaultRequestDirector implements RequestDirector {
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
   protected final RedirectHandler redirectHandler;
   protected final RedirectStrategy redirectStrategy;
   protected final HttpRequestExecutor requestExec;
   protected final HttpRequestRetryHandler retryHandler;
   protected final ConnectionReuseStrategy reuseStrategy;
   protected final HttpRoutePlanner routePlanner;
   protected final AuthenticationHandler targetAuthHandler;
   protected final AuthState targetAuthState;
   protected final UserTokenHandler userTokenHandler;
   private HttpHost virtualHost;

   public DefaultRequestDirector(Log var1, HttpRequestExecutor var2, ClientConnectionManager var3, ConnectionReuseStrategy var4, ConnectionKeepAliveStrategy var5, HttpRoutePlanner var6, HttpProcessor var7, HttpRequestRetryHandler var8, RedirectStrategy var9, AuthenticationHandler var10, AuthenticationHandler var11, UserTokenHandler var12, HttpParams var13) {
      this.redirectHandler = null;
      if (var1 != null) {
         if (var2 != null) {
            if (var3 != null) {
               if (var4 != null) {
                  if (var5 != null) {
                     if (var6 != null) {
                        if (var7 != null) {
                           if (var8 != null) {
                              if (var9 != null) {
                                 if (var10 != null) {
                                    if (var11 != null) {
                                       if (var12 != null) {
                                          if (var13 != null) {
                                             this.log = var1;
                                             this.requestExec = var2;
                                             this.connManager = var3;
                                             this.reuseStrategy = var4;
                                             this.keepAliveStrategy = var5;
                                             this.routePlanner = var6;
                                             this.httpProcessor = var7;
                                             this.retryHandler = var8;
                                             this.redirectStrategy = var9;
                                             this.targetAuthHandler = var10;
                                             this.proxyAuthHandler = var11;
                                             this.userTokenHandler = var12;
                                             this.params = var13;
                                             this.managedConn = null;
                                             this.execCount = 0;
                                             this.redirectCount = 0;
                                             this.maxRedirects = var13.getIntParameter("http.protocol.max-redirects", 100);
                                             this.targetAuthState = new AuthState();
                                             this.proxyAuthState = new AuthState();
                                          } else {
                                             throw new IllegalArgumentException("HTTP parameters may not be null");
                                          }
                                       } else {
                                          throw new IllegalArgumentException("User token handler may not be null.");
                                       }
                                    } else {
                                       throw new IllegalArgumentException("Proxy authentication handler may not be null.");
                                    }
                                 } else {
                                    throw new IllegalArgumentException("Target authentication handler may not be null.");
                                 }
                              } else {
                                 throw new IllegalArgumentException("Redirect strategy may not be null.");
                              }
                           } else {
                              throw new IllegalArgumentException("HTTP request retry handler may not be null.");
                           }
                        } else {
                           throw new IllegalArgumentException("HTTP protocol processor may not be null.");
                        }
                     } else {
                        throw new IllegalArgumentException("Route planner may not be null.");
                     }
                  } else {
                     throw new IllegalArgumentException("Connection keep alive strategy may not be null.");
                  }
               } else {
                  throw new IllegalArgumentException("Connection reuse strategy may not be null.");
               }
            } else {
               throw new IllegalArgumentException("Client connection manager may not be null.");
            }
         } else {
            throw new IllegalArgumentException("Request executor may not be null.");
         }
      } else {
         throw new IllegalArgumentException("Log may not be null.");
      }
   }

   @Deprecated
   public DefaultRequestDirector(HttpRequestExecutor var1, ClientConnectionManager var2, ConnectionReuseStrategy var3, ConnectionKeepAliveStrategy var4, HttpRoutePlanner var5, HttpProcessor var6, HttpRequestRetryHandler var7, RedirectHandler var8, AuthenticationHandler var9, AuthenticationHandler var10, UserTokenHandler var11, HttpParams var12) {
      this(LogFactory.getLog(DefaultRequestDirector.class), var1, var2, var3, var4, var5, var6, var7, new DefaultRedirectStrategyAdaptor(var8), var9, var10, var11, var12);
   }

   private void abortConnection() {
      ManagedClientConnection var1 = this.managedConn;
      if (var1 != null) {
         this.managedConn = null;

         try {
            var1.abortConnection();
         } catch (IOException var4) {
            if (this.log.isDebugEnabled()) {
               this.log.debug(var4.getMessage(), var4);
            }
         }

         try {
            var1.releaseConnection();
         } catch (IOException var3) {
            this.log.debug("Error releasing connection", var3);
         }
      }

   }

   private void invalidateAuthIfSuccessful(AuthState var1) {
      AuthScheme var2 = var1.getAuthScheme();
      if (var2 != null && var2.isConnectionBased() && var2.isComplete() && var1.getCredentials() != null) {
         var1.invalidate();
      }

   }

   private void processChallenges(Map<String, Header> var1, AuthState var2, AuthenticationHandler var3, HttpResponse var4, HttpContext var5) throws MalformedChallengeException, AuthenticationException {
      AuthScheme var6 = var2.getAuthScheme();
      AuthScheme var7 = var6;
      if (var6 == null) {
         var7 = var3.selectScheme(var1, var4, var5);
         var2.setAuthScheme(var7);
      }

      String var10 = var7.getSchemeName();
      Header var8 = (Header)var1.get(var10.toLowerCase(Locale.ENGLISH));
      if (var8 != null) {
         var7.processChallenge(var8);
         this.log.debug("Authorization challenge processed");
      } else {
         StringBuilder var9 = new StringBuilder();
         var9.append(var10);
         var9.append(" authorization challenge expected, but not found");
         throw new AuthenticationException(var9.toString());
      }
   }

   private void tryConnect(RoutedRequest var1, HttpContext var2) throws HttpException, IOException {
      HttpRoute var3 = var1.getRoute();
      int var4 = 0;

      while(true) {
         ++var4;

         IOException var10000;
         label60: {
            boolean var10001;
            label52: {
               try {
                  if (!this.managedConn.isOpen()) {
                     this.managedConn.open(var3, var2, this.params);
                     break label52;
                  }
               } catch (IOException var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label60;
               }

               try {
                  this.managedConn.setSocketTimeout(HttpConnectionParams.getSoTimeout(this.params));
               } catch (IOException var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label60;
               }
            }

            try {
               this.establishRoute(var3, var2);
               return;
            } catch (IOException var8) {
               var10000 = var8;
               var10001 = false;
            }
         }

         IOException var11 = var10000;

         try {
            this.managedConn.close();
         } catch (IOException var7) {
         }

         if (!this.retryHandler.retryRequest(var11, var4, var2)) {
            throw var11;
         }

         if (this.log.isInfoEnabled()) {
            Log var6 = this.log;
            StringBuilder var5 = new StringBuilder();
            var5.append("I/O exception (");
            var5.append(var11.getClass().getName());
            var5.append(") caught when connecting to the target host: ");
            var5.append(var11.getMessage());
            var6.info(var5.toString());
         }

         if (this.log.isDebugEnabled()) {
            this.log.debug(var11.getMessage(), var11);
         }

         this.log.info("Retrying connect");
      }
   }

   private HttpResponse tryExecute(RoutedRequest var1, HttpContext var2) throws HttpException, IOException {
      RequestWrapper var3 = var1.getRequest();
      HttpRoute var4 = var1.getRoute();
      Object var5 = null;
      IOException var13 = null;

      HttpResponse var15;
      while(true) {
         ++this.execCount;
         var3.incrementExecCount();
         if (!var3.isRepeatable()) {
            this.log.debug("Cannot retry non-repeatable request");
            if (var13 != null) {
               throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.  The cause lists the reason the original request failed.", var13);
            }

            throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
         }

         IOException var10000;
         label83: {
            boolean var10001;
            label87: {
               label72:
               try {
                  if (!this.managedConn.isOpen()) {
                     if (var4.isTunnelled()) {
                        break label72;
                     }

                     this.log.debug("Reopening the direct connection.");
                     this.managedConn.open(var4, var2, this.params);
                  }
                  break label87;
               } catch (IOException var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label83;
               }

               try {
                  this.log.debug("Proxied connection. Need to start over.");
               } catch (IOException var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label83;
               }

               var15 = (HttpResponse)var5;
               break;
            }

            try {
               if (this.log.isDebugEnabled()) {
                  Log var6 = this.log;
                  StringBuilder var14 = new StringBuilder();
                  var14.append("Attempt ");
                  var14.append(this.execCount);
                  var14.append(" to execute request");
                  var6.debug(var14.toString());
               }
            } catch (IOException var11) {
               var10000 = var11;
               var10001 = false;
               break label83;
            }

            try {
               var15 = this.requestExec.execute(var3, this.managedConn, var2);
               break;
            } catch (IOException var10) {
               var10000 = var10;
               var10001 = false;
            }
         }

         var13 = var10000;
         this.log.debug("Closing the connection.");

         try {
            this.managedConn.close();
         } catch (IOException var8) {
         }

         if (!this.retryHandler.retryRequest(var13, var3.getExecCount(), var2)) {
            throw var13;
         }

         if (this.log.isInfoEnabled()) {
            Log var7 = this.log;
            StringBuilder var16 = new StringBuilder();
            var16.append("I/O exception (");
            var16.append(var13.getClass().getName());
            var16.append(") caught when processing request: ");
            var16.append(var13.getMessage());
            var7.info(var16.toString());
         }

         if (this.log.isDebugEnabled()) {
            this.log.debug(var13.getMessage(), var13);
         }

         this.log.info("Retrying request");
      }

      return var15;
   }

   private void updateAuthState(AuthState var1, HttpHost var2, CredentialsProvider var3) {
      if (var1.isValid()) {
         String var4 = var2.getHostName();
         int var5 = var2.getPort();
         int var6 = var5;
         if (var5 < 0) {
            var6 = this.connManager.getSchemeRegistry().getScheme(var2).getDefaultPort();
         }

         AuthScheme var7 = var1.getAuthScheme();
         AuthScope var12 = new AuthScope(var4, var6, var7.getRealm(), var7.getSchemeName());
         if (this.log.isDebugEnabled()) {
            Log var9 = this.log;
            StringBuilder var8 = new StringBuilder();
            var8.append("Authentication scope: ");
            var8.append(var12);
            var9.debug(var8.toString());
         }

         Credentials var10 = var1.getCredentials();
         if (var10 == null) {
            Credentials var11 = var3.getCredentials(var12);
            var10 = var11;
            if (this.log.isDebugEnabled()) {
               if (var11 != null) {
                  this.log.debug("Found credentials");
                  var10 = var11;
               } else {
                  this.log.debug("Credentials not found");
                  var10 = var11;
               }
            }
         } else if (var7.isComplete()) {
            this.log.debug("Authentication failed");
            var10 = null;
         }

         var1.setAuthScope(var12);
         var1.setCredentials(var10);
      }
   }

   private RequestWrapper wrapRequest(HttpRequest var1) throws ProtocolException {
      return (RequestWrapper)(var1 instanceof HttpEntityEnclosingRequest ? new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)var1) : new RequestWrapper(var1));
   }

   protected HttpRequest createConnectRequest(HttpRoute var1, HttpContext var2) {
      HttpHost var6 = var1.getTargetHost();
      String var5 = var6.getHostName();
      int var3 = var6.getPort();
      int var4 = var3;
      if (var3 < 0) {
         var4 = this.connManager.getSchemeRegistry().getScheme(var6.getSchemeName()).getDefaultPort();
      }

      StringBuilder var7 = new StringBuilder(var5.length() + 6);
      var7.append(var5);
      var7.append(':');
      var7.append(Integer.toString(var4));
      return new BasicHttpRequest("CONNECT", var7.toString(), HttpProtocolParams.getVersion(this.params));
   }

   protected boolean createTunnelToProxy(HttpRoute var1, int var2, HttpContext var3) throws HttpException, IOException {
      throw new HttpException("Proxy chains are not supported.");
   }

   protected boolean createTunnelToTarget(HttpRoute var1, HttpContext var2) throws HttpException, IOException {
      HttpHost var3 = var1.getProxyHost();
      HttpHost var4 = var1.getTargetHost();
      HttpResponse var5 = null;
      boolean var6 = false;

      StringBuilder var13;
      while(!var6) {
         if (!this.managedConn.isOpen()) {
            this.managedConn.open(var1, var2, this.params);
         }

         HttpRequest var16 = this.createConnectRequest(var1, var2);
         var16.setParams(this.params);
         var2.setAttribute("http.target_host", var4);
         var2.setAttribute("http.proxy_host", var3);
         var2.setAttribute("http.connection", this.managedConn);
         var2.setAttribute("http.auth.target-scope", this.targetAuthState);
         var2.setAttribute("http.auth.proxy-scope", this.proxyAuthState);
         var2.setAttribute("http.request", var16);
         this.requestExec.preProcess(var16, this.httpProcessor, var2);
         var5 = this.requestExec.execute(var16, this.managedConn, var2);
         var5.setParams(this.params);
         this.requestExec.postProcess(var5, this.httpProcessor, var2);
         if (var5.getStatusLine().getStatusCode() < 200) {
            var13 = new StringBuilder();
            var13.append("Unexpected response to CONNECT request: ");
            var13.append(var5.getStatusLine());
            throw new HttpException(var13.toString());
         }

         CredentialsProvider var7 = (CredentialsProvider)var2.getAttribute("http.auth.credentials-provider");
         if (var7 != null && HttpClientParams.isAuthenticating(this.params)) {
            if (this.proxyAuthHandler.isAuthenticationRequested(var5, var2)) {
               this.log.debug("Proxy requested authentication");
               Map var8 = this.proxyAuthHandler.getChallenges(var5, var2);

               label56: {
                  AuthenticationException var10;
                  label55: {
                     AuthState var9;
                     AuthenticationHandler var17;
                     try {
                        var9 = this.proxyAuthState;
                        var17 = this.proxyAuthHandler;
                     } catch (AuthenticationException var12) {
                        var10 = var12;
                        break label55;
                     }

                     try {
                        this.processChallenges(var8, var9, var17, var5, var2);
                        break label56;
                     } catch (AuthenticationException var11) {
                        var10 = var11;
                     }
                  }

                  if (this.log.isWarnEnabled()) {
                     Log var15 = this.log;
                     var13 = new StringBuilder();
                     var13.append("Authentication error: ");
                     var13.append(var10.getMessage());
                     var15.warn(var13.toString());
                     break;
                  }
               }

               this.updateAuthState(this.proxyAuthState, var3, var7);
               if (this.proxyAuthState.getCredentials() != null) {
                  if (this.reuseStrategy.keepAlive(var5, var2)) {
                     this.log.debug("Connection kept alive");
                     EntityUtils.consume(var5.getEntity());
                  } else {
                     this.managedConn.close();
                  }

                  var6 = false;
               } else {
                  var6 = true;
               }
               continue;
            }

            this.proxyAuthState.setAuthScope((AuthScope)null);
         }

         var6 = true;
      }

      if (var5.getStatusLine().getStatusCode() > 299) {
         HttpEntity var14 = var5.getEntity();
         if (var14 != null) {
            var5.setEntity(new BufferedHttpEntity(var14));
         }

         this.managedConn.close();
         var13 = new StringBuilder();
         var13.append("CONNECT refused by proxy: ");
         var13.append(var5.getStatusLine());
         throw new TunnelRefusedException(var13.toString(), var5);
      } else {
         this.managedConn.markReusable();
         return false;
      }
   }

   protected HttpRoute determineRoute(HttpHost var1, HttpRequest var2, HttpContext var3) throws HttpException {
      HttpHost var4 = var1;
      if (var1 == null) {
         var4 = (HttpHost)var2.getParams().getParameter("http.default-host");
      }

      if (var4 != null) {
         return this.routePlanner.determineRoute(var4, var2, var3);
      } else {
         throw new IllegalStateException("Target host must not be null, or set in parameters.");
      }
   }

   protected void establishRoute(HttpRoute var1, HttpContext var2) throws HttpException, IOException {
      BasicRouteDirector var3 = new BasicRouteDirector();

      int var5;
      do {
         HttpRoute var4 = this.managedConn.getRoute();
         var5 = var3.nextStep(var1, var4);
         boolean var7;
         switch(var5) {
         case -1:
            StringBuilder var9 = new StringBuilder();
            var9.append("Unable to establish route: planned = ");
            var9.append(var1);
            var9.append("; current = ");
            var9.append(var4);
            throw new HttpException(var9.toString());
         case 0:
            break;
         case 1:
         case 2:
            this.managedConn.open(var1, var2, this.params);
            break;
         case 3:
            var7 = this.createTunnelToTarget(var1, var2);
            this.log.debug("Tunnel to target created.");
            this.managedConn.tunnelTarget(var7, this.params);
            break;
         case 4:
            int var6 = var4.getHopCount() - 1;
            var7 = this.createTunnelToProxy(var1, var6, var2);
            this.log.debug("Tunnel to proxy created.");
            this.managedConn.tunnelProxy(var1.getHopTarget(var6), var7, this.params);
            break;
         case 5:
            this.managedConn.layerProtocol(var2, this.params);
            break;
         default:
            StringBuilder var8 = new StringBuilder();
            var8.append("Unknown step indicator ");
            var8.append(var5);
            var8.append(" from RouteDirector.");
            throw new IllegalStateException(var8.toString());
         }
      } while(var5 > 0);

   }

   public HttpResponse execute(HttpHost param1, HttpRequest param2, HttpContext param3) throws HttpException, IOException {
      // $FF: Couldn't be decompiled
   }

   protected RoutedRequest handleResponse(RoutedRequest var1, HttpResponse var2, HttpContext var3) throws HttpException, IOException {
      HttpRoute var4 = var1.getRoute();
      RequestWrapper var5 = var1.getRequest();
      HttpParams var6 = var5.getParams();
      StringBuilder var11;
      HttpHost var22;
      if (HttpClientParams.isRedirecting(var6) && this.redirectStrategy.isRedirected(var5, var2, var3)) {
         int var7 = this.redirectCount;
         if (var7 < this.maxRedirects) {
            this.redirectCount = var7 + 1;
            this.virtualHost = null;
            HttpUriRequest var13 = this.redirectStrategy.getRedirect(var5, var2, var3);
            var13.setHeaders(var5.getOriginal().getAllHeaders());
            URI var14 = var13.getURI();
            if (var14.getHost() != null) {
               var22 = new HttpHost(var14.getHost(), var14.getPort(), var14.getScheme());
               this.targetAuthState.setAuthScope((AuthScope)null);
               this.proxyAuthState.setAuthScope((AuthScope)null);
               if (!var4.getTargetHost().equals(var22)) {
                  this.targetAuthState.invalidate();
                  AuthScheme var26 = this.proxyAuthState.getAuthScheme();
                  if (var26 != null && var26.isConnectionBased()) {
                     this.proxyAuthState.invalidate();
                  }
               }

               RequestWrapper var16 = this.wrapRequest(var13);
               var16.setParams(var6);
               HttpRoute var20 = this.determineRoute(var22, var16, var3);
               RoutedRequest var17 = new RoutedRequest(var16, var20);
               if (this.log.isDebugEnabled()) {
                  Log var25 = this.log;
                  StringBuilder var24 = new StringBuilder();
                  var24.append("Redirecting to '");
                  var24.append(var14);
                  var24.append("' via ");
                  var24.append(var20);
                  var25.debug(var24.toString());
               }

               return var17;
            } else {
               StringBuilder var15 = new StringBuilder();
               var15.append("Redirect URI does not specify a valid host name: ");
               var15.append(var14);
               throw new ProtocolException(var15.toString());
            }
         } else {
            var11 = new StringBuilder();
            var11.append("Maximum redirects (");
            var11.append(this.maxRedirects);
            var11.append(") exceeded");
            throw new RedirectException(var11.toString());
         }
      } else {
         CredentialsProvider var8 = (CredentialsProvider)var3.getAttribute("http.auth.credentials-provider");
         if (var8 != null && HttpClientParams.isAuthenticating(var6)) {
            Map var21;
            HttpHost var23;
            if (this.targetAuthHandler.isAuthenticationRequested(var2, var3)) {
               var22 = (HttpHost)var3.getAttribute("http.target_host");
               var23 = var22;
               if (var22 == null) {
                  var23 = var4.getTargetHost();
               }

               this.log.debug("Target requested authentication");
               var21 = this.targetAuthHandler.getChallenges(var2, var3);

               try {
                  this.processChallenges(var21, this.targetAuthState, this.targetAuthHandler, var2, var3);
               } catch (AuthenticationException var9) {
                  if (this.log.isWarnEnabled()) {
                     Log var12 = this.log;
                     StringBuilder var19 = new StringBuilder();
                     var19.append("Authentication error: ");
                     var19.append(var9.getMessage());
                     var12.warn(var19.toString());
                     return null;
                  }
               }

               this.updateAuthState(this.targetAuthState, var23, var8);
               if (this.targetAuthState.getCredentials() != null) {
                  return var1;
               }

               return null;
            }

            this.targetAuthState.setAuthScope((AuthScope)null);
            if (this.proxyAuthHandler.isAuthenticationRequested(var2, var3)) {
               var23 = var4.getProxyHost();
               this.log.debug("Proxy requested authentication");
               var21 = this.proxyAuthHandler.getChallenges(var2, var3);

               try {
                  this.processChallenges(var21, this.proxyAuthState, this.proxyAuthHandler, var2, var3);
               } catch (AuthenticationException var10) {
                  if (this.log.isWarnEnabled()) {
                     Log var18 = this.log;
                     var11 = new StringBuilder();
                     var11.append("Authentication error: ");
                     var11.append(var10.getMessage());
                     var18.warn(var11.toString());
                     return null;
                  }
               }

               this.updateAuthState(this.proxyAuthState, var23, var8);
               if (this.proxyAuthState.getCredentials() != null) {
                  return var1;
               }

               return null;
            }

            this.proxyAuthState.setAuthScope((AuthScope)null);
         }

         return null;
      }
   }

   protected void releaseConnection() {
      try {
         this.managedConn.releaseConnection();
      } catch (IOException var2) {
         this.log.debug("IOException releasing connection", var2);
      }

      this.managedConn = null;
   }

   protected void rewriteRequestURI(RequestWrapper var1, HttpRoute var2) throws ProtocolException {
      try {
         URI var5 = var1.getURI();
         if (var2.getProxyHost() != null && !var2.isTunnelled()) {
            if (!var5.isAbsolute()) {
               var1.setURI(URIUtils.rewriteURI(var5, var2.getTargetHost()));
            }
         } else if (var5.isAbsolute()) {
            var1.setURI(URIUtils.rewriteURI(var5, (HttpHost)null));
         }

      } catch (URISyntaxException var4) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Invalid URI: ");
         var3.append(var1.getRequestLine().getUri());
         throw new ProtocolException(var3.toString(), var4);
      }
   }
}
