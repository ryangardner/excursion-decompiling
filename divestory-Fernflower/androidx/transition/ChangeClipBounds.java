package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;

public class ChangeClipBounds extends Transition {
   private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";
   private static final String PROPNAME_CLIP = "android:clipBounds:clip";
   private static final String[] sTransitionProperties = new String[]{"android:clipBounds:clip"};

   public ChangeClipBounds() {
   }

   public ChangeClipBounds(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private void captureValues(TransitionValues var1) {
      View var2 = var1.view;
      if (var2.getVisibility() != 8) {
         Rect var3 = ViewCompat.getClipBounds(var2);
         var1.values.put("android:clipBounds:clip", var3);
         if (var3 == null) {
            Rect var4 = new Rect(0, 0, var2.getWidth(), var2.getHeight());
            var1.values.put("android:clipBounds:bounds", var4);
         }

      }
   }

   public void captureEndValues(TransitionValues var1) {
      this.captureValues(var1);
   }

   public void captureStartValues(TransitionValues var1) {
      this.captureValues(var1);
   }

   public Animator createAnimator(ViewGroup var1, TransitionValues var2, TransitionValues var3) {
      Rect var4 = null;
      ObjectAnimator var7 = var4;
      if (var2 != null) {
         var7 = var4;
         if (var3 != null) {
            var7 = var4;
            if (var2.values.containsKey("android:clipBounds:clip")) {
               if (!var3.values.containsKey("android:clipBounds:clip")) {
                  var7 = var4;
               } else {
                  Rect var5 = (Rect)var2.values.get("android:clipBounds:clip");
                  var4 = (Rect)var3.values.get("android:clipBounds:clip");
                  boolean var6;
                  if (var4 == null) {
                     var6 = true;
                  } else {
                     var6 = false;
                  }

                  if (var5 == null && var4 == null) {
                     return null;
                  }

                  Rect var8;
                  Rect var9;
                  if (var5 == null) {
                     var9 = (Rect)var2.values.get("android:clipBounds:bounds");
                     var8 = var4;
                  } else {
                     var9 = var5;
                     var8 = var4;
                     if (var4 == null) {
                        var8 = (Rect)var3.values.get("android:clipBounds:bounds");
                        var9 = var5;
                     }
                  }

                  if (var9.equals(var8)) {
                     return null;
                  }

                  ViewCompat.setClipBounds(var3.view, var9);
                  RectEvaluator var11 = new RectEvaluator(new Rect());
                  ObjectAnimator var10 = ObjectAnimator.ofObject(var3.view, ViewUtils.CLIP_BOUNDS, var11, new Rect[]{var9, var8});
                  var7 = var10;
                  if (var6) {
                     var10.addListener(new AnimatorListenerAdapter(var3.view) {
                        // $FF: synthetic field
                        final View val$endView;

                        {
                           this.val$endView = var2;
                        }

                        public void onAnimationEnd(Animator var1) {
                           ViewCompat.setClipBounds(this.val$endView, (Rect)null);
                        }
                     });
                     var7 = var10;
                  }
               }
            }
         }
      }

      return var7;
   }

   public String[] getTransitionProperties() {
      return sTransitionProperties;
   }
}
