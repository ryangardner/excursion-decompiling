package androidx.constraintlayout.motion.widget;

import android.view.animation.Interpolator;

public abstract class MotionInterpolator implements Interpolator {
   public abstract float getInterpolation(float var1);

   public abstract float getVelocity();
}