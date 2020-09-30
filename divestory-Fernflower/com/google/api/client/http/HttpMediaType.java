package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpMediaType {
   private static final Pattern FULL_MEDIA_TYPE_REGEX;
   private static final Pattern PARAMETER_REGEX;
   private static final Pattern TOKEN_REGEX = Pattern.compile("[\\p{ASCII}&&[^\\p{Cntrl} ;/=\\[\\]\\(\\)\\<\\>\\@\\,\\:\\\"\\?\\=]]+");
   private static final Pattern TYPE_REGEX = Pattern.compile("[\\w!#$&.+\\-\\^_]+|[*]");
   private String cachedBuildResult;
   private final SortedMap<String, String> parameters = new TreeMap();
   private String subType = "octet-stream";
   private String type = "application";

   static {
      StringBuilder var0 = new StringBuilder();
      var0.append("\\s*(");
      var0.append("[^\\s/=;\"]+");
      var0.append(")/(");
      var0.append("[^\\s/=;\"]+");
      var0.append(")\\s*(");
      var0.append(";.*");
      var0.append(")?");
      FULL_MEDIA_TYPE_REGEX = Pattern.compile(var0.toString(), 32);
      var0 = new StringBuilder();
      var0.append("\\s*;\\s*(");
      var0.append("[^\\s/=;\"]+");
      var0.append(")=(");
      var0.append("\"([^\"]*)\"|[^\\s;\"]*");
      var0.append(")");
      PARAMETER_REGEX = Pattern.compile(var0.toString());
   }

   public HttpMediaType(String var1) {
      this.fromString(var1);
   }

   public HttpMediaType(String var1, String var2) {
      this.setType(var1);
      this.setSubType(var2);
   }

   public static boolean equalsIgnoreParameters(String var0, String var1) {
      boolean var2;
      if ((var0 != null || var1 != null) && (var0 == null || var1 == null || !(new HttpMediaType(var0)).equalsIgnoreParameters(new HttpMediaType(var1)))) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   private HttpMediaType fromString(String var1) {
      Matcher var5 = FULL_MEDIA_TYPE_REGEX.matcher(var1);
      Preconditions.checkArgument(var5.matches(), "Type must be in the 'maintype/subtype; parameter=value' format");
      this.setType(var5.group(1));
      this.setSubType(var5.group(2));
      var1 = var5.group(3);
      String var3;
      if (var1 != null) {
         for(Matcher var2 = PARAMETER_REGEX.matcher(var1); var2.find(); this.setParameter(var3, var1)) {
            var3 = var2.group(1);
            String var4 = var2.group(3);
            var1 = var4;
            if (var4 == null) {
               var1 = var2.group(2);
            }
         }
      }

      return this;
   }

   static boolean matchesToken(String var0) {
      return TOKEN_REGEX.matcher(var0).matches();
   }

   private static String quoteString(String var0) {
      var0 = var0.replace("\\", "\\\\").replace("\"", "\\\"");
      StringBuilder var1 = new StringBuilder();
      var1.append("\"");
      var1.append(var0);
      var1.append("\"");
      return var1.toString();
   }

   public String build() {
      String var1 = this.cachedBuildResult;
      if (var1 != null) {
         return var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(this.type);
         var2.append('/');
         var2.append(this.subType);
         SortedMap var5 = this.parameters;
         if (var5 != null) {
            for(Iterator var3 = var5.entrySet().iterator(); var3.hasNext(); var2.append(var1)) {
               Entry var6 = (Entry)var3.next();
               String var4 = (String)var6.getValue();
               var2.append("; ");
               var2.append((String)var6.getKey());
               var2.append("=");
               var1 = var4;
               if (!matchesToken(var4)) {
                  var1 = quoteString(var4);
               }
            }
         }

         var1 = var2.toString();
         this.cachedBuildResult = var1;
         return var1;
      }
   }

   public void clearParameters() {
      this.cachedBuildResult = null;
      this.parameters.clear();
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof HttpMediaType;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         HttpMediaType var4 = (HttpMediaType)var1;
         var2 = var3;
         if (this.equalsIgnoreParameters(var4)) {
            var2 = var3;
            if (this.parameters.equals(var4.parameters)) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   public boolean equalsIgnoreParameters(HttpMediaType var1) {
      boolean var2;
      if (var1 != null && this.getType().equalsIgnoreCase(var1.getType()) && this.getSubType().equalsIgnoreCase(var1.getSubType())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public Charset getCharsetParameter() {
      String var1 = this.getParameter("charset");
      Charset var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = Charset.forName(var1);
      }

      return var2;
   }

   public String getParameter(String var1) {
      return (String)this.parameters.get(var1.toLowerCase(Locale.US));
   }

   public Map<String, String> getParameters() {
      return Collections.unmodifiableMap(this.parameters);
   }

   public String getSubType() {
      return this.subType;
   }

   public String getType() {
      return this.type;
   }

   public int hashCode() {
      return this.build().hashCode();
   }

   public HttpMediaType removeParameter(String var1) {
      this.cachedBuildResult = null;
      this.parameters.remove(var1.toLowerCase(Locale.US));
      return this;
   }

   public HttpMediaType setCharsetParameter(Charset var1) {
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.name();
      }

      this.setParameter("charset", var2);
      return this;
   }

   public HttpMediaType setParameter(String var1, String var2) {
      if (var2 == null) {
         this.removeParameter(var1);
         return this;
      } else {
         Preconditions.checkArgument(TOKEN_REGEX.matcher(var1).matches(), "Name contains reserved characters");
         this.cachedBuildResult = null;
         this.parameters.put(var1.toLowerCase(Locale.US), var2);
         return this;
      }
   }

   public HttpMediaType setSubType(String var1) {
      Preconditions.checkArgument(TYPE_REGEX.matcher(var1).matches(), "Subtype contains reserved characters");
      this.subType = var1;
      this.cachedBuildResult = null;
      return this;
   }

   public HttpMediaType setType(String var1) {
      Preconditions.checkArgument(TYPE_REGEX.matcher(var1).matches(), "Type contains reserved characters");
      this.type = var1;
      this.cachedBuildResult = null;
      return this;
   }

   public String toString() {
      return this.build();
   }
}
