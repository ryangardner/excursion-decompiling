package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

public class ViewUtils {
   private ViewUtils() {
   }

   public static String getXmlAttributeString(String var0, String var1, Context var2, AttributeSet var3, boolean var4, boolean var5, String var6) {
      if (var3 == null) {
         var0 = null;
      } else {
         var0 = var3.getAttributeValue(var0, var1);
      }

      String var16 = var0;
      if (var0 != null) {
         var16 = var0;
         if (var0.startsWith("@string/")) {
            var16 = var0;
            if (var4) {
               String var7 = var0.substring(8);
               String var8 = var2.getPackageName();
               TypedValue var17 = new TypedValue();

               StringBuilder var14;
               try {
                  Resources var9 = var2.getResources();
                  int var10 = String.valueOf(var8).length();
                  int var11 = String.valueOf(var7).length();
                  var14 = new StringBuilder(var10 + 8 + var11);
                  var14.append(var8);
                  var14.append(":string/");
                  var14.append(var7);
                  var9.getValue(var14.toString(), var17, true);
               } catch (NotFoundException var12) {
                  var14 = new StringBuilder(String.valueOf(var1).length() + 30 + String.valueOf(var0).length());
                  var14.append("Could not find resource for ");
                  var14.append(var1);
                  var14.append(": ");
                  var14.append(var0);
                  Log.w(var6, var14.toString());
               }

               if (var17.string != null) {
                  var16 = var17.string.toString();
               } else {
                  String var15 = String.valueOf(var17);
                  StringBuilder var18 = new StringBuilder(String.valueOf(var1).length() + 28 + String.valueOf(var15).length());
                  var18.append("Resource ");
                  var18.append(var1);
                  var18.append(" was not a string: ");
                  var18.append(var15);
                  Log.w(var6, var18.toString());
                  var16 = var0;
               }
            }
         }
      }

      if (var5 && var16 == null) {
         StringBuilder var13 = new StringBuilder(String.valueOf(var1).length() + 33);
         var13.append("Required XML attribute \"");
         var13.append(var1);
         var13.append("\" missing");
         Log.w(var6, var13.toString());
      }

      return var16;
   }
}
