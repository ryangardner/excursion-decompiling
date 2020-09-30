package com.google.android.material.transition.platform;

public final class MaterialFadeThrough extends MaterialVisibility<FadeThroughProvider> {
   private static final float DEFAULT_START_SCALE = 0.92F;

   public MaterialFadeThrough() {
      super(createPrimaryAnimatorProvider(), createSecondaryAnimatorProvider());
   }

   private static FadeThroughProvider createPrimaryAnimatorProvider() {
      return new FadeThroughProvider();
   }

   private static VisibilityAnimatorProvider createSecondaryAnimatorProvider() {
      ScaleProvider var0 = new ScaleProvider();
      var0.setScaleOnDisappear(false);
      var0.setIncomingStartScale(0.92F);
      return var0;
   }
}
