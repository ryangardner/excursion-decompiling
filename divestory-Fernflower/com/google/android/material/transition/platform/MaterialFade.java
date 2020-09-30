package com.google.android.material.transition.platform;

public final class MaterialFade extends MaterialVisibility<FadeProvider> {
   private static final float DEFAULT_FADE_END_THRESHOLD_ENTER = 0.3F;
   private static final float DEFAULT_START_SCALE = 0.8F;

   public MaterialFade() {
      super(createPrimaryAnimatorProvider(), createSecondaryAnimatorProvider());
   }

   private static FadeProvider createPrimaryAnimatorProvider() {
      FadeProvider var0 = new FadeProvider();
      var0.setIncomingEndThreshold(0.3F);
      return var0;
   }

   private static VisibilityAnimatorProvider createSecondaryAnimatorProvider() {
      ScaleProvider var0 = new ScaleProvider();
      var0.setScaleOnDisappear(false);
      var0.setIncomingStartScale(0.8F);
      return var0;
   }
}
