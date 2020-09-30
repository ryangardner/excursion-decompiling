package com.google.android.material.transition.platform;

public final class MaterialElevationScale extends MaterialVisibility<ScaleProvider> {
   private static final float DEFAULT_SCALE = 0.85F;
   private final boolean growing;

   public MaterialElevationScale(boolean var1) {
      super(createPrimaryAnimatorProvider(var1), createSecondaryAnimatorProvider());
      this.growing = var1;
   }

   private static ScaleProvider createPrimaryAnimatorProvider(boolean var0) {
      ScaleProvider var1 = new ScaleProvider(var0);
      var1.setOutgoingEndScale(0.85F);
      var1.setIncomingStartScale(0.85F);
      return var1;
   }

   private static VisibilityAnimatorProvider createSecondaryAnimatorProvider() {
      return new FadeProvider();
   }

   public boolean isGrowing() {
      return this.growing;
   }
}
