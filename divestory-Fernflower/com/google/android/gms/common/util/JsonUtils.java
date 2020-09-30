package com.google.android.gms.common.util;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {
   private static final Pattern zza = Pattern.compile("\\\\.");
   private static final Pattern zzb = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

   private JsonUtils() {
   }

   public static boolean areJsonValuesEquivalent(Object var0, Object var1) {
      if (var0 == null && var1 == null) {
         return true;
      } else if (var0 != null && var1 != null) {
         boolean var4;
         if (var0 instanceof JSONObject && var1 instanceof JSONObject) {
            JSONObject var9 = (JSONObject)var0;
            JSONObject var2 = (JSONObject)var1;
            if (var9.length() != var2.length()) {
               return false;
            } else {
               Iterator var11 = var9.keys();

               while(var11.hasNext()) {
                  String var3 = (String)var11.next();
                  if (!var2.has(var3)) {
                     return false;
                  }

                  try {
                     var4 = areJsonValuesEquivalent(var9.get((String)Preconditions.checkNotNull(var3)), var2.get(var3));
                  } catch (JSONException var7) {
                     return false;
                  }

                  if (!var4) {
                     return false;
                  }
               }

               return true;
            }
         } else if (var0 instanceof JSONArray && var1 instanceof JSONArray) {
            JSONArray var8 = (JSONArray)var0;
            JSONArray var10 = (JSONArray)var1;
            if (var8.length() != var10.length()) {
               return false;
            } else {
               for(int var5 = 0; var5 < var8.length(); ++var5) {
                  try {
                     var4 = areJsonValuesEquivalent(var8.get(var5), var10.get(var5));
                  } catch (JSONException var6) {
                     return false;
                  }

                  if (!var4) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return var0.equals(var1);
         }
      } else {
         return false;
      }
   }

   public static String escapeString(String var0) {
      String var1 = var0;
      if (!TextUtils.isEmpty(var0)) {
         Matcher var2 = zzb.matcher(var0);
         StringBuffer var5 = null;

         while(var2.find()) {
            StringBuffer var3 = var5;
            if (var5 == null) {
               var3 = new StringBuffer();
            }

            char var4 = var2.group().charAt(0);
            if (var4 != '\f') {
               if (var4 != '\r') {
                  if (var4 != '"') {
                     if (var4 != '/') {
                        if (var4 != '\\') {
                           switch(var4) {
                           case '\b':
                              var2.appendReplacement(var3, "\\\\b");
                              var5 = var3;
                              break;
                           case '\t':
                              var2.appendReplacement(var3, "\\\\t");
                              var5 = var3;
                              break;
                           case '\n':
                              var2.appendReplacement(var3, "\\\\n");
                              var5 = var3;
                              break;
                           default:
                              var5 = var3;
                           }
                        } else {
                           var2.appendReplacement(var3, "\\\\\\\\");
                           var5 = var3;
                        }
                     } else {
                        var2.appendReplacement(var3, "\\\\/");
                        var5 = var3;
                     }
                  } else {
                     var2.appendReplacement(var3, "\\\\\\\"");
                     var5 = var3;
                  }
               } else {
                  var2.appendReplacement(var3, "\\\\r");
                  var5 = var3;
               }
            } else {
               var2.appendReplacement(var3, "\\\\f");
               var5 = var3;
            }
         }

         if (var5 == null) {
            return var0;
         }

         var2.appendTail(var5);
         var1 = var5.toString();
      }

      return var1;
   }

   public static String unescapeString(String var0) {
      String var1 = var0;
      if (!TextUtils.isEmpty(var0)) {
         String var2 = zzc.zza(var0);
         Matcher var3 = zza.matcher(var2);
         StringBuffer var5 = null;

         while(var3.find()) {
            StringBuffer var6 = var5;
            if (var5 == null) {
               var6 = new StringBuffer();
            }

            char var4 = var3.group().charAt(1);
            if (var4 != '"') {
               if (var4 != '/') {
                  if (var4 != '\\') {
                     if (var4 != 'b') {
                        if (var4 != 'f') {
                           if (var4 != 'n') {
                              if (var4 != 'r') {
                                 if (var4 != 't') {
                                    throw new IllegalStateException("Found an escaped character that should never be.");
                                 }

                                 var3.appendReplacement(var6, "\t");
                                 var5 = var6;
                              } else {
                                 var3.appendReplacement(var6, "\r");
                                 var5 = var6;
                              }
                           } else {
                              var3.appendReplacement(var6, "\n");
                              var5 = var6;
                           }
                        } else {
                           var3.appendReplacement(var6, "\f");
                           var5 = var6;
                        }
                     } else {
                        var3.appendReplacement(var6, "\b");
                        var5 = var6;
                     }
                  } else {
                     var3.appendReplacement(var6, "\\\\");
                     var5 = var6;
                  }
               } else {
                  var3.appendReplacement(var6, "/");
                  var5 = var6;
               }
            } else {
               var3.appendReplacement(var6, "\"");
               var5 = var6;
            }
         }

         if (var5 == null) {
            return var2;
         }

         var3.appendTail(var5);
         var1 = var5.toString();
      }

      return var1;
   }
}
