package com.google.android.material.internal;

import android.os.Build.VERSION;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.Layout.Alignment;
import android.text.StaticLayout.Builder;
import android.text.TextUtils.TruncateAt;
import androidx.core.util.Preconditions;
import java.lang.reflect.Constructor;

final class StaticLayoutBuilderCompat {
   private static final String TEXT_DIRS_CLASS = "android.text.TextDirectionHeuristics";
   private static final String TEXT_DIR_CLASS = "android.text.TextDirectionHeuristic";
   private static final String TEXT_DIR_CLASS_LTR = "LTR";
   private static final String TEXT_DIR_CLASS_RTL = "RTL";
   private static Constructor<StaticLayout> constructor;
   private static boolean initialized;
   private static Object textDirection;
   private Alignment alignment;
   private TruncateAt ellipsize;
   private int end;
   private boolean includePad;
   private boolean isRtl;
   private int maxLines;
   private final TextPaint paint;
   private CharSequence source;
   private int start;
   private final int width;

   private StaticLayoutBuilderCompat(CharSequence var1, TextPaint var2, int var3) {
      this.source = var1;
      this.paint = var2;
      this.width = var3;
      this.start = 0;
      this.end = var1.length();
      this.alignment = Alignment.ALIGN_NORMAL;
      this.maxLines = Integer.MAX_VALUE;
      this.includePad = true;
      this.ellipsize = null;
   }

   private void createConstructorWithReflection() throws StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException {
      if (!initialized) {
         Exception var10000;
         label84: {
            boolean var1;
            boolean var10001;
            label79: {
               label78: {
                  try {
                     if (this.isRtl && VERSION.SDK_INT >= 23) {
                        break label78;
                     }
                  } catch (Exception var12) {
                     var10000 = var12;
                     var10001 = false;
                     break label84;
                  }

                  var1 = false;
                  break label79;
               }

               var1 = true;
            }

            Class var13;
            label85: {
               Class var2;
               label86: {
                  try {
                     if (VERSION.SDK_INT >= 18) {
                        break label86;
                     }
                  } catch (Exception var11) {
                     var10000 = var11;
                     var10001 = false;
                     break label84;
                  }

                  String var3;
                  ClassLoader var4;
                  label61: {
                     label60: {
                        try {
                           var4 = StaticLayoutBuilderCompat.class.getClassLoader();
                           if (!this.isRtl) {
                              break label60;
                           }
                        } catch (Exception var10) {
                           var10000 = var10;
                           var10001 = false;
                           break label84;
                        }

                        var3 = "RTL";
                        break label61;
                     }

                     var3 = "LTR";
                  }

                  try {
                     var2 = var4.loadClass("android.text.TextDirectionHeuristic");
                     Class var15 = var4.loadClass("android.text.TextDirectionHeuristics");
                     textDirection = var15.getField(var3).get(var15);
                  } catch (Exception var6) {
                     var10000 = var6;
                     var10001 = false;
                     break label84;
                  }

                  var13 = var2;
                  break label85;
               }

               var2 = TextDirectionHeuristic.class;
               TextDirectionHeuristic var14;
               if (var1) {
                  try {
                     var14 = TextDirectionHeuristics.RTL;
                  } catch (Exception var9) {
                     var10000 = var9;
                     var10001 = false;
                     break label84;
                  }
               } else {
                  try {
                     var14 = TextDirectionHeuristics.LTR;
                  } catch (Exception var8) {
                     var10000 = var8;
                     var10001 = false;
                     break label84;
                  }
               }

               try {
                  textDirection = var14;
               } catch (Exception var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label84;
               }

               var13 = var2;
            }

            try {
               Constructor var17 = StaticLayout.class.getDeclaredConstructor(CharSequence.class, Integer.TYPE, Integer.TYPE, TextPaint.class, Integer.TYPE, Alignment.class, var13, Float.TYPE, Float.TYPE, Boolean.TYPE, TruncateAt.class, Integer.TYPE, Integer.TYPE);
               constructor = var17;
               var17.setAccessible(true);
               initialized = true;
               return;
            } catch (Exception var5) {
               var10000 = var5;
               var10001 = false;
            }
         }

         Exception var16 = var10000;
         throw new StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException(var16);
      }
   }

   public static StaticLayoutBuilderCompat obtain(CharSequence var0, TextPaint var1, int var2) {
      return new StaticLayoutBuilderCompat(var0, var1, var2);
   }

   public StaticLayout build() throws StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException {
      if (this.source == null) {
         this.source = "";
      }

      int var1 = Math.max(0, this.width);
      CharSequence var2 = this.source;
      CharSequence var3 = var2;
      if (this.maxLines == 1) {
         var3 = TextUtils.ellipsize(var2, this.paint, (float)var1, this.ellipsize);
      }

      this.end = Math.min(var3.length(), this.end);
      if (VERSION.SDK_INT >= 23) {
         if (this.isRtl) {
            this.alignment = Alignment.ALIGN_OPPOSITE;
         }

         Builder var5 = Builder.obtain(var3, this.start, this.end, this.paint, var1);
         var5.setAlignment(this.alignment);
         var5.setIncludePad(this.includePad);
         TextDirectionHeuristic var7;
         if (this.isRtl) {
            var7 = TextDirectionHeuristics.RTL;
         } else {
            var7 = TextDirectionHeuristics.LTR;
         }

         var5.setTextDirection(var7);
         TruncateAt var8 = this.ellipsize;
         if (var8 != null) {
            var5.setEllipsize(var8);
         }

         var5.setMaxLines(this.maxLines);
         return var5.build();
      } else {
         this.createConstructorWithReflection();

         try {
            StaticLayout var6 = (StaticLayout)((Constructor)Preconditions.checkNotNull(constructor)).newInstance(var3, this.start, this.end, this.paint, var1, this.alignment, Preconditions.checkNotNull(textDirection), 1.0F, 0.0F, this.includePad, null, var1, this.maxLines);
            return var6;
         } catch (Exception var4) {
            throw new StaticLayoutBuilderCompat.StaticLayoutBuilderCompatException(var4);
         }
      }
   }

   public StaticLayoutBuilderCompat setAlignment(Alignment var1) {
      this.alignment = var1;
      return this;
   }

   public StaticLayoutBuilderCompat setEllipsize(TruncateAt var1) {
      this.ellipsize = var1;
      return this;
   }

   public StaticLayoutBuilderCompat setEnd(int var1) {
      this.end = var1;
      return this;
   }

   public StaticLayoutBuilderCompat setIncludePad(boolean var1) {
      this.includePad = var1;
      return this;
   }

   public StaticLayoutBuilderCompat setIsRtl(boolean var1) {
      this.isRtl = var1;
      return this;
   }

   public StaticLayoutBuilderCompat setMaxLines(int var1) {
      this.maxLines = var1;
      return this;
   }

   public StaticLayoutBuilderCompat setStart(int var1) {
      this.start = var1;
      return this;
   }

   static class StaticLayoutBuilderCompatException extends Exception {
      StaticLayoutBuilderCompatException(Throwable var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Error thrown initializing StaticLayout ");
         var2.append(var1.getMessage());
         super(var2.toString(), var1);
      }
   }
}
