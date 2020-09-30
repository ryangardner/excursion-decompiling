package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.HttpVersion;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.UnsupportedHttpVersionException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

public class HttpService {
   private volatile ConnectionReuseStrategy connStrategy;
   private volatile HttpExpectationVerifier expectationVerifier;
   private volatile HttpRequestHandlerResolver handlerResolver;
   private volatile HttpParams params;
   private volatile HttpProcessor processor;
   private volatile HttpResponseFactory responseFactory;

   public HttpService(HttpProcessor var1, ConnectionReuseStrategy var2, HttpResponseFactory var3) {
      this.params = null;
      this.processor = null;
      this.handlerResolver = null;
      this.connStrategy = null;
      this.responseFactory = null;
      this.expectationVerifier = null;
      this.setHttpProcessor(var1);
      this.setConnReuseStrategy(var2);
      this.setResponseFactory(var3);
   }

   public HttpService(HttpProcessor var1, ConnectionReuseStrategy var2, HttpResponseFactory var3, HttpRequestHandlerResolver var4, HttpParams var5) {
      this(var1, var2, var3, var4, (HttpExpectationVerifier)null, var5);
   }

   public HttpService(HttpProcessor var1, ConnectionReuseStrategy var2, HttpResponseFactory var3, HttpRequestHandlerResolver var4, HttpExpectationVerifier var5, HttpParams var6) {
      this.params = null;
      this.processor = null;
      this.handlerResolver = null;
      this.connStrategy = null;
      this.responseFactory = null;
      this.expectationVerifier = null;
      if (var1 != null) {
         if (var2 != null) {
            if (var3 != null) {
               if (var6 != null) {
                  this.processor = var1;
                  this.connStrategy = var2;
                  this.responseFactory = var3;
                  this.handlerResolver = var4;
                  this.expectationVerifier = var5;
                  this.params = var6;
               } else {
                  throw new IllegalArgumentException("HTTP parameters may not be null");
               }
            } else {
               throw new IllegalArgumentException("Response factory may not be null");
            }
         } else {
            throw new IllegalArgumentException("Connection reuse strategy may not be null");
         }
      } else {
         throw new IllegalArgumentException("HTTP processor may not be null");
      }
   }

   protected void doService(HttpRequest var1, HttpResponse var2, HttpContext var3) throws HttpException, IOException {
      HttpRequestHandler var5;
      if (this.handlerResolver != null) {
         String var4 = var1.getRequestLine().getUri();
         var5 = this.handlerResolver.lookup(var4);
      } else {
         var5 = null;
      }

      if (var5 != null) {
         var5.handle(var1, var2, var3);
      } else {
         var2.setStatusCode(501);
      }

   }

   public HttpParams getParams() {
      return this.params;
   }

   protected void handleException(HttpException var1, HttpResponse var2) {
      if (var1 instanceof MethodNotSupportedException) {
         var2.setStatusCode(501);
      } else if (var1 instanceof UnsupportedHttpVersionException) {
         var2.setStatusCode(505);
      } else if (var1 instanceof ProtocolException) {
         var2.setStatusCode(400);
      } else {
         var2.setStatusCode(500);
      }

      ByteArrayEntity var3 = new ByteArrayEntity(EncodingUtils.getAsciiBytes(var1.getMessage()));
      var3.setContentType("text/plain; charset=US-ASCII");
      var2.setEntity(var3);
   }

   public void handleRequest(HttpServerConnection var1, HttpContext var2) throws IOException, HttpException {
      var2.setAttribute("http.connection", var1);

      HttpResponse var8;
      label109: {
         HttpResponse var21;
         label108: {
            HttpException var10000;
            label113: {
               HttpRequest var3;
               DefaultedHttpParams var4;
               boolean var10001;
               ProtocolVersion var20;
               try {
                  var3 = var1.receiveRequestHeader();
                  var4 = new DefaultedHttpParams(var3.getParams(), this.params);
                  var3.setParams(var4);
                  var20 = var3.getRequestLine().getProtocolVersion();
               } catch (HttpException var19) {
                  var10000 = var19;
                  var10001 = false;
                  break label113;
               }

               Object var5 = var20;

               try {
                  if (!var20.lessEquals(HttpVersion.HTTP_1_1)) {
                     var5 = HttpVersion.HTTP_1_1;
                  }
               } catch (HttpException var18) {
                  var10000 = var18;
                  var10001 = false;
                  break label113;
               }

               boolean var6;
               try {
                  var6 = var3 instanceof HttpEntityEnclosingRequest;
               } catch (HttpException var17) {
                  var10000 = var17;
                  var10001 = false;
                  break label113;
               }

               Object var7 = null;
               var8 = (HttpResponse)var7;
               DefaultedHttpParams var23;
               if (var6) {
                  label114: {
                     label118: {
                        HttpExpectationVerifier var9;
                        try {
                           if (!((HttpEntityEnclosingRequest)var3).expectContinue()) {
                              break label118;
                           }

                           var8 = this.responseFactory.newHttpResponse((ProtocolVersion)var5, 100, var2);
                           var4 = new DefaultedHttpParams(var8.getParams(), this.params);
                           var8.setParams(var4);
                           var9 = this.expectationVerifier;
                        } catch (HttpException var16) {
                           var10000 = var16;
                           var10001 = false;
                           break label113;
                        }

                        var21 = var8;
                        if (var9 != null) {
                           label117: {
                              try {
                                 this.expectationVerifier.verify(var3, var8, var2);
                              } catch (HttpException var15) {
                                 HttpException var24 = var15;

                                 try {
                                    var21 = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, var2);
                                    var23 = new DefaultedHttpParams(var21.getParams(), this.params);
                                    var21.setParams(var23);
                                    this.handleException(var24, var21);
                                    break label117;
                                 } catch (HttpException var13) {
                                    var10000 = var13;
                                    var10001 = false;
                                    break label113;
                                 }
                              }

                              var21 = var8;
                           }
                        }

                        label81: {
                           try {
                              if (var21.getStatusLine().getStatusCode() < 200) {
                                 var1.sendResponseHeader(var21);
                                 var1.flush();
                                 var1.receiveRequestEntity((HttpEntityEnclosingRequest)var3);
                                 break label81;
                              }
                           } catch (HttpException var14) {
                              var10000 = var14;
                              var10001 = false;
                              break label113;
                           }

                           var8 = var21;
                           break label114;
                        }

                        var8 = (HttpResponse)var7;
                        break label114;
                     }

                     try {
                        var1.receiveRequestEntity((HttpEntityEnclosingRequest)var3);
                     } catch (HttpException var12) {
                        var10000 = var12;
                        var10001 = false;
                        break label113;
                     }

                     var8 = (HttpResponse)var7;
                  }
               }

               var21 = var8;
               if (var8 == null) {
                  try {
                     var21 = this.responseFactory.newHttpResponse((ProtocolVersion)var5, 200, var2);
                     var23 = new DefaultedHttpParams(var21.getParams(), this.params);
                     var21.setParams(var23);
                     var2.setAttribute("http.request", var3);
                     var2.setAttribute("http.response", var21);
                     this.processor.process(var3, var2);
                     this.doService(var3, var21, var2);
                  } catch (HttpException var11) {
                     var10000 = var11;
                     var10001 = false;
                     break label113;
                  }
               }

               var8 = var21;

               try {
                  if (!(var3 instanceof HttpEntityEnclosingRequest)) {
                     break label109;
                  }

                  EntityUtils.consume(((HttpEntityEnclosingRequest)var3).getEntity());
                  break label108;
               } catch (HttpException var10) {
                  var10000 = var10;
                  var10001 = false;
               }
            }

            HttpException var22 = var10000;
            var8 = this.responseFactory.newHttpResponse(HttpVersion.HTTP_1_0, 500, var2);
            var8.setParams(new DefaultedHttpParams(var8.getParams(), this.params));
            this.handleException(var22, var8);
            break label109;
         }

         var8 = var21;
      }

      this.processor.process(var8, var2);
      var1.sendResponseHeader(var8);
      var1.sendResponseEntity(var8);
      var1.flush();
      if (!this.connStrategy.keepAlive(var8, var2)) {
         var1.close();
      }

   }

   public void setConnReuseStrategy(ConnectionReuseStrategy var1) {
      if (var1 != null) {
         this.connStrategy = var1;
      } else {
         throw new IllegalArgumentException("Connection reuse strategy may not be null");
      }
   }

   public void setExpectationVerifier(HttpExpectationVerifier var1) {
      this.expectationVerifier = var1;
   }

   public void setHandlerResolver(HttpRequestHandlerResolver var1) {
      this.handlerResolver = var1;
   }

   public void setHttpProcessor(HttpProcessor var1) {
      if (var1 != null) {
         this.processor = var1;
      } else {
         throw new IllegalArgumentException("HTTP processor may not be null");
      }
   }

   public void setParams(HttpParams var1) {
      this.params = var1;
   }

   public void setResponseFactory(HttpResponseFactory var1) {
      if (var1 != null) {
         this.responseFactory = var1;
      } else {
         throw new IllegalArgumentException("Response factory may not be null");
      }
   }
}
