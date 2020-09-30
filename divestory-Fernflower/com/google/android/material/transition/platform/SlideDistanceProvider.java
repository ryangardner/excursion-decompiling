package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SlideDistanceProvider implements VisibilityAnimatorProvider {
   private static final int DEFAULT_DISTANCE = -1;
   private int slideDistance = -1;
   private int slideEdge;

   public SlideDistanceProvider(int var1) {
      this.slideEdge = var1;
   }

   private static Animator createTranslationAppearAnimator(View var0, View var1, int var2, int var3) {
      if (var2 != 3) {
         if (var2 != 5) {
            if (var2 != 48) {
               if (var2 != 80) {
                  float var4;
                  if (var2 != 8388611) {
                     if (var2 == 8388613) {
                        if (isRtl(var0)) {
                           var4 = (float)(-var3);
                        } else {
                           var4 = (float)var3;
                        }

                        return createTranslationXAnimator(var1, var4, 0.0F);
                     } else {
                        StringBuilder var5 = new StringBuilder();
                        var5.append("Invalid slide direction: ");
                        var5.append(var2);
                        throw new IllegalArgumentException(var5.toString());
                     }
                  } else {
                     if (isRtl(var0)) {
                        var4 = (float)var3;
                     } else {
                        var4 = (float)(-var3);
                     }

                     return createTranslationXAnimator(var1, var4, 0.0F);
                  }
               } else {
                  return createTranslationYAnimator(var1, (float)var3, 0.0F);
               }
            } else {
               return createTranslationYAnimator(var1, (float)(-var3), 0.0F);
            }
         } else {
            return createTranslationXAnimator(var1, (float)(-var3), 0.0F);
         }
      } else {
         return createTranslationXAnimator(var1, (float)var3, 0.0F);
      }
   }

   private static Animator createTranslationDisappearAnimator(View var0, View var1, int var2, int var3) {
      if (var2 != 3) {
         if (var2 != 5) {
            if (var2 != 48) {
               if (var2 != 80) {
                  float var4;
                  if (var2 != 8388611) {
                     if (var2 == 8388613) {
                        if (isRtl(var0)) {
                           var4 = (float)var3;
                        } else {
                           var4 = (float)(-var3);
                        }

                        return createTranslationXAnimator(var1, 0.0F, var4);
                     } else {
                        StringBuilder var5 = new StringBuilder();
                        var5.append("Invalid slide direction: ");
                        var5.append(var2);
                        throw new IllegalArgumentException(var5.toString());
                     }
                  } else {
                     if (isRtl(var0)) {
                        var4 = (float)(-var3);
                     } else {
                        var4 = (float)var3;
                     }

                     return createTranslationXAnimator(var1, 0.0F, var4);
                  }
               } else {
                  return createTranslationYAnimator(var1, 0.0F, (float)(-var3));
               }
            } else {
               return createTranslationYAnimator(var1, 0.0F, (float)var3);
            }
         } else {
            return createTranslationXAnimator(var1, 0.0F, (float)var3);
         }
      } else {
         return createTranslationXAnimator(var1, 0.0F, (float)(-var3));
      }
   }

   private static Animator createTranslationXAnimator(View var0, float var1, float var2) {
      return ObjectAnimator.ofPropertyValuesHolder(var0, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(View.TRANSLATION_X, new float[]{var1, var2})});
   }

   private static Animator createTranslationYAnimator(View var0, float var1, float var2) {
      return ObjectAnimator.ofPropertyValuesHolder(var0, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, new float[]{var1, var2})});
   }

   private int getSlideDistanceOrDefault(Context var1) {
      int var2 = this.slideDistance;
      return var2 != -1 ? var2 : var1.getResources().getDimensionPixelSize(R.dimen.mtrl_transition_shared_axis_slide_distance);
   }

   private static boolean isRtl(View var0) {
      int var1 = ViewCompat.getLayoutDirection(var0);
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   public Animator createAppear(ViewGroup var1, View var2) {
      return createTranslationAppearAnimator(var1, var2, this.slideEdge, this.getSlideDistanceOrDefault(var2.getContext()));
   }

   public Animator createDisappear(ViewGroup var1, View var2) {
      return createTranslationDisappearAnimator(var1, var2, this.slideEdge, this.getSlideDistanceOrDefault(var2.getContext()));
   }

   public int getSlideDistance() {
      return this.slideDistance;
   }

   public int getSlideEdge() {
      return this.slideEdge;
   }

   public void setSlideDistance(int var1) {
      if (var1 >= 0) {
         this.slideDistance = var1;
      } else {
         throw new IllegalArgumentException("Slide distance must be positive. If attempting to reverse the direction of the slide, use setSlideEdge(int) instead.");
      }
   }

   public void setSlideEdge(int var1) {
      this.slideEdge = var1;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface GravityFlag {
   }
}
