package com.google.api.client.http;

import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import com.google.api.client.util.escape.CharEscapers;
import com.google.common.base.Splitter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

public class UriTemplate {
   private static final String COMPOSITE_NON_EXPLODE_JOINER = ",";
   private static final Map<Character, UriTemplate.CompositeOutput> COMPOSITE_PREFIXES = new HashMap();

   static {
      UriTemplate.CompositeOutput.values();
   }

   public static String expand(String var0, Object var1, boolean var2) {
      Map var3 = getMap(var1);
      StringBuilder var4 = new StringBuilder();
      int var5 = var0.length();

      int var8;
      for(int var6 = 0; var6 < var5; var6 = var8 + 1) {
         int var7 = var0.indexOf(123, var6);
         if (var7 == -1) {
            if (var6 == 0 && !var2) {
               return var0;
            }

            var4.append(var0.substring(var6));
            break;
         }

         var4.append(var0.substring(var6, var7));
         var8 = var0.indexOf(125, var7 + 2);
         String var16 = var0.substring(var7 + 1, var8);
         UriTemplate.CompositeOutput var9 = getCompositeOutput(var16);
         ListIterator var10 = Splitter.on(',').splitToList(var16).listIterator();
         boolean var17 = true;

         while(var10.hasNext()) {
            var16 = (String)var10.next();
            boolean var11 = var16.endsWith("*");
            if (var10.nextIndex() == 1) {
               var7 = var9.getVarNameStartIndex();
            } else {
               var7 = 0;
            }

            int var12 = var16.length();
            int var13 = var12;
            if (var11) {
               var13 = var12 - 1;
            }

            String var14 = var16.substring(var7, var13);
            Object var15 = var3.remove(var14);
            if (var15 != null) {
               if (!var17) {
                  var4.append(var9.getExplodeJoiner());
               } else {
                  var4.append(var9.getOutputPrefix());
                  var17 = false;
               }

               if (var15 instanceof Iterator) {
                  var16 = getListPropertyValue(var14, (Iterator)var15, var11, var9);
               } else if (!(var15 instanceof Iterable) && !var15.getClass().isArray()) {
                  if (var15.getClass().isEnum()) {
                     var16 = FieldInfo.of((Enum)var15).getName();
                     if (var16 == null) {
                        var16 = var15.toString();
                     }

                     var16 = getSimpleValue(var14, var16, var9);
                  } else if (!Data.isValueOfPrimitiveType(var15)) {
                     var16 = getMapPropertyValue(var14, getMap(var15), var11, var9);
                  } else {
                     var16 = getSimpleValue(var14, var15.toString(), var9);
                  }
               } else {
                  var16 = getListPropertyValue(var14, Types.iterableOf(var15).iterator(), var11, var9);
               }

               var4.append(var16);
            }
         }
      }

      if (var2) {
         GenericUrl.addQueryParams(var3.entrySet(), var4, false);
      }

      return var4.toString();
   }

   public static String expand(String var0, String var1, Object var2, boolean var3) {
      String var7;
      if (var1.startsWith("/")) {
         GenericUrl var4 = new GenericUrl(var0);
         var4.setRawPath((String)null);
         StringBuilder var6 = new StringBuilder();
         var6.append(var4.build());
         var6.append(var1);
         var7 = var6.toString();
      } else {
         var7 = var1;
         if (!var1.startsWith("http://")) {
            if (var1.startsWith("https://")) {
               var7 = var1;
            } else {
               StringBuilder var5 = new StringBuilder();
               var5.append(var0);
               var5.append(var1);
               var7 = var5.toString();
            }
         }
      }

      return expand(var7, var2, var3);
   }

   static UriTemplate.CompositeOutput getCompositeOutput(String var0) {
      UriTemplate.CompositeOutput var1 = (UriTemplate.CompositeOutput)COMPOSITE_PREFIXES.get(var0.charAt(0));
      UriTemplate.CompositeOutput var2 = var1;
      if (var1 == null) {
         var2 = UriTemplate.CompositeOutput.SIMPLE;
      }

      return var2;
   }

   private static String getListPropertyValue(String var0, Iterator<?> var1, boolean var2, UriTemplate.CompositeOutput var3) {
      if (!var1.hasNext()) {
         return "";
      } else {
         StringBuilder var4 = new StringBuilder();
         String var5;
         if (var2) {
            var5 = var3.getExplodeJoiner();
         } else {
            if (var3.requiresVarAssignment()) {
               var4.append(CharEscapers.escapeUriPath(var0));
               var4.append("=");
            }

            var5 = ",";
         }

         while(var1.hasNext()) {
            if (var2 && var3.requiresVarAssignment()) {
               var4.append(CharEscapers.escapeUriPath(var0));
               var4.append("=");
            }

            var4.append(var3.getEncodedValue(var1.next().toString()));
            if (var1.hasNext()) {
               var4.append(var5);
            }
         }

         return var4.toString();
      }
   }

   private static Map<String, Object> getMap(Object var0) {
      LinkedHashMap var1 = new LinkedHashMap();
      Iterator var2 = Data.mapOf(var0).entrySet().iterator();

      while(var2.hasNext()) {
         Entry var4 = (Entry)var2.next();
         Object var3 = var4.getValue();
         if (var3 != null && !Data.isNull(var3)) {
            var1.put(var4.getKey(), var3);
         }
      }

      return var1;
   }

   private static String getMapPropertyValue(String var0, Map<String, Object> var1, boolean var2, UriTemplate.CompositeOutput var3) {
      if (var1.isEmpty()) {
         return "";
      } else {
         StringBuilder var4 = new StringBuilder();
         String var5 = "=";
         String var6 = ",";
         if (var2) {
            var6 = var3.getExplodeJoiner();
            var0 = var5;
            var5 = var6;
         } else {
            if (var3.requiresVarAssignment()) {
               var4.append(CharEscapers.escapeUriPath(var0));
               var4.append("=");
            }

            var0 = ",";
            var5 = var6;
         }

         Iterator var9 = var1.entrySet().iterator();

         while(var9.hasNext()) {
            Entry var7 = (Entry)var9.next();
            String var8 = var3.getEncodedValue((String)var7.getKey());
            String var10 = var3.getEncodedValue(var7.getValue().toString());
            var4.append(var8);
            var4.append(var0);
            var4.append(var10);
            if (var9.hasNext()) {
               var4.append(var5);
            }
         }

         return var4.toString();
      }
   }

   private static String getSimpleValue(String var0, String var1, UriTemplate.CompositeOutput var2) {
      return var2.requiresVarAssignment() ? String.format("%s=%s", var0, var2.getEncodedValue(var1)) : var2.getEncodedValue(var1);
   }

   private static enum CompositeOutput {
      AMP('&', "&", "&", true, false),
      DOT('.', ".", ".", false, false),
      FORWARD_SLASH('/', "/", "/", false, false),
      HASH('#', "#", ",", false, true),
      PLUS('+', "", ",", false, true),
      QUERY('?', "?", "&", true, false),
      SEMI_COLON(';', ";", ";", true, false),
      SIMPLE;

      private final String explodeJoiner;
      private final String outputPrefix;
      private final Character propertyPrefix;
      private final boolean requiresVarAssignment;
      private final boolean reservedExpansion;

      static {
         UriTemplate.CompositeOutput var0 = new UriTemplate.CompositeOutput("SIMPLE", 7, (Character)null, "", ",", false, false);
         SIMPLE = var0;
      }

      private CompositeOutput(Character var3, String var4, String var5, boolean var6, boolean var7) {
         this.propertyPrefix = var3;
         this.outputPrefix = (String)Preconditions.checkNotNull(var4);
         this.explodeJoiner = (String)Preconditions.checkNotNull(var5);
         this.requiresVarAssignment = var6;
         this.reservedExpansion = var7;
         if (var3 != null) {
            UriTemplate.COMPOSITE_PREFIXES.put(var3, this);
         }

      }

      private String getEncodedValue(String var1) {
         if (this.reservedExpansion) {
            var1 = CharEscapers.escapeUriPathWithoutReserved(var1);
         } else {
            var1 = CharEscapers.escapeUriConformant(var1);
         }

         return var1;
      }

      String getExplodeJoiner() {
         return this.explodeJoiner;
      }

      String getOutputPrefix() {
         return this.outputPrefix;
      }

      int getVarNameStartIndex() {
         byte var1;
         if (this.propertyPrefix == null) {
            var1 = 0;
         } else {
            var1 = 1;
         }

         return var1;
      }

      boolean requiresVarAssignment() {
         return this.requiresVarAssignment;
      }
   }
}
