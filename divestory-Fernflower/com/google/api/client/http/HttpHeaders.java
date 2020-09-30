package com.google.api.client.http;

import com.google.api.client.util.ArrayValueMap;
import com.google.api.client.util.Base64;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Throwables;
import com.google.api.client.util.Types;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpHeaders extends GenericData {
   @Key("Accept")
   private List<String> accept;
   @Key("Accept-Encoding")
   private List<String> acceptEncoding = new ArrayList(Collections.singleton("gzip"));
   @Key("Age")
   private List<Long> age;
   @Key("WWW-Authenticate")
   private List<String> authenticate;
   @Key("Authorization")
   private List<String> authorization;
   @Key("Cache-Control")
   private List<String> cacheControl;
   @Key("Content-Encoding")
   private List<String> contentEncoding;
   @Key("Content-Length")
   private List<Long> contentLength;
   @Key("Content-MD5")
   private List<String> contentMD5;
   @Key("Content-Range")
   private List<String> contentRange;
   @Key("Content-Type")
   private List<String> contentType;
   @Key("Cookie")
   private List<String> cookie;
   @Key("Date")
   private List<String> date;
   @Key("ETag")
   private List<String> etag;
   @Key("Expires")
   private List<String> expires;
   @Key("If-Match")
   private List<String> ifMatch;
   @Key("If-Modified-Since")
   private List<String> ifModifiedSince;
   @Key("If-None-Match")
   private List<String> ifNoneMatch;
   @Key("If-Range")
   private List<String> ifRange;
   @Key("If-Unmodified-Since")
   private List<String> ifUnmodifiedSince;
   @Key("Last-Modified")
   private List<String> lastModified;
   @Key("Location")
   private List<String> location;
   @Key("MIME-Version")
   private List<String> mimeVersion;
   @Key("Range")
   private List<String> range;
   @Key("Retry-After")
   private List<String> retryAfter;
   @Key("User-Agent")
   private List<String> userAgent;
   @Key("Warning")
   private List<String> warning;

   public HttpHeaders() {
      super(EnumSet.of(GenericData.Flags.IGNORE_CASE));
   }

   private static void addHeader(Logger var0, StringBuilder var1, StringBuilder var2, LowLevelHttpRequest var3, String var4, Object var5, Writer var6) throws IOException {
      if (var5 != null && !Data.isNull(var5)) {
         String var8 = toStringValue(var5);
         String var7;
         if (("Authorization".equalsIgnoreCase(var4) || "Cookie".equalsIgnoreCase(var4)) && (var0 == null || !var0.isLoggable(Level.ALL))) {
            var7 = "<Not Logged>";
         } else {
            var7 = var8;
         }

         if (var1 != null) {
            var1.append(var4);
            var1.append(": ");
            var1.append(var7);
            var1.append(StringUtils.LINE_SEPARATOR);
         }

         if (var2 != null) {
            var2.append(" -H '");
            var2.append(var4);
            var2.append(": ");
            var2.append(var7);
            var2.append("'");
         }

         if (var3 != null) {
            var3.addHeader(var4, var8);
         }

         if (var6 != null) {
            var6.write(var4);
            var6.write(": ");
            var6.write(var8);
            var6.write("\r\n");
         }
      }

   }

   private <T> List<T> getAsList(T var1) {
      if (var1 == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();
         var2.add(var1);
         return var2;
      }
   }

   private <T> T getFirstHeaderValue(List<T> var1) {
      Object var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.get(0);
      }

      return var2;
   }

   private static Object parseValue(Type var0, List<Type> var1, String var2) {
      return Data.parsePrimitiveValue(Data.resolveWildcardTypeOrTypeVariable(var1, var0), var2);
   }

   static void serializeHeaders(HttpHeaders var0, StringBuilder var1, StringBuilder var2, Logger var3, LowLevelHttpRequest var4) throws IOException {
      serializeHeaders(var0, var1, var2, var3, var4, (Writer)null);
   }

   static void serializeHeaders(HttpHeaders var0, StringBuilder var1, StringBuilder var2, Logger var3, LowLevelHttpRequest var4, Writer var5) throws IOException {
      HashSet var6 = new HashSet();
      Iterator var7 = var0.entrySet().iterator();

      while(true) {
         while(true) {
            String var9;
            Object var12;
            do {
               if (!var7.hasNext()) {
                  if (var5 != null) {
                     var5.flush();
                  }

                  return;
               }

               Entry var8 = (Entry)var7.next();
               var9 = (String)var8.getKey();
               Preconditions.checkArgument(var6.add(var9), "multiple headers of the same name (headers are case insensitive): %s", var9);
               var12 = var8.getValue();
            } while(var12 == null);

            FieldInfo var10 = var0.getClassInfo().getFieldInfo(var9);
            if (var10 != null) {
               var9 = var10.getName();
            }

            Class var11 = var12.getClass();
            if (!(var12 instanceof Iterable) && !var11.isArray()) {
               addHeader(var3, var1, var2, var4, var9, var12, var5);
            } else {
               Iterator var13 = Types.iterableOf(var12).iterator();

               while(var13.hasNext()) {
                  addHeader(var3, var1, var2, var4, var9, var13.next(), var5);
               }
            }
         }
      }
   }

   public static void serializeHeadersForMultipartRequests(HttpHeaders var0, StringBuilder var1, Logger var2, Writer var3) throws IOException {
      serializeHeaders(var0, var1, (StringBuilder)null, var2, (LowLevelHttpRequest)null, var3);
   }

   private static String toStringValue(Object var0) {
      String var1;
      if (var0 instanceof Enum) {
         var1 = FieldInfo.of((Enum)var0).getName();
      } else {
         var1 = var0.toString();
      }

      return var1;
   }

   public HttpHeaders addWarning(String var1) {
      if (var1 == null) {
         return this;
      } else {
         List var2 = this.warning;
         if (var2 == null) {
            this.warning = this.getAsList(var1);
         } else {
            var2.add(var1);
         }

         return this;
      }
   }

   public HttpHeaders clone() {
      return (HttpHeaders)super.clone();
   }

   public final void fromHttpHeaders(HttpHeaders var1) {
      try {
         HttpHeaders.ParseHeaderState var2 = new HttpHeaders.ParseHeaderState(this, (StringBuilder)null);
         HttpHeaders.HeaderParsingFakeLevelHttpRequest var3 = new HttpHeaders.HeaderParsingFakeLevelHttpRequest(this, var2);
         serializeHeaders(var1, (StringBuilder)null, (StringBuilder)null, (Logger)null, var3);
         var2.finish();
      } catch (IOException var4) {
         throw Throwables.propagate(var4);
      }
   }

   public final void fromHttpResponse(LowLevelHttpResponse var1, StringBuilder var2) throws IOException {
      this.clear();
      HttpHeaders.ParseHeaderState var5 = new HttpHeaders.ParseHeaderState(this, var2);
      int var3 = var1.getHeaderCount();

      for(int var4 = 0; var4 < var3; ++var4) {
         this.parseHeader(var1.getHeaderName(var4), var1.getHeaderValue(var4), var5);
      }

      var5.finish();
   }

   public final String getAccept() {
      return (String)this.getFirstHeaderValue(this.accept);
   }

   public final String getAcceptEncoding() {
      return (String)this.getFirstHeaderValue(this.acceptEncoding);
   }

   public final Long getAge() {
      return (Long)this.getFirstHeaderValue(this.age);
   }

   public final String getAuthenticate() {
      return (String)this.getFirstHeaderValue(this.authenticate);
   }

   public final List<String> getAuthenticateAsList() {
      return this.authenticate;
   }

   public final String getAuthorization() {
      return (String)this.getFirstHeaderValue(this.authorization);
   }

   public final List<String> getAuthorizationAsList() {
      return this.authorization;
   }

   public final String getCacheControl() {
      return (String)this.getFirstHeaderValue(this.cacheControl);
   }

   public final String getContentEncoding() {
      return (String)this.getFirstHeaderValue(this.contentEncoding);
   }

   public final Long getContentLength() {
      return (Long)this.getFirstHeaderValue(this.contentLength);
   }

   public final String getContentMD5() {
      return (String)this.getFirstHeaderValue(this.contentMD5);
   }

   public final String getContentRange() {
      return (String)this.getFirstHeaderValue(this.contentRange);
   }

   public final String getContentType() {
      return (String)this.getFirstHeaderValue(this.contentType);
   }

   public final String getCookie() {
      return (String)this.getFirstHeaderValue(this.cookie);
   }

   public final String getDate() {
      return (String)this.getFirstHeaderValue(this.date);
   }

   public final String getETag() {
      return (String)this.getFirstHeaderValue(this.etag);
   }

   public final String getExpires() {
      return (String)this.getFirstHeaderValue(this.expires);
   }

   public String getFirstHeaderStringValue(String var1) {
      Object var3 = this.get(var1.toLowerCase(Locale.US));
      if (var3 == null) {
         return null;
      } else {
         Class var2 = var3.getClass();
         if (var3 instanceof Iterable || var2.isArray()) {
            Iterator var4 = Types.iterableOf(var3).iterator();
            if (var4.hasNext()) {
               return toStringValue(var4.next());
            }
         }

         return toStringValue(var3);
      }
   }

   public List<String> getHeaderStringValues(String var1) {
      Object var2 = this.get(var1.toLowerCase(Locale.US));
      if (var2 == null) {
         return Collections.emptyList();
      } else {
         Class var3 = var2.getClass();
         if (!(var2 instanceof Iterable) && !var3.isArray()) {
            return Collections.singletonList(toStringValue(var2));
         } else {
            ArrayList var4 = new ArrayList();
            Iterator var5 = Types.iterableOf(var2).iterator();

            while(var5.hasNext()) {
               var4.add(toStringValue(var5.next()));
            }

            return Collections.unmodifiableList(var4);
         }
      }
   }

   public final String getIfMatch() {
      return (String)this.getFirstHeaderValue(this.ifMatch);
   }

   public final String getIfModifiedSince() {
      return (String)this.getFirstHeaderValue(this.ifModifiedSince);
   }

   public final String getIfNoneMatch() {
      return (String)this.getFirstHeaderValue(this.ifNoneMatch);
   }

   public final String getIfRange() {
      return (String)this.getFirstHeaderValue(this.ifRange);
   }

   public final String getIfUnmodifiedSince() {
      return (String)this.getFirstHeaderValue(this.ifUnmodifiedSince);
   }

   public final String getLastModified() {
      return (String)this.getFirstHeaderValue(this.lastModified);
   }

   public final String getLocation() {
      return (String)this.getFirstHeaderValue(this.location);
   }

   public final String getMimeVersion() {
      return (String)this.getFirstHeaderValue(this.mimeVersion);
   }

   public final String getRange() {
      return (String)this.getFirstHeaderValue(this.range);
   }

   public final String getRetryAfter() {
      return (String)this.getFirstHeaderValue(this.retryAfter);
   }

   public final String getUserAgent() {
      return (String)this.getFirstHeaderValue(this.userAgent);
   }

   public final List<String> getWarning() {
      ArrayList var1;
      if (this.warning == null) {
         var1 = null;
      } else {
         var1 = new ArrayList(this.warning);
      }

      return var1;
   }

   void parseHeader(String var1, String var2, HttpHeaders.ParseHeaderState var3) {
      List var4 = var3.context;
      ClassInfo var5 = var3.classInfo;
      ArrayValueMap var6 = var3.arrayValueMap;
      StringBuilder var10 = var3.logger;
      if (var10 != null) {
         StringBuilder var7 = new StringBuilder();
         var7.append(var1);
         var7.append(": ");
         var7.append(var2);
         var10.append(var7.toString());
         var10.append(StringUtils.LINE_SEPARATOR);
      }

      FieldInfo var15 = var5.getFieldInfo(var1);
      if (var15 != null) {
         Type var16 = Data.resolveWildcardTypeOrTypeVariable(var4, var15.getGenericType());
         if (Types.isArray(var16)) {
            Class var8 = Types.getRawArrayComponentType(var4, Types.getArrayComponentType(var16));
            var6.put(var15.getField(), var8, parseValue(var8, var4, var2));
         } else if (Types.isAssignableToOrFrom(Types.getRawArrayComponentType(var4, var16), Iterable.class)) {
            Collection var11 = (Collection)var15.getValue(this);
            Collection var9 = var11;
            if (var11 == null) {
               var9 = Data.newCollectionInstance(var16);
               var15.setValue(this, var9);
            }

            Type var12;
            if (var16 == Object.class) {
               var12 = null;
            } else {
               var12 = Types.getIterableParameter(var16);
            }

            var9.add(parseValue(var12, var4, var2));
         } else {
            var15.setValue(this, parseValue(var16, var4, var2));
         }
      } else {
         ArrayList var13 = (ArrayList)this.get(var1);
         ArrayList var14 = var13;
         if (var13 == null) {
            var14 = new ArrayList();
            this.set(var1, var14);
         }

         var14.add(var2);
      }

   }

   public HttpHeaders set(String var1, Object var2) {
      return (HttpHeaders)super.set(var1, var2);
   }

   public HttpHeaders setAccept(String var1) {
      this.accept = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setAcceptEncoding(String var1) {
      this.acceptEncoding = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setAge(Long var1) {
      this.age = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setAuthenticate(String var1) {
      this.authenticate = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setAuthorization(String var1) {
      return this.setAuthorization(this.getAsList(var1));
   }

   public HttpHeaders setAuthorization(List<String> var1) {
      this.authorization = var1;
      return this;
   }

   public HttpHeaders setBasicAuthentication(String var1, String var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append((String)Preconditions.checkNotNull(var1));
      var3.append(":");
      var3.append((String)Preconditions.checkNotNull(var2));
      var2 = Base64.encodeBase64String(StringUtils.getBytesUtf8(var3.toString()));
      StringBuilder var4 = new StringBuilder();
      var4.append("Basic ");
      var4.append(var2);
      return this.setAuthorization(var4.toString());
   }

   public HttpHeaders setCacheControl(String var1) {
      this.cacheControl = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setContentEncoding(String var1) {
      this.contentEncoding = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setContentLength(Long var1) {
      this.contentLength = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setContentMD5(String var1) {
      this.contentMD5 = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setContentRange(String var1) {
      this.contentRange = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setContentType(String var1) {
      this.contentType = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setCookie(String var1) {
      this.cookie = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setDate(String var1) {
      this.date = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setETag(String var1) {
      this.etag = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setExpires(String var1) {
      this.expires = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setIfMatch(String var1) {
      this.ifMatch = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setIfModifiedSince(String var1) {
      this.ifModifiedSince = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setIfNoneMatch(String var1) {
      this.ifNoneMatch = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setIfRange(String var1) {
      this.ifRange = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setIfUnmodifiedSince(String var1) {
      this.ifUnmodifiedSince = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setLastModified(String var1) {
      this.lastModified = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setLocation(String var1) {
      this.location = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setMimeVersion(String var1) {
      this.mimeVersion = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setRange(String var1) {
      this.range = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setRetryAfter(String var1) {
      this.retryAfter = this.getAsList(var1);
      return this;
   }

   public HttpHeaders setUserAgent(String var1) {
      this.userAgent = this.getAsList(var1);
      return this;
   }

   private static class HeaderParsingFakeLevelHttpRequest extends LowLevelHttpRequest {
      private final HttpHeaders.ParseHeaderState state;
      private final HttpHeaders target;

      HeaderParsingFakeLevelHttpRequest(HttpHeaders var1, HttpHeaders.ParseHeaderState var2) {
         this.target = var1;
         this.state = var2;
      }

      public void addHeader(String var1, String var2) {
         this.target.parseHeader(var1, var2, this.state);
      }

      public LowLevelHttpResponse execute() throws IOException {
         throw new UnsupportedOperationException();
      }
   }

   private static final class ParseHeaderState {
      final ArrayValueMap arrayValueMap;
      final ClassInfo classInfo;
      final List<Type> context;
      final StringBuilder logger;

      public ParseHeaderState(HttpHeaders var1, StringBuilder var2) {
         Class var3 = var1.getClass();
         this.context = Arrays.asList(var3);
         this.classInfo = ClassInfo.of(var3, true);
         this.logger = var2;
         this.arrayValueMap = new ArrayValueMap(var1);
      }

      void finish() {
         this.arrayValueMap.setValues();
      }
   }
}
