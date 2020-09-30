package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewParent;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.R;

public class MotionTelltales extends MockView {
   private static final String TAG = "MotionTelltales";
   Matrix mInvertMatrix = new Matrix();
   MotionLayout mMotionLayout;
   private Paint mPaintTelltales = new Paint();
   int mTailColor = -65281;
   float mTailScale = 0.25F;
   int mVelocityMode = 0;
   float[] velocity = new float[2];

   public MotionTelltales(Context var1) {
      super(var1);
      this.init(var1, (AttributeSet)null);
   }

   public MotionTelltales(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.init(var1, var2);
   }

   public MotionTelltales(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.init(var1, var2);
   }

   private void init(Context var1, AttributeSet var2) {
      if (var2 != null) {
         TypedArray var6 = var1.obtainStyledAttributes(var2, R.styleable.MotionTelltales);
         int var3 = var6.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var6.getIndex(var4);
            if (var5 == R.styleable.MotionTelltales_telltales_tailColor) {
               this.mTailColor = var6.getColor(var5, this.mTailColor);
            } else if (var5 == R.styleable.MotionTelltales_telltales_velocityMode) {
               this.mVelocityMode = var6.getInt(var5, this.mVelocityMode);
            } else if (var5 == R.styleable.MotionTelltales_telltales_tailScale) {
               this.mTailScale = var6.getFloat(var5, this.mTailScale);
            }
         }
      }

      this.mPaintTelltales.setColor(this.mTailColor);
      this.mPaintTelltales.setStrokeWidth(5.0F);
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
   }

   public void onDraw(Canvas var1) {
      super.onDraw(var1);
      this.getMatrix().invert(this.mInvertMatrix);
      if (this.mMotionLayout == null) {
         ViewParent var14 = this.getParent();
         if (var14 instanceof MotionLayout) {
            this.mMotionLayout = (MotionLayout)var14;
         }

      } else {
         int var2 = this.getWidth();
         int var3 = this.getHeight();
         float[] var4 = new float[]{0.1F, 0.25F, 0.5F, 0.75F, 0.9F};

         for(int var5 = 0; var5 < 5; ++var5) {
            float var6 = var4[var5];

            for(int var7 = 0; var7 < 5; ++var7) {
               float var8 = var4[var7];
               this.mMotionLayout.getViewVelocity(this, var8, var6, this.velocity, this.mVelocityMode);
               this.mInvertMatrix.mapVectors(this.velocity);
               float var9 = (float)var2 * var8;
               float var10 = (float)var3 * var6;
               float[] var11 = this.velocity;
               float var12 = var11[0];
               var8 = this.mTailScale;
               float var13 = var11[1];
               this.mInvertMatrix.mapVectors(var11);
               var1.drawLine(var9, var10, var9 - var12 * var8, var10 - var13 * var8, this.mPaintTelltales);
            }
         }

      }
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      super.onLayout(var1, var2, var3, var4, var5);
      this.postInvalidate();
   }

   public void setText(CharSequence var1) {
      this.mText = var1.toString();
      this.requestLayout();
   }
}
