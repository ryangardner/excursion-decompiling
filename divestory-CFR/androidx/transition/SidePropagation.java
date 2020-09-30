/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;
import androidx.transition.VisibilityPropagation;

public class SidePropagation
extends VisibilityPropagation {
    private float mPropagationSpeed = 3.0f;
    private int mSide = 80;

    /*
     * Unable to fully structure code
     */
    private int distance(View var1_1, int var2_2, int var3_3, int var4_4, int var5_5, int var6_6, int var7_7, int var8_8, int var9_9) {
        block8 : {
            block7 : {
                var10_10 = this.mSide;
                var11_11 = 0;
                var12_12 = 1;
                var13_13 = 1;
                if (var10_10 != 8388611) break block7;
                if (ViewCompat.getLayoutDirection(var1_1) != 1) {
                    var13_13 = 0;
                }
                if (var13_13 == 0) ** GOTO lbl-1000
                ** GOTO lbl-1000
            }
            var13_13 = var10_10;
            if (var10_10 == 8388613) {
                var13_13 = ViewCompat.getLayoutDirection(var1_1) == 1 ? var12_12 : 0;
                ** if (var13_13 != 0) goto lbl-1000
            }
            break block8;
lbl-1000: // 2 sources:
            {
                var13_13 = 5;
                ** GOTO lbl19
            }
lbl-1000: // 2 sources:
            {
                var13_13 = 3;
            }
        }
        if (var13_13 == 3) {
            return var8_8 - var2_2 + Math.abs(var5_5 - var3_3);
        }
        if (var13_13 == 5) {
            return var2_2 - var6_6 + Math.abs(var5_5 - var3_3);
        }
        if (var13_13 == 48) {
            return var9_9 - var3_3 + Math.abs(var4_4 - var2_2);
        }
        if (var13_13 == 80) return var3_3 - var7_7 + Math.abs(var4_4 - var2_2);
        return var11_11;
    }

    private int getMaxDistance(ViewGroup viewGroup) {
        int n = this.mSide;
        if (n == 3) return viewGroup.getWidth();
        if (n == 5) return viewGroup.getWidth();
        if (n == 8388611) return viewGroup.getWidth();
        if (n == 8388613) return viewGroup.getWidth();
        return viewGroup.getHeight();
    }

    @Override
    public long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues arrn, TransitionValues transitionValues) {
        int n;
        long l;
        int n2;
        int n3;
        if (arrn == null && transitionValues == null) {
            return 0L;
        }
        Rect rect = transition.getEpicenter();
        if (transitionValues != null && this.getViewVisibility((TransitionValues)arrn) != 0) {
            arrn = transitionValues;
            n3 = 1;
        } else {
            n3 = -1;
        }
        int n4 = this.getViewX((TransitionValues)arrn);
        int n5 = this.getViewY((TransitionValues)arrn);
        arrn = new int[2];
        viewGroup.getLocationOnScreen(arrn);
        int n6 = arrn[0] + Math.round(viewGroup.getTranslationX());
        int n7 = arrn[1] + Math.round(viewGroup.getTranslationY());
        int n8 = n6 + viewGroup.getWidth();
        int n9 = n7 + viewGroup.getHeight();
        if (rect != null) {
            n2 = rect.centerX();
            n = rect.centerY();
        } else {
            n2 = (n6 + n8) / 2;
            n = (n7 + n9) / 2;
        }
        float f = (float)this.distance((View)viewGroup, n4, n5, n2, n, n6, n7, n8, n9) / (float)this.getMaxDistance(viewGroup);
        long l2 = l = transition.getDuration();
        if (l >= 0L) return Math.round((float)(l2 * (long)n3) / this.mPropagationSpeed * f);
        l2 = 300L;
        return Math.round((float)(l2 * (long)n3) / this.mPropagationSpeed * f);
    }

    public void setPropagationSpeed(float f) {
        if (f == 0.0f) throw new IllegalArgumentException("propagationSpeed may not be 0");
        this.mPropagationSpeed = f;
    }

    public void setSide(int n) {
        this.mSide = n;
    }
}

