package com.google.api.client.auth.oauth2;

import com.google.api.client.http.HttpMediaType;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Strings;
import java.io.IOException;

public class TokenResponseException extends HttpResponseException {
   private static final long serialVersionUID = 4020689092957439244L;
   private final transient TokenErrorResponse details;

   TokenResponseException(HttpResponseException.Builder var1, TokenErrorResponse var2) {
      super(var1);
      this.details = var2;
   }

   public static TokenResponseException from(JsonFactory var0, HttpResponse var1) {
      HttpResponseException.Builder var2 = new HttpResponseException.Builder(var1.getStatusCode(), var1.getStatusMessage(), var1.getHeaders());
      Preconditions.checkNotNull(var0);
      String var3 = var1.getContentType();
      String var4 = null;
      Object var5 = null;

      String var11;
      TokenErrorResponse var15;
      label53: {
         TokenErrorResponse var10;
         label52: {
            IOException var14;
            label51: {
               label50: {
                  IOException var10000;
                  label57: {
                     boolean var10001;
                     label48: {
                        try {
                           if (var1.isSuccessStatusCode()) {
                              break label48;
                           }
                        } catch (IOException var9) {
                           var10000 = var9;
                           var10001 = false;
                           break label57;
                        }

                        if (var3 != null) {
                           try {
                              if (var1.getContent() != null && HttpMediaType.equalsIgnoreParameters("application/json; charset=UTF-8", var3)) {
                                 JsonObjectParser var13 = new JsonObjectParser(var0);
                                 var10 = (TokenErrorResponse)var13.parseAndClose(var1.getContent(), var1.getContentCharset(), TokenErrorResponse.class);
                                 break label50;
                              }
                           } catch (IOException var8) {
                              var10000 = var8;
                              var10001 = false;
                              break label57;
                           }
                        }
                     }

                     try {
                        var3 = var1.parseAsString();
                     } catch (IOException var7) {
                        var10000 = var7;
                        var10001 = false;
                        break label57;
                     }

                     var10 = (TokenErrorResponse)var5;
                     break label52;
                  }

                  var14 = var10000;
                  var10 = null;
                  break label51;
               }

               try {
                  var3 = var10.toPrettyString();
                  break label52;
               } catch (IOException var6) {
                  var14 = var6;
               }
            }

            var14.printStackTrace();
            var15 = var10;
            var11 = var4;
            break label53;
         }

         var4 = var3;
         var15 = var10;
         var11 = var4;
      }

      StringBuilder var12 = HttpResponseException.computeMessageBuffer(var1);
      if (!Strings.isNullOrEmpty(var11)) {
         var12.append(StringUtils.LINE_SEPARATOR);
         var12.append(var11);
         var2.setContent(var11);
      }

      var2.setMessage(var12.toString());
      return new TokenResponseException(var2, var15);
   }

   public final TokenErrorResponse getDetails() {
      return this.details;
   }
}
