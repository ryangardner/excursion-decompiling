package org.apache.http.impl.conn.tsccm;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.HttpParams;

public class ThreadSafeClientConnManager implements ClientConnectionManager {
   protected final ClientConnectionOperator connOperator;
   protected final ConnPerRouteBean connPerRoute;
   @Deprecated
   protected final AbstractConnPool connectionPool;
   private final Log log;
   protected final ConnPoolByRoute pool;
   protected final SchemeRegistry schemeRegistry;

   public ThreadSafeClientConnManager() {
      this(SchemeRegistryFactory.createDefault());
   }

   public ThreadSafeClientConnManager(SchemeRegistry var1) {
      this(var1, -1L, TimeUnit.MILLISECONDS);
   }

   public ThreadSafeClientConnManager(SchemeRegistry var1, long var2, TimeUnit var4) {
      if (var1 != null) {
         this.log = LogFactory.getLog(this.getClass());
         this.schemeRegistry = var1;
         this.connPerRoute = new ConnPerRouteBean();
         this.connOperator = this.createConnectionOperator(var1);
         ConnPoolByRoute var5 = this.createConnectionPool(var2, var4);
         this.pool = var5;
         this.connectionPool = var5;
      } else {
         throw new IllegalArgumentException("Scheme registry may not be null");
      }
   }

   @Deprecated
   public ThreadSafeClientConnManager(HttpParams var1, SchemeRegistry var2) {
      if (var2 != null) {
         this.log = LogFactory.getLog(this.getClass());
         this.schemeRegistry = var2;
         this.connPerRoute = new ConnPerRouteBean();
         this.connOperator = this.createConnectionOperator(var2);
         ConnPoolByRoute var3 = (ConnPoolByRoute)this.createConnectionPool(var1);
         this.pool = var3;
         this.connectionPool = var3;
      } else {
         throw new IllegalArgumentException("Scheme registry may not be null");
      }
   }

   public void closeExpiredConnections() {
      this.log.debug("Closing expired connections");
      this.pool.closeExpiredConnections();
   }

   public void closeIdleConnections(long var1, TimeUnit var3) {
      if (this.log.isDebugEnabled()) {
         Log var4 = this.log;
         StringBuilder var5 = new StringBuilder();
         var5.append("Closing connections idle longer than ");
         var5.append(var1);
         var5.append(" ");
         var5.append(var3);
         var4.debug(var5.toString());
      }

      this.pool.closeIdleConnections(var1, var3);
   }

   protected ClientConnectionOperator createConnectionOperator(SchemeRegistry var1) {
      return new DefaultClientConnectionOperator(var1);
   }

   @Deprecated
   protected AbstractConnPool createConnectionPool(HttpParams var1) {
      return new ConnPoolByRoute(this.connOperator, var1);
   }

   protected ConnPoolByRoute createConnectionPool(long var1, TimeUnit var3) {
      return new ConnPoolByRoute(this.connOperator, this.connPerRoute, 20, var1, var3);
   }

   protected void finalize() throws Throwable {
      try {
         this.shutdown();
      } finally {
         super.finalize();
      }

   }

   public int getConnectionsInPool() {
      return this.pool.getConnectionsInPool();
   }

   public int getConnectionsInPool(HttpRoute var1) {
      return this.pool.getConnectionsInPool(var1);
   }

   public int getDefaultMaxPerRoute() {
      return this.connPerRoute.getDefaultMaxPerRoute();
   }

   public int getMaxForRoute(HttpRoute var1) {
      return this.connPerRoute.getMaxForRoute(var1);
   }

   public int getMaxTotal() {
      return this.pool.getMaxTotalConnections();
   }

   public SchemeRegistry getSchemeRegistry() {
      return this.schemeRegistry;
   }

   public void releaseConnection(ManagedClientConnection var1, long var2, TimeUnit var4) {
      if (!(var1 instanceof BasicPooledConnAdapter)) {
         throw new IllegalArgumentException("Connection class mismatch, connection not obtained from this manager.");
      } else {
         BasicPooledConnAdapter var5 = (BasicPooledConnAdapter)var1;
         if (var5.getPoolEntry() != null && var5.getManager() != this) {
            throw new IllegalArgumentException("Connection not obtained from this manager.");
         } else {
            synchronized(var5){}

            Throwable var10000;
            boolean var10001;
            Throwable var409;
            label2894: {
               BasicPoolEntry var6;
               try {
                  var6 = (BasicPoolEntry)var5.getPoolEntry();
               } catch (Throwable var407) {
                  var10000 = var407;
                  var10001 = false;
                  break label2894;
               }

               if (var6 == null) {
                  label2849:
                  try {
                     return;
                  } catch (Throwable var393) {
                     var10000 = var393;
                     var10001 = false;
                     break label2849;
                  }
               } else {
                  label2901: {
                     boolean var7;
                     ConnPoolByRoute var410;
                     label2902: {
                        label2903: {
                           label2904: {
                              IOException var408;
                              try {
                                 try {
                                    if (var5.isOpen() && !var5.isMarkedReusable()) {
                                       var5.shutdown();
                                    }
                                    break label2903;
                                 } catch (IOException var405) {
                                    var408 = var405;
                                 }
                              } catch (Throwable var406) {
                                 var10000 = var406;
                                 var10001 = false;
                                 break label2904;
                              }

                              try {
                                 if (this.log.isDebugEnabled()) {
                                    this.log.debug("Exception shutting down released connection.", var408);
                                 }
                              } catch (Throwable var404) {
                                 var10000 = var404;
                                 var10001 = false;
                                 break label2904;
                              }

                              label2876: {
                                 try {
                                    var7 = var5.isMarkedReusable();
                                    if (!this.log.isDebugEnabled()) {
                                       break label2876;
                                    }
                                 } catch (Throwable var403) {
                                    var10000 = var403;
                                    var10001 = false;
                                    break label2901;
                                 }

                                 if (var7) {
                                    try {
                                       this.log.debug("Released connection is reusable.");
                                    } catch (Throwable var398) {
                                       var10000 = var398;
                                       var10001 = false;
                                       break label2901;
                                    }
                                 } else {
                                    try {
                                       this.log.debug("Released connection is not reusable.");
                                    } catch (Throwable var397) {
                                       var10000 = var397;
                                       var10001 = false;
                                       break label2901;
                                    }
                                 }
                              }

                              try {
                                 var5.detach();
                                 var410 = this.pool;
                                 break label2902;
                              } catch (Throwable var396) {
                                 var10000 = var396;
                                 var10001 = false;
                                 break label2901;
                              }
                           }

                           var409 = var10000;

                           label2845: {
                              try {
                                 var7 = var5.isMarkedReusable();
                                 if (!this.log.isDebugEnabled()) {
                                    break label2845;
                                 }
                              } catch (Throwable var392) {
                                 var10000 = var392;
                                 var10001 = false;
                                 break label2901;
                              }

                              if (var7) {
                                 try {
                                    this.log.debug("Released connection is reusable.");
                                 } catch (Throwable var391) {
                                    var10000 = var391;
                                    var10001 = false;
                                    break label2901;
                                 }
                              } else {
                                 try {
                                    this.log.debug("Released connection is not reusable.");
                                 } catch (Throwable var390) {
                                    var10000 = var390;
                                    var10001 = false;
                                    break label2901;
                                 }
                              }
                           }

                           try {
                              var5.detach();
                              this.pool.freeEntry(var6, var7, var2, var4);
                              throw var409;
                           } catch (Throwable var389) {
                              var10000 = var389;
                              var10001 = false;
                              break label2901;
                           }
                        }

                        label2871: {
                           try {
                              var7 = var5.isMarkedReusable();
                              if (!this.log.isDebugEnabled()) {
                                 break label2871;
                              }
                           } catch (Throwable var402) {
                              var10000 = var402;
                              var10001 = false;
                              break label2901;
                           }

                           if (var7) {
                              try {
                                 this.log.debug("Released connection is reusable.");
                              } catch (Throwable var401) {
                                 var10000 = var401;
                                 var10001 = false;
                                 break label2901;
                              }
                           } else {
                              try {
                                 this.log.debug("Released connection is not reusable.");
                              } catch (Throwable var400) {
                                 var10000 = var400;
                                 var10001 = false;
                                 break label2901;
                              }
                           }
                        }

                        try {
                           var5.detach();
                           var410 = this.pool;
                        } catch (Throwable var399) {
                           var10000 = var399;
                           var10001 = false;
                           break label2901;
                        }
                     }

                     try {
                        var410.freeEntry(var6, var7, var2, var4);
                     } catch (Throwable var395) {
                        var10000 = var395;
                        var10001 = false;
                        break label2901;
                     }

                     label2851:
                     try {
                        return;
                     } catch (Throwable var394) {
                        var10000 = var394;
                        var10001 = false;
                        break label2851;
                     }
                  }
               }
            }

            while(true) {
               var409 = var10000;

               try {
                  throw var409;
               } catch (Throwable var388) {
                  var10000 = var388;
                  var10001 = false;
                  continue;
               }
            }
         }
      }
   }

   public ClientConnectionRequest requestConnection(final HttpRoute var1, Object var2) {
      return new ClientConnectionRequest(this.pool.requestPoolEntry(var1, var2)) {
         // $FF: synthetic field
         final PoolEntryRequest val$poolRequest;

         {
            this.val$poolRequest = var2;
         }

         public void abortRequest() {
            this.val$poolRequest.abortRequest();
         }

         public ManagedClientConnection getConnection(long var1x, TimeUnit var3) throws InterruptedException, ConnectionPoolTimeoutException {
            if (var1 != null) {
               if (ThreadSafeClientConnManager.this.log.isDebugEnabled()) {
                  Log var4 = ThreadSafeClientConnManager.this.log;
                  StringBuilder var5 = new StringBuilder();
                  var5.append("Get connection: ");
                  var5.append(var1);
                  var5.append(", timeout = ");
                  var5.append(var1x);
                  var4.debug(var5.toString());
               }

               BasicPoolEntry var6 = this.val$poolRequest.getPoolEntry(var1x, var3);
               return new BasicPooledConnAdapter(ThreadSafeClientConnManager.this, var6);
            } else {
               throw new IllegalArgumentException("Route may not be null.");
            }
         }
      };
   }

   public void setDefaultMaxPerRoute(int var1) {
      this.connPerRoute.setDefaultMaxPerRoute(var1);
   }

   public void setMaxForRoute(HttpRoute var1, int var2) {
      this.connPerRoute.setMaxForRoute(var1, var2);
   }

   public void setMaxTotal(int var1) {
      this.pool.setMaxTotalConnections(var1);
   }

   public void shutdown() {
      this.log.debug("Shutting down");
      this.pool.shutdown();
   }
}
