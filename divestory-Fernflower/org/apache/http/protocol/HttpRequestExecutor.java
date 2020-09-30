package org.apache.http.protocol;

import java.io.IOException;
import java.net.ProtocolException;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;

public class HttpRequestExecutor {
   private static final void closeConnection(HttpClientConnection var0) {
      try {
         var0.close();
      } catch (IOException var1) {
      }

   }

   protected boolean canResponseHaveBody(HttpRequest var1, HttpResponse var2) {
      boolean var3 = "HEAD".equalsIgnoreCase(var1.getRequestLine().getMethod());
      boolean var4 = false;
      if (var3) {
         return false;
      } else {
         int var5 = var2.getStatusLine().getStatusCode();
         var3 = var4;
         if (var5 >= 200) {
            var3 = var4;
            if (var5 != 204) {
               var3 = var4;
               if (var5 != 304) {
                  var3 = var4;
                  if (var5 != 205) {
                     var3 = true;
                  }
               }
            }
         }

         return var3;
      }
   }

   protected HttpResponse doReceiveResponse(HttpRequest var1, HttpClientConnection var2, HttpContext var3) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 != null) {
            if (var3 != null) {
               HttpResponse var5 = null;

               for(int var4 = 0; var5 == null || var4 < 200; var4 = var5.getStatusLine().getStatusCode()) {
                  var5 = var2.receiveResponseHeader();
                  if (this.canResponseHaveBody(var1, var5)) {
                     var2.receiveResponseEntity(var5);
                  }
               }

               return var5;
            } else {
               throw new IllegalArgumentException("HTTP context may not be null");
            }
         } else {
            throw new IllegalArgumentException("HTTP connection may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }

   protected HttpResponse doSendRequest(HttpRequest var1, HttpClientConnection var2, HttpContext var3) throws IOException, HttpException {
      if (var1 != null) {
         if (var2 != null) {
            if (var3 != null) {
               var3.setAttribute("http.connection", var2);
               var3.setAttribute("http.request_sent", Boolean.FALSE);
               var2.sendRequestHeader(var1);
               boolean var4 = var1 instanceof HttpEntityEnclosingRequest;
               HttpResponse var5 = null;
               Object var6 = null;
               if (var4) {
                  boolean var7 = true;
                  ProtocolVersion var12 = var1.getRequestLine().getProtocolVersion();
                  HttpEntityEnclosingRequest var8 = (HttpEntityEnclosingRequest)var1;
                  boolean var9 = var7;
                  HttpResponse var10 = (HttpResponse)var6;
                  if (var8.expectContinue()) {
                     var9 = var7;
                     var10 = (HttpResponse)var6;
                     if (!var12.lessEquals(HttpVersion.HTTP_1_0)) {
                        var2.flush();
                        var9 = var7;
                        var10 = (HttpResponse)var6;
                        if (var2.isResponseAvailable(var1.getParams().getIntParameter("http.protocol.wait-for-continue", 2000))) {
                           var10 = var2.receiveResponseHeader();
                           if (this.canResponseHaveBody(var1, var10)) {
                              var2.receiveResponseEntity(var10);
                           }

                           int var13 = var10.getStatusLine().getStatusCode();
                           if (var13 < 200) {
                              if (var13 != 100) {
                                 StringBuffer var11 = new StringBuffer();
                                 var11.append("Unexpected response: ");
                                 var11.append(var10.getStatusLine());
                                 throw new ProtocolException(var11.toString());
                              }

                              var9 = var7;
                              var10 = (HttpResponse)var6;
                           } else {
                              var9 = false;
                           }
                        }
                     }
                  }

                  var5 = var10;
                  if (var9) {
                     var2.sendRequestEntity(var8);
                     var5 = var10;
                  }
               }

               var2.flush();
               var3.setAttribute("http.request_sent", Boolean.TRUE);
               return var5;
            } else {
               throw new IllegalArgumentException("HTTP context may not be null");
            }
         } else {
            throw new IllegalArgumentException("HTTP connection may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }

   public HttpResponse execute(HttpRequest var1, HttpClientConnection var2, HttpContext var3) throws IOException, HttpException {
      if (var1 != null) {
         if (var2 == null) {
            throw new IllegalArgumentException("Client connection may not be null");
         } else if (var3 != null) {
            IOException var16;
            label42: {
               HttpException var15;
               label41: {
                  RuntimeException var10000;
                  label51: {
                     HttpResponse var4;
                     boolean var10001;
                     try {
                        var4 = this.doSendRequest(var1, var2, var3);
                     } catch (IOException var9) {
                        var16 = var9;
                        var10001 = false;
                        break label42;
                     } catch (HttpException var10) {
                        var15 = var10;
                        var10001 = false;
                        break label41;
                     } catch (RuntimeException var11) {
                        var10000 = var11;
                        var10001 = false;
                        break label51;
                     }

                     HttpResponse var5 = var4;
                     if (var4 != null) {
                        return var5;
                     }

                     try {
                        var5 = this.doReceiveResponse(var1, var2, var3);
                        return var5;
                     } catch (IOException var6) {
                        var16 = var6;
                        var10001 = false;
                        break label42;
                     } catch (HttpException var7) {
                        var15 = var7;
                        var10001 = false;
                        break label41;
                     } catch (RuntimeException var8) {
                        var10000 = var8;
                        var10001 = false;
                     }
                  }

                  RuntimeException var12 = var10000;
                  closeConnection(var2);
                  throw var12;
               }

               HttpException var13 = var15;
               closeConnection(var2);
               throw var13;
            }

            IOException var14 = var16;
            closeConnection(var2);
            throw var14;
         } else {
            throw new IllegalArgumentException("HTTP context may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }

   public void postProcess(HttpResponse var1, HttpProcessor var2, HttpContext var3) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 != null) {
            if (var3 != null) {
               var3.setAttribute("http.response", var1);
               var2.process(var1, var3);
            } else {
               throw new IllegalArgumentException("HTTP context may not be null");
            }
         } else {
            throw new IllegalArgumentException("HTTP processor may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP response may not be null");
      }
   }

   public void preProcess(HttpRequest var1, HttpProcessor var2, HttpContext var3) throws HttpException, IOException {
      if (var1 != null) {
         if (var2 != null) {
            if (var3 != null) {
               var3.setAttribute("http.request", var1);
               var2.process(var1, var3);
            } else {
               throw new IllegalArgumentException("HTTP context may not be null");
            }
         } else {
            throw new IllegalArgumentException("HTTP processor may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP request may not be null");
      }
   }
}
