package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class DefaultClientConnectionOperator implements ClientConnectionOperator {
   private final Log log = LogFactory.getLog(this.getClass());
   protected final SchemeRegistry schemeRegistry;

   public DefaultClientConnectionOperator(SchemeRegistry var1) {
      if (var1 != null) {
         this.schemeRegistry = var1;
      } else {
         throw new IllegalArgumentException("Scheme registry amy not be null");
      }
   }

   public OperatedClientConnection createConnection() {
      return new DefaultClientConnection();
   }

   public void openConnection(OperatedClientConnection var1, HttpHost var2, InetAddress var3, HttpContext var4, HttpParams var5) throws IOException {
      if (var1 == null) {
         throw new IllegalArgumentException("Connection may not be null");
      } else if (var2 == null) {
         throw new IllegalArgumentException("Target host may not be null");
      } else if (var5 == null) {
         throw new IllegalArgumentException("Parameters may not be null");
      } else if (var1.isOpen()) {
         throw new IllegalStateException("Connection must not be open");
      } else {
         Scheme var6 = this.schemeRegistry.getScheme(var2.getSchemeName());
         SchemeSocketFactory var7 = var6.getSchemeSocketFactory();
         InetAddress[] var8 = this.resolveHostname(var2.getHostName());
         int var9 = var6.resolvePort(var2.getPort());

         for(int var10 = 0; var10 < var8.length; ++var10) {
            InetAddress var23 = var8[var10];
            int var11 = var8.length;
            boolean var12 = true;
            if (var10 != var11 - 1) {
               var12 = false;
            }

            Socket var13 = var7.createSocket(var5);
            var1.opening(var13, var2);
            HttpInetSocketAddress var14 = new HttpInetSocketAddress(var2, var23, var9);
            InetSocketAddress var24 = null;
            if (var3 != null) {
               var24 = new InetSocketAddress(var3, 0);
            }

            if (this.log.isDebugEnabled()) {
               Log var15 = this.log;
               StringBuilder var16 = new StringBuilder();
               var16.append("Connecting to ");
               var16.append(var14);
               var15.debug(var16.toString());
            }

            label95: {
               ConnectException var27;
               label94: {
                  ConnectException var30;
                  label93: {
                     ConnectTimeoutException var26;
                     label92: {
                        Socket var25;
                        label91: {
                           ConnectTimeoutException var10000;
                           label102: {
                              boolean var10001;
                              try {
                                 var25 = var7.connectSocket(var13, var14, var24, var5);
                              } catch (ConnectException var21) {
                                 var30 = var21;
                                 var10001 = false;
                                 break label93;
                              } catch (ConnectTimeoutException var22) {
                                 var10000 = var22;
                                 var10001 = false;
                                 break label102;
                              }

                              if (var13 == var25) {
                                 var25 = var13;
                                 break label91;
                              }

                              try {
                                 var1.opening(var25, var2);
                                 break label91;
                              } catch (ConnectException var19) {
                                 var30 = var19;
                                 var10001 = false;
                                 break label93;
                              } catch (ConnectTimeoutException var20) {
                                 var10000 = var20;
                                 var10001 = false;
                              }
                           }

                           var26 = var10000;
                           break label92;
                        }

                        try {
                           this.prepareSocket(var25, var4, var5);
                           var1.openCompleted(var7.isSecure(var25), var5);
                           return;
                        } catch (ConnectException var17) {
                           var27 = var17;
                           break label94;
                        } catch (ConnectTimeoutException var18) {
                           var26 = var18;
                        }
                     }

                     if (var12) {
                        throw var26;
                     }
                     break label95;
                  }

                  var27 = var30;
               }

               if (var12) {
                  throw new HttpHostConnectException(var2, var27);
               }
            }

            if (this.log.isDebugEnabled()) {
               Log var29 = this.log;
               StringBuilder var28 = new StringBuilder();
               var28.append("Connect to ");
               var28.append(var14);
               var28.append(" timed out. ");
               var28.append("Connection will be retried using another IP address");
               var29.debug(var28.toString());
            }
         }

      }
   }

   protected void prepareSocket(Socket var1, HttpContext var2, HttpParams var3) throws IOException {
      var1.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(var3));
      var1.setSoTimeout(HttpConnectionParams.getSoTimeout(var3));
      int var4 = HttpConnectionParams.getLinger(var3);
      if (var4 >= 0) {
         boolean var5;
         if (var4 > 0) {
            var5 = true;
         } else {
            var5 = false;
         }

         var1.setSoLinger(var5, var4);
      }

   }

   protected InetAddress[] resolveHostname(String var1) throws UnknownHostException {
      return InetAddress.getAllByName(var1);
   }

   public void updateSecureConnection(OperatedClientConnection var1, HttpHost var2, HttpContext var3, HttpParams var4) throws IOException {
      if (var1 != null) {
         if (var2 != null) {
            if (var4 != null) {
               if (var1.isOpen()) {
                  Scheme var5 = this.schemeRegistry.getScheme(var2.getSchemeName());
                  if (var5.getSchemeSocketFactory() instanceof LayeredSchemeSocketFactory) {
                     LayeredSchemeSocketFactory var9 = (LayeredSchemeSocketFactory)var5.getSchemeSocketFactory();

                     Socket var6;
                     try {
                        var6 = var9.createLayeredSocket(var1.getSocket(), var2.getHostName(), var2.getPort(), true);
                     } catch (ConnectException var7) {
                        throw new HttpHostConnectException(var2, var7);
                     }

                     this.prepareSocket(var6, var3, var4);
                     var1.update(var6, var2, var9.isSecure(var6), var4);
                  } else {
                     StringBuilder var8 = new StringBuilder();
                     var8.append("Target scheme (");
                     var8.append(var5.getName());
                     var8.append(") must have layered socket factory.");
                     throw new IllegalArgumentException(var8.toString());
                  }
               } else {
                  throw new IllegalStateException("Connection must be open");
               }
            } else {
               throw new IllegalArgumentException("Parameters may not be null");
            }
         } else {
            throw new IllegalArgumentException("Target host may not be null");
         }
      } else {
         throw new IllegalArgumentException("Connection may not be null");
      }
   }
}
