package androidx.appcompat.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.core.graphics.drawable.WrappedDrawable;

class AppCompatProgressBarHelper {
   private static final int[] TINT_ATTRS = new int[]{16843067, 16843068};
   private Bitmap mSampleTile;
   private final ProgressBar mView;

   AppCompatProgressBarHelper(ProgressBar var1) {
      this.mView = var1;
   }

   private Shape getDrawableShape() {
      return new RoundRectShape(new float[]{5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F, 5.0F}, (RectF)null, (float[])null);
   }

   private Drawable tileify(Drawable var1, boolean var2) {
      if (var1 instanceof WrappedDrawable) {
         WrappedDrawable var3 = (WrappedDrawable)var1;
         Drawable var4 = var3.getWrappedDrawable();
         if (var4 != null) {
            var3.setWrappedDrawable(this.tileify(var4, var2));
         }
      } else {
         if (var1 instanceof LayerDrawable) {
            LayerDrawable var11 = (LayerDrawable)var1;
            int var5 = var11.getNumberOfLayers();
            Drawable[] var17 = new Drawable[var5];
            byte var6 = 0;

            int var7;
            for(var7 = 0; var7 < var5; ++var7) {
               int var8 = var11.getId(var7);
               Drawable var13 = var11.getDrawable(var7);
               if (var8 != 16908301 && var8 != 16908303) {
                  var2 = false;
               } else {
                  var2 = true;
               }

               var17[var7] = this.tileify(var13, var2);
            }

            LayerDrawable var14 = new LayerDrawable(var17);

            for(var7 = var6; var7 < var5; ++var7) {
               var14.setId(var7, var11.getId(var7));
            }

            return var14;
         }

         if (var1 instanceof BitmapDrawable) {
            BitmapDrawable var9 = (BitmapDrawable)var1;
            Bitmap var15 = var9.getBitmap();
            if (this.mSampleTile == null) {
               this.mSampleTile = var15;
            }

            ShapeDrawable var12 = new ShapeDrawable(this.getDrawableShape());
            BitmapShader var16 = new BitmapShader(var15, TileMode.REPEAT, TileMode.CLAMP);
            var12.getPaint().setShader(var16);
            var12.getPaint().setColorFilter(var9.getPaint().getColorFilter());
            Object var10 = var12;
            if (var2) {
               var10 = new ClipDrawable(var12, 3, 1);
            }

            return (Drawable)var10;
         }
      }

      return var1;
   }

   private Drawable tileifyIndeterminate(Drawable var1) {
      Object var2 = var1;
      if (var1 instanceof AnimationDrawable) {
         AnimationDrawable var6 = (AnimationDrawable)var1;
         int var3 = var6.getNumberOfFrames();
         var2 = new AnimationDrawable();
         ((AnimationDrawable)var2).setOneShot(var6.isOneShot());

         for(int var4 = 0; var4 < var3; ++var4) {
            Drawable var5 = this.tileify(var6.getFrame(var4), true);
            var5.setLevel(10000);
            ((AnimationDrawable)var2).addFrame(var5, var6.getDuration(var4));
         }

         ((AnimationDrawable)var2).setLevel(10000);
      }

      return (Drawable)var2;
   }

   Bitmap getSampleTile() {
      return this.mSampleTile;
   }

   void loadFromAttributes(AttributeSet var1, int var2) {
      TintTypedArray var4 = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), var1, TINT_ATTRS, var2, 0);
      Drawable var3 = var4.getDrawableIfKnown(0);
      if (var3 != null) {
         this.mView.setIndeterminateDrawable(this.tileifyIndeterminate(var3));
      }

      var3 = var4.getDrawableIfKnown(1);
      if (var3 != null) {
         this.mView.setProgressDrawable(this.tileify(var3, false));
      }

      var4.recycle();
   }
}