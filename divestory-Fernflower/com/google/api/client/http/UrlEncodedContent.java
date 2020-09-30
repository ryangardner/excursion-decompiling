package com.google.api.client.http;

import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import com.google.api.client.util.escape.CharEscapers;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class UrlEncodedContent extends AbstractHttpContent {
   private Object data;

   public UrlEncodedContent(Object var1) {
      super(UrlEncodedParser.MEDIA_TYPE);
      this.setData(var1);
   }

   private static boolean appendParam(boolean var0, Writer var1, String var2, Object var3) throws IOException {
      boolean var4 = var0;
      if (var3 != null) {
         if (Data.isNull(var3)) {
            var4 = var0;
         } else {
            if (var0) {
               var0 = false;
            } else {
               var1.write("&");
            }

            var1.write(var2);
            if (var3 instanceof Enum) {
               var2 = FieldInfo.of((Enum)var3).getName();
            } else {
               var2 = var3.toString();
            }

            var2 = CharEscapers.escapeUri(var2);
            var4 = var0;
            if (var2.length() != 0) {
               var1.write("=");
               var1.write(var2);
               var4 = var0;
            }
         }
      }

      return var4;
   }

   public static UrlEncodedContent getContent(HttpRequest var0) {
      HttpContent var1 = var0.getContent();
      if (var1 != null) {
         return (UrlEncodedContent)var1;
      } else {
         UrlEncodedContent var2 = new UrlEncodedContent(new HashMap());
         var0.setContent(var2);
         return var2;
      }
   }

   public final Object getData() {
      return this.data;
   }

   public UrlEncodedContent setData(Object var1) {
      this.data = Preconditions.checkNotNull(var1);
      return this;
   }

   public UrlEncodedContent setMediaType(HttpMediaType var1) {
      super.setMediaType(var1);
      return this;
   }

   public void writeTo(OutputStream var1) throws IOException {
      BufferedWriter var8 = new BufferedWriter(new OutputStreamWriter(var1, this.getCharset()));
      Iterator var2 = Data.mapOf(this.data).entrySet().iterator();
      boolean var3 = true;

      while(true) {
         while(true) {
            Entry var4;
            Object var5;
            do {
               if (!var2.hasNext()) {
                  var8.flush();
                  return;
               }

               var4 = (Entry)var2.next();
               var5 = var4.getValue();
            } while(var5 == null);

            String var9 = CharEscapers.escapeUri((String)var4.getKey());
            Class var6 = var5.getClass();
            if (!(var5 instanceof Iterable) && !var6.isArray()) {
               var3 = appendParam(var3, var8, var9, var5);
            } else {
               Iterator var10 = Types.iterableOf(var5).iterator();
               boolean var7 = var3;

               while(true) {
                  var3 = var7;
                  if (!var10.hasNext()) {
                     break;
                  }

                  var7 = appendParam(var7, var8, var9, var10.next());
               }
            }
         }
      }
   }
}
