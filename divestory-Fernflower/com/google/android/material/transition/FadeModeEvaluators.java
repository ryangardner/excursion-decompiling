package com.google.android.material.transition;

class FadeModeEvaluators {
   private static final FadeModeEvaluator CROSS = new FadeModeEvaluator() {
      public FadeModeResult evaluate(float var1, float var2, float var3) {
         return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, var2, var3, var1), TransitionUtils.lerp(0, 255, var2, var3, var1));
      }
   };
   private static final FadeModeEvaluator IN = new FadeModeEvaluator() {
      public FadeModeResult evaluate(float var1, float var2, float var3) {
         return FadeModeResult.endOnTop(255, TransitionUtils.lerp(0, 255, var2, var3, var1));
      }
   };
   private static final FadeModeEvaluator OUT = new FadeModeEvaluator() {
      public FadeModeResult evaluate(float var1, float var2, float var3) {
         return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, var2, var3, var1), 255);
      }
   };
   private static final FadeModeEvaluator THROUGH = new FadeModeEvaluator() {
      public FadeModeResult evaluate(float var1, float var2, float var3) {
         float var4 = (var3 - var2) * 0.35F + var2;
         return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, var2, var4, var1), TransitionUtils.lerp(0, 255, var4, var3, var1));
      }
   };

   private FadeModeEvaluators() {
   }

   static FadeModeEvaluator get(int var0, boolean var1) {
      FadeModeEvaluator var2;
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 != 2) {
               if (var0 == 3) {
                  return THROUGH;
               } else {
                  StringBuilder var3 = new StringBuilder();
                  var3.append("Invalid fade mode: ");
                  var3.append(var0);
                  throw new IllegalArgumentException(var3.toString());
               }
            } else {
               return CROSS;
            }
         } else {
            if (var1) {
               var2 = OUT;
            } else {
               var2 = IN;
            }

            return var2;
         }
      } else {
         if (var1) {
            var2 = IN;
         } else {
            var2 = OUT;
         }

         return var2;
      }
   }
}
