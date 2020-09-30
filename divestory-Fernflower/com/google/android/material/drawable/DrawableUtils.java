package com.google.android.material.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.content.res.Resources.NotFoundException;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableUtils {
   private DrawableUtils() {
   }

   public static AttributeSet parseDrawableXml(Context var0, int var1, CharSequence var2) {
      Object var17;
      label61: {
         XmlPullParserException var20;
         label60: {
            IOException var10000;
            label59: {
               XmlResourceParser var15;
               boolean var10001;
               try {
                  var15 = var0.getResources().getXml(var1);
               } catch (XmlPullParserException var13) {
                  var20 = var13;
                  var10001 = false;
                  break label60;
               } catch (IOException var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label59;
               }

               while(true) {
                  int var3;
                  try {
                     var3 = var15.next();
                  } catch (XmlPullParserException var11) {
                     var20 = var11;
                     var10001 = false;
                     break label60;
                  } catch (IOException var12) {
                     var10000 = var12;
                     var10001 = false;
                     break;
                  }

                  if (var3 == 2 || var3 == 1) {
                     XmlPullParserException var16;
                     if (var3 == 2) {
                        try {
                           if (TextUtils.equals(var15.getName(), var2)) {
                              return Xml.asAttributeSet(var15);
                           }
                        } catch (XmlPullParserException var7) {
                           var20 = var7;
                           var10001 = false;
                           break label60;
                        } catch (IOException var8) {
                           var10000 = var8;
                           var10001 = false;
                           break;
                        }

                        try {
                           StringBuilder var4 = new StringBuilder();
                           var4.append("Must have a <");
                           var4.append(var2);
                           var4.append("> start tag");
                           var16 = new XmlPullParserException(var4.toString());
                           throw var16;
                        } catch (XmlPullParserException var5) {
                           var20 = var5;
                           var10001 = false;
                           break label60;
                        } catch (IOException var6) {
                           var10000 = var6;
                           var10001 = false;
                           break;
                        }
                     } else {
                        try {
                           var16 = new XmlPullParserException("No start tag found");
                           throw var16;
                        } catch (XmlPullParserException var9) {
                           var20 = var9;
                           var10001 = false;
                           break label60;
                        } catch (IOException var10) {
                           var10000 = var10;
                           var10001 = false;
                           break;
                        }
                     }
                  }
               }
            }

            var17 = var10000;
            break label61;
         }

         var17 = var20;
      }

      StringBuilder var18 = new StringBuilder();
      var18.append("Can't load badge resource ID #0x");
      var18.append(Integer.toHexString(var1));
      NotFoundException var19 = new NotFoundException(var18.toString());
      var19.initCause((Throwable)var17);
      throw var19;
   }

   public static void setRippleDrawableRadius(RippleDrawable var0, int var1) {
      if (VERSION.SDK_INT >= 23) {
         var0.setRadius(var1);
      } else {
         Object var5;
         try {
            RippleDrawable.class.getDeclaredMethod("setMaxRadius", Integer.TYPE).invoke(var0, var1);
            return;
         } catch (NoSuchMethodException var2) {
            var5 = var2;
         } catch (InvocationTargetException var3) {
            var5 = var3;
         } catch (IllegalAccessException var4) {
            var5 = var4;
         }

         throw new IllegalStateException("Couldn't set RippleDrawable radius", (Throwable)var5);
      }
   }

   public static PorterDuffColorFilter updateTintFilter(Drawable var0, ColorStateList var1, Mode var2) {
      return var1 != null && var2 != null ? new PorterDuffColorFilter(var1.getColorForState(var0.getState(), 0), var2) : null;
   }
}
