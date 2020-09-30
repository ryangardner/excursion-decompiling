package androidx.transition;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.util.Log;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewGroupUtilsApi14 {
   private static final int LAYOUT_TRANSITION_CHANGING = 4;
   private static final String TAG = "ViewGroupUtilsApi14";
   private static Method sCancelMethod;
   private static boolean sCancelMethodFetched;
   private static LayoutTransition sEmptyLayoutTransition;
   private static Field sLayoutSuppressedField;
   private static boolean sLayoutSuppressedFieldFetched;

   private ViewGroupUtilsApi14() {
   }

   private static void cancelLayoutTransition(LayoutTransition var0) {
      Method var1;
      if (!sCancelMethodFetched) {
         try {
            var1 = LayoutTransition.class.getDeclaredMethod("cancel");
            sCancelMethod = var1;
            var1.setAccessible(true);
         } catch (NoSuchMethodException var4) {
            Log.i("ViewGroupUtilsApi14", "Failed to access cancel method by reflection");
         }

         sCancelMethodFetched = true;
      }

      var1 = sCancelMethod;
      if (var1 != null) {
         try {
            var1.invoke(var0);
         } catch (IllegalAccessException var2) {
            Log.i("ViewGroupUtilsApi14", "Failed to access cancel method by reflection");
         } catch (InvocationTargetException var3) {
            Log.i("ViewGroupUtilsApi14", "Failed to invoke cancel method by reflection");
         }
      }

   }

   static void suppressLayout(ViewGroup var0, boolean var1) {
      LayoutTransition var2 = sEmptyLayoutTransition;
      boolean var3 = false;
      boolean var4 = false;
      if (var2 == null) {
         var2 = new LayoutTransition() {
            public boolean isChangingLayout() {
               return true;
            }
         };
         sEmptyLayoutTransition = var2;
         var2.setAnimator(2, (Animator)null);
         sEmptyLayoutTransition.setAnimator(0, (Animator)null);
         sEmptyLayoutTransition.setAnimator(1, (Animator)null);
         sEmptyLayoutTransition.setAnimator(3, (Animator)null);
         sEmptyLayoutTransition.setAnimator(4, (Animator)null);
      }

      if (var1) {
         var2 = var0.getLayoutTransition();
         if (var2 != null) {
            if (var2.isRunning()) {
               cancelLayoutTransition(var2);
            }

            if (var2 != sEmptyLayoutTransition) {
               var0.setTag(R.id.transition_layout_save, var2);
            }
         }

         var0.setLayoutTransition(sEmptyLayoutTransition);
      } else {
         var0.setLayoutTransition((LayoutTransition)null);
         Field var8;
         if (!sLayoutSuppressedFieldFetched) {
            try {
               var8 = ViewGroup.class.getDeclaredField("mLayoutSuppressed");
               sLayoutSuppressedField = var8;
               var8.setAccessible(true);
            } catch (NoSuchFieldException var5) {
               Log.i("ViewGroupUtilsApi14", "Failed to access mLayoutSuppressed field by reflection");
            }

            sLayoutSuppressedFieldFetched = true;
         }

         var8 = sLayoutSuppressedField;
         var1 = var3;
         if (var8 != null) {
            label69: {
               label68: {
                  try {
                     var1 = var8.getBoolean(var0);
                  } catch (IllegalAccessException var7) {
                     var1 = var4;
                     break label68;
                  }

                  if (!var1) {
                     break label69;
                  }

                  try {
                     sLayoutSuppressedField.setBoolean(var0, false);
                     break label69;
                  } catch (IllegalAccessException var6) {
                  }
               }

               Log.i("ViewGroupUtilsApi14", "Failed to get mLayoutSuppressed field by reflection");
            }
         }

         if (var1) {
            var0.requestLayout();
         }

         var2 = (LayoutTransition)var0.getTag(R.id.transition_layout_save);
         if (var2 != null) {
            var0.setTag(R.id.transition_layout_save, (Object)null);
            var0.setLayoutTransition(var2);
         }
      }

   }
}
