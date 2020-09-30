package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;
import android.view.ViewGroup;

public final class ScaleProvider implements VisibilityAnimatorProvider {
   private boolean growing;
   private float incomingEndScale;
   private float incomingStartScale;
   private float outgoingEndScale;
   private float outgoingStartScale;
   private boolean scaleOnDisappear;

   public ScaleProvider() {
      this(true);
   }

   public ScaleProvider(boolean var1) {
      this.outgoingStartScale = 1.0F;
      this.outgoingEndScale = 1.1F;
      this.incomingStartScale = 0.8F;
      this.incomingEndScale = 1.0F;
      this.scaleOnDisappear = true;
      this.growing = var1;
   }

   private static Animator createScaleAnimator(View var0, float var1, float var2) {
      return ObjectAnimator.ofPropertyValuesHolder(var0, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(View.SCALE_X, new float[]{var1, var2}), PropertyValuesHolder.ofFloat(View.SCALE_Y, new float[]{var1, var2})});
   }

   public Animator createAppear(ViewGroup var1, View var2) {
      return this.growing ? createScaleAnimator(var2, this.incomingStartScale, this.incomingEndScale) : createScaleAnimator(var2, this.outgoingEndScale, this.outgoingStartScale);
   }

   public Animator createDisappear(ViewGroup var1, View var2) {
      if (!this.scaleOnDisappear) {
         return null;
      } else {
         return this.growing ? createScaleAnimator(var2, this.outgoingStartScale, this.outgoingEndScale) : createScaleAnimator(var2, this.incomingEndScale, this.incomingStartScale);
      }
   }

   public float getIncomingEndScale() {
      return this.incomingEndScale;
   }

   public float getIncomingStartScale() {
      return this.incomingStartScale;
   }

   public float getOutgoingEndScale() {
      return this.outgoingEndScale;
   }

   public float getOutgoingStartScale() {
      return this.outgoingStartScale;
   }

   public boolean isGrowing() {
      return this.growing;
   }

   public boolean isScaleOnDisappear() {
      return this.scaleOnDisappear;
   }

   public void setGrowing(boolean var1) {
      this.growing = var1;
   }

   public void setIncomingEndScale(float var1) {
      this.incomingEndScale = var1;
   }

   public void setIncomingStartScale(float var1) {
      this.incomingStartScale = var1;
   }

   public void setOutgoingEndScale(float var1) {
      this.outgoingEndScale = var1;
   }

   public void setOutgoingStartScale(float var1) {
      this.outgoingStartScale = var1;
   }

   public void setScaleOnDisappear(boolean var1) {
      this.scaleOnDisappear = var1;
   }
}
