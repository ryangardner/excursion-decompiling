package androidx.appcompat.widget;

import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.os.Build.VERSION;
import android.util.Log;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.WrappedDrawable;
import java.lang.reflect.Field;

public class DrawableUtils {
   private static final int[] CHECKED_STATE_SET = new int[]{16842912};
   private static final int[] EMPTY_STATE_SET = new int[0];
   public static final Rect INSETS_NONE = new Rect();
   private static final String TAG = "DrawableUtils";
   private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
   private static Class<?> sInsetsClazz;

   static {
      if (VERSION.SDK_INT >= 18) {
         try {
            sInsetsClazz = Class.forName("android.graphics.Insets");
         } catch (ClassNotFoundException var1) {
         }
      }

   }

   private DrawableUtils() {
   }

   public static boolean canSafelyMutateDrawable(Drawable var0) {
      if (VERSION.SDK_INT < 15 && var0 instanceof InsetDrawable) {
         return false;
      } else if (VERSION.SDK_INT < 15 && var0 instanceof GradientDrawable) {
         return false;
      } else if (VERSION.SDK_INT < 17 && var0 instanceof LayerDrawable) {
         return false;
      } else {
         if (var0 instanceof DrawableContainer) {
            ConstantState var3 = var0.getConstantState();
            if (var3 instanceof DrawableContainerState) {
               Drawable[] var4 = ((DrawableContainerState)var3).getChildren();
               int var1 = var4.length;

               for(int var2 = 0; var2 < var1; ++var2) {
                  if (!canSafelyMutateDrawable(var4[var2])) {
                     return false;
                  }
               }
            }
         } else {
            if (var0 instanceof WrappedDrawable) {
               return canSafelyMutateDrawable(((WrappedDrawable)var0).getWrappedDrawable());
            }

            if (var0 instanceof DrawableWrapper) {
               return canSafelyMutateDrawable(((DrawableWrapper)var0).getWrappedDrawable());
            }

            if (var0 instanceof ScaleDrawable) {
               return canSafelyMutateDrawable(((ScaleDrawable)var0).getDrawable());
            }
         }

         return true;
      }
   }

   static void fixDrawable(Drawable var0) {
      if (VERSION.SDK_INT == 21 && "android.graphics.drawable.VectorDrawable".equals(var0.getClass().getName())) {
         fixVectorDrawableTinting(var0);
      }

   }

   private static void fixVectorDrawableTinting(Drawable var0) {
      int[] var1 = var0.getState();
      if (var1 != null && var1.length != 0) {
         var0.setState(EMPTY_STATE_SET);
      } else {
         var0.setState(CHECKED_STATE_SET);
      }

      var0.setState(var1);
   }

   public static Rect getOpticalBounds(Drawable var0) {
      if (VERSION.SDK_INT >= 29) {
         Insets var22 = var0.getOpticalInsets();
         Rect var21 = new Rect();
         var21.left = var22.left;
         var21.right = var22.right;
         var21.top = var22.top;
         var21.bottom = var22.bottom;
         return var21;
      } else {
         if (sInsetsClazz != null) {
            label140: {
               boolean var10001;
               Object var2;
               try {
                  var0 = DrawableCompat.unwrap(var0);
                  var2 = var0.getClass().getMethod("getOpticalInsets").invoke(var0);
               } catch (Exception var19) {
                  var10001 = false;
                  break label140;
               }

               if (var2 == null) {
                  return INSETS_NONE;
               }

               Rect var3;
               Field[] var20;
               int var4;
               try {
                  var3 = new Rect();
                  var20 = sInsetsClazz.getFields();
                  var4 = var20.length;
               } catch (Exception var18) {
                  var10001 = false;
                  break label140;
               }

               int var5 = 0;

               while(true) {
                  if (var5 >= var4) {
                     return var3;
                  }

                  Field var1 = var20[var5];

                  String var6;
                  try {
                     var6 = var1.getName();
                  } catch (Exception var12) {
                     var10001 = false;
                     break;
                  }

                  byte var7 = -1;

                  label142: {
                     label143: {
                        label144: {
                           label145: {
                              try {
                                 switch(var6.hashCode()) {
                                 case -1383228885:
                                    break label145;
                                 case 115029:
                                    break;
                                 case 3317767:
                                    break label143;
                                 case 108511772:
                                    break label144;
                                 default:
                                    break label142;
                                 }
                              } catch (Exception var17) {
                                 var10001 = false;
                                 break;
                              }

                              try {
                                 if (!var6.equals("top")) {
                                    break label142;
                                 }
                              } catch (Exception var14) {
                                 var10001 = false;
                                 break;
                              }

                              var7 = 1;
                              break label142;
                           }

                           try {
                              if (!var6.equals("bottom")) {
                                 break label142;
                              }
                           } catch (Exception var13) {
                              var10001 = false;
                              break;
                           }

                           var7 = 3;
                           break label142;
                        }

                        try {
                           if (!var6.equals("right")) {
                              break label142;
                           }
                        } catch (Exception var16) {
                           var10001 = false;
                           break;
                        }

                        var7 = 2;
                        break label142;
                     }

                     try {
                        if (!var6.equals("left")) {
                           break label142;
                        }
                     } catch (Exception var15) {
                        var10001 = false;
                        break;
                     }

                     var7 = 0;
                  }

                  if (var7 != 0) {
                     if (var7 != 1) {
                        if (var7 != 2) {
                           if (var7 == 3) {
                              try {
                                 var3.bottom = var1.getInt(var2);
                              } catch (Exception var11) {
                                 var10001 = false;
                                 break;
                              }
                           }
                        } else {
                           try {
                              var3.right = var1.getInt(var2);
                           } catch (Exception var10) {
                              var10001 = false;
                              break;
                           }
                        }
                     } else {
                        try {
                           var3.top = var1.getInt(var2);
                        } catch (Exception var9) {
                           var10001 = false;
                           break;
                        }
                     }
                  } else {
                     try {
                        var3.left = var1.getInt(var2);
                     } catch (Exception var8) {
                        var10001 = false;
                        break;
                     }
                  }

                  ++var5;
               }
            }

            Log.e("DrawableUtils", "Couldn't obtain the optical insets. Ignoring.");
         }

         return INSETS_NONE;
      }
   }

   public static Mode parseTintMode(int var0, Mode var1) {
      if (var0 != 3) {
         if (var0 != 5) {
            if (var0 != 9) {
               switch(var0) {
               case 14:
                  return Mode.MULTIPLY;
               case 15:
                  return Mode.SCREEN;
               case 16:
                  return Mode.ADD;
               default:
                  return var1;
               }
            } else {
               return Mode.SRC_ATOP;
            }
         } else {
            return Mode.SRC_IN;
         }
      } else {
         return Mode.SRC_OVER;
      }
   }
}
