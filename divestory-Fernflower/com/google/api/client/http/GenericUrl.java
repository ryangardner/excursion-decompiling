package com.google.api.client.http;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.escape.CharEscapers;
import com.google.api.client.util.escape.Escaper;
import com.google.api.client.util.escape.PercentEscaper;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Map.Entry;

public class GenericUrl extends GenericData {
   private static final Escaper URI_FRAGMENT_ESCAPER = new PercentEscaper("=&-_.!~*'()@:$,;/?:");
   private String fragment;
   private String host;
   private List<String> pathParts;
   private int port;
   private String scheme;
   private String userInfo;
   private boolean verbatim;

   public GenericUrl() {
      this.port = -1;
   }

   public GenericUrl(String var1) {
      this(var1, false);
   }

   private GenericUrl(String var1, String var2, int var3, String var4, String var5, String var6, String var7, boolean var8) {
      this.port = -1;
      this.scheme = var1.toLowerCase(Locale.US);
      this.host = var2;
      this.port = var3;
      this.pathParts = toPathParts(var4, var8);
      this.verbatim = var8;
      if (var8) {
         this.fragment = var5;
         if (var6 != null) {
            UrlEncodedParser.parse((String)var6, this, false);
         }

         this.userInfo = var7;
      } else {
         var2 = null;
         if (var5 != null) {
            var1 = CharEscapers.decodeUri(var5);
         } else {
            var1 = null;
         }

         this.fragment = var1;
         if (var6 != null) {
            UrlEncodedParser.parse((String)var6, this);
         }

         var1 = var2;
         if (var7 != null) {
            var1 = CharEscapers.decodeUri(var7);
         }

         this.userInfo = var1;
      }

   }

   public GenericUrl(String var1, boolean var2) {
      this(parseURL(var1), var2);
   }

   public GenericUrl(URI var1) {
      this(var1, false);
   }

   public GenericUrl(URI var1, boolean var2) {
      this(var1.getScheme(), var1.getHost(), var1.getPort(), var1.getRawPath(), var1.getRawFragment(), var1.getRawQuery(), var1.getRawUserInfo(), var2);
   }

   public GenericUrl(URL var1) {
      this(var1, false);
   }

   public GenericUrl(URL var1, boolean var2) {
      this(var1.getProtocol(), var1.getHost(), var1.getPort(), var1.getPath(), var1.getRef(), var1.getQuery(), var1.getUserInfo(), var2);
   }

   static void addQueryParams(Set<Entry<String, Object>> var0, StringBuilder var1, boolean var2) {
      Iterator var3 = var0.iterator();
      boolean var4 = true;

      while(true) {
         while(true) {
            Object var5;
            Entry var7;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               var7 = (Entry)var3.next();
               var5 = var7.getValue();
            } while(var5 == null);

            String var8;
            if (var2) {
               var8 = (String)var7.getKey();
            } else {
               var8 = CharEscapers.escapeUriQuery((String)var7.getKey());
            }

            if (var5 instanceof Collection) {
               Iterator var9 = ((Collection)var5).iterator();
               boolean var6 = var4;

               while(true) {
                  var4 = var6;
                  if (!var9.hasNext()) {
                     break;
                  }

                  var6 = appendParam(var6, var1, var8, var9.next(), var2);
               }
            } else {
               var4 = appendParam(var4, var1, var8, var5, var2);
            }
         }
      }
   }

   private static boolean appendParam(boolean var0, StringBuilder var1, String var2, Object var3, boolean var4) {
      if (var0) {
         var0 = false;
         var1.append('?');
      } else {
         var1.append('&');
      }

      var1.append(var2);
      if (var4) {
         var2 = var3.toString();
      } else {
         var2 = CharEscapers.escapeUriQuery(var3.toString());
      }

      if (var2.length() != 0) {
         var1.append('=');
         var1.append(var2);
      }

      return var0;
   }

   private void appendRawPathFromParts(StringBuilder var1) {
      int var2 = this.pathParts.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         String var4 = (String)this.pathParts.get(var3);
         if (var3 != 0) {
            var1.append('/');
         }

         if (var4.length() != 0) {
            if (!this.verbatim) {
               var4 = CharEscapers.escapeUriPath(var4);
            }

            var1.append(var4);
         }
      }

   }

   private static URL parseURL(String var0) {
      try {
         URL var2 = new URL(var0);
         return var2;
      } catch (MalformedURLException var1) {
         throw new IllegalArgumentException(var1);
      }
   }

   public static List<String> toPathParts(String var0) {
      return toPathParts(var0, false);
   }

   public static List<String> toPathParts(String var0, boolean var1) {
      if (var0 != null && var0.length() != 0) {
         ArrayList var2 = new ArrayList();
         boolean var3 = true;

         int var5;
         for(int var4 = 0; var3; var4 = var5 + 1) {
            var5 = var0.indexOf(47, var4);
            if (var5 != -1) {
               var3 = true;
            } else {
               var3 = false;
            }

            String var6;
            if (var3) {
               var6 = var0.substring(var4, var5);
            } else {
               var6 = var0.substring(var4);
            }

            if (!var1) {
               var6 = CharEscapers.decodeUriPath(var6);
            }

            var2.add(var6);
         }

         return var2;
      } else {
         return null;
      }
   }

   private static URI toURI(String var0) {
      try {
         URI var2 = new URI(var0);
         return var2;
      } catch (URISyntaxException var1) {
         throw new IllegalArgumentException(var1);
      }
   }

   public void appendRawPath(String var1) {
      if (var1 != null && var1.length() != 0) {
         List var5 = toPathParts(var1, this.verbatim);
         List var2 = this.pathParts;
         if (var2 != null && !var2.isEmpty()) {
            int var3 = this.pathParts.size();
            var2 = this.pathParts;
            --var3;
            StringBuilder var4 = new StringBuilder();
            var4.append((String)this.pathParts.get(var3));
            var4.append((String)var5.get(0));
            var2.set(var3, var4.toString());
            this.pathParts.addAll(var5.subList(1, var5.size()));
         } else {
            this.pathParts = var5;
         }
      }

   }

   public final String build() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.buildAuthority());
      var1.append(this.buildRelativeUrl());
      return var1.toString();
   }

   public final String buildAuthority() {
      StringBuilder var1 = new StringBuilder();
      var1.append((String)Preconditions.checkNotNull(this.scheme));
      var1.append("://");
      String var2 = this.userInfo;
      if (var2 != null) {
         if (!this.verbatim) {
            var2 = CharEscapers.escapeUriUserInfo(var2);
         }

         var1.append(var2);
         var1.append('@');
      }

      var1.append((String)Preconditions.checkNotNull(this.host));
      int var3 = this.port;
      if (var3 != -1) {
         var1.append(':');
         var1.append(var3);
      }

      return var1.toString();
   }

   public final String buildRelativeUrl() {
      StringBuilder var1 = new StringBuilder();
      if (this.pathParts != null) {
         this.appendRawPathFromParts(var1);
      }

      addQueryParams(this.entrySet(), var1, this.verbatim);
      String var2 = this.fragment;
      if (var2 != null) {
         var1.append('#');
         if (!this.verbatim) {
            var2 = URI_FRAGMENT_ESCAPER.escape(var2);
         }

         var1.append(var2);
      }

      return var1.toString();
   }

   public GenericUrl clone() {
      GenericUrl var1 = (GenericUrl)super.clone();
      if (this.pathParts != null) {
         var1.pathParts = new ArrayList(this.pathParts);
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (super.equals(var1) && var1 instanceof GenericUrl) {
         GenericUrl var2 = (GenericUrl)var1;
         return this.build().equals(var2.build());
      } else {
         return false;
      }
   }

   public Collection<Object> getAll(String var1) {
      Object var2 = this.get(var1);
      if (var2 == null) {
         return Collections.emptySet();
      } else {
         return (Collection)(var2 instanceof Collection ? Collections.unmodifiableCollection((Collection)var2) : Collections.singleton(var2));
      }
   }

   public Object getFirst(String var1) {
      Object var2 = this.get(var1);
      Object var3 = var2;
      if (var2 instanceof Collection) {
         Iterator var4 = ((Collection)var2).iterator();
         if (var4.hasNext()) {
            var3 = var4.next();
         } else {
            var3 = null;
         }
      }

      return var3;
   }

   public String getFragment() {
      return this.fragment;
   }

   public String getHost() {
      return this.host;
   }

   public List<String> getPathParts() {
      return this.pathParts;
   }

   public int getPort() {
      return this.port;
   }

   public String getRawPath() {
      if (this.pathParts == null) {
         return null;
      } else {
         StringBuilder var1 = new StringBuilder();
         this.appendRawPathFromParts(var1);
         return var1.toString();
      }
   }

   public final String getScheme() {
      return this.scheme;
   }

   public final String getUserInfo() {
      return this.userInfo;
   }

   public int hashCode() {
      return this.build().hashCode();
   }

   public GenericUrl set(String var1, Object var2) {
      return (GenericUrl)super.set(var1, var2);
   }

   public final void setFragment(String var1) {
      this.fragment = var1;
   }

   public final void setHost(String var1) {
      this.host = (String)Preconditions.checkNotNull(var1);
   }

   public void setPathParts(List<String> var1) {
      this.pathParts = var1;
   }

   public final void setPort(int var1) {
      boolean var2;
      if (var1 >= -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "expected port >= -1");
      this.port = var1;
   }

   public void setRawPath(String var1) {
      this.pathParts = toPathParts(var1, this.verbatim);
   }

   public final void setScheme(String var1) {
      this.scheme = (String)Preconditions.checkNotNull(var1);
   }

   public final void setUserInfo(String var1) {
      this.userInfo = var1;
   }

   public String toString() {
      return this.build();
   }

   public final URI toURI() {
      return toURI(this.build());
   }

   public final URL toURL() {
      return parseURL(this.build());
   }

   public final URL toURL(String var1) {
      try {
         URL var3 = new URL(this.toURL(), var1);
         return var3;
      } catch (MalformedURLException var2) {
         throw new IllegalArgumentException(var2);
      }
   }
}
